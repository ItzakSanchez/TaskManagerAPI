package inc.edgaritzak.taskmanager.entity;

import inc.edgaritzak.taskmanager.enums.Priority;
import inc.edgaritzak.taskmanager.enums.State;
import jakarta.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATE")
    private State state;

    @Column(name= "PRIORITY")
    private Priority priority;

    @ManyToOne()
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    //Constructor
    public Task() {}
    public Task(String name, String description, State state, Priority priority, Project project) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.priority = priority;
        this.project = project;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
}
