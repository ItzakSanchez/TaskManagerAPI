package inc.edgaritzak.taskmanager.mapper;

import inc.edgaritzak.taskmanager.config.TaskConfig;
import inc.edgaritzak.taskmanager.dto.task.TaskCreateDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskResponseDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskBulkUpdateDTO;
import inc.edgaritzak.taskmanager.entity.Task;
import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    private final TaskConfig taskConfig;

    @Autowired
    public TaskMapper(TaskConfig taskConfig){
        this.taskConfig = taskConfig;
    }

    public Task toEntity(TaskCreateDTO taskCreateDTO){
        Task task = new Task();

        task.setName(taskCreateDTO.getName());
        task.setDescription(taskCreateDTO.getDescription());
        task.setPriority(task.getPriority() == null ? Priority.valueOf(taskConfig.getDefaultPriority()) : task.getPriority());
        task.setState(task.getState() == null ? State.valueOf(taskConfig.getDefaultState()) : task.getState());

        return  task;
    }

    public void updateEntity(Task task, TaskBulkUpdateDTO taskDTO){

    }

    public TaskResponseDTO toResponseDTO(Task task){

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setName(task.getName());
        taskResponseDTO.setDescription(task.getDescription());
        taskResponseDTO.setPriority(task.getPriority());
        taskResponseDTO.setState(task.getState());
        taskResponseDTO.setProjectId(task.getProject().getId());

        return taskResponseDTO;
    }
}
