DROP TABLE IF EXISTS `board`.`board_main_announce`;
CREATE TABLE `board`.`board_main_announce` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime DEFAULT current_timestamp() COMMENT '등록날짜',
  `is_active` enum('YES','NO') NOT NULL DEFAULT 'YES' COMMENT '활성화 / 비활성화',
  `update_date` datetime DEFAULT NULL COMMENT '수정날짜',
  `announce_content` text DEFAULT NULL COMMENT '공지사항 내용',
  `announce_title` varchar(100) NOT NULL COMMENT '공지사항 제목',
  `announce_uuid` uuid NOT NULL COMMENT '공지사항 UUID',
  `view_count` int(11) NOT NULL COMMENT '조회 수',
  `author_seq` bigint(20) DEFAULT NULL COMMENT '작성자 SEQ (관리자)',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `UK6fardpem6qrm9pn67vl64dbgt` (`announce_uuid`),
  KEY `idx_announce_uuid` (`announce_uuid`),
  KEY `idx_announce_title` (`announce_title`),
  KEY `idx_author_seq` (`author_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;