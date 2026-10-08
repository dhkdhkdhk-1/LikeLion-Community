package likelion.community.interaction.service;

import likelion.community.global.CustomException;
import likelion.community.global.ErrorCode;
import likelion.community.global.ExistenceChecker;
import likelion.community.interaction.dto.BookmarkResponse;
import likelion.community.interaction.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import likelion.community.interaction.dto.BookmarkPageResponse;
import likelion.community.interaction.dto.BookmarkedPostResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final ExistenceChecker existenceChecker;

    public BookmarkResponse bookmark(Long postId, Long memberId) {
        existenceChecker.checkPostExists(postId);

        if (bookmarkRepository.exists(postId, memberId)) {
            throw new CustomException(ErrorCode.ALREADY_BOOKMARKED);
        }
        try {
            bookmarkRepository.insert(postId, memberId);
        } catch (DuplicateKeyException e) {
            throw new CustomException(ErrorCode.ALREADY_BOOKMARKED);
        }
        return new BookmarkResponse(true);
    }

    public BookmarkResponse unbookmark(Long postId, Long memberId) {
        existenceChecker.checkPostExists(postId);

        int deleted = bookmarkRepository.delete(postId, memberId);
        if (deleted == 0) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        return new BookmarkResponse(false);
    }
    public BookmarkPageResponse getMyBookmarks(Long memberId, int page, int size) {
        if (page < 0 || size < 1 || size > 50) {
            throw new CustomException(ErrorCode.INVALID_INPUT);
        }
        List<BookmarkedPostResponse> content =
                bookmarkRepository.findByMemberId(memberId, size, page * size);
        long total = bookmarkRepository.countByMemberId(memberId);
        int totalPages = (int) Math.ceil((double) total / size);
        return new BookmarkPageResponse(content, page, size, total, totalPages);
    }
}
