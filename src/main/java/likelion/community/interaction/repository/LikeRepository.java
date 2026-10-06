package likelion.community.interaction.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final JdbcTemplate jdbc;

    public boolean exists(Long postId, Long memberId) {
        Integer cnt = jdbc.queryForObject(
                "SELECT COUNT(*) FROM post_like WHERE post_id = ? AND member_id = ?",
                Integer.class, postId, memberId);
        return cnt != null && cnt > 0;
    }

    public void insert(Long postId, Long memberId) {
        jdbc.update("INSERT INTO post_like (post_id, member_id) VALUES (?, ?)", postId, memberId);
    }

    public int delete(Long postId, Long memberId) {
        return jdbc.update("DELETE FROM post_like WHERE post_id = ? AND member_id = ?", postId, memberId);
    }

    public int countByPostId(Long postId) {
        Integer cnt = jdbc.queryForObject(
                "SELECT COUNT(*) FROM post_like WHERE post_id = ?", Integer.class, postId);
        return cnt == null ? 0 : cnt;
    }
}