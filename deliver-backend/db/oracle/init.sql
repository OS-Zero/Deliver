-- 删除数据库（如果存在）
-- 由于Oracle中没有直接的DROP DATABASE命令，你需要手动删除表和其他对象。
-- 如果你要删除整个用户/模式，可以使用以下语句：
-- DROP USER deliver CASCADE;

-- 创建数据库（如果不存在）
-- 在Oracle中，数据库通常被称为用户或模式。你可以创建一个新用户或模式。
-- CREATE USER deliver IDENTIFIED BY your_password;

-- 授予权限
-- GRANT ALL PRIVILEGES TO deliver;

-- 消息模板表
-- 注释内容请放在表定义之前
DROP TABLE deliver.template;
CREATE TABLE deliver.template
(
    template_id   NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- 模板id
    template_name VARCHAR2(50)                         NOT NULL,   -- 模板名称
    push_range    NUMBER(1)                            NOT NULL,   -- 推送范围（0-不限 1-企业内部 2-外部）
    users_type    NUMBER(1)                            NOT NULL,   -- 用户类型（1-企业账号 2-电话 3-邮箱 4-平台userId）
    push_ways     VARCHAR2(200)                        NOT NULL,   -- 推送方式
    use_count     NUMBER(10) DEFAULT 0                 NOT NULL,   -- 模板使用数
    create_user   VARCHAR2(50),                                    -- 创建者
    update_user   VARCHAR2(50),                                    -- 更新者
    create_time   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,   -- 创建时间
    update_time   TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,   -- 更新时间
    deleted       NUMBER(1)  DEFAULT 0                 NOT NULL    -- 是否删除：0-不删除 1-删除
);

-- 渠道应用信息表（宽表）
DROP TABLE deliver.app;
CREATE TABLE deliver.app
(
    app_id      NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- appId
    name        VARCHAR2(100)                        NOT NULL,   -- 应用名称
    app_config  CLOB                                 NOT NULL,   -- 应用信息配置 json
    use_count   NUMBER(10) DEFAULT 0                 NOT NULL,   -- APP 使用数
    create_user VARCHAR2(50),                                    -- 创建者
    update_user VARCHAR2(50),                                    -- 更新者
    create_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,   -- 创建时间
    update_time TIMESTAMP  DEFAULT CURRENT_TIMESTAMP NOT NULL,   -- 更新时间
    deleted     NUMBER(1)  DEFAULT 0                 NOT NULL    -- 是否删除：0-不删除 1-删除
);

-- 模板与应用关联表
DROP TABLE deliver.template_app;
CREATE TABLE deliver.template_app
(
    id          NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, -- 主键
    template_id NUMBER                              NOT NULL,    -- 模板id
    app_id      NUMBER                              NOT NULL,    -- 应用id
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,    -- 创建时间
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,    -- 更新时间
    deleted     NUMBER(1) DEFAULT 0                 NOT NULL     -- 是否删除：0-不删除 1-删除
);

-- 消息处理日志表
