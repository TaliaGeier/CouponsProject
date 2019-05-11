DROP DATABASE IF EXISTS `project`;
CREATE DATABASE `project`;
USE project;

DROP TABLE IF EXISTS `companies`;
CREATE TABLE `companies` (
    `company_id` BIGINT NOT NULL AUTO_INCREMENT,
    `company_name` VARCHAR(30) NOT NULL DEFAULT '' UNIQUE,
    `company_email` VARCHAR(30) NOT NULL DEFAULT '' UNIQUE,
    PRIMARY KEY (`company_id`)
);

 DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
    `user_id` BIGINT(10) NOT NULL AUTO_INCREMENT,
    `company_id` BIGINT(10) DEFAULT NULL,
    `user_name` VARCHAR(30) DEFAULT NULL  UNIQUE,
    `user_password` VARCHAR(30) DEFAULT NULL,
    `user_type` VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (`user_id`),
    FOREIGN KEY (`company_id`)
        REFERENCES `companies` (`company_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE
   
);
  
  DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
    `customer_id` BIGINT(10) NOT NULL AUTO_INCREMENT,
    `customer_last_name` VARCHAR(30) DEFAULT NULL,
  `customer_first_name` VARCHAR(30) DEFAULT NULL,    
  PRIMARY KEY (`customer_id`),
  FOREIGN KEY (`customer_id`)
        REFERENCES `users` (`user_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE
);
  
  DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
    `category_id` BIGINT NOT NULL AUTO_INCREMENT,
    `category_name` VARCHAR(30) NOT NULL DEFAULT '' UNIQUE,
    PRIMARY KEY (`category_id`)
);
  
  DROP TABLE IF EXISTS `coupons`;
CREATE TABLE `coupons` (
    `coupon_id` BIGINT(10) NOT NULL AUTO_INCREMENT,
    `company_id` BIGINT(10) NOT NULL,
    `category_id` BIGINT(10) NOT NULL,
    `coupon_title` VARCHAR(50) DEFAULT NULL,
    `coupon_description` VARCHAR(200) DEFAULT NULL,
    `coupon_start_date` DATE NOT NULL DEFAULT '0000-00-00',
    `coupon_end_date` DATE NOT NULL DEFAULT '0000-00-00',
    `coupon_amount` INT(6) NOT NULL DEFAULT 0 CHECK (coupon_amount > 0),
    `coupon_price` DOUBLE NOT NULL DEFAULT 0.0,
    `coupon_image` VARCHAR(300) DEFAULT '',
    PRIMARY KEY (`coupon_id`),
    FOREIGN KEY (`company_id`)
        REFERENCES `companies` (`company_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (`category_id`)
        REFERENCES `categories` (`category_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE
);
  
   DROP TABLE IF EXISTS `purchases`;
CREATE TABLE `purchases` (
    `purchase_id` BIGINT NOT NULL auto_increment,

    `customer_id` BIGINT NOT NULL,
    `coupon_id` BIGINT NOT NULL,
    `purchase_amount` int(10) default 0,
    
    PRIMARY KEY (`purchase_id`),
    FOREIGN KEY (`customer_id`)
        REFERENCES `customers` (`customer_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (`coupon_id`)
        REFERENCES `coupons` (`coupon_id`)
        ON DELETE RESTRICT ON UPDATE CASCADE
);
  
 
  
  INSERT `categories` VALUES
  (NULL, 'COFFEE_SHOP'),
	(NULL, 'ELECTRONICS'),
	(NULL, 'RESTAURANTS'),
	(NULL, 'VACATION'),
	(NULL, 'CONCERTS'),
	(NULL, 'FOOTBALL_GAME'),
	(NULL, 'MOVIES'),
	(NULL, 'TRIPS'),
	(NULL, 'KIDS_ATTRACTIONS'),
	(NULL, 'FURNITURE'),
	(NULL, 'OPTICS'),
	(NULL, 'HAIR_SALONS'),
	(NULL, 'PUBLIC_TRANSPORTATION'),
	(NULL, 'COSMETIC_SURGERIES'),
	(NULL, 'PET_STORE');
    
SELECT 
    *
FROM
    `categories`;
