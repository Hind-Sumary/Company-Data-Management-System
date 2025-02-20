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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `pr_ID` varchar(20) DEFAULT NULL,
  `pr_name` varchar(50) DEFAULT NULL,
  `pr_height` double DEFAULT NULL,
  `pr_length` double DEFAULT NULL,
  `pr_voltage` double DEFAULT NULL,
  `pr_wattage` double DEFAULT NULL,
  `pr_color` varchar(20) DEFAULT NULL,
  `pr_price` double DEFAULT NULL,
  `pr_quantity` int NOT NULL DEFAULT '0',
  `SupplierName` varchar(20) DEFAULT NULL,
  KEY `SupplierName` (`SupplierName`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`SupplierName`) REFERENCES `supplier` (`SupplierName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('Product01','Twinkle',10,10,220,7.8,'Red',100,88,'Supplier1'),('Product02','Icile',18,18,220,7.2,'Warm White',80,50,'Supplier2'),('Product03','Curtain(2.2*20)',12,12,220,25.2,'White',60,70,'Supplier3'),('Product04','SnowFall',9,9,220,36,'Cool White',33,45,'Supplier2'),('Product05','Neon Flex',38,38,220,25,'Warm White',22,90,'Supplier2'),('Product06','GarLand',10,10,220,9,'Yellow',101,70,'Supplier1'),('Product07','MOTIF PI239',0.98,0.98,220,60,'White',129,55,'Supplier2'),('Product08','MOTIF IPLO28B',3,1,220,50,'Blue',84,48,'Supplier3'),('Product09','Large MOON',4,3,220,40,'Warm White',227,75,'Supplier3'),('Product10','MOTIF MOON',2,0.9,220,68,'Pure White',202,85,'Supplier1'),('Product11','MOTIF Pixel',1,1,220,70,'White',188,55,'Supplier1'),('Product12','Pixel Tunnle',3.8,11,220,50,'Pink',23,30,'Supplier2'),('Product13','Pixel Free',3.5,10,220,60,'Orange',69,100,'Supplier3'),('Product14','Pixel Garland',4,10,220,50,'Purple',55,285,'Supplier1');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
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
