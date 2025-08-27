package inc.edgaritzak.taskmanager.controller;

import inc.edgaritzak.taskmanager.dto.project.ProjectCreateDTO;
import inc.edgaritzak.taskmanager.dto.project.ProjectResponseDTO;
import inc.edgaritzak.taskmanager.dto.project.ProjectUpdateDTO;
import inc.edgaritzak.taskmanager.service.serviceImpl.ProjectServiceImpl;
import inc.edgaritzak.taskmanager.service.serviceImpl.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectServiceImpl projectServiceImpl;
    private final TaskServiceImpl taskServiceImpl;

    @Autowired
    public ProjectController(ProjectServiceImpl projectServiceImpl, TaskServiceImpl taskServiceImpl){
        this.projectServiceImpl = projectServiceImpl;
        this.taskServiceImpl = taskServiceImpl;
    }

    @PostMapping("")
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectCreateDTO project){
        ProjectResponseDTO projectResponseDTO = projectServiceImpl.saveProject(project);
        return new ResponseEntity<>(projectResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable("id") Long id){
        ProjectResponseDTO projectResponseDTO = projectServiceImpl.getProjectById(id);
        return new ResponseEntity<>(projectResponseDTO, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllProjects(){
        return new ResponseEntity<>(projectServiceImpl.getAllProjects(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable("id") Long id, @Valid @RequestBody ProjectUpdateDTO project){
        return new ResponseEntity<>(projectServiceImpl.updateProject(id, project), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") Long id){
        projectServiceImpl.deleteProjectById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
