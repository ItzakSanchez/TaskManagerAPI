package inc.edgaritzak.taskmanager.dto.project;

import inc.edgaritzak.taskmanager.dto.task.TaskResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class ProjectResponseDTO {

    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TaskResponseDTO> tasks;

    public ProjectResponseDTO() {}
    public ProjectResponseDTO(Long id, String name, String description, LocalDate startDate, LocalDate endDate, List<TaskResponseDTO> tasks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
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
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setTasks(List<TaskResponseDTO> tasks) {
        this.tasks = tasks;
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
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public List<TaskResponseDTO> getTasks() {
        return tasks;
    }
}

