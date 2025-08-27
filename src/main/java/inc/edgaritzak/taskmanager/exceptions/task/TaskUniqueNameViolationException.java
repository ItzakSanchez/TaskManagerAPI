package inc.edgaritzak.taskmanager.exceptions.task;

public class TaskUniqueNameViolationException extends RuntimeException {
    public TaskUniqueNameViolationException(String taskName, String projectName) {
        super("The task name '"+taskName+"' already exists in the project '"+projectName+"'.");
    }
}
