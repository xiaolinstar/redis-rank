USE `redis`;

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
                             `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                             `name` varchar(30) NOT NULL COMMENT '书名',
                             `author_id` bigint unsigned NOT NULL COMMENT '书的作者id',
                             `click_num` int unsigned NOT NULL COMMENT '点击量',
                             `like_num` int unsigned NOT NULL COMMENT '点赞数',
                             `comment_num` int unsigned NOT NULL COMMENT '评论数',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='书籍';


INSERT INTO `book` values (1, 'John', 1, 0, 0, 0, '2019-03-01 00:01:01', '2023-03-01 00:01:02');
INSERT INTO `book` values (2, 'Tom', 1, 0, 0, 0, '2019-03-02 00:02:01', '2019-03-02 00:06:12');
INSERT INTO `book` values (3, 'Mike', 1, 0, 0, 0, '2019-03-03 00:03:01', '2019-03-04 00:07:12');
INSERT INTO `book` values (4, 'John', 1, 0, 0, 0, '2019-03-04 00:04:01', '2019-03-04 00:06:12');
INSERT INTO `book` values (5, 'James', 1, 0, 0, 0, '2019-03-04 00:05:01', '2019-03-06 00:06:12');
INSERT INTO `book` values (6, 'Noah', 1, 0, 0, 0, '2019-03-05 00:05:01', '2019-03-12 00:16:12');
INSERT INTO `book` values (7, 'Jake', 1, 0, 0, 0, '2019-03-06 00:02:11', '2019-03-07 00:06:12');
INSERT INTO `book` values (8, 'Oliver', 1, 0, 0, 0, '2019-03-06 00:05:01', '2019-03-07 00:06:12');
INSERT INTO `book` values (9, 'Harry', 1, 0, 0, 0, '2019-03-07 00:00:01', '2019-03-07 00:06:12');
INSERT INTO `book` values (10, 'Lim', 1, 0, 0, 0, '2019-03-07 01:23:01', '2019-03-08 00:06:12');
INSERT INTO `book` values (11, 'Robert', 1, 0, 0, 0, '2019-03-08 00:05:01', '2019-03-09 00:06:12');
INSERT INTO `book` values (12, 'David', 1, 0, 0, 0, '2019-03-09 00:05:01', '2019-03-11 00:06:12');
INSERT INTO `book` values (13, 'Thomas', 1, 0, 0, 0, '2019-03-10 00:05:01', '2019-03-12 00:06:12');

