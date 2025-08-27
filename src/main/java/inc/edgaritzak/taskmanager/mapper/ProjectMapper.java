package inc.edgaritzak.taskmanager.mapper;

import inc.edgaritzak.taskmanager.dto.project.ProjectCreateDTO;
import inc.edgaritzak.taskmanager.dto.project.ProjectResponseDTO;
import inc.edgaritzak.taskmanager.entity.Project;
import inc.edgaritzak.taskmanager.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectMapper {

    private final TaskMapper taskMapper;

    @Autowired
    public ProjectMapper(TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }

    public ProjectResponseDTO toResponseDTO(Project project){
        if(project == null){
            throw new RuntimeException("Cannot parse project to projectDTO because project is null.");
        }

        ProjectResponseDTO projectDTO = new ProjectResponseDTO();

        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setStartDate(project.getStartDate());
        projectDTO.setEndDate(project.getEndDate());
        projectDTO.setTasks(project.getTasks()
                .stream()
                .map(taskMapper::toResponseDTO)
                .toList());

        return  projectDTO;
    }

    public Project toEntity(ProjectCreateDTO projectCreateDTO){
        Project project = new Project();

        project.setName(projectCreateDTO.getName());
        project.setDescription(projectCreateDTO.getDescription());
        project.setStartDate(projectCreateDTO.getStartDate());
        project.setEndDate(projectCreateDTO.getEndDate());

        List<Task> taskList = projectCreateDTO.getTasks() == null ? new ArrayList<Task>() :
            projectCreateDTO.getTasks()
                    .stream()
                    .map(taskMapper::toEntity)
                    .peek(task -> task.setProject(project))
                    .toList();
        project.setTasks(taskList);

        return project;
    }
}
