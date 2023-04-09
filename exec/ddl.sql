CREATE DATABASE  IF NOT EXISTS `mtc` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mtc`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mtc
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alarm`
--

DROP TABLE IF EXISTS `alarm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alarm` (
  `alarm_index` bigint NOT NULL AUTO_INCREMENT,
  `alarm_time` datetime NOT NULL,
  `alarm_dtype` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `alarm_user_index` bigint NOT NULL,
  `alarm_follow_user_index` bigint DEFAULT NULL,
  `alarm_article_index` bigint DEFAULT NULL,
  `alarm_comment_index` bigint DEFAULT NULL,
  `alarm_reply_index` bigint DEFAULT NULL,
  `alarm_check` tinyint DEFAULT '0',
  PRIMARY KEY (`alarm_index`),
  KEY `fk_alarm_user1_idx` (`alarm_user_index`),
  KEY `fk_alarm_user2_idx` (`alarm_follow_user_index`),
  KEY `fk_alarm_article1_idx` (`alarm_article_index`),
  KEY `fk_alarm_comment1_idx` (`alarm_comment_index`),
  KEY `fk_alarm_reply1_idx` (`alarm_reply_index`),
  CONSTRAINT `fk_alarm_article1` FOREIGN KEY (`alarm_article_index`) REFERENCES `article` (`article_index`),
  CONSTRAINT `fk_alarm_comment1` FOREIGN KEY (`alarm_comment_index`) REFERENCES `comment` (`comment_index`),
  CONSTRAINT `fk_alarm_reply1` FOREIGN KEY (`alarm_reply_index`) REFERENCES `reply` (`reply_index`),
  CONSTRAINT `fk_alarm_user1` FOREIGN KEY (`alarm_user_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `fk_alarm_user2` FOREIGN KEY (`alarm_follow_user_index`) REFERENCES `user` (`user_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarm`
--

LOCK TABLES `alarm` WRITE;
/*!40000 ALTER TABLE `alarm` DISABLE KEYS */;
/*!40000 ALTER TABLE `alarm` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_index` bigint NOT NULL AUTO_INCREMENT,
  `article_user_index` bigint NOT NULL,
  `article_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_as_cs NOT NULL,
  `article_hit` int DEFAULT '0',
  `article_regist_time` datetime DEFAULT NULL,
  `article_visible` tinyint DEFAULT '1',
  `article_picture_source` varchar(1000) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `article_max_good` int DEFAULT '0',
  PRIMARY KEY (`article_index`),
  KEY `fk_article_user1_idx` (`article_user_index`),
  CONSTRAINT `fk_article_user1` FOREIGN KEY (`article_user_index`) REFERENCES `user` (`user_index`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `block`
--

DROP TABLE IF EXISTS `block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `block` (
  `block_index` bigint NOT NULL AUTO_INCREMENT,
  `block_user_index` bigint NOT NULL,
  `block_target_index` bigint NOT NULL,
  PRIMARY KEY (`block_index`),
  KEY `block_target_index` (`block_target_index`),
  KEY `block_ibfk_1` (`block_user_index`),
  CONSTRAINT `block_ibfk_1` FOREIGN KEY (`block_user_index`) REFERENCES `user` (`user_index`) ON DELETE CASCADE,
  CONSTRAINT `block_ibfk_2` FOREIGN KEY (`block_target_index`) REFERENCES `user` (`user_index`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `block`
--

LOCK TABLES `block` WRITE;
/*!40000 ALTER TABLE `block` DISABLE KEYS */;
/*!40000 ALTER TABLE `block` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_index` bigint NOT NULL AUTO_INCREMENT,
  `comment_user_index` bigint NOT NULL,
  `comment_article_index` bigint NOT NULL,
  `comment_content` varchar(1000) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `comment_good` int DEFAULT NULL,
  `comment_bad` int DEFAULT NULL,
  `comment_regist_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_index`),
  KEY `comment_user_index` (`comment_user_index`),
  KEY `comment_article_index` (`comment_article_index`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`comment_user_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`comment_article_index`) REFERENCES `article` (`article_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_like`
--

DROP TABLE IF EXISTS `comment_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_like` (
  `comment_like_index` bigint NOT NULL AUTO_INCREMENT,
  `comment_like_user_index` bigint NOT NULL,
  `comment_like_comment_index` bigint NOT NULL,
  `comment_like_valuate` int NOT NULL,
  PRIMARY KEY (`comment_like_index`),
  KEY `comment_like_user_index` (`comment_like_user_index`),
  KEY `comment_like_comment_index` (`comment_like_comment_index`),
  CONSTRAINT `comment_like_ibfk_1` FOREIGN KEY (`comment_like_user_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `comment_like_ibfk_2` FOREIGN KEY (`comment_like_comment_index`) REFERENCES `comment` (`comment_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_like`
--

LOCK TABLES `comment_like` WRITE;
/*!40000 ALTER TABLE `comment_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluate`
--

DROP TABLE IF EXISTS `evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `evaluate` (
  `evaluate_index` bigint NOT NULL AUTO_INCREMENT,
  `evaluate_article_index` bigint NOT NULL,
  `evaluate_user_index` bigint NOT NULL,
  `evaluate_value` varchar(4) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `evaluate_date` datetime DEFAULT NULL,
  PRIMARY KEY (`evaluate_index`),
  KEY `fk_evaluate_user1_idx` (`evaluate_user_index`),
  KEY `fk_evaluate_article1` (`evaluate_article_index`),
  CONSTRAINT `fk_evaluate_article1` FOREIGN KEY (`evaluate_article_index`) REFERENCES `article` (`article_index`) ON DELETE CASCADE,
  CONSTRAINT `fk_evaluate_user1` FOREIGN KEY (`evaluate_user_index`) REFERENCES `user` (`user_index`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluate`
--

LOCK TABLES `evaluate` WRITE;
/*!40000 ALTER TABLE `evaluate` DISABLE KEYS */;
/*!40000 ALTER TABLE `evaluate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `follow_index` bigint NOT NULL AUTO_INCREMENT,
  `follow_user_index` bigint NOT NULL,
  `follow_target_index` bigint NOT NULL,
  PRIMARY KEY (`follow_index`),
  KEY `follow_target_index` (`follow_target_index`),
  KEY `follow_ibfk_1` (`follow_user_index`),
  CONSTRAINT `follow_ibfk_1` FOREIGN KEY (`follow_user_index`) REFERENCES `user` (`user_index`) ON DELETE CASCADE,
  CONSTRAINT `follow_ibfk_2` FOREIGN KEY (`follow_target_index`) REFERENCES `user` (`user_index`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtag`
--

DROP TABLE IF EXISTS `hashtag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtag` (
  `tagname` varchar(20) COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`tagname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtag`
--

LOCK TABLES `hashtag` WRITE;
/*!40000 ALTER TABLE `hashtag` DISABLE KEYS */;
/*!40000 ALTER TABLE `hashtag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hashtagging`
--

DROP TABLE IF EXISTS `hashtagging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hashtagging` (
  `hashtagging_index` bigint NOT NULL AUTO_INCREMENT,
  `hashtagging_article_index` bigint NOT NULL,
  `hashtagging_tagname` varchar(20) COLLATE utf8mb4_0900_as_cs NOT NULL,
  PRIMARY KEY (`hashtagging_index`),
  KEY `fk_hashtagging_article1_idx` (`hashtagging_article_index`),
  KEY `fk_hashtagging_hashtag1_idx` (`hashtagging_tagname`),
  CONSTRAINT `fk_hashtagging_article1` FOREIGN KEY (`hashtagging_article_index`) REFERENCES `article` (`article_index`) ON DELETE CASCADE,
  CONSTRAINT `fk_hashtagging_hashtag1` FOREIGN KEY (`hashtagging_tagname`) REFERENCES `hashtag` (`tagname`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hashtagging`
--

LOCK TABLES `hashtagging` WRITE;
/*!40000 ALTER TABLE `hashtagging` DISABLE KEYS */;
/*!40000 ALTER TABLE `hashtagging` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recent_view`
--

DROP TABLE IF EXISTS `recent_view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recent_view` (
  `recent_view_index` bigint NOT NULL AUTO_INCREMENT,
  `recent_view_user_index` bigint NOT NULL,
  `recent_view_article_index` bigint NOT NULL,
  `recent_view_time` datetime DEFAULT NULL,
  PRIMARY KEY (`recent_view_index`),
  KEY `fk_recent_view_article1_idx` (`recent_view_article_index`),
  KEY `fk_recent_view_user1` (`recent_view_user_index`),
  CONSTRAINT `fk_recent_view_article1` FOREIGN KEY (`recent_view_article_index`) REFERENCES `article` (`article_index`) ON DELETE CASCADE,
  CONSTRAINT `fk_recent_view_user1` FOREIGN KEY (`recent_view_user_index`) REFERENCES `user` (`user_index`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recent_view`
--

LOCK TABLES `recent_view` WRITE;
/*!40000 ALTER TABLE `recent_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `recent_view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply`
--

DROP TABLE IF EXISTS `reply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply` (
  `reply_index` bigint NOT NULL AUTO_INCREMENT,
  `reply_user_index` bigint NOT NULL,
  `reply_comment_index` bigint NOT NULL,
  `reply_content` varchar(1000) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `reply_regist_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reply_index`),
  KEY `reply_user_index` (`reply_user_index`),
  KEY `reply_comment_index` (`reply_comment_index`),
  CONSTRAINT `reply_ibfk_1` FOREIGN KEY (`reply_user_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `reply_ibfk_2` FOREIGN KEY (`reply_comment_index`) REFERENCES `comment` (`comment_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply`
--

LOCK TABLES `reply` WRITE;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reply_like`
--

DROP TABLE IF EXISTS `reply_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reply_like` (
  `reply_like_index` bigint NOT NULL AUTO_INCREMENT,
  `reply_like_user_index` bigint NOT NULL,
  `reply_like_reply_index` bigint NOT NULL,
  `reply_like_valuate` int NOT NULL,
  PRIMARY KEY (`reply_like_index`),
  KEY `reply_like_user_index` (`reply_like_user_index`),
  KEY `reply_like_reply_index` (`reply_like_reply_index`),
  CONSTRAINT `reply_like_ibfk_1` FOREIGN KEY (`reply_like_user_index`) REFERENCES `user` (`user_index`),
  CONSTRAINT `reply_like_ibfk_2` FOREIGN KEY (`reply_like_reply_index`) REFERENCES `reply` (`reply_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reply_like`
--

LOCK TABLES `reply_like` WRITE;
/*!40000 ALTER TABLE `reply_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `reply_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `report_index` bigint NOT NULL AUTO_INCREMENT,
  `report_user_index` bigint NOT NULL,
  `report_content` varchar(255) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `report_dtype` varchar(255) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `report_article_index` bigint DEFAULT NULL,
  `report_comment_index` bigint DEFAULT NULL,
  `report_reply_index` bigint DEFAULT NULL,
  PRIMARY KEY (`report_index`),
  KEY `fk_report_article1_idx` (`report_article_index`),
  KEY `fk_report_reply1_idx` (`report_reply_index`),
  KEY `fk_report_comment1_idx` (`report_comment_index`),
  KEY `fk_report_user1_idx` (`report_user_index`),
  CONSTRAINT `fk_report_article1` FOREIGN KEY (`report_article_index`) REFERENCES `article` (`article_index`),
  CONSTRAINT `fk_report_comment1` FOREIGN KEY (`report_comment_index`) REFERENCES `comment` (`comment_index`),
  CONSTRAINT `fk_report_reply1` FOREIGN KEY (`report_reply_index`) REFERENCES `reply` (`reply_index`),
  CONSTRAINT `fk_report_user1` FOREIGN KEY (`report_user_index`) REFERENCES `user` (`user_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting` (
  `setting_index` bigint NOT NULL AUTO_INCREMENT,
  `setting_user_index` bigint NOT NULL,
  `setting_dark_mode` tinyint DEFAULT '0',
  `setting_left_hand` tinyint DEFAULT '0',
  `setting_ignore_follow` tinyint DEFAULT '0',
  `setting_ignore_evaluate` tinyint DEFAULT '0',
  `setting_ignore_comment` tinyint DEFAULT '0',
  `setting_ignore_reply` tinyint DEFAULT '0',
  PRIMARY KEY (`setting_index`),
  KEY `fk_setting_user1_idx` (`setting_user_index`),
  CONSTRAINT `fk_setting_user1` FOREIGN KEY (`setting_user_index`) REFERENCES `user` (`user_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_index` bigint NOT NULL AUTO_INCREMENT,
  `user_nickname` varchar(100) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `user_picture_source` varchar(1000) COLLATE utf8mb4_0900_as_cs DEFAULT NULL,
  `user_uid` varchar(200) COLLATE utf8mb4_0900_as_cs NOT NULL,
  `user_reported_count` bigint DEFAULT '0',
  PRIMARY KEY (`user_index`),
  UNIQUE KEY `user_nickname` (`user_nickname`),
  UNIQUE KEY `user_uid_UNIQUE` (`user_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_as_cs;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'mtc'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-03 10:13:30
