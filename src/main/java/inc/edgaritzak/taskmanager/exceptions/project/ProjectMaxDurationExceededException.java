package inc.edgaritzak.taskmanager.exceptions.project;

public class ProjectMaxDurationExceededException extends ProjectDateException {
  public ProjectMaxDurationExceededException(int projectDaysDuration, int maxDuration) {
    super("The number of days of project duration ("+projectDaysDuration+" days) exceeds the max project duration ("+maxDuration+" days).");
  }
}
