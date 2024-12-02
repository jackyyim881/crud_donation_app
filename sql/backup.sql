-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: donation_db
-- ------------------------------------------------------
-- Server version	8.0.32

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
  `amountRaised` decimal(38,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,'Education Support HK','Providing educational resources to underprivileged students in Hong Kong.',200000.00,'2023-01-01','2023-12-31',1001.00,NULL),(2,'Health Care Initiative','Supporting local clinics in remote areas.',150000.00,'2023-02-01','2023-11-30',1500.00,NULL),(3,'Environmental Awareness HK','Promoting recycling and waste reduction in the city.',100000.00,'2023-03-01','2023-10-31',1200.00,NULL),(4,'Elderly Care Program','Assisting elderly citizens living alone.',120000.00,'2023-04-01','2023-09-30',800.00,NULL),(5,'Community Development','Improving community facilities in urban areas.',180000.00,'2023-05-01','2023-08-31',900.00,NULL),(6,'Youth Empowerment','Providing mentorship programs for youth.',160000.00,'2023-06-01','2023-12-31',1100.00,NULL),(7,'Arts and Culture Support','Funding local artists and cultural events.',110000.00,'2023-07-01','2023-11-30',700.00,NULL),(8,'Disaster Relief HK','Emergency response for natural disasters.',250000.00,'2023-08-01','2023-12-31',1300.00,NULL),(9,'Animal Welfare','Supporting animal shelters and adoption programs.',90000.00,'2023-09-01','2023-12-31',950.00,NULL),(10,'Public Health Education','Raising awareness about health and hygiene.',130000.00,'2023-10-01','2023-12-31',850.00,NULL);
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `contact_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `submitted_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'jacky','jacky@gmail.com','1234','hello','2024-10-08 10:38:45'),(2,'hello','hello@gmail.com','hi','hi','2024-10-23 01:30:57');
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `donor_id` bigint NOT NULL,
  `student_id` int NOT NULL,
  `amount` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  `payment_method_id` int DEFAULT NULL,
  `campaign_id` int DEFAULT NULL,
  `recurring` tinyint(1) DEFAULT '0',
  `frequency` enum('monthly','yearly') DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `donor_id` (`donor_id`),
  KEY `student_id` (`student_id`),
  KEY `payment_method_id` (`payment_method_id`),
  KEY `campaign_id` (`campaign_id`),
  KEY `idx_donation_amount` (`amount`),
  KEY `idx_donation_date` (`date`),
  CONSTRAINT `donation_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`),
  CONSTRAINT `donation_ibfk_3` FOREIGN KEY (`payment_method_id`) REFERENCES `paymentmethod` (`id`),
  CONSTRAINT `donation_ibfk_4` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (1,1,1,1000,'2023-01-15',1,1,0,NULL),(2,2,2,1500,'2023-02-20',2,2,0,NULL),(3,3,3,1200,'2023-03-10',3,3,0,NULL),(4,4,4,800,'2023-04-05',4,4,0,NULL),(5,5,5,900,'2023-05-18',5,5,0,NULL),(6,6,6,1100,'2023-06-22',6,6,0,NULL),(7,7,7,700,'2023-07-13',7,7,0,NULL),(8,8,8,1300,'2023-08-29',8,8,0,NULL),(9,9,9,950,'2023-09-07',9,9,0,NULL),(10,10,10,850,'2023-10-16',10,10,0,NULL);
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
  `donor_type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_id` (`user_id`),
  CONSTRAINT `donor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donor`
--

