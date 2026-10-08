package likelion.community.interaction.repository;

import likelion.community.interaction.dto.ReportTargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportRepository {

    private final JdbcTemplate jdbc;

    public boolean exists(Long reporterId, ReportTargetType targetType, Long targetId) {
        Integer cnt = jdbc.queryForObject(
                "SELECT COUNT(*) FROM report WHERE reporter_id = ? AND target_type = ? AND target_id = ?",
                Integer.class, reporterId, targetType.name(), targetId);
        return cnt != null && cnt > 0;
    }

    public void insert(Long reporterId, ReportTargetType targetType, Long targetId,
                       String reason, String detail) {
        jdbc.update(
                "INSERT INTO report (reporter_id, target_type, target_id, reason, detail) VALUES (?, ?, ?, ?, ?)",
                reporterId, targetType.name(), targetId, reason, detail);
    }

    // 신고 대상(글/댓글)의 작성자 id 조회 (본인 신고 방지용)
    public Optional<Long> findWriterId(ReportTargetType targetType, Long targetId) {
        String sql = (targetType == ReportTargetType.POST)
                ? "SELECT member_id FROM post WHERE id = ?"
                : "SELECT member_id FROM comment WHERE id = ?";
        List<Long> result = jdbc.queryForList(sql, Long.class, targetId);
        return result.stream().findFirst();
    }
}