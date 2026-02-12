DROP TABLE IF EXISTS `board`.`board_main_qna_answer`;
CREATE TABLE `board`.`board_main_qna_answer` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime DEFAULT current_timestamp() COMMENT '등록날짜',
  `is_active` enum('YES','NO') NOT NULL DEFAULT 'YES' COMMENT '활성화 / 비활성화',
  `update_date` datetime DEFAULT NULL COMMENT '수정날짜',
  `answer_content` text DEFAULT NULL COMMENT '답변 내용',
  `answer_uuid` uuid NOT NULL COMMENT '답변 UUID',
  `author_seq` bigint(20) DEFAULT NULL COMMENT '작성자 SEQ',
  `qna_seq` bigint(20) DEFAULT NULL COMMENT 'QnA SEQ',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `UKf2qen6n8e7g4ovhtsdomqq84x` (`answer_uuid`),
  KEY `idx_answer_uuid` (`answer_uuid`),
  KEY `idx_author_seq` (`author_seq`),
  KEY `idx_qna_seq` (`qna_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
