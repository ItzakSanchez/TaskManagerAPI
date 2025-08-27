package inc.edgaritzak.taskmanager.dto.task;

import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;
import jakarta.validation.constraints.NotBlank;

public class TaskUpdateDTO extends AbstractTaskDTO{

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private State state;
    private Priority priority;

    public TaskUpdateDTO() {}
    public TaskUpdateDTO(String name, String description, State state, Priority priority) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.priority = priority;
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

    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}