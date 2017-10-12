/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50515
Source Host           : localhost:3306
Source Database       : upmptcl

Target Server Type    : MYSQL
Target Server Version : 50515
File Encoding         : 65001

Date: 2015-09-11 17:03:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `isShowMenu` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentId` int(11) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', null, '0', null, '', '系统管理', '0', null);
INSERT INTO `resource` VALUES ('2', null, '0', null, '', '用户管理', '1', '/user/list');
INSERT INTO `resource` VALUES ('3', null, '0', null, '', '角色管理', '1', '/role/list');
INSERT INTO `resource` VALUES ('4', null, '0', null, '', '资源管理', '1', '/resource/list');
INSERT INTO `resource` VALUES ('5', null, '0', null, '', '系统日志', '1', '/systemlog/list');
INSERT INTO `resource` VALUES ('6', '2015-09-11 17:00:47', '0', '2015-09-11 17:00:47', '', '计划管理', '0', '/plan/list');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '2015-09-11 15:32:40', '0', '2015-09-11 16:38:04', '超级管理员');

-- ----------------------------
-- Table structure for role_resource
-- ----------------------------
DROP TABLE IF EXISTS `role_resource`;
CREATE TABLE `role_resource` (
  `role_id` bigint(20) NOT NULL,
  `resource_id` bigint(20) NOT NULL,
  KEY `FK_8mxwcdjdftg3le1dp65o0md3e` (`resource_id`),
  KEY `FK_pi1uyvvdin7grcttpe2bq9lox` (`role_id`),
  CONSTRAINT `FK_pi1uyvvdin7grcttpe2bq9lox` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_8mxwcdjdftg3le1dp65o0md3e` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resource
-- ----------------------------
INSERT INTO `role_resource` VALUES ('1', '1');
INSERT INTO `role_resource` VALUES ('1', '2');
INSERT INTO `role_resource` VALUES ('1', '3');
INSERT INTO `role_resource` VALUES ('1', '4');
INSERT INTO `role_resource` VALUES ('1', '5');

-- ----------------------------
-- Table structure for systemlog
-- ----------------------------
DROP TABLE IF EXISTS `systemlog`;
CREATE TABLE `systemlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `exception` varchar(1000) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `params` varchar(1000) DEFAULT NULL,
  `remoteAddr` varchar(255) DEFAULT NULL,
  `requestUri` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `userAgent` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hmk9vmeuwn3s77lf4uy427948` (`user_id`),
  CONSTRAINT `FK_hmk9vmeuwn3s77lf4uy427948` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemlog
-- ----------------------------
INSERT INTO `systemlog` VALUES ('1', '2015-09-11 16:06:13', '0', '2015-09-11 16:06:13', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet', 'POST', null, 'search.status_eq=0&page=1&rows=10&sort=id&order=desc', '0:0:0:0:0:0:0:1', '/user/list', '2', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('2', '2015-09-11 16:06:18', '0', '2015-09-11 16:06:18', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet', 'POST', null, 'search.status_eq=0&page=1&rows=10&sort=id&order=desc', '0:0:0:0:0:0:0:1', '/role/list', '2', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('3', '2015-09-11 16:06:29', '0', '2015-09-11 16:06:29', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet', 'POST', null, 'search.status_eq=0&page=1&rows=10&sort=id&order=desc', '0:0:0:0:0:0:0:1', '/role/list', '2', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('4', '2015-09-11 16:17:12', '0', '2015-09-11 16:17:12', 'org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet; SQL [n/a]; nested exception is org.hibernate.exception.SQLGrammarException: could not extract ResultSet', 'POST', null, 'search.status_eq=0&sort=id&order=desc', '0:0:0:0:0:0:0:1', '/role/list', '2', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('5', '2015-09-11 16:18:48', '0', '2015-09-11 16:18:48', '', 'POST', null, 'id=1&loginName=admin&password=&fullName=admin&phone=&email=&address=', '0:0:0:0:0:0:0:1', '/user/save', '1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('6', '2015-09-11 16:20:25', '0', '2015-09-11 16:20:25', '', 'POST', null, 'id=1&loginName=admin&password=&realName=admin&phone=&email=&address=&roleList[0].id=1', '0:0:0:0:0:0:0:1', '/user/save', '1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('7', '2015-09-11 16:37:59', '0', '2015-09-11 16:37:59', '', 'POST', null, 'id=1&name=超级管理员&resourceList[0].id=2&resourceList[1].id=3&resourceList[2].id=4', '0:0:0:0:0:0:0:1', '/role/save', '1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('8', '2015-09-11 16:38:04', '0', '2015-09-11 16:38:04', '', 'POST', null, 'id=1&name=超级管理员&resourceList[0].id=1&resourceList[1].id=2&resourceList[2].id=3&resourceList[3].id=4&resourceList[4].id=5', '0:0:0:0:0:0:0:1', '/role/save', '1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('9', '2015-09-11 16:59:42', '0', '2015-09-11 16:59:42', '', 'POST', null, 'id=&name=计划管理&url=/plan/list&parentId=&isShowMenu=1', '0:0:0:0:0:0:0:1', '/resource/save', '1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');
INSERT INTO `systemlog` VALUES ('10', '2015-09-11 17:00:47', '0', '2015-09-11 17:00:47', '', 'POST', null, 'id=&name=计划管理&url=/plan/list&isShowMenu=1', '0:0:0:0:0:0:0:1', '/resource/save', '1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `updateDate` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `realName` varchar(255) DEFAULT NULL,
  `loginDate` datetime DEFAULT NULL,
  `loginIp` varchar(255) DEFAULT NULL,
  `loginName` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, '0', '2015-09-11 16:20:25', '', '', 'admin', '2015-09-11 16:53:37', '0:0:0:0:0:0:0:1', 'admin', '123456', '');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FK_it77eq964jhfqtu54081ebtio` (`role_id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
