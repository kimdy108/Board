DROP TABLE IF EXISTS `board`.`board_main_post`;
CREATE TABLE `board`.`board_main_post` (
  `seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime DEFAULT current_timestamp() COMMENT '등록날짜',
  `is_active` enum('YES','NO') NOT NULL DEFAULT 'YES' COMMENT '활성화 / 비활성화',
  `update_date` datetime DEFAULT NULL COMMENT '수정날짜',
  `post_content` text DEFAULT NULL COMMENT '게시글 내용',
  `post_title` varchar(100) NOT NULL COMMENT '게시글 제목',
  `post_type` enum('DEVELOPMENT','STACK','FREE') NOT NULL DEFAULT 'FREE' COMMENT '게시글 타입',
  `post_uuid` uuid NOT NULL COMMENT '게시글 UUID',
  `view_count` int(11) NOT NULL COMMENT '조회 수',
  `author_seq` bigint(20) DEFAULT NULL COMMENT '작성자 SEQ',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `UKbe0uvo9ikupeoefj7jfnxrl22` (`post_uuid`),
  KEY `idx_post_uuid` (`post_uuid`),
  KEY `idx_post_title` (`post_title`),
  KEY `idx_post_type` (`post_type`),
  KEY `idx_author_seq` (`author_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;