package likelion.community.global;

import jakarta.servlet.http.HttpSession;

public final class AuthUtil {

    public static final String MEMBER_ID = "memberId";
    public static final String ROLE = "role";
    public static final String ADMIN_ROLE = "ADMIN";

    private AuthUtil() {
    }

    public static Long requireLogin(HttpSession session) {
        if (session == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        Object memberId = session.getAttribute(MEMBER_ID);
        if (memberId instanceof Long id) {
            return id;
        }
        throw new CustomException(ErrorCode.UNAUTHORIZED);
    }

    public static Long requireAdmin(HttpSession session) {
        Long memberId = requireLogin(session);
        if (!ADMIN_ROLE.equals(session.getAttribute(ROLE))) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }
        return memberId;
    }

    public static Long getLoginMemberIdOrNull(HttpSession session) {
        if (session == null) {
            return null;
        }

        Object memberId = session.getAttribute(MEMBER_ID);

        if (memberId instanceof Long id) {
            return id;
        }

        return null;
    }

    public static boolean isAdmin(HttpSession session) {
        return session != null
                && ADMIN_ROLE.equals(session.getAttribute(ROLE));
    }

    public static void login(HttpSession session, Long memberId, String role) {
        session.setAttribute(MEMBER_ID, memberId);
        session.setAttribute(ROLE, role);
    }
}
