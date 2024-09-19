-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: donation_db
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` tinytext,
  `goal_amount` decimal(15,2) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `goalAmount` decimal(15,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,'Education Support HK','Providing educational resources to underprivileged students in Hong Kong.',200000.00,'2023-01-01','2023-12-31',0.00),(2,'Health Care Initiative','Supporting local clinics in remote areas.',150000.00,'2023-02-01','2023-11-30',0.00),(3,'Environmental Awareness HK','Promoting recycling and waste reduction in the city.',100000.00,'2023-03-01','2023-10-31',0.00),(4,'Elderly Care Program','Assisting elderly citizens living alone.',120000.00,'2023-04-01','2023-09-30',0.00),(5,'Community Development','Improving community facilities in urban areas.',180000.00,'2023-05-01','2023-08-31',0.00),(6,'Youth Empowerment','Providing mentorship programs for youth.',160000.00,'2023-06-01','2023-12-31',0.00),(7,'Arts and Culture Support','Funding local artists and cultural events.',110000.00,'2023-07-01','2023-11-30',0.00),(8,'Disaster Relief HK','Emergency response for natural disasters.',250000.00,'2023-08-01','2023-12-31',0.00),(9,'Animal Welfare','Supporting animal shelters and adoption programs.',90000.00,'2023-09-01','2023-12-31',0.00),(10,'Public Health Education','Raising awareness about health and hygiene.',130000.00,'2023-10-01','2023-12-31',0.00);
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donor_id` int NOT NULL,
  `student_id` int NOT NULL,
  `amount` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `payment_method_id` int DEFAULT NULL,
  `campaign_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `donor_id` (`donor_id`),
  KEY `student_id` (`student_id`),
  KEY `payment_method_id` (`payment_method_id`),
  KEY `campaign_id` (`campaign_id`),
  CONSTRAINT `donation_ibfk_1` FOREIGN KEY (`donor_id`) REFERENCES `donor` (`id`),
  CONSTRAINT `donation_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `donation_ibfk_3` FOREIGN KEY (`payment_method_id`) REFERENCES `paymentmethod` (`id`),
  CONSTRAINT `donation_ibfk_4` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (1,1,1,1000,'2023-01-15',1,1),(2,2,2,1500,'2023-02-20',2,2),(3,3,3,1200,'2023-03-10',3,3),(4,4,4,800,'2023-04-05',4,4),(5,5,5,900,'2023-05-18',5,5),(6,6,6,1100,'2023-06-22',6,6),(7,7,7,700,'2023-07-13',7,7),(8,8,8,1300,'2023-08-29',8,8),(9,9,9,950,'2023-09-07',9,9),(10,10,10,850,'2023-10-16',10,10);
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donor`
--

DROP TABLE IF EXISTS `donor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donor` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `donor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donor`
--

LOCK TABLES `donor` WRITE;
/*!40000 ALTER TABLE `donor` DISABLE KEYS */;
INSERT INTO `donor` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10);
/*!40000 ALTER TABLE `donor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donorfeedback`
--

DROP TABLE IF EXISTS `donorfeedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donorfeedback` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donor_id` int NOT NULL,
  `message` tinytext,
  `submitted_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `donor_id` (`donor_id`),
  CONSTRAINT `donorfeedback_ibfk_1` FOREIGN KEY (`donor_id`) REFERENCES `donor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donorfeedback`
--

LOCK TABLES `donorfeedback` WRITE;
/*!40000 ALTER TABLE `donorfeedback` DISABLE KEYS */;
INSERT INTO `donorfeedback` VALUES (1,1,'Happy to support education in Hong Kong.','2023-01-17'),(2,2,'Great initiative for healthcare.','2023-02-22'),(3,3,'Proud to be part of environmental awareness.','2023-03-12'),(4,4,'Elderly care is important for our society.','2023-04-07'),(5,5,'Community development makes a difference.','2023-05-20'),(6,6,'Youth empowerment is the key to the future.','2023-06-24'),(7,7,'Supporting local arts and culture.','2023-07-15'),(8,8,'Disaster relief efforts are crucial.','2023-08-31'),(9,9,'Animal welfare is close to my heart.','2023-09-09'),(10,10,'Public health education benefits everyone.','2023-10-18');
/*!40000 ALTER TABLE `donorfeedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentmethod`
--

DROP TABLE IF EXISTS `paymentmethod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paymentmethod` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method_name` varchar(50) NOT NULL,
  `methodName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentmethod`
--

