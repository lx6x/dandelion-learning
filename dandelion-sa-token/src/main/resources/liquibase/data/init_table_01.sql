DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`           bigint                                                       NOT NULL COMMENT '用户ID',
    `user_name`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
    `real_name`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
    `nick_name`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '昵称',
    `avatar`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户头像',
    `password`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '用户密码',
    `salt`         varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '密码加盐',
    `mobile_phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
    `tel`          varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '联系电话',
    `email`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '邮箱',
    `admin_type`   int                                                           DEFAULT NULL COMMENT '管理员类型<sys_admin_type>',
    `sex`          int                                                           DEFAULT '3' COMMENT '性别<sys_sex>',
    `is_locked`    tinyint(1)                                                    DEFAULT '0' COMMENT '是否锁定',
    `dept_id`      bigint                                                        DEFAULT NULL COMMENT '所属部门',
    `post_id`      bigint                                                        DEFAULT NULL COMMENT '所属岗位',
    `remark`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `create_time`  datetime(3)                                                   DEFAULT NULL COMMENT '创建时间',
    `create_user`  bigint                                                        DEFAULT NULL COMMENT '创建用户',
    `update_time`  datetime(3)                                                   DEFAULT NULL COMMENT '更新时间',
    `update_user`  bigint                                                        DEFAULT NULL COMMENT '更新用户',
    `is_deleted`   tinyint(1)                                                    DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `idx_sys_user_user_name` (`user_name`) USING BTREE,
    KEY `idx_sys_user_real_name` (`real_name`) USING BTREE,
    KEY `idx_sys_user_mobile_phone` (`mobile_phone`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户';