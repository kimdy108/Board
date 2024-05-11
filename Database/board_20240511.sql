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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `board` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `content` text NOT NULL,
  `filename` varchar(150) DEFAULT NULL,
  `filepath` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES
(7,'Hello6','내용6',NULL,NULL),
(8,'제목7','내용7',NULL,NULL),
(9,'제목8','내용8',NULL,NULL),
(10,'제목9','내용9',NULL,NULL),
(11,'제목10','내용10',NULL,NULL),
(12,'제목11','내용11',NULL,NULL),
(13,'제목12','내용12',NULL,NULL),
(14,'제목13','내용13',NULL,NULL),
(15,'제목14','내용14',NULL,NULL),
(16,'제목15','내용15',NULL,NULL),
(17,'제목16','내용16',NULL,NULL),
(18,'제목17','내용17',NULL,NULL),
(19,'제목18','내용18',NULL,NULL),
(20,'제목19','내용19',NULL,NULL),
(21,'제목20','내용20',NULL,NULL),
(22,'제목21','내용21',NULL,NULL),
(23,'제목22','내용22',NULL,NULL),
(24,'제목23','내용23',NULL,NULL),
(25,'제목24','내용24',NULL,NULL),
(26,'제목25','내용25',NULL,NULL),
(27,'제목26','내용26',NULL,NULL),
(28,'제목27','내용27',NULL,NULL),
(29,'제목28','내용28',NULL,NULL),
(30,'제목29','내용29',NULL,NULL),
(31,'제목30','내용30',NULL,NULL),
(32,'제목31','내용31',NULL,NULL),
(33,'제목32','내용32',NULL,NULL),
(34,'제목33','내용33',NULL,NULL),
(35,'제목34','내용34',NULL,NULL),
(36,'제목35','내용35',NULL,NULL),
(37,'제목36','내용36',NULL,NULL),
(38,'제목37','내용37',NULL,NULL),
(39,'제목38','내용38',NULL,NULL),
(40,'제목39','내용39',NULL,NULL),
(41,'제목40','내용40',NULL,NULL),
(42,'제목41','내용41',NULL,NULL),
(43,'제목42','내용42',NULL,NULL),
(44,'제목43','내용43',NULL,NULL),
(45,'제목44','내용44',NULL,NULL),
(46,'제목45','내용45',NULL,NULL),
(47,'제목46','내용46',NULL,NULL),
(48,'제목47','내용47',NULL,NULL),
(49,'제목48','내용48',NULL,NULL),
(50,'제목49','내용49',NULL,NULL),
(51,'제목50','내용50',NULL,NULL),
(52,'제목51','내용51',NULL,NULL),
(53,'제목52','내용52',NULL,NULL),
(54,'제목53','내용53',NULL,NULL),
(55,'제목54','내용54',NULL,NULL),
(56,'제목55','내용55',NULL,NULL),
(57,'제목56','내용56',NULL,NULL),
(58,'제목57','내용57',NULL,NULL),
(59,'제목58','내용58',NULL,NULL),
(60,'제목59','내용59',NULL,NULL),
(61,'제목60','내용60',NULL,NULL),
(62,'제목61','내용61',NULL,NULL),
(63,'제목62','내용62',NULL,NULL),
(64,'제목63','내용63',NULL,NULL),
(65,'제목64','내용64',NULL,NULL),
(66,'제목65','내용65',NULL,NULL),
(67,'제목66','내용66',NULL,NULL),
(68,'제목67','내용67',NULL,NULL),
(69,'제목68','내용68',NULL,NULL),
(70,'제목69','내용69',NULL,NULL),
(71,'제목70','내용70',NULL,NULL),
(72,'제목71','내용71',NULL,NULL),
(73,'제목72','내용72',NULL,NULL),
(74,'제목73','내용73',NULL,NULL),
(75,'제목74','내용74',NULL,NULL),
(76,'제목75','내용75',NULL,NULL),
(77,'제목76','내용76',NULL,NULL),
(78,'제목77','내용77',NULL,NULL),
(79,'제목78','내용78',NULL,NULL),
(80,'제목79','내용79',NULL,NULL),
(81,'제목80','내용80',NULL,NULL),
(82,'제목81','내용81',NULL,NULL),
(83,'제목82','내용82',NULL,NULL),
(84,'제목83','내용83',NULL,NULL),
(85,'제목84','내용84',NULL,NULL),
(86,'제목85','내용85',NULL,NULL),
(87,'제목86','내용86',NULL,NULL),
(88,'제목87','내용87',NULL,NULL),
(89,'제목88','내용88',NULL,NULL),
(90,'제목89','내용89',NULL,NULL),
(91,'제목90','내용90',NULL,NULL),
(92,'제목91','내용91',NULL,NULL),
(93,'제목92','내용92',NULL,NULL),
(94,'제목93','내용93',NULL,NULL),
(95,'제목94','내용94',NULL,NULL),
(96,'제목95','내용95',NULL,NULL),
(97,'제목96','내용96',NULL,NULL),
(98,'제목97','내용97',NULL,NULL),
(99,'제목98','내용98',NULL,NULL),
(100,'제목99','내용99',NULL,NULL),
(101,'제목100','내용100',NULL,NULL),
(102,'제목101','내용101',NULL,NULL),
(103,'제목102','내용102',NULL,NULL),
(104,'제목103','내용103',NULL,NULL),
(105,'제목104','내용104',NULL,NULL),
(106,'제목105','내용105',NULL,NULL),
(107,'제목106','내용106',NULL,NULL),
(108,'제목107','내용107',NULL,NULL),
(109,'제목108','내용108',NULL,NULL),
(110,'제목109','내용109',NULL,NULL),
(111,'제목110','내용110',NULL,NULL),
(112,'제목111','내용111',NULL,NULL),
(113,'제목112','내용112',NULL,NULL),
(114,'제목113','내용113',NULL,NULL),
(115,'제목114','내용114',NULL,NULL),
(116,'제목115','내용115',NULL,NULL),
(117,'제목116','내용116',NULL,NULL),
(118,'제목117','내용117',NULL,NULL),
(119,'제목118','내용118',NULL,NULL),
(120,'제목119','내용119',NULL,NULL),
(121,'제목120','내용120',NULL,NULL),
(122,'Hello6','내용6',NULL,NULL),
(123,'Hello6','내용6',NULL,NULL),
(124,'alert 띄우기 예제','alert 띄우기 예제 test',NULL,NULL),
(125,'alert title','alert content',NULL,NULL),
(126,'ddd','ddddㅎㅎ',NULL,NULL),
(127,'uploadTest','uploadContent',NULL,NULL),
(130,'dd','dd','bd7add02-09c5-4fd5-ad50-045ecc65e8ea_다운로드.jpeg','/files/bd7add02-09c5-4fd5-ad50-045ecc65e8ea_다운로드.jpeg'),
(135,'as','as',NULL,NULL);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `member_seq` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `phone_num` varchar(11) NOT NULL,
  PRIMARY KEY (`member_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES
(1,'admin','rhksflwk!','01012341234');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-11 17:02:04