LOCK TABLES `paymentmethod` WRITE;
/*!40000 ALTER TABLE `paymentmethod` DISABLE KEYS */;
INSERT INTO `paymentmethod` VALUES (1,'Credit Card',''),(2,'Debit Card',''),(3,'PayPal',''),(4,'Apple Pay',''),(5,'Google Pay',''),(6,'Bank Transfer',''),(7,'Cheque',''),(8,'Cash',''),(9,'WeChat Pay',''),(10,'Alipay','');
/*!40000 ALTER TABLE `paymentmethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receipt` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donation_id` int NOT NULL,
  `receipt_number` varchar(50) NOT NULL,
  `issued_date` date DEFAULT NULL,
  `receiptNumber` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `donation_id` (`donation_id`),
  CONSTRAINT `receipt_ibfk_1` FOREIGN KEY (`donation_id`) REFERENCES `donation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receipt`
--

LOCK TABLES `receipt` WRITE;
/*!40000 ALTER TABLE `receipt` DISABLE KEYS */;
INSERT INTO `receipt` VALUES (1,1,'HKR202301150001','2023-01-16',''),(2,2,'HKR202302200002','2023-02-21',''),(3,3,'HKR202303100003','2023-03-11',''),(4,4,'HKR202304050004','2023-04-06',''),(5,5,'HKR202305180005','2023-05-19',''),(6,6,'HKR202306220006','2023-06-23',''),(7,7,'HKR202307130007','2023-07-14',''),(8,8,'HKR202308290008','2023-08-30',''),(9,9,'HKR202309070009','2023-09-08',''),(10,10,'HKR202310160010','2023-10-17','');
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `age` int DEFAULT NULL,
  `school` varchar(100) DEFAULT NULL,
  `bio` tinytext,
  `student_image` longblob,
  `studentImage` tinyblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Thomas Lee',10,'Hong Kong Primary School','A diligent student from a low-income family.',NULL,NULL),(2,'Emily Chan',12,'Kowloon Middle School','Passionate about science and dreams of becoming a doctor.',NULL,NULL),(3,'Jason Wong',15,'New Territories High School','An aspiring artist who loves painting.',NULL,NULL),(4,'Sophia Ho',11,'Island District School','Enjoys mathematics and problem-solving.',NULL,NULL),(5,'Daniel Lam',14,'Harbour Secondary School','Wants to study engineering in the future.',NULL,NULL),(6,'Grace Cheung',13,'Peak Academy','Avid reader and aspiring writer.',NULL,NULL),(7,'Matthew Ng',9,'Happy Valley School','Loves sports and outdoor activities.',NULL,NULL),(8,'Chloe Lau',16,'Victoria High School','Interested in environmental conservation.',NULL,NULL),(9,'Andrew Tsang',10,'Aberdeen School','Enjoys music and plays the piano.',NULL,NULL),(10,'Natalie Kwok',12,'Stanley School','Dreams of becoming a teacher.',NULL,NULL);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `profile_image` longblob,
  `phoneNumber` varchar(20) DEFAULT NULL,
  `profileImage` tinyblob,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jameswong','password123','james.wong@example.hk','85291234567','Central, Hong Kong',NULL,NULL,NULL),(2,'sarahchan','password123','sarah.chan@example.hk','85291234568','Tsim Sha Tsui, Hong Kong',NULL,NULL,NULL),(3,'michaellee','password123','michael.lee@example.hk','85291234569','Causeway Bay, Hong Kong',NULL,NULL,NULL),(4,'emilyho','password123','emily.ho@example.hk','85291234570','Mong Kok, Hong Kong',NULL,NULL,NULL),(5,'davidcheung','password123','david.cheung@example.hk','85291234571','Wan Chai, Hong Kong',NULL,NULL,NULL),(6,'amykwok','password123','amy.kwok@example.hk','85291234572','Shatin, Hong Kong',NULL,NULL,NULL),(7,'benjaminng','password123','benjamin.ng@example.hk','85291234573','Kowloon Tong, Hong Kong',NULL,NULL,NULL),(8,'jessicalau','password123','jessica.lau@example.hk','85291234574','Tseung Kwan O, Hong Kong',NULL,NULL,NULL),(9,'alexchow','password123','alex.chow@example.hk','85291234575','Tsuen Wan, Hong Kong',NULL,NULL,NULL),(10,'karenli','password123','karen.li@example.hk','85291234576','Tuen Mun, Hong Kong',NULL,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (1);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-19 14:02:05
