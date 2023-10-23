drop database if exists deliver;
create database if not exists deliver;

use deliver;

-- 消息模板表
drop table if exists deliver.template;
create table if not exists deliver.template
(
    template_id   bigint auto_increment primary key comment '模板id',
    template_name varchar(50)                                                    not null comment '模板名称',
    push_range    tinyint                                                        not null comment '推送范围（0-不限 1-企业内部 2-外部）',
    users_type    tinyint                                                        not null comment '用户类型（1-企业账号 2-电话 3-邮箱 4-平台userId）',
    push_ways     varchar(200)                                                   not null comment '推送方式
{
"channelType":（1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
"messageType": 参考 MessageTypeEnum 的 code
}',
    use_count     int      default 0                                             not null comment '模板使用数',
    create_user   varchar(50)                                                    null comment '创建者',
    update_user   varchar(50)                                                    null comment '更新者',
    create_time   datetime default CURRENT_TIMESTAMP                             not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null comment '更新时间',
    deleted       tinyint  default 0                                             not null comment '是否删除：0-不删除 1-删除'
) comment '消息模板';

-- 渠道应用信息表（宽表）
drop table if exists deliver.app;
create table if not exists deliver.app
(
    app_id       bigint auto_increment primary key comment 'appId',
    name         varchar(100)                                                   not null comment '应用名称',
    channel_type tinyint                                                        not null comment '消息发送渠道类型 （1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）',
    app_config   text                                                           not null comment '应用信息配置 json',
    use_count    int      default 0                                             not null comment 'APP 使用数',
    create_user  varchar(50)                                                    null comment '创建者',
    update_user  varchar(50)                                                    null comment '更新者',
    create_time  datetime default CURRENT_TIMESTAMP                             not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null comment '更新时间',
    deleted      tinyint  default 0                                             not null comment '是否删除：0-不删除 1-删除'
) comment '渠道应用信息';

-- 模板与应用关联表
drop table if exists deliver.template_app;
create table if not exists deliver.template_app
(
    id          bigint auto_increment primary key comment '主键',
    template_id bigint                                                         not null comment '模板id',
    app_id      bigint                                                         not null comment '应用id',
    create_time datetime default CURRENT_TIMESTAMP                             not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null comment '更新时间',
    deleted     tinyint  default 0                                             not null comment '是否删除：0-不删除 1-删除'
) comment '模板与应用关联表';

-- 消息处理日志表

