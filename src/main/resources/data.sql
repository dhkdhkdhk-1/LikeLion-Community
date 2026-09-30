-- 비밀번호는 BCrypt 해시가 들어가야 합니다. (아래 값은 자리표시자)
-- 5번째 팀원이 "password123"을 BCrypt로 해시한 값으로 교체해서 올려주세요.
INSERT INTO member (email, password, nickname, cohort, track, role) VALUES
('admin@likelion.net', '$2a$10$wHynw/5rzH1PCz5tDpK9z.QVkG.HRZnRpCDMGxRHZtSBzRhnjTRQG', '운영진', 13, '백엔드', 'ADMIN'),
('user1@likelion.net', '$2a$10$wHynw/5rzH1PCz5tDpK9z.QVkG.HRZnRpCDMGxRHZtSBzRhnjTRQG', '아기사자1', 13, '백엔드', 'USER'),
('user2@likelion.net', '$2a$10$wHynw/5rzH1PCz5tDpK9z.QVkG.HRZnRpCDMGxRHZtSBzRhnjTRQG', '아기사자2', 13, '프론트엔드', 'USER'),
('user3@likelion.net', '$2a$10$wHynw/5rzH1PCz5tDpK9z.QVkG.HRZnRpCDMGxRHZtSBzRhnjTRQG', '아기사자3', 12, 'AI', 'USER');

INSERT INTO category (name) VALUES ('공지'), ('자유'), ('질문'), ('스터디/팀원모집'), ('정보공유');

INSERT INTO post (member_id, category_id, title, content) VALUES
(1, 1, '커뮤니티 이용 안내', '서로 존중하며 사용해주세요.'),
(2, 2, '오늘 세션 너무 어려웠어요', '다들 이해되셨나요?'),
(3, 3, 'Spring Boot 질문 있어요', 'Controller와 Service 차이가 뭔가요?');

INSERT INTO comment (post_id, member_id, parent_id, content) VALUES
(2, 3, NULL, '저도 어려웠어요 ㅠㅠ'),
(2, 2, 1, '같이 복습해요!');