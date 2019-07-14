/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost
 Source Database       : cream

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : utf-8

 Date: 07/14/2019 18:44:18 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  `id` bigint(20) NOT NULL,
  `create_by` bigint(20) NOT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `oauth_client_details`
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('CouponSystem', null, '$2a$10$dYRcFip80f0jIKGzRGulFelK12036xWQKgajanfxT65QB4htsEXNK', 'user_info', 'authorization_code', 'http://localhost:8082/login', null, null, null, null, 'user_info', '0', '0', null, null), ('MemberSystem', null, '$2a$10$dYRcFip80f0jIKGzRGulFelK12036xWQKgajanfxT65QB4htsEXNK', 'user_info', 'authorization_code', 'http://localhost:8081/login', null, null, null, null, 'user_info', '0', '0', null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_operation`
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation`;
CREATE TABLE `sys_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` bigint(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `operation_descritpion` varchar(255) DEFAULT NULL,
  `operation_name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_operation`
-- ----------------------------
BEGIN;
INSERT INTO `sys_operation` VALUES ('1', '1', '2019-03-04 10:57:34', null, null, '查询结果', '查询'), ('2', '1', '2019-03-04 10:57:34', null, null, '查看详情结果', '查看详情');
COMMIT;

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` bigint(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `http_method` varchar(6) NOT NULL,
  `http_url` varchar(255) NOT NULL,
  `permission_descritpion` varchar(255) DEFAULT NULL,
  `permission_name` varchar(60) NOT NULL,
  `operation_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKeg5dwj8malqhecan0bntb1ert` (`operation_id`),
  KEY `FK1a47qotyi6qc853d20pylfoav` (`resource_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_permission`
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES ('1', '1', '2019-03-04 10:57:34', null, null, 'GET', '/test1', '用户管理查询权限', '用户管理查询', '1', '1'), ('2', '1', '2019-03-04 10:57:34', null, null, 'GET', '/test2', '用户管理查看详情权限', '用户管理查看详情', '2', '1'), ('3', '1', '2019-03-04 10:57:34', null, null, 'GET', '/test3', '角色管理查看详情权限', '角色管理查看详情', '1', '2'), ('4', '1', '2019-03-04 10:57:34', null, null, 'GET', '/test4', '角色管理查看详情权限', '角色管理查看详情', '2', '2');
COMMIT;

-- ----------------------------
--  Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` bigint(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `resource_descritpion` varchar(255) DEFAULT NULL,
  `resource_name` varchar(60) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3fekum3ead5klp7y4lckn5ohi` (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_resource`
-- ----------------------------
BEGIN;
INSERT INTO `sys_resource` VALUES ('1', '1', '2019-03-04 10:57:34', null, null, '用户管理资源', '用户管理', null), ('2', '1', '2019-03-04 10:57:34', null, null, '角色管理资源', '角色管理', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` bigint(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `role_descritpion` varchar(255) DEFAULT NULL,
  `role_name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '1', '2019-03-04 10:57:34', null, null, '普通用户角色', '普通用户');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`permission_id`,`role_id`),
  KEY `FK9q28ewrhntqeipl1t04kh1be7` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES ('1', '1'), ('1', '2');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` bigint(20) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modified_by` bigint(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `email` varchar(50) NOT NULL,
  `head_img_url` varchar(255) NOT NULL,
  `is_account_non_expired` bit(1) DEFAULT NULL,
  `is_account_non_locked` bit(1) DEFAULT NULL,
  `is_credentials_non_expired` bit(1) DEFAULT NULL,
  `is_enabled` bit(1) DEFAULT NULL,
  `is_turn_on_captcha` bit(1) DEFAULT NULL,
  `last_login_date` datetime DEFAULT NULL,
  `last_login_ip` varchar(15) DEFAULT NULL,
  `login_num` int(11) DEFAULT NULL,
  `nickname` varchar(60) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `sex` tinyint(1) NOT NULL,
  `user_password` char(60) NOT NULL,
  `username` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', '1', '2019-03-04 10:57:34', null, null, '41620578@qq.com', 'https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D150/sign=60c2ec4d7a0e0cf3a0f74afe3a47f23d/0dd7912397dda1445da42dedbab7d0a20df486c4.jpg', b'1', b'1', b'1', b'1', b'0', null, null, '0', '超级管理员', '18300007075', '1', '$2a$10$cPXwK6XKjb/5tsdgofvorupkBDv1Efg8IJlCLUgQKDN97l089ezby', 'admin');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_connection`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_connection`;
CREATE TABLE `sys_user_connection` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `provider_id` tinyint(1) NOT NULL,
  `provider_user_id` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj02bc83219j6fi4q2249mbnwd` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
