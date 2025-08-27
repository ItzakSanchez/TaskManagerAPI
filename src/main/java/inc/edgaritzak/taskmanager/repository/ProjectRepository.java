package inc.edgaritzak.taskmanager.repository;

import inc.edgaritzak.taskmanager.entity.Project;
import inc.edgaritzak.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p.name from Project p")
    public List<String> findAllNames();
    public boolean existsByName(String name);

}
