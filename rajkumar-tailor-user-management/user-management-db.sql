/*
SQLyog Professional v12.09 (64 bit)
MySQL - 10.3.13-MariaDB : Database - user-management-2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`user-management-2` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `user-management-2`;

/*Table structure for table `app_action` */

DROP TABLE IF EXISTS `app_action`;

CREATE TABLE `app_action` (
  `action_id` int(11) NOT NULL AUTO_INCREMENT,
  `action_code` varchar(64) NOT NULL,
  `action_desc` varchar(64) NOT NULL,
  `action_class` varchar(256) DEFAULT NULL,
  `help_file_path` varchar(512) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`action_id`),
  UNIQUE KEY `UK_535j6qq1oi4ehi73gcy4xu5er` (`action_code`),
  UNIQUE KEY `UK535j6qq1oi4ehi73gcy4xu5er` (`action_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_action` */

insert  into `app_action`(`action_id`,`action_code`,`action_desc`,`action_class`,`help_file_path`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'SIGN_UP','Sign Up','/signup',NULL,1,1,'2023-05-13 03:11:37.000000',NULL,NULL);

/*Table structure for table `app_config_module` */

DROP TABLE IF EXISTS `app_config_module`;

CREATE TABLE `app_config_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_config_module` */

insert  into `app_config_module`(`id`,`code`,`name`,`created_by`,`created_date`) values (1,'UMS','UMS',1,'2023-05-13 04:01:52.000000');

/*Table structure for table `app_menu` */

DROP TABLE IF EXISTS `app_menu`;

CREATE TABLE `app_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(64) NOT NULL,
  `menu_desc` varchar(64) NOT NULL,
  `image` varchar(64) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `action_id` int(11) DEFAULT NULL,
  `has_sub_menu` tinyint(4) NOT NULL,
  `parent_menu_id` int(11) DEFAULT NULL,
  `display_order` tinyint(4) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `UK_q7kbr1gqueapbc7ft5e1ryqqi` (`menu_code`),
  UNIQUE KEY `UKq7kbr1gqueapbc7ft5e1ryqqi` (`menu_code`),
  KEY `FK2a2rin695qyo2kmuataqniohd` (`action_id`),
  CONSTRAINT `FK2a2rin695qyo2kmuataqniohd` FOREIGN KEY (`action_id`) REFERENCES `app_action` (`action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `app_menu` */

insert  into `app_menu`(`menu_id`,`menu_code`,`menu_desc`,`image`,`status`,`action_id`,`has_sub_menu`,`parent_menu_id`,`display_order`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'USER','User','bx bxs-user-detail',1,NULL,0,0,1,1,'2023-05-13 02:41:28.000000',NULL,NULL),(2,'SIGN_UP','Sign Up','fa fa-circle',1,1,0,1,3,1,'2023-05-13 03:18:05.000000',NULL,NULL);

/*Table structure for table `app_privilege` */

DROP TABLE IF EXISTS `app_privilege`;

CREATE TABLE `app_privilege` (
  `privilege_id` int(11) NOT NULL AUTO_INCREMENT,
  `privilege_code` varchar(32) NOT NULL,
  `privilege_desc` varchar(64) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `action_id` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`privilege_id`),
  UNIQUE KEY `UK_tgts316oi5wdw1lrm9cdvu1fd` (`privilege_desc`),
  UNIQUE KEY `UKtgts316oi5wdw1lrm9cdvu1fd` (`privilege_desc`),
  KEY `FKkoh5csyp5x2iat8rlyfwehf1t` (`action_id`),
  CONSTRAINT `FKkoh5csyp5x2iat8rlyfwehf1t` FOREIGN KEY (`action_id`) REFERENCES `app_action` (`action_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_privilege` */

insert  into `app_privilege`(`privilege_id`,`privilege_code`,`privilege_desc`,`status`,`action_id`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'VIEW','Sign Up View',1,1,1,'2023-05-13 03:19:20.000000',NULL,NULL);

/*Table structure for table `app_profile_option` */

DROP TABLE IF EXISTS `app_profile_option`;

CREATE TABLE `app_profile_option` (
  `option_id` int(11) NOT NULL AUTO_INCREMENT,
  `option_name` varchar(100) NOT NULL,
  `option_desc` varchar(100) DEFAULT NULL,
  `module_id` int(11) DEFAULT NULL,
  `current_value` varchar(500) NOT NULL,
  `default_value` varchar(500) NOT NULL,
  `app_config_module_id` int(11) DEFAULT NULL,
  `option_data_type` varchar(10) DEFAULT NULL,
  `allow_user_override` char(1) DEFAULT NULL,
  `apply_to_devices` tinyint(1) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`option_id`),
  UNIQUE KEY `UK_fvp0ngu7h0iwd7w9vt58qglke` (`option_name`),
  UNIQUE KEY `UKfvp0ngu7h0iwd7w9vt58qglke` (`option_name`),
  KEY `FKddcpw0hmwj3h2imea09nh7xvm` (`app_config_module_id`),
  CONSTRAINT `FKddcpw0hmwj3h2imea09nh7xvm` FOREIGN KEY (`app_config_module_id`) REFERENCES `app_config_module` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `app_profile_option` */

insert  into `app_profile_option`(`option_id`,`option_name`,`option_desc`,`module_id`,`current_value`,`default_value`,`app_config_module_id`,`option_data_type`,`allow_user_override`,`apply_to_devices`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (2,'IMAGE_UPLOAD_PATH','Image Upload Path',1,'/home/apache-tomcat-7.0.32/webapps/content/images','/home/apache-tomcat-7.0.32/webapps/content/images',1,'varchar','1',0,1,1,'2023-05-13 04:01:55.000000',NULL,NULL),(3,'FTP_SERVER_DOMAIN','FTP_SERVER_DOMAIN',1,'hydapp.corpus.com','hydapp.corpus.com',1,'varchar','1',0,1,0,'2023-05-13 04:02:06.000000',NULL,NULL),(4,'FTP_SERVER_PORT','FTP_SERVER_PORT',1,'21','21',1,'varchar','1',0,1,0,'2023-05-13 04:02:06.000000',NULL,NULL),(5,'FTP_USERNAME','FTP_USERNAME',1,'indsys','indsys',1,'varchar','1',0,1,0,'2023-05-13 04:02:06.000000',NULL,NULL),(6,'FTP_PASSWORD','FTP_PASSWORD',1,'Ind$y$##','Ind$y$##',1,'varchar','1',0,1,0,'2023-05-13 04:02:06.000000',NULL,NULL),(7,'FROM_EMAIL_ADDRESS','From Email Address',1,'corpusdevalert@gmail.com','corpusdevalert@gmail.com',1,'varchar','1',0,1,1,'2023-05-13 04:02:06.000000',NULL,NULL),(8,'FROM_EMAIL_PASSWORD','From Email Password',1,'Corpus@123','Corpus@123',1,'varchar','1',0,1,1,'2023-05-13 04:02:06.000000',NULL,NULL),(9,'SMTP_PORT','SMTP Port',1,'587','587',1,'varchar','1',0,1,1,'2023-05-13 04:02:06.000000',NULL,NULL),(10,'SMTP_HOST_NAME','SMTP Host Name',1,'smtp.gmail.com','smtp.gmail.com',1,'varchar','1',0,1,1,'2023-05-13 04:02:06.000000',NULL,NULL),(11,'SMTP_USER_NAME','SMTP User Name',1,'corpusdevalert@gmail.com','corpusdevalert@gmail.com',1,'varchar','1',0,1,1,'2023-05-13 04:02:06.000000',NULL,NULL),(12,'SMTP_STARTTLS_ENABLE','SMTP STARTTLS Enable',1,'false','false',1,'boolean','1',0,1,1,'2023-05-13 04:02:07.000000',NULL,NULL),(13,'DOWNLOAD_PATH','DOWNLOAD_PATH',1,'/home/corpus/digital','/home/corpus/digital',1,'varchar','1',0,2,0,'2023-05-13 04:02:07.000000',NULL,NULL),(14,'CONTENT_UPLOAD_PATH','Content Upload Path',1,'/home/apache-tomcat-7.0.32/webapps/content','/home/apache-tomcat-7.0.32/webapps/content',1,'varchar','1',0,1,1,'2023-05-13 04:02:07.000000',NULL,NULL),(15,'CONTENT_URL_PATH','Content URL Path',1,'http://192.168.65.37:8080/content','http://192.168.65.37:8080/content',1,'varchar','1',0,1,1,'2023-05-13 04:02:07.000000',NULL,NULL),(16,'APP_SERVER_URL','App Server URL',1,'http://192.168.65.37:8080/appserver/rest','http://192.168.65.249:8080/middleware/rest',1,'varchar','1',0,1,1,'2023-05-13 04:02:07.000000',NULL,NULL);

/*Table structure for table `app_role` */

DROP TABLE IF EXISTS `app_role`;

CREATE TABLE `app_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(16) NOT NULL,
  `role_desc` varchar(64) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `un_app_role_code` (`role_code`),
  UNIQUE KEY `UKtb401hjiwjsyonw2201mdvckq` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_role` */

insert  into `app_role`(`role_id`,`role_code`,`role_desc`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'ADMIN','Admin',1,1,'2023-05-13 03:08:45',NULL,NULL);

/*Table structure for table `app_role_privilege_mapping` */

DROP TABLE IF EXISTS `app_role_privilege_mapping`;

CREATE TABLE `app_role_privilege_mapping` (
  `role_privilege_mapping_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `privilege_id` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`role_privilege_mapping_id`),
  UNIQUE KEY `UKjff8y2lc5mnsy03ykfp4eurdm` (`role_id`,`privilege_id`),
  KEY `FK2xjt95980l7kvtutk6iurta02` (`privilege_id`),
  CONSTRAINT `FK2xjt95980l7kvtutk6iurta02` FOREIGN KEY (`privilege_id`) REFERENCES `app_privilege` (`privilege_id`),
  CONSTRAINT `FKqjeonrg94bl60t6u2nblsfk7k` FOREIGN KEY (`role_id`) REFERENCES `app_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_role_privilege_mapping` */

insert  into `app_role_privilege_mapping`(`role_privilege_mapping_id`,`role_id`,`privilege_id`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,1,1,1,1,'2023-05-13 03:19:25.000000',NULL,NULL);

/*Table structure for table `app_user` */

DROP TABLE IF EXISTS `app_user`;

CREATE TABLE `app_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) NOT NULL,
  `display_name` varchar(64) DEFAULT NULL,
  `user_password` varchar(128) DEFAULT NULL,
  `api_key` varchar(128) NOT NULL,
  `session_id` varchar(128) DEFAULT NULL,
  `email_id` varchar(64) DEFAULT NULL,
  `mobile_no` varchar(16) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `remarks` varchar(512) DEFAULT NULL,
  `reset_token` varchar(128) DEFAULT NULL,
  `reset_token_expiry_time` datetime DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `un_app_user_name` (`user_name`),
  UNIQUE KEY `UKcpt2jpnop7mcpds1sv2i5629w` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_user` */

insert  into `app_user`(`user_id`,`user_name`,`display_name`,`user_password`,`api_key`,`session_id`,`email_id`,`mobile_no`,`status`,`remarks`,`reset_token`,`reset_token_expiry_time`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,'admin','Admin','$2a$10$AG.Ns.lSAbpwtt4Us3lS..yZWI2pBZWgfxCaAQhQv3h0NbMZ2tJyO','839d4d3b-c04b-4fc7-b554-bf5b581c5263',NULL,'onlinelearning162@gmail.com','9867205329',1,'Admin User','65354b0f-4c07-4575-92da-38f0bccebbc4:1684052888759','2023-05-14 14:18:08',1,'2023-05-13 03:00:54',1,'2023-05-14 13:58:08');

/*Table structure for table `app_user_login_failed_attempts` */

DROP TABLE IF EXISTS `app_user_login_failed_attempts`;

CREATE TABLE `app_user_login_failed_attempts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `active_session_id` varchar(256) DEFAULT NULL,
  `app_user_id` int(11) NOT NULL,
  `failed_attempts` int(11) DEFAULT NULL,
  `last_login_time` datetime(6) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2nhlds58qbnfh95841ddomqs5` (`app_user_id`),
  UNIQUE KEY `UK2nhlds58qbnfh95841ddomqs5` (`app_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_user_login_failed_attempts` */

insert  into `app_user_login_failed_attempts`(`id`,`active_session_id`,`app_user_id`,`failed_attempts`,`last_login_time`,`modified_date`) values (1,NULL,1,0,'2023-05-14 15:34:43.000000','2023-05-14 15:34:43.000000');

/*Table structure for table `app_user_role_mapping` */

DROP TABLE IF EXISTS `app_user_role_mapping`;

CREATE TABLE `app_user_role_mapping` (
  `user_role_mapping_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_by` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`user_role_mapping_id`),
  KEY `fk_aurm_userid` (`user_id`),
  KEY `fk_aurm_roleid` (`role_id`),
  CONSTRAINT `fk_aurm_roleid` FOREIGN KEY (`role_id`) REFERENCES `app_role` (`role_id`),
  CONSTRAINT `fk_aurm_userid` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `app_user_role_mapping` */

insert  into `app_user_role_mapping`(`user_role_mapping_id`,`user_id`,`role_id`,`status`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,1,1,1,1,'2023-05-13 03:09:30',NULL,NULL);

/*Table structure for table `transaction_history` */

DROP TABLE IF EXISTS `transaction_history`;

CREATE TABLE `transaction_history` (
  `tran_history_id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(64) NOT NULL,
  `catagory` varchar(45) DEFAULT NULL,
  `change_log` varchar(9000) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `customer_id` bigint(20) DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`tran_history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `transaction_history` */

insert  into `transaction_history`(`tran_history_id`,`action`,`catagory`,`change_log`,`created_by`,`created_date`,`customer_id`,`modified_by`,`modified_date`) values (2,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearningtest819@gmail.com',1,'2023-05-13 03:38:56.000000',NULL,NULL,NULL),(3,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearningtest819@gmail.com',1,'2023-05-13 03:42:15.000000',NULL,NULL,NULL),(4,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearningtest819@gmail.com',1,'2023-05-13 03:42:42.000000',NULL,NULL,NULL),(5,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearningtest819@gmail.com',1,'2023-05-13 04:04:34.000000',NULL,NULL,NULL),(6,'Reset Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-13 04:07:24.000000',NULL,NULL,NULL),(7,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-13 04:26:53.000000',NULL,NULL,NULL),(8,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-13 04:31:35.000000',NULL,NULL,NULL),(9,'Reset Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-13 04:31:43.000000',NULL,NULL,NULL),(10,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 00:13:53.000000',NULL,NULL,NULL),(11,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 00:16:37.000000',NULL,NULL,NULL),(12,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 00:18:15.000000',NULL,NULL,NULL),(13,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 00:22:45.000000',NULL,NULL,NULL),(14,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 00:38:59.000000',NULL,NULL,NULL),(15,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 00:41:36.000000',NULL,NULL,NULL),(16,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 00:57:34.000000',NULL,NULL,NULL),(17,'Forgot Password','AppUser',' Appuser Updated Successfully with Details :: UserName : admin, Display Name :  Admin ,MobileNo : 9867205329, Email : onlinelearning162@gmail.com',1,'2023-05-14 13:58:08.000000',NULL,NULL,NULL);

/*Table structure for table `user_app_profile_mapping` */

DROP TABLE IF EXISTS `user_app_profile_mapping`;

CREATE TABLE `user_app_profile_mapping` (
  `user_app_profile_mapping_id` int(11) NOT NULL AUTO_INCREMENT,
  `app_profile_option_id` int(11) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `app_profile_option_value` varchar(500) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_date` datetime(6) NOT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`user_app_profile_mapping_id`),
  UNIQUE KEY `UKrrkr8twvg6krrfx92v5k4chsi` (`user_id`,`app_profile_option_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `user_app_profile_mapping` */

insert  into `user_app_profile_mapping`(`user_app_profile_mapping_id`,`app_profile_option_id`,`user_id`,`app_profile_option_value`,`created_by`,`created_date`,`modified_by`,`modified_date`) values (1,16,1,'http://192.168.65.37:8080/appserver/rest',1,'2023-05-13 04:03:14.000000',NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
