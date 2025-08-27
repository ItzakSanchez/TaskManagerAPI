package inc.edgaritzak.taskmanager.service.serviceImpl;

import inc.edgaritzak.taskmanager.config.ProjectConfig;
import inc.edgaritzak.taskmanager.config.TaskConfig;
import inc.edgaritzak.taskmanager.dto.project.ProjectCreateDTO;
import inc.edgaritzak.taskmanager.dto.project.AbstractProjectDTO;
import inc.edgaritzak.taskmanager.dto.project.ProjectResponseDTO;
import inc.edgaritzak.taskmanager.dto.project.ProjectUpdateDTO;
import inc.edgaritzak.taskmanager.dto.task.AbstractTaskDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskBulkUpdateDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskCreateDTO;
import inc.edgaritzak.taskmanager.entity.Task;
import inc.edgaritzak.taskmanager.entity.Project;
import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;
import inc.edgaritzak.taskmanager.exceptions.task.ProjectCreationTaskUniqueNameViolationException;
import inc.edgaritzak.taskmanager.exceptions.project.*;
import inc.edgaritzak.taskmanager.exceptions.task.TaskDoNotBelongToProjectException;
import inc.edgaritzak.taskmanager.exceptions.task.TaskNotFoundException;
import inc.edgaritzak.taskmanager.exceptions.task.TaskUniqueNameViolationException;
import inc.edgaritzak.taskmanager.mapper.ProjectMapper;
import inc.edgaritzak.taskmanager.mapper.TaskMapper;
import inc.edgaritzak.taskmanager.repository.ProjectRepository;
import inc.edgaritzak.taskmanager.repository.TaskRepository;
import inc.edgaritzak.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final ProjectMapper projectMapper;
    private final TaskMapper taskMapper;
    private final TaskConfig taskConfig;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, TaskRepository taskRepository, ProjectMapper projectMapper, TaskMapper taskMapper, TaskConfig taskConfig){
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.projectMapper = projectMapper;
        this.taskMapper = taskMapper;
        this.taskConfig = taskConfig;
    }

    @Override
    public ProjectResponseDTO getProjectById(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        return projectMapper.toResponseDTO(optionalProject.orElseThrow( ()-> new ProjectNotFoundException(id)));
    }

    @Override
    @Transactional
    public ProjectResponseDTO saveProject(ProjectCreateDTO projectCreateDTO) {

        //Validate if name already exists
        validateProjectName(projectCreateDTO);
        //Set

        Project newProject = projectMapper.toEntity(projectCreateDTO);

        try{
            return projectMapper.toResponseDTO(projectRepository.save(newProject));
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("A database restriction generate an error while saving Project: "+ e.getMessage(), e);
        }
        catch (RuntimeException e){
            throw new RuntimeException("An error occurred while saving Project: "+ e.getMessage(), e);
        }
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {
        try{
            List<Project> projectList = projectRepository.findAll();
            return projectList.stream()
                    .map(projectMapper::toResponseDTO)
                    .collect(Collectors.toList());
        } catch (RuntimeException e){
            throw new RuntimeException("An error occurred while retrieving the list of Projects: " +e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectUpdateDTO projectUpdateDTO) {

        Project original = projectRepository.findById(id).orElseThrow( ()-> new ProjectNotFoundException(id));

        //Validate if the project exists and project name is available
        validateProjectNameUpdate(id, projectUpdateDTO);

        //Validate if task names (update/new) do not cause conflict (unique names)
        validateTaskNamesForUpdateProject(original, projectUpdateDTO);

        //UPDATE PROJECT
        original.setName(projectUpdateDTO.getName());
        original.setDescription(projectUpdateDTO.getDescription());
        original.setStartDate(projectUpdateDTO.getStartDate());
        original.setEndDate(projectUpdateDTO.getEndDate());

        //VALIDATE LIST OF TASKS
        validateTaskNamesForUpdateProject(original, projectUpdateDTO);

        //UPDATE TASKS
        if (projectUpdateDTO.getTasksToUpdate() != null && !projectUpdateDTO.getTasksToUpdate().isEmpty()){
            for (TaskBulkUpdateDTO taskDTO: projectUpdateDTO.getTasksToUpdate()){
                Task task = taskRepository.findById(taskDTO.getId()).orElseThrow(() -> new TaskNotFoundException(taskDTO.getId()));
                task.setName(taskDTO.getName());
                task.setDescription(taskDTO.getDescription());
                task.setPriority(taskDTO.getPriority() != null ? task.getPriority() : Priority.valueOf(taskConfig.getDefaultPriority()));
                task.setState(taskDTO.getState() != null ? task.getState() : State.valueOf(taskConfig.getDefaultState()));
            }
        }

        //MAP NEW TASKS. FROM DTO -> TO ENTITY
        if(projectUpdateDTO.getTasksToAdd() != null && !projectUpdateDTO.getTasksToAdd().isEmpty()){
            List<Task> newTasks = projectUpdateDTO.getTasksToAdd()
                    .stream()
                    .map(taskMapper::toEntity)
                    .peek(task -> task.setProject(original))
                    .toList();

            List<Task> allTasks = original.getTasks();
            allTasks.addAll(newTasks);  //ADD NEW TASKS TO LIST
            original.setTasks(allTasks); //SET LIST OF ALL TASKS
        }
        //UPDATE
        try{
            return projectMapper.toResponseDTO(projectRepository.save(original));
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("A database restriction generate an error while updating the Project: "+ e.getMessage(), e);
        }
        catch (RuntimeException e){
            throw new RuntimeException("An error occurred while updating the Project: "+ e.getMessage(), e);
        }
    }

    @Override
    public void deleteProjectById(Long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (optionalProject.isEmpty()){
            throw new ProjectNotFoundException(id);
        }

        try{
            projectRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("A database restriction generate an error while deleting the Project: "+ e.getMessage(), e);
        }
        catch (RuntimeException e){
            throw new RuntimeException("An error occurred while deleting the Project: "+ e.getMessage(), e);
        }
    }


    // UTILITY FUNCTIONS
    // UTILITY FUNCTIONS
    // UTILITY FUNCTIONS
    private void validateProjectName(ProjectCreateDTO projectCreateDTO){
        if (projectRepository.existsByName(projectCreateDTO.getName())){
            throw new ProjectUniqueNameViolationException(projectCreateDTO.getName());
        }
    }

    private void validateProjectNameUpdate(Long id, ProjectUpdateDTO projectUpdateDTO){
        Project original = projectRepository.findById(id).orElseThrow(() -> new ProjectNotFoundException(id));

        if(!original.getName().equals(projectUpdateDTO.getName()) && projectRepository.existsByName(projectUpdateDTO.getName())){
                throw new ProjectUniqueNameViolationException(projectUpdateDTO.getName());
        }
    }

    private void validateTaskNamesForUpdateProject(Project originalProject, ProjectUpdateDTO projectUpdateDTO){
        //Create a map of tasks (id:name) from db
        Map<Long, String> tasksMap = originalProject.getTasks()
                .stream()
                .collect(Collectors.toMap(Task::getId, Task::getName));

        //Update the map with new names
        if (projectUpdateDTO.getTasksToUpdate() != null){
            for(TaskBulkUpdateDTO task: projectUpdateDTO.getTasksToUpdate()){
                if (tasksMap.containsKey(task.getId())){
                    tasksMap.put(task.getId(), task.getName());
                } else {
                    throw new TaskDoNotBelongToProjectException("Task with id: "+task.getId()+" does not belong to project with id "+ originalProject.getId() +". Cannot modify task.");
                }
            }
        }

        List<String> globalNames = new ArrayList<>(tasksMap.values().stream().toList());
        //add the addTasks list to global list
        if (projectUpdateDTO.getTasksToAdd() != null) {
            globalNames.addAll(projectUpdateDTO.getTasksToAdd().stream().map(task->task.getName()).toList());
        }

        Set<String> unique = new HashSet<>();
        List<String> duplicates = globalNames.stream()
                .filter(name -> !unique.add(name))
                .toList();
        if (!duplicates.isEmpty()){
            throw new TaskUniqueNameViolationException("["+String.join(", ",duplicates)+"]",originalProject.getName());
        }
    }
}
