USE `redis`;

DROP TABLE IF EXISTS `sort`;

CREATE TABLE `sort` (
                             `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                             `book_id` bigint unsigned NOT NULL COMMENT '书籍id',
                             `click_num` int unsigned NOT NULL COMMENT '点击量',
                             `like_num` int unsigned NOT NULL COMMENT '点赞数',
                             `comment_num` int unsigned NOT NULL COMMENT '评论数',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='排序';

