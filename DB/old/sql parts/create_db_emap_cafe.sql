-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Schema epam_cafe
--
-- Описание предметной области.
-- Вы работаете администратором кафе. Вашей задачей является отслеживание финансовой стороны работы кафе.
-- Деятельность кафе организована следующим образом: кафе предоставляет меню клиентам для формирования заказа из блюд, представленных в этом меню. Каждое блюдо характеризуется типом (салаты, супы, основные блюда, десерты, горячие напитки, охлаждающие напитки), описанием и ценой.
-- Клиентами кафе являются различные люди, у которых собирается определенная информация (емаил, пароль, имя, фамилия, телефон).  Клиент формирует заказ, в котором указывает желаемые блюда и время, когда бы он хотел получить этот заказ. В заказе отражается итоговая цена, а также тип оплаты – с клиентского счета, наличными при получении заказа или начисляемыми баллами лояльности. Клиент может отменить заказ. После того, как заказ готов (наступило время получения заказа), клиент может забрать заказ, поставить оценку заказу и оставить отзыв.
-- Развитие постановки задачи:
-- Необходимо предусмотреть начисление клиенту баллов лояльности за предварительные заказы. Если клиент делает заказ, но не забирает его, то баллы лояльности снимаются вплоть до блокировки клиента.
-- 
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `epam_cafe` DEFAULT CHARACTER SET utf8 ;
USE `epam_cafe` ;

