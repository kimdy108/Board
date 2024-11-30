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
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_announce`
--

LOCK TABLES `board_announce` WRITE;
/*!40000 ALTER TABLE `board_announce` DISABLE KEYS */;
INSERT INTO `board_announce` VALUES
(21,'공지내용1','공지사항1','2024-10-12 22:24:28.906357','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-10-13 16:25:29.784012','b0eadb3f-97aa-4c43-a5d0-4c6d7ebed833',''),
(22,'공지내용2','공지사항2','2024-10-12 22:26:34.495654','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-10-13 21:50:00.596971','3b85fb2d-c4a4-4cf4-afcb-1f22f7f2f061',''),
(25,'공지내용3','공지사항3','2024-10-13 21:50:13.148166','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-10-13 21:50:13.148207','8c7a5e87-fdff-4f62-b526-00b94ad60ec9',''),
(26,'공지내용4','공지사항4','2024-11-30 15:29:19.786570','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-30 15:29:19.786597','57620738-7fce-4c5f-aa6f-276568f2ec03',''),
(27,'공지내용5','공지사항5','2024-11-30 17:10:22.576569','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-30 17:10:22.576729','464e985e-7ce6-4bfc-bac0-ff464407ab5c',''),
(28,'공지내용6','공지사항6','2024-11-30 17:10:36.846399','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-30 17:10:51.239292','623078f5-a958-4bc6-80e9-1eb67a01171f','\0');
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
(11,'Java 17\nSpringBoot 3.X\nVue 3.X\nPrimeVue\nTailwindCSS','Java SpringBoot','2024-10-19 15:43:47.785128','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:10:30.589434','b3582f16-8e12-45f0-af88-8df6c91940b3',''),
(12,'ㅇㅋ','에바임','2024-11-23 17:41:11.421991','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-23 17:41:26.887750','846098f9-8be2-47db-a60b-4a46438ef287','\0'),
(13,'test01','test01','2024-11-30 16:05:00.570282','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-30 16:05:57.111797','43cf7349-0276-4aa9-bffb-f9c6769ee49d','\0');
/*!40000 ALTER TABLE `board_development_stack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_development_stack_comment`
--

DROP TABLE IF EXISTS `board_development_stack_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_development_stack_comment` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `development_stack_guid` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  `comment_guid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_development_stack_guid` (`development_stack_guid`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_development_stack_comment`
--

