package inc.edgaritzak.taskmanager.exceptions.project;

import java.time.LocalDate;

public class InvalidProjectEndDateException extends ProjectDateException {
    public InvalidProjectEndDateException(LocalDate endDate, LocalDate today)
    {
        super("The end date ("+endDate+") cannot be before today ("+today+").");
    }
}
