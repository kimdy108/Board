-- --------------------------------------------------------
-- 호스트:                          127.0.0.1
-- 서버 버전:                        10.6.16-MariaDB - mariadb.org binary distribution
-- 서버 OS:                        Win64
-- HeidiSQL 버전:                  12.4.0.6659
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- board 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `board` /*!40100 DEFAULT CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci */;
USE `board`;

-- 테이블 board.board_announce 구조 내보내기
CREATE TABLE IF NOT EXISTS `board_announce` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `annouce_guid` varchar(255) DEFAULT NULL,
  `announce_content` varchar(255) DEFAULT NULL,
  `announce_title` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_announce_title` (`announce_title`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 board.board_announce:~3 rows (대략적) 내보내기
INSERT INTO `board_announce` (`seq`, `annouce_guid`, `announce_content`, `announce_title`, `insert_date`, `member_guid`, `update_date`, `use_flag`) VALUES
	(1, '6ee9e39b-0ff1-4371-a269-d6104f7cf46a', '공지내용1', '공지사항1', '2024-10-25 15:20:14.799154', '0826e289-b5d6-42b0-955e-8064df39b084', '2024-10-25 15:20:14.799154', b'1'),
	(2, '143eaa75-c47b-4126-88f9-e800c2a1179b', '공지내용2', '공지사항2', '2024-10-25 15:20:23.797759', '0826e289-b5d6-42b0-955e-8064df39b084', '2024-10-25 15:20:23.797759', b'1'),
	(3, 'ee654a97-9ff1-4abc-afee-651dc343d931', '공지내용3', '공지사항3', '2024-10-25 15:20:29.949459', '0826e289-b5d6-42b0-955e-8064df39b084', '2024-10-25 15:20:29.949459', b'1');

-- 테이블 board.board_development_stack 구조 내보내기
CREATE TABLE IF NOT EXISTS `board_development_stack` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `development_stack_content` varchar(255) DEFAULT NULL,
  `development_stack_guid` varchar(255) DEFAULT NULL,
  `development_stack_title` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_development_stack_title` (`development_stack_title`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 board.board_development_stack:~0 rows (대략적) 내보내기

-- 테이블 board.board_free 구조 내보내기
CREATE TABLE IF NOT EXISTS `board_free` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `free_content` varchar(255) DEFAULT NULL,
  `free_guid` varchar(255) DEFAULT NULL,
  `free_title` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_free_title` (`free_title`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 board.board_free:~0 rows (대략적) 내보내기

-- 테이블 board.board_member 구조 내보내기
CREATE TABLE IF NOT EXISTS `board_member` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_email` varchar(255) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `member_id` varchar(255) DEFAULT NULL,
  `member_name` varchar(255) DEFAULT NULL,
  `member_nick_name` varchar(255) DEFAULT NULL,
  `member_password` varchar(255) DEFAULT NULL,
  `member_phone` varchar(255) DEFAULT NULL,
  `member_role` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_member_id` (`member_id`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 board.board_member:~3 rows (대략적) 내보내기
INSERT INTO `board_member` (`seq`, `insert_date`, `member_email`, `member_guid`, `member_id`, `member_name`, `member_nick_name`, `member_password`, `member_phone`, `member_role`, `update_date`, `use_flag`) VALUES
	(1, '2024-10-25 15:15:16.345220', '+ei0OVU9QOZgAkjYsYXchbfCScBWFTQ1DKu+UepuLdE=', '0826e289-b5d6-42b0-955e-8064df39b084', 'NvNbS29/yY6z8syqD52pNw==', '/LLM6mPRd9J7DL+zBc81XQ==', '최고관리자', '$2a$10$GPcDQ.JAfOu1ge1zS5znX.Jdz8WUNq7HzIudycWVMvDbZuP4dVQwy', 'VZ8LX2DWALcf38ob7QjCzQ==', 'manager', '2024-10-25 15:15:16.345220', b'1'),
	(2, '2024-10-25 15:17:01.328520', 'm9UjTNBcnxcanKudDlJcT4uhTC9SUKmUi0eQYznfqfg=', '00d1e6c5-549b-4253-a7f9-7796a32e1ecd', 'glYPZH4jGua9levw/lXo8Q==', 'pvTj+jF78BuckXD+HRqP2w==', '매니저', '$2a$10$rEi9vmmS0CnPiWBQtPk6Pe/7o9h2.1nVF29uj0AC669TJkSk0cwf6', '7UxStB1f1QjeK+DUU+gHvQ==', 'manager', '2024-10-25 15:17:46.655966', b'1'),
	(3, '2024-10-25 15:17:39.122837', 'hRMVbTir9gy8NiFgyUgAOjwCgu856mZItSHqBcEf50A=', 'c0a8b729-9b24-4564-a2ae-ffff3321a295', 'TqpV528CIP+MYYGr5mFXPg==', '7hWcP1JxgGkcRmLMtnX32g==', '마르', '$2a$10$dlwVR./GaKrpGWFblfwiOOeRtQjv9sTg6zPqHSl38mbqtpZyRKo3y', 'Uqtg02wTY65Tv/LpqfWBCA==', 'member', '2024-10-25 15:17:39.122837', b'1');

-- 테이블 board.board_qna 구조 내보내기
CREATE TABLE IF NOT EXISTS `board_qna` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `qna_content` varchar(255) DEFAULT NULL,
  `qna_guid` varchar(255) DEFAULT NULL,
  `qna_title` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_qna_title` (`qna_title`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

-- 테이블 데이터 board.board_qna:~3 rows (대략적) 내보내기
INSERT INTO `board_qna` (`seq`, `insert_date`, `member_guid`, `qna_content`, `qna_guid`, `qna_title`, `update_date`, `use_flag`) VALUES
	(1, '2024-10-25 15:20:42.155023', '0826e289-b5d6-42b0-955e-8064df39b084', '문의내용1', '1d4244b0-1f3e-4458-a3da-36c93390e023', '자주하는질문1', '2024-10-25 15:20:42.155023', b'1'),
	(2, '2024-10-25 15:20:49.428516', '0826e289-b5d6-42b0-955e-8064df39b084', '문의내용2', 'b7824390-6b9a-43fc-8fdd-243a48d84ebd', '자주하는질문2', '2024-10-25 15:20:57.073000', b'1'),
	(3, '2024-10-25 15:21:07.073362', '0826e289-b5d6-42b0-955e-8064df39b084', '문의내용3', '5508a374-cfd1-4a06-b00d-33edd682f7d6', '자주하는질문3', '2024-10-25 15:21:07.073362', b'1');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
