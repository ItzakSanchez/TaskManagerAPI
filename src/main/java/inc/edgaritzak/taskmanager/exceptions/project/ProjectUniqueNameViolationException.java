package inc.edgaritzak.taskmanager.exceptions.project;

public class ProjectUniqueNameViolationException extends RuntimeException {
    public ProjectUniqueNameViolationException(String projectName) {
        super("The project name '"+projectName+"' already exists.");
    }
}
