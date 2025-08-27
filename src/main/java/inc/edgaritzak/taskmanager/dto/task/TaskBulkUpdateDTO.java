package inc.edgaritzak.taskmanager.dto.task;

import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


//ONLY USED FOR PROJECT UPDATE METHOD
//IF YOU WANT TO UPDATE A SINGLE TASK USE TASK CREATE DTO
public class TaskBulkUpdateDTO extends AbstractTaskDTO{
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private State state;
    private Priority priority;


    public TaskBulkUpdateDTO() {}
    public TaskBulkUpdateDTO(Long id, String name, String description, State state, Priority priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public State getState() {
        return state;
    }
    public Priority getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public void setState(State state) {
        this.state = state;
    }
    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
