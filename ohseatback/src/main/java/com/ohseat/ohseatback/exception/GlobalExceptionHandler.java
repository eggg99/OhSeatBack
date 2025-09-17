package com.ohseat.ohseatback.exception;

import com.ohseat.ohseatback.exception.business.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 사용자 요청 관련 예외 처리 */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex) {
        return buildErrorResponse("DUPLICATE_RESOURCE", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return buildErrorResponse("USER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPassword(InvalidPasswordException ex) {
        return buildErrorResponse("INVALID_PASSWORD", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        return buildErrorResponse("UNAUTHORIZED", ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    // 게시글 관련 예외
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFound(PostNotFoundException ex) {
        return buildErrorResponse("POST_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // 유효성 검사 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildErrorResponse("VALIDATION_FAILED", message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoChangesDetectedException.class)
    public ResponseEntity<ErrorResponse> handleNoChangesDetected(NoChangesDetectedException ex) {
        return buildErrorResponse("NO_CHANGES_DETECTED", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /** Spring Security 관련 예외 처리 */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        return buildErrorResponse("ACCESS_DENIED", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(AuthenticationException ex) {
        return buildErrorResponse("AUTHENTICATION_FAILED", "인증에 실패했습니다. 다시 로그인해주세요.", HttpStatus.UNAUTHORIZED);
    }

    /** 시스템 오류 예외 처리 */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNPE(NullPointerException ex) {
        ex.printStackTrace();
        return buildErrorResponse("NULL_POINTER", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /** 알 수 없는 예외 처리 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        ex.printStackTrace();
        return buildErrorResponse("UNKNOWN_ERROR", "알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 공통 응답 구조 메서드
    private ResponseEntity<ErrorResponse> buildErrorResponse(String error, String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorResponse(error, message));
    }

}
