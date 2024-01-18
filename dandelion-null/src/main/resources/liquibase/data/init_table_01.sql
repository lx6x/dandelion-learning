DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`        int                                                           NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`      varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '员工姓名',
    `sex`       varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci  DEFAULT NULL COMMENT '性别',
    `sal`       float                                                         DEFAULT NULL COMMENT '工资',
    `face`      varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '头像',
    `dno`       int                                                           DEFAULT NULL COMMENT '部门号',
    `join_date` date                                                          DEFAULT NULL COMMENT '入职时间',
    `level`     int                                                           DEFAULT NULL COMMENT '级别',
    `password`  varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '密码',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
