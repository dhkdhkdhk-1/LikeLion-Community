package likelion.community.comment.domain;

// import는 다른 패키지의 클래스를 이 파일에서 사용할 수 있게 하는 선언이다.
// 아래 import는 Lombok 어노테이션을 사용하기 위해 필요하다.
// String은 java.lang 패키지에 있어 자동으로 사용할 수 있지만, Lombok은 import가 필요하다.
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// Lombok은 아래 표시를 보고 컴파일할 때 반복 코드를 자동으로 만들어준다.
// @Getter: private 필드를 읽는 메서드를 만든다. 예: getContent(), getPostId()
// boolean deleted의 읽기 메서드 이름은 isDeleted()이다.
@Getter
// @Builder: 필드 이름을 지정해서 객체를 만들 수 있게 한다.
// 예: Comment.builder().postId(1L).memberId(2L).content("좋은 글이네요!").build()
// build()가 객체를 만들며, 지정하지 않은 참조형 필드는 null, boolean은 false로 남는다.
@Builder
// @AllArgsConstructor: 모든 필드를 선언 순서대로 받는 생성자를 만든다.
// Builder도 이 생성자를 사용해 객체를 만든다.
@AllArgsConstructor
public class Comment {

    private Long id;
    private Long postId;
    private Long memberId;
    private Long parentId;

    private String content;
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
