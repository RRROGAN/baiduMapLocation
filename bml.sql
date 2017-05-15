-- 2017年3月22日19:59:01
create table `tb_user`(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `login_name` varchar(20) NOT NULL DEFAULT '' COMMENT '登录名（账户名称）',
    `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户姓名',
    `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
    `salt` varchar(255) DEFAULT NULL COMMENT '密码加密盐值',
    `gender` char(1) DEFAULT NULL COMMENT '性别',
    `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
    `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
    `icon` varchar(500) DEFAULT NULL COMMENT '头像',
    `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `state` char(1) DEFAULT NULL COMMENT '状态',
    `description` varchar(255) DEFAULT NULL COMMENT '描述',
    `extend_info` varchar(255) DEFAULT NULL COMMENT '扩展字段',
    `login_count`  int DEFAULT NULL COMMENT '登录次数',
    `last_visit` datetime DEFAULT NULL COMMENT '最后一次登录时间',
    `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8

CREATE TABLE `tb_favorite` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `start_posi` varchar(25) NOT NULL COMMENT '起点位置',
  `end_posi` varchar(25) DEFAULT NULL COMMENT '终点位置',
  `start_detail_posi` varchar(25) NOT NULL COMMENT '起点详细位置',
  `end_detail_posi` varchar(25) DEFAULT NULL COMMENT '终点详细位置',
  `start_lat` decimal(3,7) DEFAULT NULL COMMENT '起始纬度',
  `start_lng` decimal(3,7) DEFAULT NULL COMMENT '起始经度',
  `end_lat` decimal(3,7) DEFAULT NULL COMMENT '终点纬度',
  `end_lng` decimal(3,7) DEFAULT NULL COMMENT '终点经度',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `label` varchar(255) DEFAULT NULL COMMENT '标签',
  `isfavorite` char(1) DEFAULT NULL COMMENT '状态 1：收藏， 0：取消收藏',
  `collect_type` char(1) DEFAULT NULL COMMENT '状态 1：收藏的是点， 0：收藏的是线路',
  `extend_info` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  `img` varchar(500) DEFAULT NULL COMMENT '图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




alter table `tb_favorite` ADD `start_lat` DECIMAL DEFAULT NULL  COMMENT '起始精度' AFTER  `end_detail_posi`;
alter table `tb_favorite` ADD `start_lng` DECIMAL DEFAULT NULL  COMMENT '起始纬度' AFTER  `start_lat`;
alter table `tb_favorite` ADD `end_lat` DECIMAL DEFAULT NULL  COMMENT '起始精度' AFTER  `start_lng`;
alter table `tb_favorite` ADD `end_lng` DECIMAL DEFAULT NULL  COMMENT '起始精度' AFTER  `end_lat`;

alter table tb_favorite drop  start_lat

-- 用户角色表
CREATE TABLE `user_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_USER_ROL_REFERENCE_ROLE` (`ROLE_ID`) USING BTREE,
  KEY `FK_USER_ROL_REFERENCE_USERS` (`USER_ID`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`ROLE_ID`) REFERENCES `tb_role` (`ID`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`USER_ID`) REFERENCES `tb_user` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_role` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) NOT NULL,
  `ROLE_CODE` varchar(20) NOT NULL,
  `DESCRIPTION` text,
  `SORT` smallint(6) DEFAULT NULL,
  `DEL_FLAG` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `role_permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) DEFAULT NULL,
  `PERMISSION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_ROLE_PER_REFERENCE_PERMISSI` (`PERMISSION_ID`) USING BTREE,
  KEY `FK_ROLE_PER_REFERENCE_ROLE` (`ROLE_ID`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`PERMISSION_ID`) REFERENCES `tb_permission` (`ID`),
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`ROLE_ID`) REFERENCES `tb_role` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2078 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_permission` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PID` int(11) DEFAULT NULL COMMENT '父节点名称',
  `NAME` varchar(50) NOT NULL COMMENT '名称',
  `TYPE` varchar(20) DEFAULT NULL COMMENT '类型:菜单or功能',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  `URL` varchar(255) DEFAULT NULL,
  `PERM_CODE` varchar(50) DEFAULT NULL COMMENT '菜单编码',
  `ICON` varchar(255) DEFAULT NULL,
  `STATE` varchar(10) DEFAULT NULL,
  `DESCRIPTION` text,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=510 DEFAULT CHARSET=utf8;
