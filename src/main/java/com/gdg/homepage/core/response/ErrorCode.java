package com.gdg.homepage.core.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // ========================
    // 400 Bad Request
    // ========================
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_APPROVE_MEMBER(400_001, HttpStatus.BAD_REQUEST, "멤버는 다른 멤버를 승인할 수 없습니다."),
    NOT_APPROVE_YET(400_002, HttpStatus.BAD_REQUEST, "아직 신청서가 승인되지 않아서 역할을 수정할 수 업습니다."),
    APPROVE_ORGANIZER(400_003, HttpStatus.BAD_REQUEST, "ORGANIZER 만 권한을 수정할 수 있습니다."),
    NOT_APPROVE_ORGANIZER(400_004, HttpStatus.BAD_REQUEST, "ORGANIZER로 승급할 수 없습니다."),
    NOT_PERIOD(400_010, HttpStatus.BAD_REQUEST, "가입시간이 아닙니다."),
    BAD_PARAMETER(400_999, HttpStatus.BAD_REQUEST, "요청 파라미터에 문제가 존재합니다."),
    PASSWORD_ERROR(400_011, HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다."),

    // ========================
    // 401 UNAUTHORIZED
    // ========================
    UNAUTHORIZED(401_000, HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    INVALID_TOKEN(401_001, HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다"),
    TOKEN_EXPIRED(401_000, HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_INVALID(401_001, HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_NOT_FOUND(401_002, HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    TOKEN_UNSUPPORTED(401_003, HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰 형식입니다."),
    INVALID_CREDENTIALS(401_004, HttpStatus.UNAUTHORIZED, "인증 정보가 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN(401_005, HttpStatus.UNAUTHORIZED, "재발급 토큰이 유효하지 않습니다."),
    INVALID_ACCESS_TOKEN(401_006, HttpStatus.UNAUTHORIZED, "접근 토큰이 유효하지 않습니다."),
    INVALID_LOGIN(401_008, HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    REFRESH_TOKEN_NOT_FOUND(401_011, HttpStatus.UNAUTHORIZED, "저장된 리프레시 토큰이 존재하지 않습니다."),
    REFRESH_TOKEN_MISMATCH(401_009, HttpStatus.UNAUTHORIZED, "저장된 리프레시 토큰과 일치하지 않습니다."),
    EXPIRED_REFRESH_TOKEN(401_010, HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다."),

    // ========================
    // 403 Forbidden
    // ========================

    FORBIDDEN(403, HttpStatus.FORBIDDEN, "접속 권한이 없습니다."),
    NOT_VALID(403_001, HttpStatus.BAD_REQUEST, "토큰이 적절하지않습니다."),

    // ========================
    // 404 Not Found
    // ========================
    NOT_FOUND_END_POINT(404, HttpStatus.NOT_FOUND, "요청한 대상이 존재하지 않습니다."),
    USER_NOT_FOUND(404_001, HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_NOT_FOUND_IN_COOKIE(404_002, HttpStatus.NOT_FOUND, "쿠키에서 사용자 정보를 찾을 수 없습니다."),


    // ========================
    // 409 Not Found
    // ========================
    DUPLICATE_EMAIL(409_000, HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    // ========================
    // 500 서버 내부 오류
    // ========================
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    /// 요청 파라미터

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    /**
     * 메시지를 바탕으로 ErrorCode를 반환합니다.
     * 동일한 메시지가 여러 ErrorCode에 할당된 경우, 첫 번째로 일치하는 ErrorCode를 반환합니다.
     *
     * @param message 에러 메시지
     * @return 일치하는 ErrorCode
     */
    public static ErrorCode fromMessage(String message) {
        return Arrays.stream(values())
                .filter(code -> code.message.equalsIgnoreCase(message))
                .findFirst()
                .orElse(ErrorCode.INTERNAL_SERVER_ERROR); // 기본 에러 처리
    }
}
