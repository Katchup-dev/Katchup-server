package site.katchup.katchupserver.config.jwt;

public enum JwtExceptionType {
    VALID_JWT_TOKEN,
    INVALID_JWT_SIGNATURE,
    INVALID_JWT_TOKEN,
    EXPIRED_JWT_TOKEN,
    UNSUPPORTED_JWT_TOKEN,
    EMPTY_JWT
}
