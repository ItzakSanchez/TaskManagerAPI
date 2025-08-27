package inc.edgaritzak.taskmanager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NewProjectTaskNameValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTaskNames {
    String message() default "Duplicate task names are not allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
