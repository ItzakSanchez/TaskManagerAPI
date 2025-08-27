package inc.edgaritzak.taskmanager.exceptions.task;

import java.util.Arrays;
import java.util.List;

public class ProjectCreationTaskUniqueNameViolationException extends RuntimeException {
    public ProjectCreationTaskUniqueNameViolationException(List<String> taskNames, String projectName) {
        super("Cannot create project with name '"+projectName+"'. Tasks names are not unique. Repeated task names:'"+ Arrays.toString(taskNames.toArray()) +"'.");
    }
}
