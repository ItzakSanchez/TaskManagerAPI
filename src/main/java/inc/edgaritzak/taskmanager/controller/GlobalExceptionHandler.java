package inc.edgaritzak.taskmanager.controller;

import inc.edgaritzak.taskmanager.exceptions.project.ProjectDateException;
import inc.edgaritzak.taskmanager.exceptions.project.ProjectNotFoundException;
import inc.edgaritzak.taskmanager.exceptions.project.ProjectUniqueNameViolationException;
import inc.edgaritzak.taskmanager.exceptions.task.TaskDoNotBelongToProjectException;
import inc.edgaritzak.taskmanager.exceptions.task.TaskNotFoundException;
import inc.edgaritzak.taskmanager.exceptions.task.TaskUniqueNameViolationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "inc.edgaritzak")
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<ProblemDetail> projectNotFound(ProjectNotFoundException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Resource not found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create("https://example.com/errors/not-found"));

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ProblemDetail> taskNotFound(TaskNotFoundException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Resource not found");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create("https://example.com/errors/not-found"));

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectUniqueNameViolationException.class)
    public ResponseEntity<ProblemDetail> projectUniqueName(ProjectUniqueNameViolationException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Name conflict");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create("https://example.com/errors/bad-request/unique-project-name"));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskUniqueNameViolationException.class)
    public ResponseEntity<ProblemDetail> taskUniqueName(TaskUniqueNameViolationException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Name conflict");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create("https://example.com/errors/bad-request/unique-task-name"));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProjectDateException.class)
    public ResponseEntity<ProblemDetail> projectDateExceptions(ProjectDateException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Invalid dates");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create("https://example.com/errors/bad-request/invalid-project-dates"));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(TaskDoNotBelongToProjectException.class)
    public ResponseEntity<ProblemDetail> taskDoNotBelongToProjectException(TaskDoNotBelongToProjectException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Invalid task id");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create("https://example.com/errors/bad-request/invalid-arguments"));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> requestDtoValidationException(MethodArgumentNotValidException e, HttpServletRequest request){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Invalid input");

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String detail = fieldErrors.stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        problemDetail.setDetail(detail);
        problemDetail.setInstance(URI.create(request.getRequestURI()));

        problemDetail.setType(URI.create("https://example.com/errors/bad-request/invalid-arguments"));

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ProblemDetail> runtimeException (RuntimeException e, HttpServletRequest request){

        System.out.println(Arrays.toString(e.getStackTrace()));
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(e.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setType(URI.create("https://example.com/errors/internal-server-error"));

        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}