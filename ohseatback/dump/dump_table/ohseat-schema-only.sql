-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: ohseat
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `area_id` smallint unsigned NOT NULL COMMENT '지역 구분',
  `area_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '지역 이름',
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `category_id` int NOT NULL,
  `category_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `category_name` (`category_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinema`
--

DROP TABLE IF EXISTS `cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinema` (
  `cinema_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '영화관 구분',
  `multiplex_id` tinyint unsigned DEFAULT NULL COMMENT '멀티플렉스 구분',
  `area_id` smallint unsigned NOT NULL COMMENT '지역 구분',
  `cinema_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '영화관 이름',
  `cinema_addr` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '영화관 주소',
  PRIMARY KEY (`cinema_id`),
  KEY `idx_cinema_multiplex` (`multiplex_id`),
  KEY `idx_cinema_area` (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cinesquare`
--

DROP TABLE IF EXISTS `cinesquare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cinesquare` (
  `post_id` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `views` int unsigned NOT NULL DEFAULT '0',
  `like_count` int unsigned NOT NULL DEFAULT '0' COMMENT '좋아요 갯수',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `author_id` int unsigned NOT NULL,
  `category_id` int NOT NULL,
  `city` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `district` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`post_id`),
  KEY `author_id` (`author_id`),
  KEY `fk_cinesquare_category` (`category_id`),
  KEY `idx_cinesquare_city_district` (`city`,`district`),
  CONSTRAINT `cineSquare_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_cinesquare_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `comments_cinesquare`
--

DROP TABLE IF EXISTS `comments_cinesquare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments_cinesquare` (
  `comment_id` int unsigned NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL,
  `commenter_id` int unsigned NOT NULL,
  `post_id` int unsigned NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `commenter_id` (`commenter_id`),
  KEY `idx_comments_cinesquare_post_id` (`post_id`),
  CONSTRAINT `comments_cinesquare_ibfk_1` FOREIGN KEY (`commenter_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `comments_cinesquare_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `cinesquare` (`post_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `event_id` int unsigned NOT NULL AUTO_INCREMENT,
  `event_se` enum('EVT','ANN') COLLATE utf8mb4_unicode_ci NOT NULL,
  `category_id` tinyint unsigned NOT NULL COMMENT '전체 0 시사회 1 예매권 2',
  `author_id` int unsigned NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text COLLATE utf8mb4_unicode_ci,
  `ann_count` int unsigned NOT NULL DEFAULT '0',
  `start_dt` date DEFAULT NULL,
  `end_dt` date DEFAULT NULL,
  `views` int unsigned DEFAULT '0',
  `like_count` int unsigned NOT NULL DEFAULT '0' COMMENT '좋아요 갯수',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`event_id`),
  KEY `author_id` (`author_id`),
  CONSTRAINT `event_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `event_like`
--

DROP TABLE IF EXISTS `event_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_like` (
  `event_like_id` int unsigned NOT NULL AUTO_INCREMENT,
  `event_id` int unsigned NOT NULL,
  `user_id` int unsigned NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`event_like_id`),
  UNIQUE KEY `uq_event_like` (`event_id`,`user_id`),
  KEY `idx_event_like_event_id` (`event_id`),
  KEY `idx_event_like_user_id` (`user_id`),
  CONSTRAINT `fk_event_like_event` FOREIGN KEY (`event_id`) REFERENCES `event` (`event_id`) ON DELETE CASCADE,
  CONSTRAINT `fk_event_like_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `file_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '파일 PK',
  `file_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '파일명(원본 이름)',
  `file_url` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '저장 경로',
  `file_size` int unsigned NOT NULL COMMENT '파일 크기',
  `file_type` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '확장자',
  `entity_id` int unsigned DEFAULT NULL COMMENT '파일이 속한 엔티티의 PK(회원, 게시글, 댓글)',
  `entity_type` enum('PROFILE','CINESQUARE_POST','RECOMMEND_POST','EVENT','TEMP') COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_role` enum('POSTER','THUMB','BANNER','CONTENT','OTHER') COLLATE utf8mb4_unicode_ci DEFAULT 'OTHER',
  `is_representative` enum('Y','N') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '대표 이미지 여부(Y/N)',
  `uploaded_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업로드 일시',
  PRIMARY KEY (`file_id`),
  KEY `idx_entity` (`entity_type`,`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=270 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원/게시글/댓글 파일 관리 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `multiplex`
--

DROP TABLE IF EXISTS `multiplex`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multiplex` (
  `multiplex_id` tinyint unsigned NOT NULL COMMENT '멀티플렉스 구분',
  `multiplex_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '멀티플렉스 이름',
  PRIMARY KEY (`multiplex_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `multiplex_area`
--

DROP TABLE IF EXISTS `multiplex_area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multiplex_area` (
  `multiplex_id` tinyint unsigned NOT NULL COMMENT '멀티플렉스 구분',
  `area_id` smallint unsigned NOT NULL COMMENT '지역 구분',
  PRIMARY KEY (`multiplex_id`,`area_id`),
  KEY `idx_multiplex_area_area` (`area_id`),
  KEY `idx_multiplex_area_multiplex` (`multiplex_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `notice_id` int unsigned NOT NULL AUTO_INCREMENT,
  `target_board` enum('CINESQUARE','RECOMMEND') COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '공지 노출 위치',
  `author_id` int unsigned NOT NULL COMMENT '작성자',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '공지 제목',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '공지 내용',
  `views` int unsigned NOT NULL DEFAULT '0' COMMENT '조회수',
  `is_pinned` tinyint(1) NOT NULL DEFAULT '0' COMMENT '상단 고정 여부',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '노출 여부',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
  `updated_at` datetime DEFAULT NULL COMMENT '수정일',
  PRIMARY KEY (`notice_id`),
  KEY `idx_notice_board_active` (`target_board`,`is_active`,`is_pinned`,`created_at`),
  KEY `fk_notice_author` (`author_id`),
  CONSTRAINT `fk_notice_author` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `post_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '게시글시퀀스',
  `multiplex_id` tinyint unsigned DEFAULT NULL COMMENT '멀티플렉스구분',
  `area_id` smallint unsigned DEFAULT NULL COMMENT '지역구분',
  `cinema_id` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '영화관구분',
  `screen_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '상영관구분',
  `author_id` int unsigned NOT NULL COMMENT '회원시퀀스',
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '제목',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '게시글내용',
  `views` int unsigned NOT NULL DEFAULT '0' COMMENT '조회수',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
  `like_count` int unsigned NOT NULL DEFAULT '0' COMMENT '좋아요개수',
  PRIMARY KEY (`post_id`),
  KEY `author_id` (`author_id`),
  KEY `theater_id` (`screen_id`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_comment`
--

DROP TABLE IF EXISTS `post_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_comment` (
  `comment_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '댓글시퀀스',
  `post_id` int unsigned NOT NULL COMMENT '게시글시퀀스',
  `commenter_id` int unsigned NOT NULL COMMENT '회원시퀀스',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '댓글내용',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
  PRIMARY KEY (`comment_id`),
  KEY `post_id` (`post_id`),
  KEY `commenter_id` (`commenter_id`),
  CONSTRAINT `post_comment_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`) ON DELETE CASCADE,
  CONSTRAINT `post_comment_ibfk_2` FOREIGN KEY (`commenter_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='댓글';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_like`
--

DROP TABLE IF EXISTS `post_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_like` (
  `post_like_id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '게시판좋아요시퀀스',
  `post_id` int unsigned NOT NULL COMMENT '게시글시퀀스',
  `post_like_user_id` int unsigned NOT NULL COMMENT '좋아요한 회원아이디',
  `create_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성일',
  PRIMARY KEY (`post_like_id`),
  KEY `idx_post_id` (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `post_like_cinesquare`
--

DROP TABLE IF EXISTS `post_like_cinesquare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_like_cinesquare` (
  `post_like_id` int unsigned NOT NULL AUTO_INCREMENT,
  `post_id` int unsigned NOT NULL,
  `post_like_user_id` int unsigned NOT NULL,
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_like_id`),
  UNIQUE KEY `uq_like_unique` (`post_id`,`post_like_user_id`),
  KEY `post_like_user_id` (`post_like_user_id`),
  KEY `idx_post_like_cinesquare_post_id` (`post_id`),
  CONSTRAINT `post_like_cinesquare_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `cinesquare` (`post_id`) ON DELETE CASCADE,
  CONSTRAINT `post_like_cinesquare_ibfk_2` FOREIGN KEY (`post_like_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `screen`
--

DROP TABLE IF EXISTS `screen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `screen` (
  `screen_id` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '상영관 구분',
  `multiplex_id` tinyint unsigned DEFAULT NULL COMMENT '멀티플렉스 구분',
  `screen_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '상영관 이름',
  `cinema_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '영화관 구분',
  PRIMARY KEY (`screen_id`),
  KEY `idx_screen_cinema` (`cinema_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `nickname` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_number` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `role` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `nickname` (`nickname`),
  UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원정보';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'ohseat'
--

--
-- Dumping routines for database 'ohseat'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-04 13:55:15
