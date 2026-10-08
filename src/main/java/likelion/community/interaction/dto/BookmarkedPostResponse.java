package likelion.community.interaction.dto;

import java.time.LocalDateTime;

public record BookmarkedPostResponse(
        Long postId,
        String categoryName,
        String title,
        String writerNickname,
        int viewCount,
        LocalDateTime bookmarkedAt
) {
}