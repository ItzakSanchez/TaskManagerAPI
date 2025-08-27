package inc.edgaritzak.taskmanager.dto.project;

import java.time.LocalDate;

public abstract class AbstractProjectDTO {

    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

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
}
