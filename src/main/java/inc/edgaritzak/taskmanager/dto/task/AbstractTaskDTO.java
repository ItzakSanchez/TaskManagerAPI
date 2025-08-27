package inc.edgaritzak.taskmanager.dto.task;

import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;

public abstract class AbstractTaskDTO {
    private String name;
    private String description;
    private State state;
    private Priority priority;

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

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setName(String name) {
        this.name = name;
    }
}
