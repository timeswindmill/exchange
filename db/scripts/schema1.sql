CREATE DATABASE IF NOT EXISTS `fingen1` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `fingen1`;
-- MySQL dump 10.13  Distrib 5.5.34, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: fingen1
-- ------------------------------------------------------
-- Server version	5.5.34-0ubuntu0.13.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `Clients`
--

DROP TABLE IF EXISTS `Clients`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Clients` (
  `ID`         INT(11) NOT NULL,
  `ClientName` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `InstrumentStatic`
--

DROP TABLE IF EXISTS `InstrumentStatic`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `InstrumentStatic` (
  `ID`           INT(11) NOT NULL,
  `Name`         VARCHAR(45) DEFAULT NULL,
  `Symbol`       VARCHAR(10) DEFAULT NULL,
  `UnderlyingID` INT(11) DEFAULT NULL,
  `RefPrice`     VARCHAR(45) DEFAULT NULL,
  `Multiplier`   INT(11) DEFAULT NULL,
  `TickSize`     DECIMAL(10, 0) DEFAULT NULL,
  PRIMARY KEY (`ID`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `executionReports`
--

DROP TABLE IF EXISTS `executionReports`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `executionReports` (
  `client`        VARCHAR(45) DEFAULT NULL,
  `ClientOrderID` VARCHAR(45) DEFAULT NULL,
  `ExecutionID`   VARCHAR(45) NOT NULL DEFAULT '1',
  `ExecutionTime` BIGINT(20) DEFAULT NULL,
  `Quantity`      INT(11) DEFAULT NULL,
  `Price`         DECIMAL(10, 0) DEFAULT NULL,
  PRIMARY KEY (`ExecutionID`)
)
  ENGINE =InnoDB
  DEFAULT CHARSET =latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `executionSummary`
--

DROP TABLE IF EXISTS `executionSummary`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `executionSummary` (
  `exectime`   INT(11) DEFAULT NULL,
  `instrument` VARCHAR(45) DEFAULT NULL,
  `price`      VARCHAR(45) DEFAULT NULL,
  `uptick`     INT(11) DEFAULT NULL
)
  ENGINE =InnoDB
  DEFAULT CHARSET =latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2013-11-21  8:30:33
