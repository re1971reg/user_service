package school.faang.user_service.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import school.faang.user_service.util.Utils;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionApiHandler {

    public static final String RUNTIME_ERROR = "Runtime error, see log";

    private final Utils utils;

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerConstraintViolationException(
        ConstraintViolationException e
    ) {
        final Map<String, String> detail = e.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                ConstraintViolation::getMessage
            ));
        String errorMessage = utils.format("Validation failed with {} errors",
            e.getConstraintViolations().size());
        return getErrorResponse("handlerConstraintViolationException", errorMessage, detail, e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final Map<String, String> detail = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                error -> Objects.requireNonNullElse(error.getDefaultMessage(), "")
            ));
        final String errorMessage = utils.format("Validation failed with {} errors",
            e.getBindingResult().getFieldErrors().size());
        return getErrorResponse("handlerMethodArgumentNotValidException", errorMessage, detail, e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handlerMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return getErrorResponse("handlerMethodArgumentTypeMismatchException", e);
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerUnrecognizedPropertyException(UnrecognizedPropertyException e) {
        return getErrorResponse("handleUnrecognizedPropertyException", e);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handlerRuntimeException(ForbiddenException e) {
        return getErrorResponse("handlerRuntimeException", e);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handlerRuntimeException(RuntimeException e) {
        return getErrorResponse("handlerRuntimeException", RUNTIME_ERROR, e);
    }

    private ErrorResponse getErrorResponse(String exceptionLabel, Exception e) {
        log.error("{}: {}", exceptionLabel, e.getMessage(), e);
        return new ErrorResponse(e.getMessage());
    }

    private ErrorResponse getErrorResponse(String exceptionLabel, String errorMessage, Exception e) {
        log.error("{}: {}", exceptionLabel, e.getMessage(), e);
        return new ErrorResponse(errorMessage);
    }

    private ErrorResponse getErrorResponse(
        String exceptionLabel,
        String errorMessage,
        Map<String, String> detail,
        Exception e
    ) {
        log.error("{}: {}", exceptionLabel, e.getMessage(), e);
        return new ErrorResponse(errorMessage, detail);
    }
}
