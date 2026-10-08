package likelion.community.interaction.service;

import likelion.community.global.CustomException;
import likelion.community.global.ErrorCode;
import likelion.community.global.ExistenceChecker;
import likelion.community.interaction.dto.ReportRequest;
import likelion.community.interaction.dto.ReportResponse;
import likelion.community.interaction.dto.ReportTargetType;
import likelion.community.interaction.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final ExistenceChecker existenceChecker;

    public ReportResponse report(Long reporterId, ReportRequest request) {
        ReportTargetType type = request.getTargetType();
        Long targetId = request.getTargetId();

        // 1. 대상 존재 확인 (없으면 404)
        if (type == ReportTargetType.POST) {
            existenceChecker.checkPostExists(targetId);
        } else {
            existenceChecker.checkCommentExists(targetId);
        }

        // 2. 본인 글/댓글은 신고 불가 (400)
        Long writerId = reportRepository.findWriterId(type, targetId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_INPUT));
        if (writerId.equals(reporterId)) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }

        // 3. 중복 신고 방지 (409)
        if (reportRepository.exists(reporterId, type, targetId)) {
            throw new CustomException(ErrorCode.ALREADY_REPORTED);
        }

        // 4. 저장
        try {
            reportRepository.insert(reporterId, type, targetId,
                    request.getReason().name(), request.getDetail());
        } catch (DuplicateKeyException e) {
            throw new CustomException(ErrorCode.ALREADY_REPORTED);
        }
        return new ReportResponse(type, targetId);
    }
}