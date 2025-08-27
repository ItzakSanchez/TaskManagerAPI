package inc.edgaritzak.taskmanager.validation;

import inc.edgaritzak.taskmanager.dto.project.ProjectCreateDTO;
import inc.edgaritzak.taskmanager.exceptions.task.ProjectCreationTaskUniqueNameViolationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class NewProjectTaskNameValidator implements ConstraintValidator<ValidTaskNames, ProjectCreateDTO> {
    @Override
    public boolean isValid(ProjectCreateDTO dto, ConstraintValidatorContext context) {
        if (dto.getTasks() == null || dto.getTasks().isEmpty()) return true;

        HashSet<String> seen = new HashSet<>();
        HashSet<String> duplicates = new HashSet<>();
        List<String> names = dto.getTasks()
                .stream()
                .filter(task -> task != null && task.getName() != null)
                .map(task -> task.getName())
                .toList();

        for (String name: names){
            if(!seen.add(name.toLowerCase())){
                duplicates.add(name.toLowerCase());
            }
        }

        if(!duplicates.isEmpty()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Duplicated task names are not allowed. Duplicated task names:" +String.join(", ",duplicates))
                    .addPropertyNode("tasks").addConstraintViolation();
            return false;
        }

        return true;
    }
}
