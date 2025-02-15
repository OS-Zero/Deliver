drop database if exists deliver;
create database if not exists deliver;

use deliver;
-- 用户表
drop table if exists deliver.user_info;
create table if not exists deliver.user_info
(
    user_id        bigint auto_increment comment '用户id' primary key,
    user_email     varchar(20)                           not null comment '用户邮箱',
    user_password  varchar(50)                           not null comment '用户密码加密',
    user_real_name varchar(50)                           not null comment '用户真实姓名',
    user_role      varchar(10) default 'ordinary'        not null comment '用户角色（管理员admin、普通用户ordinary）',
    create_user    varchar(10)                           null comment '创建者',
    update_user    varchar(10)                           null comment '更新者',
    create_time    datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted        tinyint     default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '用户表';

-- 分组表
drop table if exists deliver.group_info;
create table if not exists deliver.group_info
(
    group_id          bigint auto_increment comment '分组id' primary key,
    group_name        varchar(10)                        not null comment '分组名称',
    group_description varchar(50)                        not null comment '分组描述',
    create_user       varchar(10)                        not null comment '创建者',
    update_user       varchar(10)                        not null comment '更新者',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted           tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '分组表';

-- 用户与分组关联表
drop table if exists deliver.user_group;
create table if not exists deliver.user_group
(
    id          bigint auto_increment comment '主键' primary key,
    user_id     bigint                             not null comment '用户id',
    group_id    bigint                             not null comment '分组id',
    top_up      tinyint  default 0                 not null comment '是否置顶（0-不置顶、1-置顶）',
    create_user varchar(10)                        not null comment '创建者',
    update_user varchar(10)                        not null comment '更新者',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '用户与分组关联表';

-- 消息模板表
drop table if exists deliver.message_template;
create table if not exists deliver.message_template
(
    template_id           bigint auto_increment comment '模板id' primary key,
    template_name         varchar(10)                        not null comment '模板名称',
    template_description  varchar(50)                        not null comment '模板描述',
    users_type            tinyint                            not null comment '用户类型（参考用户类型枚举）',
    channel_type          tinyint                            not null comment '渠道类型（参考渠道类型枚举）',
    channel_provider_type tinyint                            not null comment '渠道供应商类型（参考渠道供应商类型枚举）',
    message_type          varchar(10)                        not null comment '消息类型（参考消息类型枚举）',
    template_status       tinyint  default 0                 not null comment '模板状态（1-启用、0-禁用）',
    group_id              bigint                             not null comment '关联分组id',
    create_user           varchar(10)                        not null comment '创建者',
    update_user           varchar(10)                        not null comment '更新者',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted               tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '消息模板表';

-- 渠道应用信息表（宽表）
drop table if exists deliver.channel_app;
create table if not exists deliver.channel_app
(
    app_id                bigint auto_increment comment '应用id' primary key,
    app_name              varchar(10)                        not null comment '应用名称',
    app_description       varchar(50)                        not null comment '应用描述',
    channel_type          tinyint                            not null comment '渠道类型（参考渠道类型枚举）',
    channel_provider_type tinyint                            not null comment '渠道供应商类型（参考渠道供应商类型枚举）',
    app_config            text                               not null comment '应用信息JSON配置',
    app_status            tinyint  default 0                 not null comment '应用状态（1-启用、0-禁用）',
    group_id              bigint                             not null comment '关联分组id',
    create_user           varchar(10)                        not null comment '创建者',
    update_user           varchar(10)                        not null comment '更新者',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time           datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted               tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '渠道应用信息表';

-- 模板与应用关联表
drop table if exists deliver.template_app;
create table if not exists deliver.template_app
(
    id          bigint auto_increment comment '主键' primary key,
    template_id bigint                             not null comment '模板id',
    app_id      bigint                             not null comment '应用id',
    create_user varchar(10)                        not null comment '创建者',
    update_user varchar(10)                        not null comment '更新者',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '模板与应用关联表';

-- 平台文件表
drop table if exists deliver.platform_file;
create table if not exists deliver.platform_file
(
    platform_file_id          bigint auto_increment comment '平台文件id' primary key,
    platform_file_name        varchar(10)                        not null comment '平台文件名',
    platform_file_description varchar(50)                        not null comment '平台文件描述',
    platform_file_type        varchar(5)                         not null comment '平台文件类型（参考平台文件类型枚举）',
    platform_file_key         varchar(100)                       not null comment '平台文件Key',
    channel_type              tinyint                            not null comment '渠道类型（参考渠道类型枚举）',
    platform_file_status      tinyint  default 1                 null comment '平台文件状态（1-生效、0-失效）',
    app_id                    bigint                             not null comment '关联应用id',
    group_id                  bigint                             not null comment '关联分组id',
    create_user               varchar(10)                        not null comment '创建者',
    update_user               varchar(10)                        not null comment '更新者',
    create_time               datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time               datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted                   tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '平台文件表';

-- 群发任务表
drop table if exists deliver.send_task;
create table if not exists deliver.send_task
(
    task_id              bigint auto_increment comment '任务id' primary key,
    task_name            varchar(10)                        not null comment '任务名称',
    task_description     varchar(50)                        not null comment '任务描述',
    task_message_param   text                               not null comment '任务消息参数JSON',
    task_time_expression varchar(50)                        null comment '任务时间表达式',
    task_type            tinyint                            not null comment '任务类型（1-实时任务、2-定时循环任务、3-定时单次任务）',
    task_status          tinyint  default 0                 not null comment '任务状态（1-生效、0-失效）',
    template_id          bigint                             not null comment '关联模板id',
    people_group_id      bigint                             not null comment '关联人群id',
    group_id             bigint                             not null comment '关联分组id',
    create_user          varchar(10)                        not null comment '创建者',
    update_user          varchar(10)                        not null comment '更新者',
    create_time          datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time          datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted              tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '群发任务表';

-- 人群表
drop table if exists deliver.people_group;
create table if not exists deliver.people_group
(
    people_group_id          bigint auto_increment comment '人群id' primary key,
    people_group_name        varchar(10)                        not null comment '人群名称',
    people_group_description varchar(50)                        not null comment '人群描述',
    people_group_list        text                               not null comment '人群列表（英文逗号分割）',
    users_type               tinyint                            not null comment '用户类型（参考用户类型枚举）',
    group_id                 bigint                             not null comment '关联分组id',
    create_user              varchar(10)                        not null comment '创建者',
    update_user              varchar(10)                        not null comment '更新者',
    create_time              datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time              datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted                  tinyint  default 0                 not null comment '是否删除（0-不删除、1-删除）'
) comment '人群表';

-- 初始化创建一个管理员用户 密码为123456
insert into deliver.user_info(user_email, user_password, user_real_name, user_role, create_user, update_user)
    value ('oszero@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 'deliver', 'admin', 'oszero', 'oszero')