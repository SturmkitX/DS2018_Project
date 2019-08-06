-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: ds-project
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `channel_categories`
--

DROP TABLE IF EXISTS `channel_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channel_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channel_categories`
--

LOCK TABLES `channel_categories` WRITE;
/*!40000 ALTER TABLE `channel_categories` DISABLE KEYS */;
INSERT INTO `channel_categories` VALUES (1,'Entertainment'),(3,'Music'),(4,'Others'),(2,'Video Games');
/*!40000 ALTER TABLE `channel_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `channel_comments`
--

DROP TABLE IF EXISTS `channel_comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channel_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` int(11) NOT NULL,
  `comment` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `channel_id` (`channel_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `channel_comments_ibfk_1` FOREIGN KEY (`channel_id`) REFERENCES `channels` (`id`),
  CONSTRAINT `channel_comments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channel_comments`
--

LOCK TABLES `channel_comments` WRITE;
/*!40000 ALTER TABLE `channel_comments` DISABLE KEYS */;
INSERT INTO `channel_comments` VALUES (1,1,'This is the first channel ever, wow!','2018-11-30 16:16:32',1),(2,1,'This is the first channel ever, wow!','2018-11-30 16:19:47',1),(3,1,'This is the first channel ever, wow!','2018-11-30 16:21:55',1),(4,1,'This is the first channel ever, wow!','2018-11-30 16:23:38',1),(5,4,'The first valid comment :)','2019-01-04 15:02:51',1);
/*!40000 ALTER TABLE `channel_comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `channels`
--

DROP TABLE IF EXISTS `channels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `channels` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `display_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `active` smallint(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `display_id` (`display_id`),
  KEY `user_id` (`user_id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `channels_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `channels_ibfk_2` FOREIGN KEY (`category_id`) REFERENCES `channel_categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `channels`
--

LOCK TABLES `channels` WRITE;
/*!40000 ALTER TABLE `channels` DISABLE KEYS */;
INSERT INTO `channels` VALUES (1,1,'First User channel','This is the description of the first channel','mLmywhM8fORbZMi',1,1),(4,3,'','','VjBZ2ndhGj9WoEn',NULL,1),(5,4,'','','ibZA6NqhgaSDOWr',NULL,1),(6,7,'','','VpehRIykSeBCrFe',NULL,0),(7,8,'','','o9G7ZtMkiVGMrC8',NULL,0),(12,13,'','','25JRupTn2qJRMsa',NULL,1);
/*!40000 ALTER TABLE `channels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_types`
--

DROP TABLE IF EXISTS `email_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_types`
--

LOCK TABLES `email_types` WRITE;
/*!40000 ALTER TABLE `email_types` DISABLE KEYS */;
INSERT INTO `email_types` VALUES (1,'Activation'),(2,'Conversation');
/*!40000 ALTER TABLE `email_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emails`
--

DROP TABLE IF EXISTS `emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `email_type` int(11) NOT NULL,
  `message_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `email_type` (`email_type`),
  KEY `message_id` (`message_id`),
  CONSTRAINT `emails_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `emails_ibfk_2` FOREIGN KEY (`email_type`) REFERENCES `email_types` (`id`),
  CONSTRAINT `emails_ibfk_3` FOREIGN KEY (`message_id`) REFERENCES `messages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emails`
--

LOCK TABLES `emails` WRITE;
/*!40000 ALTER TABLE `emails` DISABLE KEYS */;
/*!40000 ALTER TABLE `emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genders`
--

DROP TABLE IF EXISTS `genders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genders`
--

LOCK TABLES `genders` WRITE;
/*!40000 ALTER TABLE `genders` DISABLE KEYS */;
INSERT INTO `genders` VALUES (1,'Female'),(2,'Male');
/*!40000 ALTER TABLE `genders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_entries`
--

DROP TABLE IF EXISTS `history_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `history_entries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `channel_id` int(11) NOT NULL,
  `access_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `channel_id` (`channel_id`),
  CONSTRAINT `history_entries_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `history_entries_ibfk_2` FOREIGN KEY (`channel_id`) REFERENCES `channels` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_entries`
--

LOCK TABLES `history_entries` WRITE;
/*!40000 ALTER TABLE `history_entries` DISABLE KEYS */;
INSERT INTO `history_entries` VALUES (1,1,1,'2018-11-13 08:00:00'),(2,1,1,'2018-12-27 19:54:01'),(3,1,1,'2018-12-27 20:10:09'),(4,1,12,'2019-01-03 19:59:11'),(5,1,12,'2019-01-03 20:03:16'),(6,1,12,'2019-01-03 20:13:57'),(7,1,12,'2019-01-03 20:14:22'),(8,1,12,'2019-01-03 20:14:55'),(9,1,12,'2019-01-03 20:16:35'),(10,1,12,'2019-01-03 20:17:00'),(11,1,12,'2019-01-03 20:19:14'),(12,1,1,'2019-01-03 20:26:06'),(13,1,4,'2019-01-03 20:26:35'),(14,1,1,'2019-01-04 14:18:36'),(15,1,1,'2019-01-04 14:20:39'),(16,1,1,'2019-01-04 14:26:26'),(17,1,1,'2019-01-04 14:27:23'),(18,1,1,'2019-01-04 14:27:37'),(19,1,1,'2019-01-04 14:28:38'),(20,1,1,'2019-01-04 14:32:37'),(21,1,1,'2019-01-04 14:33:22'),(22,1,1,'2019-01-04 14:37:32'),(23,1,4,'2019-01-04 14:37:50'),(24,1,1,'2019-01-04 14:37:54'),(25,1,1,'2019-01-04 14:38:09'),(26,1,1,'2019-01-04 14:39:02'),(27,1,1,'2019-01-04 14:39:10'),(28,1,4,'2019-01-04 14:39:17'),(29,1,1,'2019-01-04 14:39:19'),(30,1,1,'2019-01-04 14:39:56'),(31,1,4,'2019-01-04 14:40:01'),(32,1,5,'2019-01-04 14:40:18'),(33,1,1,'2019-01-04 14:40:22'),(34,1,1,'2019-01-04 14:41:38'),(35,1,5,'2019-01-04 14:41:59'),(36,1,1,'2019-01-04 14:42:25'),(37,1,1,'2019-01-04 14:42:38'),(38,1,5,'2019-01-04 14:42:51'),(39,1,1,'2019-01-04 14:42:54'),(40,1,1,'2019-01-04 14:43:08'),(41,1,1,'2019-01-04 14:43:37'),(42,1,1,'2019-01-04 14:44:21'),(43,1,5,'2019-01-04 14:44:35'),(44,1,1,'2019-01-04 14:44:38'),(45,1,1,'2019-01-04 14:50:28'),(46,1,1,'2019-01-04 14:54:32'),(47,1,1,'2019-01-04 14:54:39'),(48,1,1,'2019-01-04 14:56:41'),(49,1,1,'2019-01-04 14:59:32'),(50,1,1,'2019-01-04 15:02:31'),(51,1,1,'2019-01-04 15:02:35'),(52,1,4,'2019-01-04 15:02:39');
/*!40000 ALTER TABLE `history_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `sender_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `sender_id` (`sender_id`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (4,'Test Email Hope','This is a test email, just for testing purposes',1),(5,'gsejh','fdsahtk',1),(6,'Account activation','<p>Hello, Bogdan R</p><p>You received this email because you just created a new account on my platform and have to confirm your account.</p><p>In order to do this, please visit <a href=\"http://localhost:8080/resource/user/activate/12\">the following link</a>...</p>',4),(7,'Account activation','<p>Hello, Bogdan R</p><p>You received this email because you just created a new account on my platform and have to confirm your account.</p><p>In order to do this, please visit <a href=\"http://localhost:8080/resource/user/activate/13\">the following link</a>...</p>',4),(8,'Reminder','Ce faci mai bogdane?',1),(10,'Reminder','Ce faci bai?',1),(12,'Email notification: Reminder','<p>Message from Test User with Channel:</p><p>Ce faci bai?</p><p>If you don\'t want to receive email notifications anymore, you can change your preferences in the Settings tab</p>',1);
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages_users`
--

DROP TABLE IF EXISTS `messages_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messages_users` (
  `receiver_id` int(11) NOT NULL,
  `message_id` int(11) NOT NULL,
  PRIMARY KEY (`receiver_id`,`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages_users`
--

LOCK TABLES `messages_users` WRITE;
/*!40000 ALTER TABLE `messages_users` DISABLE KEYS */;
INSERT INTO `messages_users` VALUES (1,4),(3,5),(12,6),(13,7),(13,8),(13,10),(13,12);
/*!40000 ALTER TABLE `messages_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'Administrator'),(3,'EmailAdministrator'),(1,'Regular');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` int(11) NOT NULL DEFAULT '0',
  `gender_id` int(11) DEFAULT NULL,
  `active` smallint(6) NOT NULL DEFAULT '0',
  `enable_emails` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id` (`role_id`),
  KEY `gender_id` (`gender_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `users_ibfk_2` FOREIGN KEY (`gender_id`) REFERENCES `genders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Test User with Channel','test2@testus.com','$2a$10$OJ6xFk8DdDFm6PQB2ilVIeSp70jQBbEK/0TmLPnbUHWZGlCMHEHeS',1,NULL,1,0),(3,'Another test user','test@testus.com','$2a$10$OJ6xFk8DdDFm6PQB2ilVIeSp70jQBbEK/0TmLPnbUHWZGlCMHEHeS',2,2,1,0),(4,'Email Administrator System','email-sender@bogdan.ro','$2a$10$OJ6xFk8DdDFm6PQB2ilVIeSp70jQBbEK/0TmLPnbUHWZGlCMHEHeS',3,NULL,1,0),(7,'Laboratory test user','testlab@testus.com','$2a$10$XsuJCJXid8PTiumTC.uP/OlpzCBHzDPEWZc8MErS3sve0p3ynsjQm',2,2,0,0),(8,'testman','testman@testus.com','$2a$10$yKVBB3e0Kg7GPE/xzrxIseBcHZ3Y2CQKDdrDsUnqqbkgIHqRQ1aS6',1,NULL,0,0),(13,'Bogdan R','bogdanius96@gmail.com','$2a$10$aZdYydNtDqxXnFoHqiSjZeQLSKeBufAkPmaCc5o.pKP7eZ.9txneO',1,NULL,1,1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-04 17:17:09
