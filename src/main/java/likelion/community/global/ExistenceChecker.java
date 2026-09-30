package likelion.community.global;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ExistenceChecker {

    private final JdbcTemplate jdbcTemplate;

    public ExistenceChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void checkMemberExists(Long memberId) {
        if (!exists("SELECT EXISTS (SELECT 1 FROM member WHERE id = ?)", memberId)) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
    }

    public void checkPostExists(Long postId) {
        if (!exists("SELECT EXISTS (SELECT 1 FROM post WHERE id = ?)", postId)) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
    }

    public void checkCommentExists(Long commentId) {
        if (!exists("SELECT EXISTS (SELECT 1 FROM comment WHERE id = ?)", commentId)) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }

    private boolean exists(String sql, Long id) {
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, id);
        return Boolean.TRUE.equals(result);
    }
}
