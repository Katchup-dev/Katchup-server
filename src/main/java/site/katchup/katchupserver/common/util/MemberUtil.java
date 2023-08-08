package site.katchup.katchupserver.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import site.katchup.katchupserver.common.exception.UnauthorizedException;
import site.katchup.katchupserver.common.response.ErrorStatus;

import java.security.Principal;

@RequiredArgsConstructor
public class MemberUtil {
    public static Long getMemberId(Principal principal) {
        if (principal == null) {
            throw new UnauthorizedException(ErrorStatus.INVALID_MEMBER);
        }
        return Long.valueOf(principal.getName());
    }
}
