package likelion.community.comment.dto;

// jakarta.validation은 입력값 검증에 사용하는 표준 API이다.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
// Lombok은 Getter, Setter, 생성자 같은 반복 코드를 컴파일할 때 만들어준다.
// 값을 읽거나 변경하는 등 클래스에 필요한 기능만 선택해서 사용한다.
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// @Getter: 요청으로 받은 값을 읽는 메서드를 만든다. 예: getContent(), getParentId()
@Getter
// @Setter: 값을 넣거나 바꾸는 메서드를 만든다. 예: setContent(String content)
@Setter
// @NoArgsConstructor: 매개변수 없는 기본 생성자를 만든다.
// JSON 데이터를 받을 때 기본 생성자로 객체를 만들고 Setter로 값을 넣을 수 있다.
@NoArgsConstructor
public class CommentCreateRequest {

    // DTO는 API로 들어오거나 나가는 데이터를 담는다. 이 DTO는 댓글 작성 요청용이다.
    // 아래 검증 조건은 나중에 Controller의 요청 매개변수에 @Valid를 붙여 적용한다.
    // @NotBlank: null, 빈 문자열(""), 공백만 있는 문자열을 허용하지 않는다.
    @NotBlank(message = "댓글 내용은 필수입니다.")
    // @Size: 댓글 내용의 길이를 최대 1,000자로 제한한다.
    @Size(max = 1000, message = "댓글은 최대 1,000자까지 작성할 수 있습니다.")
    private String content;
    // 일반 댓글이면 null, 대댓글이면 부모 댓글의 번호를 받는다.
    // postId는 URL에서, memberId는 로그인 세션에서 가져오므로 이 DTO에 넣지 않는다.
    private Long parentId;
}