LOCK TABLES `donor` WRITE;
/*!40000 ALTER TABLE `donor` DISABLE KEYS */;
INSERT INTO `donor` VALUES (1,1,'personal'),(2,2,'personal'),(3,3,'personal'),(4,4,'personal'),(5,5,'personal'),(6,6,'personal'),(7,7,'personal'),(8,8,'personal'),(9,9,'personal'),(10,10,'personal'),(13,17,'personal');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donorfeedback`
--

LOCK TABLES `donorfeedback` WRITE;
/*!40000 ALTER TABLE `donorfeedback` DISABLE KEYS */;
INSERT INTO `donorfeedback` VALUES (1,1,'Happy to support education in Hong Kong.','2023-01-17'),(2,2,'Great initiative for healthcare.','2023-02-22'),(3,3,'Proud to be part of environmental awareness.','2023-03-12'),(4,4,'Elderly care is important for our society.','2023-04-07'),(5,5,'Community development makes a difference.','2023-05-20'),(6,6,'Youth empowerment is the key to the future.','2023-06-24'),(7,7,'Supporting local arts and culture.','2023-07-15'),(8,8,'Disaster relief efforts are crucial.','2023-08-31'),(9,9,'Animal welfare is close to my heart.','2023-09-09'),(10,10,'Public health education benefits everyone.','2023-10-18'),(11,1,'ok','2024-10-06');
/*!40000 ALTER TABLE `donorfeedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `start` timestamp NOT NULL,
  `end` timestamp NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `color` varchar(7) DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_event_user` (`user_id`),
  CONSTRAINT `fk_event_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `homelessstudentdetails`
--

DROP TABLE IF EXISTS `homelessstudentdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `homelessstudentdetails` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `currentShelter` varchar(255) DEFAULT NULL,
  `durationOfHomelessness` varchar(100) DEFAULT NULL,
  `emergencyContact` varchar(255) DEFAULT NULL,
  `specialNeeds` text,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `homelessstudentdetails_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `homelessstudentdetails`
--

