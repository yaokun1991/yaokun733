/*
 Navicat Premium Data Transfer

 Source Server         : itheima
 Source Server Type    : MySQL
 Source Server Version : 50540
 Source Host           : localhost:3306
 Source Schema         : bank

 Target Server Type    : MySQL
 Target Server Version : 50540
 File Encoding         : 65001

 Date: 04/09/2018 15:01:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `AccountID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帐号',
  `Password` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `Remaining` decimal(7, 2) DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`AccountID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('aaa', 'aaa', 1000.00);
INSERT INTO `account` VALUES ('bbb', 'bbb', 1000.00);

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `AccountID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '帐号',
  `TradeType` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易类型 交易类型有3种,分别为:存款、取款和转出, 1,2,3',
  `TradeMoney` decimal(7, 2) DEFAULT NULL COMMENT '交易金额',
  `TradeTime` date DEFAULT NULL COMMENT '交易时间',
  `TradeDigest` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易摘要',
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `AccountID`(`AccountID`) USING BTREE,
  CONSTRAINT `trade_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `account` (`AccountID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of trade
-- ----------------------------
INSERT INTO `trade` VALUES (1, 'bbb', '1', 3000.00, '2018-09-01', '0');
INSERT INTO `trade` VALUES (2, 'bbb', '1', 2000.00, '2018-09-03', '11');
INSERT INTO `trade` VALUES (3, 'bbb', '1', 500.00, '2018-08-14', '');
INSERT INTO `trade` VALUES (4, 'aaa', '1', 6000.00, '2018-09-01', NULL);
INSERT INTO `trade` VALUES (5, 'aaa', '2', 2000.00, '2018-09-03', NULL);
INSERT INTO `trade` VALUES (6, 'aaa', '3', 1000.00, '2018-08-24', NULL);
INSERT INTO `trade` VALUES (7, 'aaa', '3', 3.00, '2018-09-04', '接受账号:3');
INSERT INTO `trade` VALUES (8, 'aaa', '3', 100.00, '2018-09-04', '接受账号:3');
INSERT INTO `trade` VALUES (9, 'aaa', '3', 100.00, '2018-09-04', '接受账号:zhangsan');
INSERT INTO `trade` VALUES (10, 'aaa', '3', 100.65, '2018-09-04', '接受账号:zhangsan');
INSERT INTO `trade` VALUES (11, 'aaa', '3', 5000.00, '2018-09-04', '接受账号:zhangsan');
INSERT INTO `trade` VALUES (12, 'aaa', '3', 311.00, '2018-09-04', '接受账号:bbb');
INSERT INTO `trade` VALUES (13, 'aaa', '3', 1.00, '2018-09-04', '接受账号:bbb');
INSERT INTO `trade` VALUES (14, 'aaa', '3', 111.00, '2018-09-04', '接受账号:aaa');
INSERT INTO `trade` VALUES (15, 'aaa', '3', 11.00, '2018-09-04', '接受账号:bbb');

SET FOREIGN_KEY_CHECKS = 1;
