/*
Navicat MySQL Data Transfer

Source Server Version : 50713
Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2018-10-10 11:17:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for operationlog
-- ----------------------------
DROP TABLE IF EXISTS `operationlog`;
CREATE TABLE `operationlog` (
  `id` varchar(18) NOT NULL,
  `operationType` int(1) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `createName` varchar(36) DEFAULT NULL,
  `createDate` varchar(36) NOT NULL,
  `personnelName` varchar(36) NOT NULL,
  `workType` varchar(100) NOT NULL,
  `personnelId` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(36) NOT NULL,
  `url` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', 'admin:editUser');
INSERT INTO `permission` VALUES ('2', 'admin:count');
INSERT INTO `permission` VALUES ('3', 'manager:file');

-- ----------------------------
-- Table structure for personnel
-- ----------------------------
DROP TABLE IF EXISTS `personnel`;
CREATE TABLE `personnel` (
  `id` varchar(32) NOT NULL,
  `userName` varchar(24) NOT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `education` varchar(34) DEFAULT NULL,
  `workType` varchar(100) DEFAULT NULL,
  `serviceYear` varchar(4) DEFAULT NULL,
  `phoneNumber` varchar(24) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL,
  `createDate` varchar(36) NOT NULL,
  `fileUrl` varchar(100) DEFAULT NULL,
  `uploadUserId` varchar(48) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(36) NOT NULL,
  `roleKey` varchar(56) NOT NULL,
  `roleName` varchar(56) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'admin', '系统管理员');
INSERT INTO `role` VALUES ('10', 'user', '用户');
INSERT INTO `role` VALUES ('2', 'manager', '经理');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` varchar(36) NOT NULL,
  `roleId` varchar(36) NOT NULL,
  `permissionId` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '2');
INSERT INTO `role_permission` VALUES ('3', '1', '3');
INSERT INTO `role_permission` VALUES ('4', '2', '3');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(38) NOT NULL,
  `userName` varchar(58) NOT NULL,
  `passWord` varchar(58) NOT NULL,
  `nickName` varchar(58) NOT NULL,
  `gender` int(1) DEFAULT NULL,
  `age` int(4) DEFAULT NULL,
  `phoneNumber` varchar(18) DEFAULT NULL,
  `email` varchar(48) DEFAULT NULL,
  `createDate` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4c9d264403fd4faaaed1095b1c39689c', 'test@test.com', '9b69cb519ec95b02ec811337ee413e37', '管理员', '1', '23', '12356767867', '342@qq.com', '2017-11-27');


-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(36) NOT NULL,
  `userId` varchar(36) NOT NULL,
  `roleId` varchar(36) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('04c92e7a8c1549a9bac6351976022410', '4c9d264403fd4faaaed1095b1c39689c', '10');

