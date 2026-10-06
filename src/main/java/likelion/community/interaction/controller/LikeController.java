package likelion.community.interaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import likelion.community.global.ApiResponse;
import likelion.community.interaction.dto.LikeResponse;
import likelion.community.interaction.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
@Tag(name = "좋아요")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    @Operation(summary = "좋아요 등록")
    public ResponseEntity<ApiResponse<LikeResponse>> like(@PathVariable Long postId, HttpSession session) {
        Long memberId = 1L; // TODO 로그인 연동 후 AuthUtil.requireLogin(session)으로 교체
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("좋아요를 눌렀습니다.", likeService.like(postId, memberId)));
    }

    @DeleteMapping
    @Operation(summary = "좋아요 취소")
    public ResponseEntity<ApiResponse<LikeResponse>> unlike(@PathVariable Long postId, HttpSession session) {
        Long memberId = 1L; // TODO 로그인 연동 후 AuthUtil.requireLogin(session)으로 교체
        return ResponseEntity.ok(ApiResponse.success("좋아요를 취소했습니다.", likeService.unlike(postId, memberId)));
    }
}