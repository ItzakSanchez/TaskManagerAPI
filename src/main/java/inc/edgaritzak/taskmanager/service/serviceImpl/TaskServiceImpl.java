package inc.edgaritzak.taskmanager.service.serviceImpl;

import inc.edgaritzak.taskmanager.config.TaskConfig;
import inc.edgaritzak.taskmanager.dto.task.AbstractTaskDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskCreateDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskResponseDTO;
import inc.edgaritzak.taskmanager.entity.Project;
import inc.edgaritzak.taskmanager.entity.Task;
import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;
import inc.edgaritzak.taskmanager.exceptions.project.ProjectNotFoundException;
import inc.edgaritzak.taskmanager.exceptions.task.TaskNotFoundException;
import inc.edgaritzak.taskmanager.exceptions.task.TaskUniqueNameViolationException;
import inc.edgaritzak.taskmanager.mapper.TaskMapper;
import inc.edgaritzak.taskmanager.repository.ProjectRepository;
import inc.edgaritzak.taskmanager.repository.TaskRepository;
import inc.edgaritzak.taskmanager.service.TaskService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final TaskConfig taskConfig;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectRepository projectRepository, TaskConfig taskConfig){
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
        this. taskConfig = taskConfig;
    }

    @Override
    public TaskResponseDTO getTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        return taskMapper.toResponseDTO(optionalTask.orElseThrow(() -> new TaskNotFoundException(id)));
    }

    @Transactional
    @Override
    public TaskResponseDTO saveTask(Long projectId, TaskCreateDTO task) {
        Task newTask = taskMapper.toEntity(task);
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        newTask.setProject(project);



        if (!isNameUnique(task.getName(), projectId)){
            throw new TaskUniqueNameViolationException(task.getName(), project.getName());
        }

        try{
            return taskMapper.toResponseDTO(taskRepository.save(newTask));
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("A database restriction generate an error while saving the task: "+ e.getMessage(), e);
        } catch (RuntimeException e){
            throw new RuntimeException("An error occurred while retrieving the list of Projects: " +e.getMessage(), e);
        }
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskResponseDTO> getAllTasksByProjectId(Long projectId) {
        if(projectRepository.findById(projectId).isEmpty()){
            throw new ProjectNotFoundException(projectId);
        }

        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return tasks
                .stream()
                .map(taskMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TaskResponseDTO updateTask(Long id, TaskCreateDTO taskDto) {
        //VALIDATE TASK EXIST
        Optional<Task> optionalTask = taskRepository.findById(id);
        Task originalTask = optionalTask.orElseThrow( () -> new TaskNotFoundException(id));
        //VALIDATE UNIQUE TASK NAME IF TASK NAME CHANGED AND IS NOT UNIQUE THROW AN EXCEPTION
        Project project = originalTask.getProject();
        if (!originalTask.getName().equals(taskDto.getName()) && !isNameUnique(taskDto.getName(), project.getId())){
            throw new TaskUniqueNameViolationException(taskDto.getName(), project.getName());
        }

        //UPDATE TASK
        originalTask.setName(taskDto.getName());
        originalTask.setDescription(taskDto.getDescription());
        originalTask.setState(taskDto.getState() == null ? State.valueOf(taskConfig.getDefaultState()) : taskDto.getState());
        originalTask.setPriority(taskDto.getPriority() == null ? Priority.valueOf(taskConfig.getDefaultPriority()) : taskDto.getPriority());

        try{
            return taskMapper.toResponseDTO(taskRepository.save(originalTask));
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("A database restriction generate an error while saving the task: "+ e.getMessage(), e);
        } catch (RuntimeException e){
            throw new RuntimeException("An error occurred while saving the task: " +e.getMessage(), e);
        }
    }

    @Override
    public void deleteTaskById(Long id) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Task task = optionalTask.orElseThrow(()-> new TaskNotFoundException(id));

        try{
            taskRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("A database restriction generate an error while saving the task: "+ e.getMessage(), e);
        } catch (RuntimeException e){
            throw new RuntimeException("An error occurred while saving the task: " +e.getMessage(), e);
        }
    }

    //Utility
    private boolean isNameUnique(String taskName, Long projectId){
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        for (Task task : tasks) {
            if (task.getName().equals(taskName)) {
                return false;
            }
        }
        return true;
    }
}
