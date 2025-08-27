package inc.edgaritzak.taskmanager.exceptions.task;

public class TaskDoNotBelongToProjectException extends  RuntimeException{
    public TaskDoNotBelongToProjectException(String msg){
        super(msg);
    }
}
