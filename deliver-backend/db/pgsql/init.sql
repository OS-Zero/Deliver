-- 删除数据库（如果存在）
DROP DATABASE IF EXISTS deliver;

-- 创建数据库（如果不存在）
CREATE DATABASE deliver;

-- 连接到数据库
\c deliver;

-- 消息模板表
drop table if exists deliver.template;
create table if not exists deliver.template
(
    template_id     bigserial primary key,
    template_name   varchar(50)                         not null,
    push_range      smallint                            not null,
    users_type      smallint                            not null,
    push_ways       jsonb                               not null,
    use_count       integer   default 0                 not null,
    template_status smallint  default 1                 not null,
    create_user     varchar(50)                         null,
    update_user     varchar(50)                         null,
    create_time     timestamp default CURRENT_TIMESTAMP not null,
    update_time     timestamp default CURRENT_TIMESTAMP not null,
    deleted         smallint  default 0                 not null
);

-- 添加注释到表
COMMENT ON TABLE deliver.template IS '消息模板表';

-- 添加注释到列
COMMENT ON COLUMN deliver.template.template_id IS '模板id';
COMMENT ON COLUMN deliver.template.template_name IS '模板名称';
COMMENT ON COLUMN deliver.template.push_range IS '推送范围（0-不限 1-企业内部 2-外部）';
COMMENT ON COLUMN deliver.template.users_type IS '用户类型（1-企业账号 2-电话 3-邮箱 4-平台userId）';
COMMENT ON COLUMN deliver.template.push_ways IS '推送方式
{
"channelType":（1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）
"messageType": 参考 MessageTypeEnum 的 code
}';
COMMENT ON COLUMN deliver.template.use_count IS '模板使用数';
COMMENT ON COLUMN deliver.template.template_status IS '渠道状态（1-启用 0-禁用）';
COMMENT ON COLUMN deliver.template.create_user IS '创建者';
COMMENT ON COLUMN deliver.template.update_user IS '更新者';
COMMENT ON COLUMN deliver.template.create_time IS '创建时间';
COMMENT ON COLUMN deliver.template.update_time IS '更新时间';
COMMENT ON COLUMN deliver.template.deleted IS '是否删除：0-不删除 1-删除';

-- 渠道应用信息表（宽表）
drop table if exists deliver.app;
create table if not exists deliver.app
(
    app_id       bigserial primary key,
    name         varchar(100)                        not null,
    channel_type smallint                            not null,
    app_config   jsonb                               not null,
    use_count    integer   default 0                 not null,
    app_status   smallint  default 1                 not null,
    create_user  varchar(50)                         null,
    update_user  varchar(50)                         null,
    create_time  timestamp default CURRENT_TIMESTAMP not null,
    update_time  timestamp default CURRENT_TIMESTAMP not null,
    deleted      smallint  default 0                 not null
);

-- 添加注释到表
COMMENT ON TABLE deliver.app IS '渠道应用信息表（宽表）';

-- 添加注释到列
COMMENT ON COLUMN deliver.app.app_id IS 'appId';
COMMENT ON COLUMN deliver.app.name IS '应用名称';
COMMENT ON COLUMN deliver.app.channel_type IS '消息发送渠道类型 （1-打电话 2-发短信 3-邮件 4-企业微信 5-钉钉 6-飞书）';
COMMENT ON COLUMN deliver.app.app_config IS '应用信息配置 json';
COMMENT ON COLUMN deliver.app.use_count IS 'APP 使用数';
COMMENT ON COLUMN deliver.app.app_status IS '应用状态（1-启用 0-禁用）';
COMMENT ON COLUMN deliver.app.create_user IS '创建者';
COMMENT ON COLUMN deliver.app.update_user IS '更新者';
COMMENT ON COLUMN deliver.app.create_time IS '创建时间';
COMMENT ON COLUMN deliver.app.update_time IS '更新时间';
COMMENT ON COLUMN deliver.app.deleted IS '是否删除：0-不删除 1-删除';

-- 模板与应用关联表
drop table if exists deliver.template_app;
create table if not exists deliver.template_app
(
    id          bigserial primary key,
    template_id bigint                              not null,
    app_id      bigint                              not null,
    create_time timestamp default CURRENT_TIMESTAMP not null,
    update_time timestamp default CURRENT_TIMESTAMP not null,
    deleted     smallint  default 0                 not null
);

-- 添加注释到表
COMMENT ON TABLE deliver.template_app IS '模板与应用关联表';

-- 添加注释到列
COMMENT ON COLUMN deliver.template_app.id IS '主键';
COMMENT ON COLUMN deliver.template_app.template_id IS '模板id';
COMMENT ON COLUMN deliver.template_app.app_id IS '应用id';
COMMENT ON COLUMN deliver.template_app.create_time IS '创建时间';
COMMENT ON COLUMN deliver.template_app.update_time IS '更新时间';
COMMENT ON COLUMN deliver.template_app.deleted IS '是否删除：0-不删除 1-删除';

-- 消息处理日志表
