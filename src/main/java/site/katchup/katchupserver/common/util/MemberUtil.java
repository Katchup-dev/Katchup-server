package site.katchup.katchupserver.common.util;

import lombok.RequiredArgsConstructor;
import site.katchup.katchupserver.common.exception.UnauthorizedException;
import site.katchup.katchupserver.common.response.ErrorCode;

import java.security.Principal;

@RequiredArgsConstructor
public class MemberUtil {
    public static Long getMemberId(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException(ErrorCode.INVALID_MEMBER);
        }
        return Long.valueOf(principal.getName());
    }
}
