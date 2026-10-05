package likelion.community.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {
private Long id;
private Long writerId;
private String writerNickname;

private String content;
private boolean deleted;



    // 이 댓글에 달린 대댓글 목록
    // List<CommentResponse>는 CommentResponse 객체 여러 개를 담는 목록이다.
    // ArrayList는 그 목록을 실제로 저장하는 객체이다.
    // 대댓글이 없으면 빈 목록으로 둔다.
@Builder.Default
private List<CommentResponse> replies = new ArrayList<>();



}
