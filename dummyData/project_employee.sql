-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: project
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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `EmployeeID` varchar(10) NOT NULL,
  `EmployeeName` varchar(50) NOT NULL,
  `DOB` date NOT NULL,
  `EmploymentDate` date NOT NULL,
  `EmailAddress` varchar(100) NOT NULL,
  `DepartmentID` varchar(10) DEFAULT NULL,
  `PhoneNum` varchar(20) NOT NULL,
  `Address` varchar(100) NOT NULL,
  `Salary` double NOT NULL,
  `BankInfo` varchar(100) NOT NULL,
  PRIMARY KEY (`EmployeeID`),
  KEY `DepartmentID` (`DepartmentID`),
  CONSTRAINT `employee_ibfk_1` FOREIGN KEY (`DepartmentID`) REFERENCES `department` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('EMP01','Jake Witherspoon','1990-01-01','2022-01-01','jack.withS@example.com','DEP02','123-456-7890','123 Main St',50000,'BankInfo01'),('EMP02','Alice John','1985-05-15','2022-02-15','alice.john@example.com','DEP02','987-654-3210','456 Oak St',60000,'BankInfo02'),('EMP03','Bobby Williams','1992-07-20','2022-03-10','bobby.williams@example.com','DEP04','555-123-7890','789 Elm St',70000,'BankInfo03'),('EMP04','Charlz waterson','1988-03-08','2022-04-05','charlz.themitch@example.com','DEP02','111-222-3333','321 Pine St',55000,'BankInfo04'),('EMP05','Mohammed klai','1995-12-12','2022-05-20','other1@example.com','DEP02','999-888-7777','987 Birch St',75000,'BankInfo05'),('EMP06','Emily White','1993-08-21','2022-06-15','emily.white@example.com','DEP01','555-666-7777','543 Maple St',60000,'BankInfo06'),('EMP07','Daniel Johnson','1987-04-30','2022-07-01','daniel.johnson@example.com','DEP03','777-888-9999','678 Pine St',70000,'BankInfo07'),('EMP08','Grace Davis','1990-11-12','2022-08-10','grace.davis@example.com','DEP02','888-999-0000','765 Cedar St',80000,'BankInfo08'),('EMP09','Andrew Smith','1994-02-25','2022-09-05','andrew.smith@example.com','DEP01','999-000-1111','876 Birch St',90000,'BankInfo09'),('EMP10','Sophia Brown','1989-07-08','2022-10-20','sophia.brown@example.com','DEP04','111-222-3333','987 Oak St',95000,'BankInfo10'),('EMP11','Liam Wilson','1996-03-17','2022-11-15','liam.wilson@example.com','DEP01','333-444-5555','234 Elm St',100000,'BankInfo11'),('EMP12','Ella Martinez','1984-09-02','2022-12-01','ella.martinez@example.com','DEP02','444-555-6666','432 Pine St',75000,'BankInfo12'),('EMP13','Aiden Taylor','1991-01-18','2023-01-10','aiden.taylor@example.com','DEP04','555-666-7777','543 Maple St',80000,'BankInfo13'),('EMP14','Ava Anderson','1986-06-05','2023-02-15','ava.anderson@example.com','DEP03','666-777-8888','654 Birch St',85000,'BankInfo14'),('EMP15','Oliver Hernandez','1997-12-24','2023-03-10','oliver.hernandez@example.com','DEP03','777-888-9999','765 Cedar St',90000,'BankInfo15'),('EMP16','Mia Thomas','1983-04-11','2023-04-05','mia.thomas@example.com','DEP02','888-999-0000','876 Birch St',95000,'BankInfo16'),('M01','John Smith','1980-01-01','2022-01-01','john.smith@example.com','DEP01','111-111-1111','Manager Address 1',80000,'ManagerBankInfo01'),('M02','Alice Johnson','1982-05-15','2022-02-15','alice.johnson@example.com','DEP02','222-222-2222','Manager Address 2',90000,'ManagerBankInfo02'),('M03','Bob Williams','1978-07-20','2022-03-10','bob.williams@example.com','DEP03','333-333-3333','Manager Address 3',95000,'ManagerBankInfo03'),('M04','Charlie Davis','1985-03-08','2022-04-05','charlie.davis@example.com','DEP04','444-444-4444','Manager Address 4',100000,'ManagerBankInfo04');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-20 16:30:52
