DROP TABLE IF EXISTS `board`.`board_main_qna`;
CREATE TABLE `board`.`board_main_qna` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime DEFAULT current_timestamp() COMMENT '등록날짜',
  `is_active` enum('YES','NO') NOT NULL DEFAULT 'YES' COMMENT '활성화 / 비활성화',
  `update_date` datetime DEFAULT NULL COMMENT '수정날짜',
  `is_answer` enum('YES','NO') NOT NULL DEFAULT 'NO' COMMENT '답변 여부',
  `qna_content` text DEFAULT NULL COMMENT 'QnA 내용',
  `qna_title` varchar(100) NOT NULL COMMENT 'QnA 제목',
  `qna_uuid` uuid NOT NULL COMMENT 'QnA UUID',
  `author_seq` bigint(20) DEFAULT NULL COMMENT '작성자 SEQ',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `UKj9qnykgjwhp9b4nruaq3vi3ve` (`qna_uuid`),
  KEY `idx_qna_uuid` (`qna_uuid`),
  KEY `idx_qna_title` (`qna_title`),
  KEY `idx_qna_content` (`qna_content`(768)),
  KEY `idx_author_seq` (`author_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
