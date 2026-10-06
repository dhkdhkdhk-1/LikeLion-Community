package likelion.community.interaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkResponse {
    private boolean bookmarked;   // 지금 내가 북마크 한 상태인지
}