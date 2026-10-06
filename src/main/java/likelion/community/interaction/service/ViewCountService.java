package likelion.community.interaction.service;

import jakarta.servlet.http.HttpSession;
import likelion.community.interaction.repository.ViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ViewCountService {

    private static final String VIEWED_POSTS = "viewedPostIds";

    private final ViewCountRepository viewCountRepository;

    /**
     * 같은 세션(같은 브라우저 로그인 상태)에서 같은 글은 한 번만 조회수를 올린다.
     * 김도윤(PostService.getPost)이 게시글 상세 조회 때 호출한다.
     */
    @SuppressWarnings("unchecked")
    public void increase(Long postId, HttpSession session) {
        Set<Long> viewed = (Set<Long>) session.getAttribute(VIEWED_POSTS);
        if (viewed == null) {
            viewed = new HashSet<>();
        }

        if (viewed.contains(postId)) {
            return;                       // 이미 본 글이면 증가 없음
        }

        viewCountRepository.increase(postId);
        viewed.add(postId);
        session.setAttribute(VIEWED_POSTS, viewed);
    }
}