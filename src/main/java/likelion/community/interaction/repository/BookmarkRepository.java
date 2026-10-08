package likelion.community.interaction.repository;

import likelion.community.interaction.dto.BookmarkedPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookmarkRepository {

    private final JdbcTemplate jdbc;

    private final RowMapper<BookmarkedPostResponse> bookmarkedPostMapper = (rs, i) ->
            new BookmarkedPostResponse(
                    rs.getLong("post_id"),
                    rs.getString("category_name"),
                    rs.getString("title"),
                    rs.getString("writer_nickname"),
                    rs.getInt("view_count"),
                    rs.getTimestamp("bookmarked_at").toLocalDateTime());

    public boolean exists(Long postId, Long memberId) {
        Integer cnt = jdbc.queryForObject(
                "SELECT COUNT(*) FROM bookmark WHERE post_id = ? AND member_id = ?",
                Integer.class, postId, memberId);
        return cnt != null && cnt > 0;
    }

    public void insert(Long postId, Long memberId) {
        jdbc.update("INSERT INTO bookmark (post_id, member_id) VALUES (?, ?)", postId, memberId);
    }

    public int delete(Long postId, Long memberId) {
        return jdbc.update("DELETE FROM bookmark WHERE post_id = ? AND member_id = ?", postId, memberId);
    }

    // 내 북마크 목록 (최신 북마크순)
    public List<BookmarkedPostResponse> findByMemberId(Long memberId, int limit, int offset) {
        String sql = """
                SELECT p.id AS post_id, c.name AS category_name, p.title,
                       m.nickname AS writer_nickname, p.view_count,
                       b.created_at AS bookmarked_at
                FROM bookmark b
                JOIN post p     ON b.post_id = p.id
                JOIN category c ON p.category_id = c.id
                JOIN member m   ON p.member_id = m.id
                WHERE b.member_id = ?
                ORDER BY b.id DESC
                LIMIT ? OFFSET ?
                """;
        return jdbc.query(sql, bookmarkedPostMapper, memberId, limit, offset);
    }

    public long countByMemberId(Long memberId) {
        Long cnt = jdbc.queryForObject(
                "SELECT COUNT(*) FROM bookmark WHERE member_id = ?", Long.class, memberId);
        return cnt == null ? 0 : cnt;
    }
}