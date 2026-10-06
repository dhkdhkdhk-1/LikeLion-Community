package likelion.community.interaction.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ViewCountRepository {

    private final JdbcTemplate jdbc;

    public void increase(Long postId) {
        jdbc.update("UPDATE post SET view_count = view_count + 1 WHERE id = ?", postId);
    }
}