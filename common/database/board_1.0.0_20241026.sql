-- MariaDB dump 10.19-11.3.2-MariaDB, for osx10.19 (arm64)
--
-- Host: localhost    Database: board
-- ------------------------------------------------------
-- Server version	10.11.7-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board_announce`
--

DROP TABLE IF EXISTS `board_announce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_announce` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `announce_content` varchar(255) DEFAULT NULL,
  `announce_title` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `annouce_guid` varchar(255) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_announce_title` (`announce_title`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_announce`
--

LOCK TABLES `board_announce` WRITE;
/*!40000 ALTER TABLE `board_announce` DISABLE KEYS */;
INSERT INTO `board_announce` VALUES
(21,'공지내용1','공지사항1','2024-10-12 22:24:28.906357','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-10-13 16:25:29.784012','b0eadb3f-97aa-4c43-a5d0-4c6d7ebed833',''),
(22,'공지내용2','공지사항2','2024-10-12 22:26:34.495654','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-10-13 21:50:00.596971','3b85fb2d-c4a4-4cf4-afcb-1f22f7f2f061',''),
(25,'공지내용3','공지사항3','2024-10-13 21:50:13.148166','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-10-13 21:50:13.148207','8c7a5e87-fdff-4f62-b526-00b94ad60ec9','');
/*!40000 ALTER TABLE `board_announce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_development_stack`
--

DROP TABLE IF EXISTS `board_development_stack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_development_stack` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `development_stack_content` varchar(255) DEFAULT NULL,
  `development_stack_title` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `development_stack_guid` varchar(255) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_development_stack_title` (`development_stack_title`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_development_stack`
--

LOCK TABLES `board_development_stack` WRITE;
/*!40000 ALTER TABLE `board_development_stack` DISABLE KEYS */;
INSERT INTO `board_development_stack` VALUES
(6,'12','11','2024-10-13 16:39:32.299050','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-13 16:39:32.298986','fd65d813-e184-4af3-ab38-40427e10d663',''),
(7,'134','13','2024-10-13 16:39:36.205045','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-13 16:39:36.205008','10a9b238-8687-4eb1-9295-7861eba25399',''),
(8,'asdf','asdf1','2024-10-13 16:39:58.418033','baf60c9e-5875-4484-934c-819166acc1cb','2024-10-13 16:39:58.417983','38cb12e0-58c5-4b56-bca1-1946f7f40af1',''),
(9,'asdf1123','asd1123','2024-10-13 16:40:03.934668','baf60c9e-5875-4484-934c-819166acc1cb','2024-10-13 16:40:03.934628','cc916dcd-e427-449a-b110-2ecde92c52bd',''),
(10,'샘물1234\n\n\ntest','샘물','2024-10-13 17:29:29.870370','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-13 17:29:29.870311','a2a4e6c5-010e-4597-99d7-c54e09b5d0dd',''),
(11,'Java 17\nSpringBoot 3.X\nVue 3.X\nPrimeVue\nTailwindCSS','Java SpringBoot','2024-10-19 15:43:47.785128','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:10:30.589434','b3582f16-8e12-45f0-af88-8df6c91940b3','\0');
/*!40000 ALTER TABLE `board_development_stack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_free`
--

DROP TABLE IF EXISTS `board_free`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_free` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `free_content` varchar(255) DEFAULT NULL,
  `free_title` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `free_guid` varchar(255) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_free_title` (`free_title`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_free`
--

LOCK TABLES `board_free` WRITE;
/*!40000 ALTER TABLE `board_free` DISABLE KEYS */;
INSERT INTO `board_free` VALUES
(5,'asdf','df1','2024-10-13 16:39:42.933528','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:25:04.041806','3ae5a99f-cdb8-421f-bfec-cda936276e22',''),
(6,'1233s','123444','2024-10-13 16:39:48.667333','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:23:50.509915','00b4494b-ceec-409b-adf9-16487826a37d',''),
(7,'tees`','test`23','2024-10-13 16:40:11.167092','baf60c9e-5875-4484-934c-819166acc1cb','2024-10-19 16:17:14.583812','260f2eca-559a-4eeb-91b1-ac1319ad1f65',''),
(8,'spring에서 lombok으로 data사용함 ㅋ','이거 어케함','2024-10-19 16:23:43.705943','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:23:43.705900','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','');
/*!40000 ALTER TABLE `board_free` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_member`
--

DROP TABLE IF EXISTS `board_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_member` (
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_member`
--

LOCK TABLES `board_member` WRITE;
/*!40000 ALTER TABLE `board_member` DISABLE KEYS */;
INSERT INTO `board_member` VALUES
(6,'2024-10-01 14:50:33.246523','+ei0OVU9QOZgAkjYsYXchbfCScBWFTQ1DKu+UepuLdE=','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','NvNbS29/yY6z8syqD52pNw==','Fx0F6z0VJAIubOd4TZum0g==','관리자','$2a$10$3.ddd3ykT6XmtHgLj58MPufsJ6OyD/DnlF1y/kUxG.4neP2gNxzsK','VZ8LX2DWALcf38ob7QjCzQ==','manager','2024-10-19 15:31:37.073293',''),
(7,'2024-10-13 13:36:34.168696','zSkDeS0q6C/+hx4hY4iFVQ==','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','XuCKVVhn9aGBEr0oclEv9Q==','T84CvUiBLqJFjotzl2lcyg==','마르','$2a$10$sHKYOadlkvmG4jNwR8C3Wukdw0/pgc5bVA2AlLg64IEneSpdCDmkm','7UxStB1f1QjeK+DUU+gHvQ==','member','2024-10-20 15:55:04.007933',''),
(8,'2024-10-13 16:08:54.132202','GpRH3b8giLty1LO2aUEyHMt61CrKIEQG3+POHuaFziw=','baf60c9e-5875-4484-934c-819166acc1cb','GYAw2IVsLKOjk7APbFlFJQ==','T84CvUiBLqJFjotzl2lcyg==','샘물','$2a$10$IgDHyuSM2W/WuJDW/Y5iS.1iqiOkGAgy120KFh5it0fkrrbprSuUy','xk8N7yvfEsX+GuFJCOh3Gg==','member','2024-10-20 15:41:41.742263',''),
(9,'2024-10-19 16:30:22.996304','m9UjTNBcnxcanKudDlJcT4uhTC9SUKmUi0eQYznfqfg=','1698d4b1-7cc6-4436-a0e3-22d93ad9e9d8','KPWOoIGCYCYBwVYPfHyfmw==','5qfB0HZW9rSCxSzgS4N5Bg==','사용자','$2a$10$Cu0ZNef5oMWhO20ePpmnV.WpTtXyRPqnBqzokyQvJ1TJDXjQjZFT.','Uqtg02wTY65Tv/LpqfWBCA==','member','2024-10-20 16:08:55.750158',''),
(10,'2024-10-20 16:09:57.497159','TZXZVm+dPx/OxXDNti2MK6NDxI0ALqjtdjS1YHvx+Hc=','9ed8844b-2d78-47cb-aa14-bcfa01f851bf','glYPZH4jGua9levw/lXo8Q==','pvTj+jF78BuckXD+HRqP2w==','매니저','$2a$10$ILiqQQ7JF3Z15lC/stQjL.fXFn6AJOKerfSIOw03TNy/n/xKBCTeK','MoRcyp5xJk+dvol0mH0V1Q==','manager','2024-10-20 16:10:04.387676','');
/*!40000 ALTER TABLE `board_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_qna`
--

DROP TABLE IF EXISTS `board_qna`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_qna` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `qna_content` varchar(255) DEFAULT NULL,
  `qna_title` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `qna_guid` varchar(255) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_qna_title` (`qna_title`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_qna`
--

LOCK TABLES `board_qna` WRITE;
/*!40000 ALTER TABLE `board_qna` DISABLE KEYS */;
INSERT INTO `board_qna` VALUES
(1,'2024-10-13 17:49:22.580408','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','문의내용1','자주하는 문의1','2024-10-13 21:49:04.575998','664c016f-410a-4f68-b2c8-f662cd7e33ca',''),
(2,'2024-10-13 21:48:57.606910','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','문의내용2','자주하는 문의2','2024-10-13 21:48:57.606855','7a15c695-509a-4a95-8a18-d952652b1039',''),
(3,'2024-10-19 16:15:57.711022','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','문의내용3','자주하는 문의3','2024-10-19 16:16:05.948953','5a2a92f1-9686-407f-adc0-d2403180b329','');
/*!40000 ALTER TABLE `board_qna` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-26 13:28:05
