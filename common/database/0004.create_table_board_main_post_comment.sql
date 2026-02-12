DROP TABLE IF EXISTS `board`.`board_main_post_comment`;
CREATE TABLE `board`.`board_main_post_comment` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime DEFAULT current_timestamp() COMMENT '등록날짜',
  `is_active` enum('YES','NO') NOT NULL DEFAULT 'YES' COMMENT '활성화 / 비활성화',
  `update_date` datetime DEFAULT NULL COMMENT '수정날짜',
  `comment_content` varchar(100) NOT NULL COMMENT '댓글 내용',
  `comment_uuid` uuid NOT NULL COMMENT '댓글 UUID',
  `is_encrypt` enum('YES','NO') NOT NULL DEFAULT 'NO' COMMENT '비밀글 여부',
  `author_seq` bigint(20) DEFAULT NULL COMMENT '작성자 SEQ',
  `post_seq` bigint(20) DEFAULT NULL COMMENT '게시글 SEQ',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `UKkmrcxx2sqcygth27nps1hnlsn` (`comment_uuid`),
  KEY `idx_comment_uuid` (`comment_uuid`),
  KEY `idx_author_seq` (`author_seq`),
  KEY `idx_post_seq` (`post_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;