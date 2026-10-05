package likelion.community.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

// 댓글 작성 성공 시, DB에서 생성된 댓글 번호를 응답으로 전달하는 DTO이다.
// CommentCreateRequest는 사용자가 보내는 데이터, 이 클래스는 서버가 돌려주는 데이터이다.
// @Getter: id를 읽는 getId() 메서드를 만든다. JSON 응답을 만들 때도 사용된다.
@Getter
// @AllArgsConstructor: 모든 필드를 받는 생성자를 만든다. 여기서는 CommentIdResponse(Long id)이다.
// 예: new CommentIdResponse(5L) → 댓글 번호가 5인 응답 객체 생성
// 5L의 L은 숫자 5를 long 타입으로 표현하며, Long 매개변수에 전달될 때 객체로 변환된다.
@AllArgsConstructor
public class CommentIdResponse {
    // DB가 댓글 저장 시 생성한 번호를 담는다. DB의 BIGINT와 맞추기 위해 Long을 사용한다.
    // 나중에 Controller에서 ApiResponse로 감싸면 응답의 data 부분이 {"id": 5} 형태가 된다.
    private Long id;
}
