package inc.edgaritzak.taskmanager.dto.task;

import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;

public class TaskResponseDTO {
    private Long id;
    private String name;
    private String description;
    private State state;
    private Priority priority;
    private Long projectId;

    public TaskResponseDTO() {}
    public TaskResponseDTO(Long id, String name, String description, State state, Priority priority, Long projectId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.projectId = projectId;
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
    public Long getProjectId() {
        return projectId;
    }

    public void setId(Long id) {
        this.id = id;
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
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
