package ghojeong.common.exception;

import ghojeong.common.dto.response.ExceptionResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(CommonException.class)
    public ExceptionResponse<CommonException> handleException(
            CommonException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception
        );
    }

    @ExceptionHandler(UnavailableException.class)
    public ExceptionResponse<UnavailableException> handleException(
            UnavailableException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.SERVICE_UNAVAILABLE,
                exception
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ExceptionResponse<UnauthorizedException> handleException(
            UnauthorizedException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.UNAUTHORIZED,
                exception
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ExceptionResponse<ForbiddenException> handleException(
            ForbiddenException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.FORBIDDEN,
                exception
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ExceptionResponse<NotFoundException> handleException(
            NotFoundException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.NOT_FOUND,
                exception
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ExceptionResponse<BadRequestException> handleException(
            BadRequestException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionResponse<DataIntegrityViolationException> handleException(
            DataIntegrityViolationException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(SQLException.class)
    public ExceptionResponse<SQLException> handleException(
            SQLException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionResponse<IllegalArgumentException> handleException(
            IllegalArgumentException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse<MethodArgumentNotValidException> handleException(
            MethodArgumentNotValidException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ExceptionResponse<HttpMessageNotReadableException> handleException(
            HttpMessageNotReadableException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse<ConstraintViolationException> handleException(
            ConstraintViolationException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResponse<MissingServletRequestParameterException> handleException(
            MissingServletRequestParameterException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ExceptionResponse<DateTimeParseException> handleException(
            DateTimeParseException exception
    ) {
        return new ExceptionResponse<>(
                HttpStatus.BAD_REQUEST,
                exception
        );
    }
}
