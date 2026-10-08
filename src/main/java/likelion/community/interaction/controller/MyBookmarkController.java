package likelion.community.interaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import likelion.community.global.ApiResponse;
import likelion.community.interaction.dto.BookmarkPageResponse;
import likelion.community.interaction.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/me/bookmarks")
@RequiredArgsConstructor
@Tag(name = "내 북마크")
public class MyBookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    @Operation(summary = "내 북마크 목록")
    public ResponseEntity<ApiResponse<BookmarkPageResponse>> myBookmarks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpSession session) {
        Long memberId = 1L; // TODO 로그인 연동 후 AuthUtil.requireLogin(session)으로 교체
        return ResponseEntity.ok(ApiResponse.success(bookmarkService.getMyBookmarks(memberId, page, size)));
    }
}