package inc.edgaritzak.taskmanager.service;

import inc.edgaritzak.taskmanager.dto.project.ProjectCreateDTO;
import inc.edgaritzak.taskmanager.dto.project.ProjectResponseDTO;
import inc.edgaritzak.taskmanager.dto.project.ProjectUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    ProjectResponseDTO getProjectById (Long id);
    ProjectResponseDTO saveProject (ProjectCreateDTO project);
    List<ProjectResponseDTO> getAllProjects ();
    ProjectResponseDTO updateProject(Long id, ProjectUpdateDTO project);
    void deleteProjectById(Long id);
}
