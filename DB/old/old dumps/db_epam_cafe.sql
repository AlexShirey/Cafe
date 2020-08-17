CREATE DATABASE  IF NOT EXISTS `epam_cafe` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `epam_cafe`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: epam_cafe
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `dish`
--

DROP TABLE IF EXISTS `dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish` (
  `dish_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id блюда',
  `type_id` tinyint(3) unsigned NOT NULL COMMENT 'id типа блюда, F.K.',
  `name` varchar(45) NOT NULL COMMENT 'название блюда',
  `description` text COMMENT 'описание блюда',
  `dish_price` decimal(5,2) unsigned NOT NULL COMMENT 'цена блюда',
  `in_menu` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'boolean значение, если in_menu=1, то блюдо доступно для заказа (есть в меню), если  in_menu=0, то блюдо не доступно для заказа (нет в меню)',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'дата создания блюда',
  PRIMARY KEY (`dish_id`),
  KEY `idx_fk_dish_type` (`type_id`),
  KEY `idx_dish_name` (`name`),
  CONSTRAINT `fk_dish_type` FOREIGN KEY (`type_id`) REFERENCES `dish_type` (`type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='Таблица, содержащая и описывающая все блюда.\nЕсли для какого-то блюда значение in_menu=1, то это блюдо доступно для заказа (есть в меню).';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish`
--

LOCK TABLES `dish` WRITE;
/*!40000 ALTER TABLE `dish` DISABLE KEYS */;
INSERT INTO `dish` VALUES (1,1,'3 Cheese Tomato','Our signature tomato soup, with cheddar, jack and parmesan cheeses.',10.00,1,'2017-10-01 12:00:00'),(2,1,'Gazpacho','Chilled Spanish-style cucumber, pepper and tomato. Refreshing',9.00,1,'2017-10-01 12:00:00'),(3,1,'Chicken Posole','Delicious roast pork and corn hominy stew, with chiles, lime juice, bell peppers and more',8.00,1,'2017-10-01 12:00:00'),(4,1,'Curry Red Potato ','Chunky and delicious, featuring our own toasted curry spice mix with Okra',8.00,1,'2017-10-01 12:00:00'),(5,1,'Chef soup','Depends on chefs daily desire. Just try it.',7.50,1,'2017-10-01 12:00:00'),(6,2,'Caesar','Classic Caesar salad with chicken. Perfect mix of cream and crunch.',4.50,1,'2017-10-01 12:00:00'),(7,2,'Cucumber, Black Olive and Mint Salad','A chunky salad of cucumbers, cherry tomatoes, peppery mint leaves drizzled with a black olive sauce.',3.00,1,'2017-10-01 12:00:00'),(8,2,'Carrot Salad with Black Grape Dressing','A carrot salad with a freshly made black grape dressing.',3.00,1,'2017-10-01 12:00:00'),(9,2,'BBQ Potato Salad','The good old potato salad with a twist! This one has some cola flavored BBQ sauce as a dressing ingredient.',3.00,1,'2017-10-01 12:00:00'),(10,2,'Olivier','Classic olivier with chicken',4.00,1,'2017-10-01 12:00:00'),(11,3,'Foiled Fish','The filet is herb-crusted, and then cooked in foil with lemon and butter. Served with campfire roasted local squash and corn',7.00,1,'2017-10-01 12:00:00'),(12,3,'Turkey','Ground turkey seasoned with Cajun spices and minced red onion.',6.00,1,'2017-10-01 12:00:00'),(13,3,'Pasta with Meatballs','The ground beef is seasoned \"Italian\" style. Served over pasta with red sauce.',6.00,1,'2017-10-01 12:00:00'),(14,3,'Pizza','Pizza from chef',5.00,1,'2017-10-01 12:00:00'),(15,4,'Chocolate Chip Cookie','Our large chocolate chip cookie!',3.00,1,'2017-10-01 12:00:00'),(16,4,'Fresh Fruits','Start or finish your meal with freshly sliced fruit.  We choose fruits of',3.00,1,'2017-10-01 12:00:00'),(17,4,'Ice Cream','You Scream, I Scream, We all Scream for Ice Cream!',1.50,1,'2017-10-01 12:00:00'),(18,4,'Pound Cake','Our pound cake is made with plenty of eggs, milk and butter.  Baked to',2.50,1,'2017-10-01 12:00:00'),(19,5,'Espresso',NULL,2.00,1,'2017-10-01 12:00:00'),(20,5,'Americano',NULL,2.00,1,'2017-10-01 12:00:00'),(21,5,'Cappuciono',NULL,3.00,1,'2017-10-01 12:00:00'),(22,5,'Latte',NULL,3.00,1,'2017-10-01 12:00:00'),(23,5,'Black Tea',NULL,1.30,1,'2017-10-01 12:00:00'),(24,5,'Green Tea',NULL,1.30,1,'2017-10-01 12:00:00'),(25,6,'Fanta',NULL,1.50,1,'2017-10-01 12:00:00'),(26,6,'Coca Cola',NULL,1.50,1,'2017-10-01 12:00:00'),(27,6,'Sprite',NULL,1.50,1,'2017-10-01 12:00:00'),(28,6,'Bonaqua',NULL,1.50,1,'2017-10-01 12:00:00');
/*!40000 ALTER TABLE `dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dish_type`
--

DROP TABLE IF EXISTS `dish_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dish_type` (
  `type_id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id типа',
  `type` varchar(45) NOT NULL COMMENT 'тип блюда (суп, салат, основное блюдо, десерт, горячий напиток, охлаждающий напиток)',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Таблица, описывающая тип блюда (салат, суп и т.д.)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dish_type`
--

LOCK TABLES `dish_type` WRITE;
/*!40000 ALTER TABLE `dish_type` DISABLE KEYS */;
INSERT INTO `dish_type` VALUES (1,'soup'),(2,'salad'),(3,'main_dish'),(4,'dessert'),(5,'hot_drink'),(6,'soft_drink');
/*!40000 ALTER TABLE `dish_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id заказа',
  `user_id` smallint(5) unsigned NOT NULL COMMENT 'id пользователя, F.K.',
  `payment_type` enum('ACCOUNT','CASH','LOYALTY_POINTS') NOT NULL COMMENT 'тип платежа - с клиентского счета, наличными при доставке заказа или баллы лояльности . Указывается клиентом при заказе.',
  `pick_up_time` datetime NOT NULL COMMENT 'желаемое время получения заказа, указывается клиентом',
  `order_price` decimal(8,2) unsigned NOT NULL COMMENT 'общая цена заказа\n',
  `is_paid` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'статус платежа, указывает, совершен ли платеж или нет. boolean значение, 1 - платеж совершен, 0 - платеж не совершен',
  `status` enum('ACTIVE','CANCELLED','FINISHED') NOT NULL DEFAULT 'ACTIVE' COMMENT 'статус заказа - active, cancelled, finished',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'время создания заказа',
  `rating` tinyint(1) unsigned DEFAULT NULL COMMENT 'оценка, которую пользователь выставляет за заказ, от 1 до 5. по умолчанию NULL',
  `review` text COMMENT 'отзыв, который может оставить пользователь после того, как выставил оценку. по умолчанию NULL',
  PRIMARY KEY (`order_id`),
  KEY `idx_fk_order_user` (`user_id`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='таблица, содержащая и описывающая заказы, формируемые пользоваетелями';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_has_dish`
--

DROP TABLE IF EXISTS `order_has_dish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_has_dish` (
  `order_id` smallint(5) unsigned NOT NULL COMMENT 'id заказа',
  `dish_id` smallint(5) unsigned NOT NULL COMMENT 'id блюда',
  `dish_price` decimal(5,2) unsigned NOT NULL COMMENT 'цена блюда в заказе',
  `dish_quantity` smallint(5) unsigned NOT NULL COMMENT 'кол-во ',
  PRIMARY KEY (`order_id`,`dish_id`),
  KEY `idx_fk_order_has_dish_dish` (`dish_id`),
  KEY `idx_fk_order_has_dish_order` (`order_id`),
  CONSTRAINT `fk_order_has_dish_dish1` FOREIGN KEY (`dish_id`) REFERENCES `dish` (`dish_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_dish_order1` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='таблица связи заказ : блюдо.\nодин заказ содержит от 1 до n блюд\nодно блюдо может иметь от 0 до n заказов';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_has_dish`
--

LOCK TABLES `order_has_dish` WRITE;
/*!40000 ALTER TABLE `order_has_dish` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_has_dish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id пользователя',
  `email` varchar(40) NOT NULL COMMENT 'email пользователя, он же логин, должен быть уникальным\n',
  `password` char(32) NOT NULL COMMENT 'пароль пользователя',
  `first_name` varchar(20) NOT NULL COMMENT 'имя пользователя',
  `last_name` varchar(20) NOT NULL COMMENT 'фамилия пользователя',
  `phone` char(20) NOT NULL COMMENT 'телефон пользователя',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'дата регистрации пользователя',
  `balance` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'баланс пользователя - сумма денег на клиентском счете, по умолчанию 0',
  `loyalty_points` decimal(8,2) unsigned NOT NULL DEFAULT '0.00' COMMENT 'баллы лояльности пользователя, по умолчанию 0. Начисляются после заказа ',
  `active` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT 'статус пользователя, boolean значение, 1 - пользователь не забанен, 0 - пользователь забанен',
  `role_id` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT 'id роли пользователя, определяющая его права. F.K. По умолчанию устанавливается значение role_id = 1, cоответсвующее типу USER таблицы role',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `idx_email_UNIQUE` (`email`),
  KEY `idx_fk_user_role` (`role_id`),
  KEY `idx_second_name` (`last_name`),
  CONSTRAINT `fk_user_role` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='пользователи';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin@gmail.com','ed3e97c60f89b4748ac9b1072f8c7d23','Alex','Shirey','+375296126109','2017-10-01 11:00:00',0.00,0.00,1,2),(2,'customer@gmail.com','ed3e97c60f89b4748ac9b1072f8c7d23','Jacob','Smith','+375291234567','2017-10-02 12:00:00',30.00,0.00,1,1),(3,'sophia_smith@gmail.com','9b9270e52ee0bc7561bf61e7e161b78d','Sophia','Smtih','+375445556785','2017-10-02 13:15:00',50.00,0.00,1,1),(4,'emily55@gmail.com','49a7f3b7c901db91a5f069df5495bf3c','Emily','Johnson','+375296845512','2017-10-03 17:00:00',70.00,0.00,1,1),(5,'lily1983@gmail.com','2624782b52062a5f8003568fdf93c50e','Lily','Brown','+375296541232','2017-10-04 19:00:00',80.00,0.00,1,1),(6,'mason_taylor@gmail.com','305d99cf63c0239fa904db9067c84f57','Mason','Taylor','+375448879551','2017-10-05 22:05:00',90.00,0.00,1,1),(7,'justlogan@gmail.com','f19ae0a6efda61bf44baf9170f80d85a','Logan','Davis','+375331265458','2017-10-06 17:10:00',100.00,100.00,1,1),(8,'miller87@gmail.com','bc03948afd745adc17fa62f076a7daac','Lucas','Miller','+375336126619','2017-10-07 13:05:00',20.00,0.00,1,1),(9,'john_white@gmail.com','05f88b2eb27adc9651f318e1dd72ec4e','John','White','+375445559898','2017-10-08 11:00:00',115.00,0.00,1,1),(10,'hoch_black@gmail.com','32668d3dc5c82245cf84ba907634645b','John','Black','+375296126778','2017-10-09 11:30:00',220.00,150.00,1,1),(11,'christi@gmail.com','b0572cfc05b3889b052b4d098d860924','Christian','Lewis','+375443389651','2017-10-10 16:55:00',5.00,0.00,1,1),(12,'emily99@gmail.com','3b4b3481ead00ffe42ee04c4f0e07da2','Emily','Murphy','+375296512398','2017-10-11 16:09:00',0.00,0.00,1,1),(13,'olivia_cooper@gmail.com','19cb11657e71fb2a4b8e1ee3ac504e29','Olivia','Cooper','+375445986232','2017-10-12 23:59:00',0.00,0.00,0,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `role_id` tinyint(1) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id роли пользователя',
  `role` varchar(20) NOT NULL COMMENT 'роль пользователя, определяющая его права. роль может быть двух типов - customer или admin',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='таблица, описывающая роль поль пользователя (customer, admin)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,'customer'),(2,'admin');
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-22 22:31:43
