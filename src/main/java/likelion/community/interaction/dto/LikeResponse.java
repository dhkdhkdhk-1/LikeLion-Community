package likelion.community.interaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeResponse {
    private boolean liked;
    private int likeCount;
}