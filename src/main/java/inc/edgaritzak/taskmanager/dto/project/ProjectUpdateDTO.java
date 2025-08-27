package inc.edgaritzak.taskmanager.dto.project;

import inc.edgaritzak.taskmanager.dto.task.TaskCreateDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskBulkUpdateDTO;
import inc.edgaritzak.taskmanager.validation.ValidProjectDates;
import inc.edgaritzak.taskmanager.validation.ValidUpdateTaskNames;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.List;

@ValidProjectDates
@ValidUpdateTaskNames
public class ProjectUpdateDTO extends AbstractProjectDTO {

    @NotBlank(message = "The name of the Project must not be empty")
    private String name;

    @NotBlank(message = "The name of the Project description must not be empty")
    private String description;

    @NotNull(message = "The name of the start date must not be null")
    private LocalDate startDate;

    @NotNull(message = "The name of the end date must not be null")
    @FutureOrPresent
    private LocalDate endDate;

    @Valid
    @UniqueElements(message = "Repeated tasks are not allowed")
    private List<TaskCreateDTO> tasksToAdd;

    @Valid
    @UniqueElements(message = "Repeated tasks are not allowed")
    private List<TaskBulkUpdateDTO> tasksToUpdate;

    public ProjectUpdateDTO() {}

    public ProjectUpdateDTO(String name, String description, LocalDate startDate, LocalDate endDate, List<TaskCreateDTO> tasksToAdd, List<TaskBulkUpdateDTO> tasksToUpdate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasksToAdd = tasksToAdd;
        this.tasksToUpdate = tasksToUpdate;
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
    public List<TaskCreateDTO> getTasksToAdd() {
        return tasksToAdd;
    }
    public List<TaskBulkUpdateDTO> getTasksToUpdate() {
        return tasksToUpdate;
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
    public void setTasksToAdd(List<TaskCreateDTO> tasksToAdd) {
        this.tasksToAdd = tasksToAdd;
    }
    public void setTasksToUpdate(List<TaskBulkUpdateDTO> tasksToUpdate) {
        this.tasksToUpdate = tasksToUpdate;
    }
}