LOCK TABLES `board_development_stack_comment` WRITE;
/*!40000 ALTER TABLE `board_development_stack_comment` DISABLE KEYS */;
INSERT INTO `board_development_stack_comment` VALUES
(23,'asdf','a2a4e6c5-010e-4597-99d7-c54e09b5d0dd','2024-11-09 16:05:10.999394','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-09 16:15:48.862961','\0','23f98367-fbe6-43bc-8ba4-cf572f976fc2'),
(24,'d3dd','a2a4e6c5-010e-4597-99d7-c54e09b5d0dd','2024-11-09 16:15:52.191288','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-09 16:16:01.414930','\0','91eec693-5154-4a70-b26d-6fb54f1cb039'),
(25,'asdf333','a2a4e6c5-010e-4597-99d7-c54e09b5d0dd','2024-11-09 16:16:16.171586','baf60c9e-5875-4484-934c-819166acc1cb','2024-11-09 16:37:48.975662','','f96d40cc-9651-402a-9c0b-28d71cc73e1f'),
(26,'asdfasdfasdf','a2a4e6c5-010e-4597-99d7-c54e09b5d0dd','2024-11-09 16:16:29.538735','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-09 16:16:29.538768','','45db25ef-a757-47c8-bebe-fe6e97c0c45f'),
(27,'2222','cc916dcd-e427-449a-b110-2ecde92c52bd','2024-11-09 16:24:02.641781','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-09 16:24:02.641832','','1667c919-80b7-49d5-ab97-6030274eb14c'),
(28,'3333','cc916dcd-e427-449a-b110-2ecde92c52bd','2024-11-09 16:24:05.187924','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-09 16:24:28.634677','\0','567e1c88-9d83-416d-a6a3-c77ec0aa8c86'),
(29,'1111','cc916dcd-e427-449a-b110-2ecde92c52bd','2024-11-09 16:24:07.286030','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-09 16:24:07.286067','','deb97ffe-e83e-4876-99df-f0d3df485ea7'),
(30,'나도 ㅋ 진심임 ㅋㅋㅋㅋ','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-23 16:06:50.256179','1698d4b1-7cc6-4436-a0e3-22d93ad9e9d8','2024-11-24 14:51:47.193582','\0','ea3ab08a-a961-4787-bb98-d52f5e84c588'),
(31,'ㅇㅈ ㅋ','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 14:28:22.737972','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 14:28:22.738010','','fa57e8f4-1dfe-45e6-9b72-1af6ba098f03'),
(32,'asdf','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:08:33.353932','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:12.117347','\0','9c95273b-1885-460d-986c-16a3967d0a8f'),
(33,'asdf','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:08:35.502783','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:12.757803','\0','8a6c9fc9-c683-47a4-8553-a970e93e0e5f'),
(34,'asdf','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:08:38.174067','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:16.516048','\0','32af3d03-6d03-453b-aaa2-85bf904d2787'),
(35,'asdf','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:08:39.845672','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:17.353472','\0','8c991547-db23-4def-aa60-235d88be56c1'),
(36,'asdf','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:08:41.648433','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:18.133222','\0','f3216521-f14d-4670-9ec7-1d973626d2dd'),
(37,'asdf','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:08:43.490209','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:18.975404','\0','de980f0f-256b-4ff2-baed-8c7982f26f7e'),
(38,'dd','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:09:00.187059','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:19.725849','\0','12c9346b-a72a-40cf-8169-1d0c5d462b17'),
(39,'df','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:09:02.389327','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:20.597767','\0','47ad9a24-d405-462c-a702-a22ea6af1162'),
(40,'asdf','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:09:05.346503','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-24 15:09:21.447249','\0','abd92fbb-ffdb-43e1-a228-8d306a2de8fe'),
(41,'그러니까 답이 없어요 ㅋㅋㅋㅋ','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 15:48:54.700417','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-24 15:48:54.700463','','9e8b341b-a594-45d5-a9ff-fc8a821e15ba'),
(42,'1234','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 17:52:24.541429','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 17:54:38.476077','\0','588359b0-514b-4eb4-a6e6-84fe959fcb94'),
(43,'2222','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 17:52:48.025153','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 17:54:37.409801','\0','e02b30ce-9392-474e-a857-850fd87aba2c'),
(44,'33','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 17:53:33.613757','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 17:54:31.947772','\0','584c5623-7047-416a-8977-ca7fc1d51760'),
(45,'33','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 17:54:10.636336','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 17:54:32.833919','\0','a510495f-1db2-45d2-a8a7-c54e0b47c2eb'),
(46,'11','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-24 17:54:25.885067','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 17:54:33.619884','\0','4c67db49-8678-4270-a81f-cef44f4f11e6'),
(47,'ㅋㅋㅋㅋㅋㅋㅋ','b3582f16-8e12-45f0-af88-8df6c91940b3','2024-11-30 15:57:46.055509','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-30 15:57:46.055536','','c6d11314-b8f1-4b88-84cc-2e5c2e544fd8'),
(48,'test12','43cf7349-0276-4aa9-bffb-f9c6769ee49d','2024-11-30 16:05:42.797508','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-30 16:05:53.962638','\0','20b8ef78-8d18-4a1f-8d55-4a3e49214441');
/*!40000 ALTER TABLE `board_development_stack_comment` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_free`
--

LOCK TABLES `board_free` WRITE;
/*!40000 ALTER TABLE `board_free` DISABLE KEYS */;
INSERT INTO `board_free` VALUES
(5,'asdf','df1','2024-10-13 16:39:42.933528','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:25:04.041806','3ae5a99f-cdb8-421f-bfec-cda936276e22',''),
(6,'1233s','123444','2024-10-13 16:39:48.667333','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:23:50.509915','00b4494b-ceec-409b-adf9-16487826a37d',''),
(7,'test!!!!!','test123','2024-10-13 16:40:11.167092','baf60c9e-5875-4484-934c-819166acc1cb','2024-10-30 20:02:08.266898','260f2eca-559a-4eeb-91b1-ac1319ad1f65',''),
(8,'spring에서 lombok으로 data사용함 ㅋ','이거 어케함','2024-10-19 16:23:43.705943','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-10-19 16:23:43.705900','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd',''),
(9,'test02','test02','2024-11-30 16:12:29.931561','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-30 16:13:01.399548','ffe1e5de-af96-4b23-ae86-8cbe5428c3f9','\0');
/*!40000 ALTER TABLE `board_free` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_free_comment`
--

DROP TABLE IF EXISTS `board_free_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board_free_comment` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `comment_guid` varchar(255) DEFAULT NULL,
  `free_guid` varchar(255) DEFAULT NULL,
  `insert_date` datetime(6) DEFAULT NULL,
  `member_guid` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `use_flag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `idx_seq` (`seq`),
  KEY `idx_free_guid` (`free_guid`),
  KEY `idx_member_guid` (`member_guid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_free_comment`
--

LOCK TABLES `board_free_comment` WRITE;
/*!40000 ALTER TABLE `board_free_comment` DISABLE KEYS */;
INSERT INTO `board_free_comment` VALUES
(1,'나도임 ㅋ','cbc809db-744c-42c7-acaa-1115f01d7534','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','2024-11-09 17:15:37.899003','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-09 17:16:06.778959',''),
(2,'댓글1','05af834c-fee9-419a-ba0e-11a5b939f476','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','2024-11-09 17:17:52.617665','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','2024-11-09 17:17:52.617598',''),
(3,'comment222','8a9832f6-4a95-4d2a-b1b1-9216fd08ba6e','260f2eca-559a-4eeb-91b1-ac1319ad1f65','2024-11-09 17:18:18.327498','baf60c9e-5875-4484-934c-819166acc1cb','2024-11-09 17:18:18.327457',''),
(4,'댓글2','5b153ecd-2945-4c22-b098-1e1df938f124','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','2024-11-09 17:18:30.121581','baf60c9e-5875-4484-934c-819166acc1cb','2024-11-09 17:18:30.121540',''),
(5,'asdf','4f3c3c97-a1df-4467-9817-7825b872904d','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','2024-11-24 15:21:08.989294','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 15:21:19.529995','\0'),
(6,'dddd','4c2b910a-db3e-4034-b5dd-4df6d8c13a60','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','2024-11-24 15:21:11.206159','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 15:21:20.056800','\0'),
(7,'ff','665a5b88-be84-47b9-8ed8-bcbfad40a7a0','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','2024-11-24 15:21:13.263101','447569cb-0056-4b08-9f0c-9720d0cb4ac4','2024-11-24 15:21:21.073006','\0'),
(8,'answer112233','1f2f0017-8601-4d73-bb56-905f879d2188','a0e1bfcc-9c42-489f-9d76-d5c8cdbbe2bd','2024-11-30 15:58:02.838315','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-30 15:58:02.838281',''),
(9,'test22','41ff9192-6716-4a0e-8a91-1a6d09dbd15f','ffe1e5de-af96-4b23-ae86-8cbe5428c3f9','2024-11-30 16:12:49.545984','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','2024-11-30 16:12:58.867868','\0');
/*!40000 ALTER TABLE `board_free_comment` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_member`
--

LOCK TABLES `board_member` WRITE;
/*!40000 ALTER TABLE `board_member` DISABLE KEYS */;
INSERT INTO `board_member` VALUES
(6,'2024-10-01 14:50:33.246523','+ei0OVU9QOZgAkjYsYXchbfCScBWFTQ1DKu+UepuLdE=','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','NvNbS29/yY6z8syqD52pNw==','Fx0F6z0VJAIubOd4TZum0g==','관리자','$2a$10$iMbtt8lA4WJlyRk089RG6OytbnTlALJ7eC5gCgIf3wi9ARKs5Mus6','VZ8LX2DWALcf38ob7QjCzQ==','manager','2024-11-30 15:49:09.086442',''),
(7,'2024-10-13 13:36:34.168696','zSkDeS0q6C/+hx4hY4iFVQ==','eda148c2-74b7-49ab-91ba-5cd4a94f4dde','XuCKVVhn9aGBEr0oclEv9Q==','T84CvUiBLqJFjotzl2lcyg==','마르','$2a$10$CGQv.hTOJmULX200Sl3o2edWgeiV30z11nyyazz0OCbi8jn5unP2.','7UxStB1f1QjeK+DUU+gHvQ==','member','2024-11-24 16:05:56.629539',''),
(8,'2024-10-13 16:08:54.132202','GpRH3b8giLty1LO2aUEyHMt61CrKIEQG3+POHuaFziw=','baf60c9e-5875-4484-934c-819166acc1cb','GYAw2IVsLKOjk7APbFlFJQ==','T84CvUiBLqJFjotzl2lcyg==','샘물','$2a$10$DS8s4q0Hjnv6DEYelE/COeJtRjp./B4agvzt5SjcYr8Z84bh15x2e','xk8N7yvfEsX+GuFJCOh3Gg==','member','2024-11-24 16:06:04.991600',''),
(9,'2024-10-19 16:30:22.996304','m9UjTNBcnxcanKudDlJcT4uhTC9SUKmUi0eQYznfqfg=','1698d4b1-7cc6-4436-a0e3-22d93ad9e9d8','KPWOoIGCYCYBwVYPfHyfmw==','5qfB0HZW9rSCxSzgS4N5Bg==','사용자','$2a$10$eGR6DkRyMfPyiGdFgVlEoONsHGcRNVHB3dWpq8bXO3jUuQsyu/cGS','Uqtg02wTY65Tv/LpqfWBCA==','member','2024-11-30 15:50:32.908694',''),
(10,'2024-10-20 16:09:57.497159','TZXZVm+dPx/OxXDNti2MK6NDxI0ALqjtdjS1YHvx+Hc=','9ed8844b-2d78-47cb-aa14-bcfa01f851bf','glYPZH4jGua9levw/lXo8Q==','pvTj+jF78BuckXD+HRqP2w==','매니저','$2a$10$IIzaxtqY8sskygqJPXZdzO7eSXJYynIRpTGi7P41cO9Sik.W/oUgS','H/YklkhfkTRwMVqTIMhHHg==','manager','2024-11-30 15:55:49.633904',''),
(13,'2024-11-24 14:27:25.566103','hRMVbTir9gy8NiFgyUgAOjwCgu856mZItSHqBcEf50A=','447569cb-0056-4b08-9f0c-9720d0cb4ac4','TqpV528CIP+MYYGr5mFXPg==','7hWcP1JxgGkcRmLMtnX32g==','마르108','$2a$10$tNbh0vfU7kl0XkshKo6U.ulF2bkPK06YLe88vnrEO5wistpH2Ry0u','7tq1Pz+oS/GuMlpt0IF9QQ==','member','2024-11-24 16:06:10.210733',''),
(14,'2024-11-30 15:17:28.203822','kv5Zfww697FW+6E3/5Nmhp5c3gxQk+/AmPd02OVHzyk=','b9e11dc1-3431-4d89-b35f-30902d545be4','424+cTueC9AwVOutMRuR9Q==','T84CvUiBLqJFjotzl2lcyg==','사용자1','$2a$10$7l6bMUSPayjJ1bcfAxBsMON8TYXp07ZLpWVTbC/E0sX.NHqXc/MKW','nLYG0Vj10KaO37tC40pt2Q==','manager','2024-11-30 15:54:24.749906',''),
(15,'2024-11-30 16:46:08.019904','K94d4bSXcP9no6bysin77evAFSj0Xgq5tyzdKw5V79I=','927db25a-7173-47b9-bfab-afc9650a4f67','3hcGqrPPA8E5VAluKY1AmQ==','1/Xq5KzmHImIN/73xwFVrKlyKCW7e+L2QHy7fSEY2/w=','사용자2','$2a$10$ema2WVo5GKvCB1u0MMCmt./KNE3GsZ5/Jr/iTF5DG24pw9ONBECuC','TfS0sQG2ocWMBiK8kAvWdQ==','member','2024-11-30 16:46:59.649015','');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_qna`
--

LOCK TABLES `board_qna` WRITE;
/*!40000 ALTER TABLE `board_qna` DISABLE KEYS */;
INSERT INTO `board_qna` VALUES
(1,'2024-10-13 17:49:22.580408','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','문의내용1','자주하는 문의1','2024-10-13 21:49:04.575998','664c016f-410a-4f68-b2c8-f662cd7e33ca',''),
(2,'2024-10-13 21:48:57.606910','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','문의내용2','자주하는 문의2','2024-10-13 21:48:57.606855','7a15c695-509a-4a95-8a18-d952652b1039',''),
(3,'2024-10-19 16:15:57.711022','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','문의내용3','자주하는 문의3','2024-10-19 16:16:05.948953','5a2a92f1-9686-407f-adc0-d2403180b329',''),
(4,'2024-11-30 17:15:38.129329','052a9638-9ee5-4eb3-b1b1-8a774e15a3b7','문의내용4','자주하는 문의4','2024-11-30 17:15:56.159320','444f56f8-798c-4514-b86b-1a61af5b73f1','\0');
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

-- Dump completed on 2024-11-30 17:18:50
