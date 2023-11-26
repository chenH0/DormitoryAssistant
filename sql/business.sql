drop table if exists `parti`;
create table `parti` (
  `id` bigint not null comment 'id',
  `name` varchar(20) not null comment '宿舍楼名',
  `name_pinyin` varchar(50) not null comment '分区拼音',
  `name_py` varchar(50) not null comment '分区拼音首字母',
  `create_time` datetime(3) comment '新增时间',
  `update_time` datetime(3) comment '修改时间',
  primary key (`id`),
  unique key `name_unique` (`name`)
) engine=innodb default charset=utf8mb4 comment='宿舍';

drop table if exists `storys`;
create table `storys` (
  `id` bigint not null comment 'id',
  `code` varchar(20) not null comment '宿舍楼层编号',
  `type` char(1) not null comment '性别|枚举[TrainTypeEnum]',
  `total` int not null comment '房间数',
  `name` varchar(20) not null comment '宿舍楼名',
  `floors` char(4) not null comment '宿舍楼层',
  `name_pinyin` varchar(20) not null comment '宿舍楼名拼音',
  `update_time` datetime(3) comment '修改时间',
  `status` int comment '状态',
  primary key (`id`),
  unique key `code_unique` (`code`)
) engine=innodb default charset=utf8mb4 comment='楼层';

drop table if exists `room_carriage`;
create table `room_carriage` (
  `id` bigint not null comment 'id',
  `name` varchar(30) not null comment 'name',
  `floors_code` varchar(20) not null comment '宿舍楼层编号',
  `index` int not null comment '宿舍编号',
  `bed_type` char(3) not null comment '房间类型|枚举[SeatTypeEnum]',
  `bed_count` int not null comment '床位数',
  `status` int not null comment '开放状态',
  `create_time` datetime(3) comment '新增时间',
  `update_time` datetime(3) comment '修改时间',
  unique key `train_code_index_unique` (`name`),
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='楼层宿舍';

drop table if exists `bed_seat`;
create table `bed_seat` (
  `id` bigint not null comment 'id',
  `floors_code` varchar(20) not null comment '宿舍楼层编号',
  `room_code` bigint not null comment '宿舍编号',
  `room_name` varchar(20) not null comment '宿舍名',
  `index` char(2) not null comment '座位号|01, 02|03|04',
  `status` int not null comment '状态|枚举[0未占用，1已占用]',
  `can_select` int not null comment '状态|枚举[0开放，1未开放]',
  `user_name` varchar(10) comment '住户名',
  `user_id` bigint comment '住户id',
  `create_time` datetime(3) comment '新增时间',
  `update_time` datetime(3) comment '修改时间',
  unique key `bed_index` (`room_name`, `index`),
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='座位';

drop table if exists `bed_ticket`;
create table `bed_ticket` (
  `id` bigint not null comment 'id',
  `date` date comment '日期',
  `room_code` bigint not null comment '宿舍编号',
  `room_name` varchar(20) not null comment '宿舍名字',
  `floors_code` varchar(20) not null comment '宿舍楼层',
  `bed_type` char(3) not null comment '房间类型|枚举[SeatTypeEnum]',
  `total` int not null comment '宿舍余票',
  `one` int not null comment '1余票',
  `two` int not null comment '2余票',
  `three` int not null comment '3余票',
  `four` int not null comment '4余票',
  `create_time` datetime(3) comment '新增时间',
  `update_time` datetime(3) comment '修改时间',
  primary key (`id`),
  unique key `date_room_code_unique` (`date`, `room_code`)
) engine=innodb default charset=utf8mb4 comment='余票信息';

drop table if exists `confirm_order`;
create table `confirm_order` (
  `id` bigint not null comment 'id',
  `member_id` bigint not null comment '会员id',
  `member_name` varchar(20) not null comment '会员名字',
  `date` date not null comment '日期',
  `room_name` varchar(20) not null comment '宿舍名字',
  `floors_code` varchar(20) not null comment '宿舍楼层',
  `index` char(2) not null comment '座位号|01, 02|03|04',
  `date_room_ticket_id` bigint not null comment '余票ID',
  `status` char(1) not null comment '订单状态|枚举[ConfirmOrderStatusEnum]',
  `create_time` datetime(3) comment '新增时间',
  `update_time` datetime(3) comment '修改时间',
  primary key (`id`),
  index `date_room_code_index` (`member_id`)
) engine=innodb default charset=utf8mb4 comment='确认订单';

CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists `sk_token`;
create table `sk_token` (
  `id` bigint not null comment 'id',
  `date` date not null comment '日期',
  `train_code` varchar(20) not null comment '车次编号',
  `count` int not null comment '令牌余量',
  `create_time` datetime(3) comment '新增时间',
  `update_time` datetime(3) comment '修改时间',
  primary key (`id`),
  unique key `date_train_code_unique` (`date`, `train_code`)
) engine=innodb default charset=utf8mb4 comment='秒杀令牌';
