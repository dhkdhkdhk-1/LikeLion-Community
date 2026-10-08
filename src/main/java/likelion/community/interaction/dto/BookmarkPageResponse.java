package likelion.community.interaction.dto;

import java.util.List;

public record BookmarkPageResponse(
        List<BookmarkedPostResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {
}