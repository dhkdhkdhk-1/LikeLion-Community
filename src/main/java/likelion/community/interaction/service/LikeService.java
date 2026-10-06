package likelion.community.interaction.service;


import likelion.community.global.CustomException;
import likelion.community.global.ErrorCode;
import likelion.community.global.ExistenceChecker;
import likelion.community.interaction.dto.LikeResponse;
import likelion.community.interaction.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final ExistenceChecker existenceChecker;

    public LikeResponse like(Long postId, Long memberId) {
        existenceChecker.checkPostExists(postId);

        if (likeRepository.exists(postId, memberId)) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }
        try {
            likeRepository.insert(postId, memberId);
        } catch (DuplicateKeyException e) {
            throw new CustomException(ErrorCode.ALREADY_LIKED);
        }
        return new LikeResponse(true, likeRepository.countByPostId(postId));
    }

    public LikeResponse unlike(Long postId, Long memberId) {
        existenceChecker.checkPostExists(postId);

        int deleted = likeRepository.delete(postId, memberId);
        if (deleted == 0) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        return new LikeResponse(false, likeRepository.countByPostId(postId));
    }
}