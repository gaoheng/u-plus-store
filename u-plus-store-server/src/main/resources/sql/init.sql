-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: u_plus_store
-- ------------------------------------------------------
-- Server version	5.7.14-log

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
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `skuId` int(11) NOT NULL,
  `price` decimal(9,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `IDX_ORDER_ID` (`orderId`),
  KEY `IDX_SKU_ID` (`skuId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,4,99.99,99,'2018-05-07 19:16:38','2018-05-07 11:16:38');
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` decimal(9,2) NOT NULL DEFAULT '0.00',
  `discounted` decimal(9,2) NOT NULL DEFAULT '0.00',
  `actuallyPaid` decimal(9,2) NOT NULL DEFAULT '0.00',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,100.00,8.88,91.12,'2018-05-07 19:03:28','2018-05-07 11:03:28');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skus`
--

DROP TABLE IF EXISTS `sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sku` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '名称',
  `code` varchar(16) NOT NULL DEFAULT '' COMMENT '编码',
  `codeSource` varchar(16) DEFAULT '' COMMENT '编码来源: ORIGINAL 原厂编码, GENERATED 生成编码',
  `price` decimal(9,2) DEFAULT '0.00' COMMENT '价格',
  `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
  `color` varchar(16) NOT NULL DEFAULT '' COMMENT '颜色',
  `size` varchar(16) NOT NULL DEFAULT '' COMMENT '尺码',
  `source` varchar(16) DEFAULT '' COMMENT '来源',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skus`
--

LOCK TABLES `skus` WRITE;
/*!40000 ALTER TABLE `skus` DISABLE KEYS */;
INSERT INTO `skus` VALUES (1,' 纯色中大童纯棉花边衬衣长袖','111',111.11,1,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526120231646&di=c707802711fb6c7659fa5ac0d78bf308&imgtype=0&src=http%3A%2F%2Fimg011.hc360.cn%2Fm2%2FM01%2F7A%2F03%2FwKhQcVRAB-aEaoDwAAAAABlfcvQ790.jpg','http://','纯色','135','2018-05-12 12:48:49','2018-05-12 08:23:36'),(2,'男童冷酷到底棉服','222',222.22,2,'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3892630385,603469629&fm=11&gp=0.jpg','','军绿','S','2018-05-12 15:32:19','2018-05-12 08:23:36'),(3,'把harmont&blaine(小黄狗),童装','333',333.33,3,'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526718141&di=478f2692bf20b8ca61451cd1ff581659&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.t100.cn%2Fupload%2F2011%2F12%2F09%2F1642245ichei7m.jpg','','卡其','L','2018-05-12 15:32:19','2018-05-12 08:23:36'),(4,'欧美风花朵腰带女童裙 公主','444',444.44,4,'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3904116457,2373067000&fm=11&gp=0.jpg','http://','白','XL','2018-05-07 18:34:35','2018-05-12 08:23:36');
/*!40000 ALTER TABLE `skus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

CREATE TABLE `order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) NOT NULL,
  `skuId` int(11) NOT NULL,
  `skuName` varchar(32) DEFAULT NULL,
  `skuCode` varchar(16) DEFAULT NULL,
  `skuColor` varchar(16) DEFAULT NULL,
  `skuSize` varchar(16) DEFAULT NULL,
  `skuPrice` decimal(9,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `IDX_ORDER_ID` (`orderId`),
  KEY `IDX_SKU_ID` (`skuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total` decimal(9,2) NOT NULL DEFAULT '0.00',
  `discount` decimal(9,2) NOT NULL DEFAULT '0.00',
  `paid` decimal(9,2) NOT NULL DEFAULT '0.00',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `skus_importing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batchNo` varchar(32) NOT NULL,
  `name` varchar(32) NOT NULL,
  `code` varchar(16) NOT NULL,
  `color` varchar(16) NOT NULL,
  `size` varchar(16) NOT NULL,
  `price` decimal(9,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `createTime` datetime DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `stock_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `skuId` int(11) NOT NULL DEFAULT '-1',
  `type` varchar(16) NOT NULL DEFAULT '',
  `delta` int(11) NOT NULL DEFAULT '0',
  `reason` varchar(32) NOT NULL DEFAULT '',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_skuId` (`skuId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-12 16:44:24
