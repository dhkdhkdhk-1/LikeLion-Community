package likelion.community.interaction.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import likelion.community.global.ApiResponse;
import likelion.community.interaction.dto.ReportRequest;
import likelion.community.interaction.dto.ReportResponse;
import likelion.community.interaction.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "신고")
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @Operation(summary = "신고 등록")
    public ResponseEntity<ApiResponse<ReportResponse>> report(
            @Valid @RequestBody ReportRequest request, HttpSession session) {
        Long memberId = 1L; // TODO 로그인 연동 후 AuthUtil.requireLogin(session)으로 교체
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("신고가 접수되었습니다.", reportService.report(memberId, request)));
    }
}