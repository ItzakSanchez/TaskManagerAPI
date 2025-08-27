package inc.edgaritzak.taskmanager.repository;

import inc.edgaritzak.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository <Task, Long> {
    public List<Task> findByProjectId(Long projectId);
    public boolean existsByName(String name);
}
