package inc.edgaritzak.taskmanager.validation;

import inc.edgaritzak.taskmanager.dto.project.ProjectUpdateDTO;
import inc.edgaritzak.taskmanager.dto.task.AbstractTaskDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.Set;

public class ValidUpdateTasKNamesValidator implements ConstraintValidator<ValidUpdateTaskNames, ProjectUpdateDTO> {
    @Override
    public boolean isValid(ProjectUpdateDTO dto, ConstraintValidatorContext context) {

        if (dto.getTasksToAdd() == null || dto.getTasksToAdd().isEmpty()
                && dto.getTasksToUpdate() == null || dto.getTasksToUpdate().isEmpty()) return true;


        context.disableDefaultConstraintViolation();
        boolean valid = true;
        Set<String> globalNames = new HashSet<>();

        //New tasks
        if (!dto.getTasksToAdd().isEmpty()) {
            Set<String> localNames = new HashSet<>();
            for (int i = 0; i < dto.getTasksToAdd().size(); i++) {

                if (!localNames.add(dto.getTasksToAdd().get(i).getName())){
                    context.buildConstraintViolationWithTemplate("Duplicate name in tasksToAdd: ")
                            .addPropertyNode("tasksToAdd").inIterable().atIndex(i);
                    valid = false;
                }

                if (!globalNames.add(dto.getTasksToAdd().get(i).getName())){
                    context.buildConstraintViolationWithTemplate("Duplicate name across lists: ")
                            .addPropertyNode("tasksToAdd").inIterable().atIndex(i);
                    valid = false;
                }
            }
        }

        //Update tasks
        if (!dto.getTasksToUpdate().isEmpty()) {
            Set<String> localNames = new HashSet<>();
            for (int i = 0; i < dto.getTasksToUpdate().size(); i++) {

                if (!localNames.add(dto.getTasksToUpdate().get(i).getName())){
                    context.buildConstraintViolationWithTemplate("Duplicate name in tasksToUpdate: ")
                            .addPropertyNode("tasksToUpdate").inIterable().atIndex(i);
                    valid = false;
                }

                if (!globalNames.add(dto.getTasksToUpdate().get(i).getName())){
                    context.buildConstraintViolationWithTemplate("Duplicate name across lists: ")
                            .addPropertyNode("tasksToUpdate").inIterable().atIndex(i);
                    valid = false;
                }
            }
        }

        return true;
    }
}
