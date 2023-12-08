drop database if exists deliver;
create database if not exists deliver;

use deliver;

-- 消息模板表
drop table if exists deliver.template;
create table if not exists deliver.template
(
    template_id     bigint auto_increment primary key comment '模板id',
    template_name   varchar(50)                                                    not null comment '模板名称',
    push_range      tinyint                                                        not null comment '推送范围（0-不限 1-企业内部 2-外部）',
    users_type      tinyint                                                        not null comment '用户类型（1-企业账号 2-电话 3-邮箱 4-平台userId）',
    push_ways       varchar(200)                                                   not null comment '推送方式
{
"channelType":（1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
"messageType": 参考 MessageTypeEnum 的 code
}',
    use_count       int      default 0                                             not null comment '模板使用数',
    template_status tinyint  default 1                                             not null comment '模板状态（1-启用 0-禁用）',
    create_user     varchar(50)                                                    null comment '创建者',
    update_user     varchar(50)                                                    null comment '更新者',
    create_time     datetime default CURRENT_TIMESTAMP                             not null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null comment '更新时间',
    deleted         tinyint  default 0                                             not null comment '是否删除：0-不删除 1-删除'
) comment '消息模板';

-- 渠道应用信息表（宽表）
drop table if exists deliver.app;
create table if not exists deliver.app
(
    app_id       bigint auto_increment primary key comment 'appId',
    app_name     varchar(100)                                                   not null comment '应用名称',
    channel_type tinyint                                                        not null comment '消息发送渠道类型 （1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）',
    app_config   text                                                           not null comment '应用信息配置 json',
    use_count    int      default 0                                             not null comment 'APP 使用数',
    app_status   tinyint  default 1                                             not null comment '应用状态（1-启用 0-禁用）',
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

-- 消息记录表
create table deliver.message_record
(
    trace_id       varchar(100)                       not null comment '消息链路 id',
    template_id    bigint                             not null comment '模板 id',
    app_id         bigint                             not null comment 'appId',
    message_status tinyint  default 1                 not null comment '消息状态（1-发送成功0-发送失败）',
    user_type      tinyint                            null comment '用户类型（1-企业账号2-手机3-邮箱4-平台userId）',
    push_user      varchar(100)                       null comment '消息推送人',
    channel_type   tinyint                            not null comment '发送渠道类型',
    message_type   varchar(10)                        not null comment '消息类型（见 MessageTypeEnum）',
    push_range     tinyint  default 0                 not null comment '推送范围（0-不限1-企业内部2-外部）',
    retried        tinyint  default 0                 not null comment '是否重试消息（1-是 0-首次发送）',
    create_time    datetime default CURRENT_TIMESTAMP not null
) comment '消息记录';

-- 平台文件表
create table platform_file
(
    id          bigint auto_increment primary key comment '主键',
    file_name   varchar(50)                        not null comment '文件名',
    app_type    tinyint                            not null comment 'APP 类型（1-钉钉2-企业微信3-飞书）',
    file_type   varchar(20)                        not null comment '文件类型',
    file_key    varchar(100)                       not null comment '文件平台 Key',
    create_user varchar(100)                       null comment '创建者',
    create_time datetime default CURRENT_TIMESTAMP null,
    app_id      bigint                             not null comment '关联 APPID'
) comment '平台文件表';





