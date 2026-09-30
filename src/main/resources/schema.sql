SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS report;
DROP TABLE IF EXISTS bookmark;
DROP TABLE IF EXISTS post_like;
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS member;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE member (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,               -- BCrypt 해시
    nickname    VARCHAR(30)  NOT NULL UNIQUE,
    cohort      INT,                                  -- 기수 (예: 13)
    track       VARCHAR(30),                          -- 트랙 (백엔드/프론트엔드/AI 등)
    role        VARCHAR(10)  NOT NULL DEFAULT 'USER', -- USER / ADMIN
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE category (
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(30) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE post (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id    BIGINT       NOT NULL,
    category_id  BIGINT       NOT NULL,
    title        VARCHAR(200) NOT NULL,
    content      TEXT         NOT NULL,
    view_count   INT          NOT NULL DEFAULT 0,
    created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id)   REFERENCES member(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE comment (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id     BIGINT       NOT NULL,
    member_id   BIGINT       NOT NULL,
    parent_id   BIGINT,                                -- NULL이면 일반 댓글, 값이 있으면 대댓글
    content     VARCHAR(1000) NOT NULL,
    is_deleted  BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id)   REFERENCES post(id)    ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (parent_id) REFERENCES comment(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE post_like (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id     BIGINT   NOT NULL,
    member_id   BIGINT   NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (post_id, member_id),                       -- 한 사람이 한 글에 좋아요 1번만
    FOREIGN KEY (post_id)   REFERENCES post(id)   ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE bookmark (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id     BIGINT   NOT NULL,
    member_id   BIGINT   NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (post_id, member_id),
    FOREIGN KEY (post_id)   REFERENCES post(id)   ON DELETE CASCADE,
    FOREIGN KEY (member_id) REFERENCES member(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE report (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    reporter_id  BIGINT       NOT NULL,
    target_type  VARCHAR(10)  NOT NULL,                -- POST / COMMENT
    target_id    BIGINT       NOT NULL,
    reason       VARCHAR(20)  NOT NULL,                -- SPAM / ABUSE / ADULT / ETC
    detail       VARCHAR(500),
    status       VARCHAR(10)  NOT NULL DEFAULT 'PENDING', -- PENDING / DONE
    created_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (reporter_id, target_type, target_id),      -- 같은 대상 중복 신고 방지
    FOREIGN KEY (reporter_id) REFERENCES member(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;