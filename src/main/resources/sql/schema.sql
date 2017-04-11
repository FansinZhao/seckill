-- 初始化数据库
-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
USE seckill;
-- 创建表
-- 第二个时间戳不能插入问题
set explict_defaults_for_timestamp=1;
-- 创建秒杀表
CREATE TABLE seckill (
  `seckill_id`  BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '秒杀产品id',
  `name`        VARCHAR(250) NOT NULL
  COMMENT '秒杀产品名称',
  `number`      INT          NOT NULL
  COMMENT '产品数量',
  `start_time`  TIMESTAMP    NOT NULL
  COMMENT '秒杀开始时间',
  `end_time`    TIMESTAMP    NOT NULL
  COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '秒杀创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time (start_time),
  KEY idx_end_time (end_time),
  KEY idx_create_time (create_time)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1000
  DEFAULT CHARSET = utf8
  COMMENT '秒杀库存表';

-- 初始化数据
INSERT INTO seckill (name, number, start_time, end_time)
VALUES
  ('1000元秒杀iPhone8', 5, '2017-04-09 00:00:00', '2017-04-10 00:00:00'),
  ('500元秒杀iPad3', 10, '2017-04-09 00:00:00', '2017-04-10 00:00:00'),
  ('200元秒杀小米5', 20, '2017-04-09 00:00:00', '2017-04-10 00:00:00'),
  ('100元秒杀红米4', 100, '2017-04-09 00:00:00', '2017-04-10 00:00:00');

-- 创建秒杀详情表
CREATE  TABLE success_seckilled(
  seckill_id BIGINT NOT NULL COMMENT '秒杀活动表主键',
  user_phone BIGINT NOT NULL COMMENT '用户电话',
  state TINYINT NOT NULL DEFAULT 0 COMMENT '秒杀状态:0 成功,1 已付款 2 已发货',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '秒杀成功时间',
  PRIMARY KEY (seckill_id,user_phone),
  KEY idx_create_time(create_time)
)ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT '秒杀成功表';

-- connect msql
-- docker
docker run --name mysql-server -e MYSQL_ROOT_PASSWORD=root -d mysql
-- 查看docker mysql ip
docker inspect mysql-server | grep IP
-- 连接数据库
mysql -h 172.17.0.3 -u root -proot