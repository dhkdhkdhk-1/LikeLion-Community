package likelion.community.interaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportResponse {
    private ReportTargetType targetType;
    private Long targetId;
}