-- -----------------------------------------------------
-- Table `epam_cafe`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_cafe`.`user_role` (
  `role_id` TINYINT(1) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id роли пользователя',
  `role` VARCHAR(20) NOT NULL COMMENT 'роль пользователя, определяющая его права. роль может быть двух типов - customer или admin',
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
COMMENT = 'таблица, описывающая роль поль пользователя (customer, admin)';


-- -----------------------------------------------------
-- Table `epam_cafe`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_cafe`.`user` (
  `user_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id пользователя',
  `email` VARCHAR(40) NOT NULL COMMENT 'email пользователя, он же логин, должен быть уникальным\n',
  `password` CHAR(32) NOT NULL COMMENT 'пароль пользователя',
  `first_name` VARCHAR(20) NOT NULL COMMENT 'имя пользователя',
  `last_name` VARCHAR(20) NOT NULL COMMENT 'фамилия пользователя',
  `phone` CHAR(20) NOT NULL COMMENT 'телефон пользователя',
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'дата регистрации пользователя',
  `balance` DECIMAL(8,2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'баланс пользователя - сумма денег на клиентском счете, по умолчанию 0',
  `loyalty_points` DECIMAL(8,2) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'баллы лояльности пользователя, по умолчанию 0. Начисляются после заказа ',
  `active` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'статус пользователя, boolean значение, 1 - пользователь не забанен, 0 - пользователь забанен',
  `role_id` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT 'id роли пользователя, определяющая его права. F.K. По умолчанию устанавливается значение role_id = 1, cоответсвующее типу USER таблицы role',
  PRIMARY KEY (`user_id`),
  INDEX `idx_fk_user_role` (`role_id` ASC),
  UNIQUE INDEX `idx_email_UNIQUE` (`email` ASC),
  INDEX `idx_second_name` (`last_name` ASC),
  CONSTRAINT `fk_user_role`
    FOREIGN KEY (`role_id`)
    REFERENCES `epam_cafe`.`user_role` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'пользователи';


-- -----------------------------------------------------
-- Table `epam_cafe`.`dish_type`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_cafe`.`dish_type` (
  `type_id` TINYINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id типа',
  `type` VARCHAR(45) NOT NULL COMMENT 'тип блюда (суп, салат, основное блюдо, десерт, горячий напиток, охлаждающий напиток)',
  PRIMARY KEY (`type_id`))
ENGINE = InnoDB
COMMENT = 'Таблица, описывающая тип блюда (салат, суп и т.д.)';


-- -----------------------------------------------------
-- Table `epam_cafe`.`dish`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_cafe`.`dish` (
  `dish_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id блюда',
  `type_id` TINYINT UNSIGNED NOT NULL COMMENT 'id типа блюда, F.K.',
  `name` VARCHAR(45) NOT NULL COMMENT 'название блюда',
  `description` TEXT NULL DEFAULT NULL COMMENT 'описание блюда',
  `dish_price` DECIMAL(5,2) UNSIGNED NOT NULL COMMENT 'цена блюда',
  `in_menu` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'boolean значение, если in_menu=1, то блюдо доступно для заказа (есть в меню), если  in_menu=0, то блюдо не доступно для заказа (нет в меню)',
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'дата создания блюда',
  PRIMARY KEY (`dish_id`),
  INDEX `idx_fk_dish_type` (`type_id` ASC),
  INDEX `idx_dish_name` (`name` ASC),
  CONSTRAINT `fk_dish_type`
    FOREIGN KEY (`type_id`)
    REFERENCES `epam_cafe`.`dish_type` (`type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Таблица, содержащая и описывающая все блюда.\nЕсли для какого-то блюда значение in_menu=1, то это блюдо доступно для заказа (есть в меню).';


-- -----------------------------------------------------
-- Table `epam_cafe`.`order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_cafe`.`order` (
  `order_id` SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id заказа',
  `user_id` SMALLINT UNSIGNED NOT NULL COMMENT 'id пользователя, F.K.',
  `payment_type` ENUM('ACCOUNT', 'CASH', 'LOYALTY_POINTS') NOT NULL COMMENT 'тип платежа - с клиентского счета, наличными при доставке заказа или баллы лояльности . Указывается клиентом при заказе.',
  `pick_up_time` DATETIME NOT NULL COMMENT 'желаемое время получения заказа, указывается клиентом',
  `order_price` DECIMAL(8,2) UNSIGNED NOT NULL COMMENT 'общая цена заказа\n',
  `is_paid` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT 'статус платежа, указывает, совершен ли платеж или нет. boolean значение, 1 - платеж совершен, 0 - платеж не совершен',
  `status` ENUM('ACTIVE', 'CANCELLED', 'FINISHED') NOT NULL DEFAULT 'ACTIVE' COMMENT 'статус заказа - active, cancelled, finished',
  `create_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'время создания заказа',
  `rating` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT 'оценка, которую пользователь выставляет за заказ, от 1 до 5. по умолчанию NULL',
  `review` TEXT NULL DEFAULT NULL COMMENT 'отзыв, который может оставить пользователь после того, как выставил оценку. по умолчанию NULL',
  PRIMARY KEY (`order_id`),
  INDEX `idx_fk_order_user` (`user_id` ASC),
  CONSTRAINT `fk_order_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `epam_cafe`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'таблица, содержащая и описывающая заказы, формируемые пользоваетелями';


-- -----------------------------------------------------
-- Table `epam_cafe`.`order_has_dish`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `epam_cafe`.`order_has_dish` (
  `order_id` SMALLINT UNSIGNED NOT NULL COMMENT 'id заказа',
  `dish_id` SMALLINT UNSIGNED NOT NULL COMMENT 'id блюда',
  `dish_price` DECIMAL(5,2) UNSIGNED NOT NULL COMMENT 'цена блюда в заказе',
  `dish_quantity` SMALLINT UNSIGNED NOT NULL COMMENT 'кол-во ',
  PRIMARY KEY (`order_id`, `dish_id`),
  INDEX `idx_fk_order_has_dish_dish` (`dish_id` ASC),
  INDEX `idx_fk_order_has_dish_order` (`order_id` ASC),
  CONSTRAINT `fk_order_has_dish_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `epam_cafe`.`order` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_has_dish_dish1`
    FOREIGN KEY (`dish_id`)
    REFERENCES `epam_cafe`.`dish` (`dish_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'таблица связи заказ : блюдо.\nодин заказ содержит от 1 до n блюд\nодно блюдо может иметь от 0 до n заказов';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
