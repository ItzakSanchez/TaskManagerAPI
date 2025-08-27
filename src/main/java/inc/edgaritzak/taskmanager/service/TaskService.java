package inc.edgaritzak.taskmanager.service;

import inc.edgaritzak.taskmanager.dto.task.TaskCreateDTO;
import inc.edgaritzak.taskmanager.dto.task.TaskResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    TaskResponseDTO getTaskById (Long id);
    TaskResponseDTO saveTask (Long ProjectId, TaskCreateDTO task);List<TaskResponseDTO> getAllTasks ();
    List<TaskResponseDTO> getAllTasksByProjectId (Long projectId);
    TaskResponseDTO updateTask(Long id, TaskCreateDTO project);
    void deleteTaskById(Long id);
}
