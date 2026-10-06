package likelion.community.interaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import likelion.community.global.ApiResponse;
import likelion.community.interaction.dto.BookmarkResponse;
import likelion.community.interaction.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts/{postId}/bookmarks")
@RequiredArgsConstructor
@Tag(name = "북마크")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    @Operation(summary = "북마크 등록")
    public ResponseEntity<ApiResponse<BookmarkResponse>> bookmark(@PathVariable Long postId, HttpSession session) {
        Long memberId = 1L; // TODO 로그인 연동 후 AuthUtil.requireLogin(session)으로 교체
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("북마크했습니다.", bookmarkService.bookmark(postId, memberId)));
    }

    @DeleteMapping
    @Operation(summary = "북마크 취소")
    public ResponseEntity<ApiResponse<BookmarkResponse>> unbookmark(@PathVariable Long postId, HttpSession session) {
        Long memberId = 1L; // TODO 로그인 연동 후 AuthUtil.requireLogin(session)으로 교체
        return ResponseEntity.ok(ApiResponse.success("북마크를 취소했습니다.", bookmarkService.unbookmark(postId, memberId)));
    }
}