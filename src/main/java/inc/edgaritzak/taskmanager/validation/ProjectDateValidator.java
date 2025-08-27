package inc.edgaritzak.taskmanager.validation;

import inc.edgaritzak.taskmanager.config.ProjectConfig;
import inc.edgaritzak.taskmanager.dto.project.AbstractProjectDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDate;

    public class ProjectDateValidator implements ConstraintValidator<ValidProjectDates, AbstractProjectDTO> {

    @Autowired
    private ProjectConfig projectConfig;

    @Override
    public boolean isValid(AbstractProjectDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.getStartDate() == null || dto.getEndDate() == null) {
            return true;
        }

        LocalDate start = dto.getStartDate();
        LocalDate end = dto.getEndDate();

        boolean valid = true;

        // 1. startDate debe ser antes que endDate
        if (end.isBefore(start)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("End date("+end+") must be after start date ("+start+")")
                    .addPropertyNode("endDate").addConstraintViolation();
            valid = false;
        }

        // 2. Duración no puede ser mayor al límite
        long duration = Duration.between(start.atStartOfDay(), end.atStartOfDay()).toDays();
        if (duration > projectConfig.getMaxDurationDays()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Project duration("+duration+" days) exceeds maximum of " + projectConfig.getMaxDurationDays() + " days")
                    .addPropertyNode("endDate").addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
