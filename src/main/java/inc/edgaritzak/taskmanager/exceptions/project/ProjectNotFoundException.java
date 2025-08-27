package inc.edgaritzak.taskmanager.exceptions.project;

public class ProjectNotFoundException extends RuntimeException{

    public ProjectNotFoundException(Long id){
        super("Project with id:'"+ id +"' not found");
    }
}
