-- MySQL dump 10.13  Distrib 8.0.25, for Win64 (x86_64)
--
-- Host: localhost    Database: availabilityschema
-- ------------------------------------------------------
-- Server version	8.0.25

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
-- Table structure for table `admintable`
--

DROP TABLE IF EXISTS `admintable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admintable` (
  `AdminID` int NOT NULL,
  `AdminName` varchar(45) DEFAULT NULL,
  `ContactNumber` varchar(45) DEFAULT NULL,
  `Email` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`AdminID`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  UNIQUE KEY `ContactNumber_UNIQUE` (`ContactNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admintable`
--

LOCK TABLES `admintable` WRITE;
/*!40000 ALTER TABLE `admintable` DISABLE KEYS */;
INSERT INTO `admintable` VALUES (1001,'AdminA','9874563214','admina@gmail.com','AdminA');
/*!40000 ALTER TABLE `admintable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointmenttable`
--

DROP TABLE IF EXISTS `appointmenttable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointmenttable` (
  `aID` int NOT NULL AUTO_INCREMENT,
  `aDate` date NOT NULL,
  `aTime` time NOT NULL,
  `DoctorId` int NOT NULL,
  `PatientID` int NOT NULL,
  PRIMARY KEY (`aID`),
  KEY `fk_AppointmentTable_DoctorTable1_idx` (`DoctorId`),
  KEY `fk_AppointmentTable_PatientTable1_idx` (`PatientID`),
  CONSTRAINT `fk_AppointmentTable_DoctorTable1` FOREIGN KEY (`DoctorId`) REFERENCES `doctortable` (`DoctorId`) ON DELETE CASCADE,
  CONSTRAINT `fk_AppointmentTable_PatientTable1` FOREIGN KEY (`PatientID`) REFERENCES `patienttable` (`PatientID`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointmenttable`
--

LOCK TABLES `appointmenttable` WRITE;
/*!40000 ALTER TABLE `appointmenttable` DISABLE KEYS */;
INSERT INTO `appointmenttable` VALUES (1,'2021-06-21','09:00:00',1,101),(3,'2021-06-21','11:00:00',1,103),(4,'2021-06-21','12:00:00',1,104),(5,'2021-06-21','09:00:00',2,105),(6,'2021-06-21','09:00:00',5,106),(7,'2021-06-21','10:00:00',2,107),(8,'2021-06-21','11:00:00',2,102),(10,'2021-06-24','14:00:00',2,102);
/*!40000 ALTER TABLE `appointmenttable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `availabilitytable`
--

DROP TABLE IF EXISTS `availabilitytable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `availabilitytable` (
  `DoctorDate` date NOT NULL,
  `InTime` time NOT NULL,
  `OutTime` time DEFAULT NULL,
  `DoctorId` int NOT NULL,
  PRIMARY KEY (`DoctorDate`,`DoctorId`),
  KEY `fk_AvailabilityTable_DoctorTable_idx` (`DoctorId`),
  KEY `docind` (`DoctorId`,`DoctorDate`),
  CONSTRAINT `fk_AvailabilityTable_DoctorTable` FOREIGN KEY (`DoctorId`) REFERENCES `doctortable` (`DoctorId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `availabilitytable`
--

LOCK TABLES `availabilitytable` WRITE;
/*!40000 ALTER TABLE `availabilitytable` DISABLE KEYS */;
INSERT INTO `availabilitytable` VALUES ('2021-06-21','08:00:00','18:00:00',1),('2021-06-21','08:00:00','18:00:00',2),('2021-06-21','08:00:00','18:00:00',3),('2021-06-21','08:00:00','18:00:00',4),('2021-06-21','08:00:00','18:00:00',5),('2021-06-22','08:00:00','18:00:00',1),('2021-06-22','08:00:00','18:00:00',2),('2021-06-22','08:00:00','18:00:00',3),('2021-06-22','08:00:00','18:00:00',4),('2021-06-22','08:00:00','18:00:00',5),('2021-06-23','11:00:00','18:00:00',2),('2021-06-24','10:00:00','20:00:00',2);
/*!40000 ALTER TABLE `availabilitytable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctortable`
--

DROP TABLE IF EXISTS `doctortable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctortable` (
  `DoctorId` int NOT NULL AUTO_INCREMENT,
  `DoctorName` varchar(45) NOT NULL,
  `DoctorGender` varchar(45) DEFAULT NULL,
  `DoctorAge` int DEFAULT NULL,
  `DoctorMobile` varchar(45) NOT NULL,
  `DoctorEmail` varchar(45) NOT NULL,
  `DoctorPassword` varchar(45) NOT NULL,
  PRIMARY KEY (`DoctorId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctortable`
--

LOCK TABLES `doctortable` WRITE;
/*!40000 ALTER TABLE `doctortable` DISABLE KEYS */;
INSERT INTO `doctortable` VALUES (1,'DoctorA','Female',49,'9000000001','doctora@gmail.com','DoctorA'),(2,'DoctorB','Female',39,'9000000002','doctorb@gmail.com','DoctorB'),(3,'DoctorC','Male',29,'9000000003','doctorc@gmail.com','DoctorC'),(4,'DoctorD','Female',28,'9000000004','doctord@gmail.com','DoctorD'),(5,'DoctorE','Female',59,'9000000005','doctore@gmail.com','DoctorE');
/*!40000 ALTER TABLE `doctortable` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `doctortable_BEFORE_INSERT` BEFORE INSERT ON `doctortable` FOR EACH ROW BEGIN
set new.DoctorName=trim(new.DoctorName);
if instr(new.DoctorEmail,'@')=0 then
set new.DoctorEmail=concat(new.DoctorEmail,'@gmail.com');
end if;
if new.DoctorMobile regexp '[1-9][0-9]{9}'
 then
set new.DoctorMobile=new.DoctorMobile;
else set new.DoctorMobile='Not Valid';
end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `patienttable`
--

DROP TABLE IF EXISTS `patienttable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patienttable` (
  `PatientID` int NOT NULL AUTO_INCREMENT,
  `PatientName` varchar(30) NOT NULL,
  `PatientGender` varchar(45) DEFAULT NULL,
  `PatientAge` int NOT NULL,
  `PatientMobile` varchar(45) NOT NULL,
  `PatientEmail` varchar(45) NOT NULL,
  `PatientPassword` varchar(45) NOT NULL,
  PRIMARY KEY (`PatientID`,`PatientEmail`),
  KEY `patind` (`PatientEmail`,`PatientPassword`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patienttable`
--

LOCK TABLES `patienttable` WRITE;
/*!40000 ALTER TABLE `patienttable` DISABLE KEYS */;
INSERT INTO `patienttable` VALUES (102,'PatientB','Male',31,'8574647823','patientb@gmail.com','PatientB'),(103,'PatientC','Female',32,'8476352379','patientc@gmail.com','PatientC'),(104,'PatientD','Female',33,'8593475873','patientd@gmail.com','PatientD'),(105,'PatientE','Female',34,'8575758392','patiente@gmail.com','PatientE'),(106,'PatientF','Male',35,'6738293040','patientf@gmail.com','PatientF'),(107,'PatientG','Female',36,'6738293041','patientg@gmail.com','PatientG'),(108,'PatientH','Female',35,'6738293042','patienth@gmail.com','PatientH'),(109,'PatientI','Male',34,'6738293043','patienti@gmail.com','PatientI'),(110,'PatientJ','Male',33,'6738293044','patientj@gmail.com','PatientJ'),(135,'PatientA','Male',55,'9282645378','patienta@gmail.com','PatientA'),(136,'PatientK','Male',24,'8686746379','patientk@gmail.com','PatientK');
/*!40000 ALTER TABLE `patienttable` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `patienttable_BEFORE_INSERT` BEFORE INSERT ON `patienttable` FOR EACH ROW BEGIN
set new.PatientName=trim(new.PatientName);
if new.PatientMobile regexp '[1-9][0-9]{9}'
 then
set new.PatientMobile=new.PatientMobile;
else set new.PatientMobile='Not Valid';
end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'availabilityschema'
--

--
-- Dumping routines for database 'availabilityschema'
--
/*!50003 DROP PROCEDURE IF EXISTS `delete_appointment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_appointment`(in pid int, in aidd int)
BEGIN
delete from appointmenttable where aID=aidd and patientID=pid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-22 11:32:36
