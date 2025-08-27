package inc.edgaritzak.taskmanager.controller;

import inc.edgaritzak.taskmanager.dto.task.TaskCreateDTO;
import inc.edgaritzak.taskmanager.service.serviceImpl.TaskServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {

    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService){
        this.taskService = taskService;
    }

    @PostMapping("project/{id}/task")
    public ResponseEntity<?> createTask (@PathVariable("id") Long id, @Valid @RequestBody TaskCreateDTO taskCreateDTO){
        return new ResponseEntity<>(taskService.saveTask(id, taskCreateDTO), HttpStatus.CREATED);
    }

    @GetMapping("project/{id}/task")
    public ResponseEntity<?> getAllTasksByProjectId(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskService.getAllTasksByProjectId(id), HttpStatus.OK);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTaskDetail(@PathVariable("id") Long id){
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @Valid @RequestBody TaskCreateDTO taskCreateDTO){
        return new ResponseEntity<>(taskService.updateTask(id, taskCreateDTO), HttpStatus.OK);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id){
        taskService.deleteTaskById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