LOCK TABLES `homelessstudentdetails` WRITE;
/*!40000 ALTER TABLE `homelessstudentdetails` DISABLE KEYS */;
INSERT INTO `homelessstudentdetails` VALUES (1,1,'Downtown Shelter','3 months','Thomas LeeD\'s Aunt - 85291234567','Needs winter clothing and school supplies'),(2,2,'Kowloon Family Support Center','1 month','Emily Chan\'s Father - 85291234568','Requires assistance for science lab fees'),(3,3,'New Territories Shelter','6 months','Jason Wong\'s Uncle - 85291234569','Art supplies for painting classes'),(4,4,'Island Community Center','2 weeks','Sophia Ho\'s Mother - 85291234570','Extra tutoring in mathematics'),(5,5,'Harbour Support House','4 months','Daniel Lam\'s Sister - 85291234571','Engineering learning kits'),(6,6,'Peak Support Center','5 months','Grace Cheung\'s Guardian - 85291234572','Books for reading and writing'),(7,7,'Happy Valley Shelter','2 months','Matthew Ng\'s Mother - 85291234573','Sports gear for outdoor activities'),(8,8,'Victoria Family Shelter','3 months','Chloe Lau\'s Aunt - 85291234574','Materials for environmental science project'),(9,9,'Aberdeen Youth Center','1 month','Andrew Tsang\'s Father - 85291234575','Music sheets and piano practice time'),(10,10,'Stanley Family Center','7 months','Natalie Kwok\'s Mother - 85291234576','Teaching supplies and books for training');
/*!40000 ALTER TABLE `homelessstudentdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalrecords`
--

DROP TABLE IF EXISTS `medicalrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicalrecords` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `medicalCondition` varchar(255) DEFAULT NULL,
  `medication` varchar(255) DEFAULT NULL,
  `lastCheckupDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `medicalrecords_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalrecords`
--

LOCK TABLES `medicalrecords` WRITE;
/*!40000 ALTER TABLE `medicalrecords` DISABLE KEYS */;
INSERT INTO `medicalrecords` VALUES (1,1,'Asthma','Inhaler','2024-01-10'),(2,3,'Anxiety','Counseling Sessions','2024-01-20'),(3,4,'Allergy','Antihistamines','2024-03-10'),(4,6,'General Weakness','Vitamin Supplements','2024-04-05'),(5,7,'Minor Injury','Painkillers','2024-03-25'),(6,9,'Poor Vision','Prescription Glasses','2024-02-15');
/*!40000 ALTER TABLE `medicalrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `needs`
--

DROP TABLE IF EXISTS `needs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `needs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `needDescription` varchar(255) NOT NULL,
  `goalAmount` decimal(10,2) DEFAULT NULL,
  `currentAmount` decimal(10,2) DEFAULT '0.00',
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `needs_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `needs`
--

LOCK TABLES `needs` WRITE;
/*!40000 ALTER TABLE `needs` DISABLE KEYS */;
INSERT INTO `needs` VALUES (1,1,'Winter clothing and shoes',200.00,50.00),(2,2,'Science lab equipment',150.00,25.00),(3,3,'Art supplies for painting',300.00,100.00),(4,4,'Mathematics tutoring',250.00,150.00),(5,5,'Engineering learning kits',400.00,80.00),(6,6,'Books for reading and writing',120.00,40.00),(7,7,'Sports equipment',180.00,60.00),(8,8,'Environmental project supplies',200.00,70.00),(9,9,'Music sheets and practice time',150.00,90.00),(10,10,'Teaching training books',300.00,150.00);
/*!40000 ALTER TABLE `needs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notes`
--

DROP TABLE IF EXISTS `notes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notes`
--

LOCK TABLES `notes` WRITE;
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `donor_id` int DEFAULT NULL,
  `user_id` int NOT NULL,
  `campaign_id` int DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `timestamp` datetime NOT NULL,
  `is_read` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `donor_id` (`donor_id`),
  KEY `user_id` (`user_id`),
  KEY `campaign_id` (`campaign_id`),
  CONSTRAINT `notification_ibfk_1` FOREIGN KEY (`donor_id`) REFERENCES `donor` (`id`),
  CONSTRAINT `notification_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `notification_ibfk_3` FOREIGN KEY (`campaign_id`) REFERENCES `campaign` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentmethod`
--

LOCK TABLES `paymentmethod` WRITE;
/*!40000 ALTER TABLE `paymentmethod` DISABLE KEYS */;
INSERT INTO `paymentmethod` VALUES (1,'Credit Card'),(2,'Debit Card'),(3,'PayPal'),(4,'Apple Pay'),(5,'Google Pay'),(6,'Bank Transfer'),(7,'Cheque'),(8,'Cash'),(9,'WeChat Pay'),(10,'Alipay');
/*!40000 ALTER TABLE `paymentmethod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progressupdate`
--

DROP TABLE IF EXISTS `progressupdate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `progressupdate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `updateDescription` varchar(255) NOT NULL,
  `updateDate` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `progressupdate_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progressupdate`
--

LOCK TABLES `progressupdate` WRITE;
/*!40000 ALTER TABLE `progressupdate` DISABLE KEYS */;
INSERT INTO `progressupdate` VALUES (1,1,'Received winter clothing, feeling much warmer now.','2024-02-05'),(2,3,'Got school supplies, ready for the new semester.','2024-03-01'),(3,4,'Attended first dance class, it was amazing!','2024-03-15'),(4,6,'Medical checkup done, health condition improving.','2024-04-05'),(5,7,'Started tutoring sessions for math, feeling more confident.','2024-04-20'),(6,9,'Received new books, excited to start reading.','2024-05-01');
/*!40000 ALTER TABLE `progressupdate` ENABLE KEYS */;
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
INSERT INTO `receipt` VALUES (1,1,'HKR202301150001','2023-01-16'),(2,2,'HKR202302200002','2023-02-21'),(3,3,'HKR202303100003','2023-03-11'),(4,4,'HKR202304050004','2023-04-06'),(5,5,'HKR202305180005','2023-05-19'),(6,6,'HKR202306220006','2023-06-23'),(7,7,'HKR202307130007','2023-07-14'),(8,8,'HKR202308290008','2023-08-30'),(9,9,'HKR202309070009','2023-09-08'),(10,10,'HKR202310160010','2023-10-17');
/*!40000 ALTER TABLE `receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shelterhistory`
--

DROP TABLE IF EXISTS `shelterhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shelterhistory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `shelterName` varchar(255) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `shelterhistory_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shelterhistory`
--

LOCK TABLES `shelterhistory` WRITE;
/*!40000 ALTER TABLE `shelterhistory` DISABLE KEYS */;
INSERT INTO `shelterhistory` VALUES (1,1,'Downtown Shelter','2023-11-01','2024-02-01'),(2,3,'Community Support Center','2023-08-15','2024-02-15'),(3,4,'St. Mary\'s Shelter','2024-01-01',NULL),(4,6,'Northside Shelter','2023-09-20','2024-03-20'),(5,7,'Safe Haven Shelter','2024-03-01',NULL),(6,9,'Hope Shelter','2023-12-10','2024-04-10');
/*!40000 ALTER TABLE `shelterhistory` ENABLE KEYS */;
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
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `isHomeless` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'Thomas LeeDs',11,'Hong Kong Primary School','A diligent student from a low-income family',NULL,NULL,22.2849,114.1586,0),(2,'Emily Chan',12,'Kowloon Middle School','Passionate about science and dreams of becoming a doctor.',NULL,NULL,22.3364,114.1601,0),(3,'Jason Wong',15,'New Territories High School','An aspiring artist who loves painting.',NULL,NULL,22.3721,114.1115,0),(4,'Sophia Ho',11,'Island District School','Enjoys mathematics and problem-solving.',NULL,NULL,22.2809,114.2005,0),(5,'Daniel Lam',14,'Harbour Secondary School','Wants to study engineering in the future.',NULL,NULL,22.3152,114.1695,0),(6,'Grace Cheung',13,'Peak Academy','Avid reader and aspiring writer.',NULL,NULL,22.2569,114.1831,0),(7,'Matthew Ng',9,'Happy Valley School','Loves sports and outdoor activities.',NULL,NULL,22.2783,114.1825,0),(8,'Chloe Lau',16,'Victoria High School','Interested in environmental conservation.',NULL,NULL,22.3193,114.1751,0),(9,'Andrew Tsang',10,'Aberdeen School','Enjoys music and plays the piano.',NULL,NULL,22.2827,114.1365,0),(10,'Natalie Kwok',12,'Stanley School','Dreams of becoming a teacher.',NULL,NULL,22.2933,114.2007,0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supportservices`
--

DROP TABLE IF EXISTS `supportservices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supportservices` (
  `id` int NOT NULL AUTO_INCREMENT,
  `student_id` int DEFAULT NULL,
  `serviceType` varchar(100) NOT NULL,
  `serviceDescription` text,
  `serviceDate` date NOT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `supportservices_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supportservices`
--

LOCK TABLES `supportservices` WRITE;
/*!40000 ALTER TABLE `supportservices` DISABLE KEYS */;
/*!40000 ALTER TABLE `supportservices` ENABLE KEYS */;
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
  `phone_number` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `profileImage` tinyblob,
  `name` varchar(50) DEFAULT NULL,
  `role` varchar(50) NOT NULL DEFAULT 'ROLE_USER',
  `currency` varchar(10) NOT NULL,
  `language` varchar(10) NOT NULL,
  `email_notifications` tinyint(1) NOT NULL DEFAULT '0',
  `sms_notifications` tinyint(1) NOT NULL DEFAULT '0',
  `profile_image_path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'jameswong','password123','james.wong@example.hk','85291234567','Central, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(2,'sarahchan','password123','sarah.chan@example.hk','85291234568','Tsim Sha Tsui, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(3,'michaellee','password123','michael.lee@example.hk','85291234569','Causeway Bay, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(4,'emilyho','password123','emily.ho@example.hk','85291234570','Mong Kok, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(5,'davidcheung','password123','david.cheung@example.hk','85291234571','Wan Chai, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(6,'amykwok','password123','amy.kwok@example.hk','85291234572','Shatin, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(7,'benjaminng','password123','benjamin.ng@example.hk','85291234573','Kowloon Tong, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(8,'jessicalau','password123','jessica.lau@example.hk','85291234574','Tseung Kwan O, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(9,'alexchow','password123','alex.chow@example.hk','85291234575','Tsuen Wan, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(10,'karenli','password123','karen.li@example.hk','85291234576','Tuen Mun, Hong Kong',NULL,'','ROLE_USER','','',0,0,NULL),(17,'test','$2a$10$hZ8sWcoi2bVRNyY5w5baLep0GvAhEi.8HWZnLNjEbAS47IRryPYM6','test@gmail.com',NULL,NULL,NULL,NULL,'ROLE_USER','USD','en',0,0,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `status` enum('active','inactive') DEFAULT 'active',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (17,1,'2024-11-07 03:46:27','active');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
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

-- Dump completed on 2024-11-07 13:32:52
