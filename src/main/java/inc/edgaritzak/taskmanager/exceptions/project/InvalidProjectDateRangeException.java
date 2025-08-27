package inc.edgaritzak.taskmanager.exceptions.project;

import java.time.LocalDate;

public class InvalidProjectDateRangeException extends ProjectDateException {
    public InvalidProjectDateRangeException(LocalDate startDate, LocalDate endDate)
    {
        super("Project's end date ("+endDate+") cannot be before start date("+startDate+")");
    }
}
