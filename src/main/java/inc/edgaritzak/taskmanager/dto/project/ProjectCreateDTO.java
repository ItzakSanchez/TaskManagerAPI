package inc.edgaritzak.taskmanager.dto.project;

import inc.edgaritzak.taskmanager.dto.task.TaskCreateDTO;
import inc.edgaritzak.taskmanager.validation.ValidProjectDates;
import inc.edgaritzak.taskmanager.validation.ValidTaskNames;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.List;

@ValidTaskNames
@ValidProjectDates
public class ProjectCreateDTO extends AbstractProjectDTO {

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
    private List<TaskCreateDTO> tasks;

    public ProjectCreateDTO() {}
    public ProjectCreateDTO(String name, String description, LocalDate startDate, LocalDate endDate, List<TaskCreateDTO> tasks) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tasks = tasks;
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
    public List<TaskCreateDTO> getTasks() {
        return tasks;
    }

}
