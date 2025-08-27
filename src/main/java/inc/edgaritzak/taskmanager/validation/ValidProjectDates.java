package inc.edgaritzak.taskmanager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ProjectDateValidator.class) // clase que hace la lógica
@Target({ElementType.TYPE}) // se aplica sobre clases, no campos
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProjectDates {

    String message() default "Invalid project date range";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
