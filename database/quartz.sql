use deliver;

-- 创建作业详情表
drop table if exists DELIVER_QRTZ_JOB_DETAILS;
create table if not exists DELIVER_QRTZ_JOB_DETAILS
(
    sched_name        varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    job_name          varchar(200) not null comment '作业名称，唯一标识作业',
    job_group         varchar(200) not null comment '作业分组，作业的分组标识',
    description       varchar(250) null comment '作业描述',
    job_class_name    varchar(250) not null comment '作业类名',
    is_durable        varchar(1)   not null comment '作业是否持久化',
    is_nonconcurrent  varchar(1)   not null comment '作业是否不可并发',
    is_update_data    varchar(1)   not null comment '作业是否更新数据',
    requests_recovery varchar(1)   not null comment '作业是否请求恢复',
    job_data          blob         null comment '作业数据',
    primary key (sched_name, job_name, job_group)
) comment = '作业详情表，用于存储作业的基本信息';

-- 创建触发器表
drop table if exists DELIVER_QRTZ_TRIGGERS;
create table if not exists DELIVER_QRTZ_TRIGGERS
(
    sched_name     varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    trigger_name   varchar(200) not null comment '触发器名称，唯一标识触发器',
    trigger_group  varchar(200) not null comment '触发器分组，触发器的分组标识',
    job_name       varchar(200) not null comment '关联的作业名称',
    job_group      varchar(200) not null comment '关联的作业分组',
    description    varchar(250) null comment '触发器描述',
    next_fire_time bigint(13)   null comment '下一次触发时间，时间戳表示下一次触发的时间',
    prev_fire_time bigint(13)   null comment '上一次触发时间，时间戳表示上一次触发的时间',
    priority       integer      null comment '触发器优先级',
    trigger_state  varchar(16)  not null comment '触发器当前状态',
    trigger_type   varchar(8)   not null comment '触发器类型',
    start_time     bigint(13)   not null comment '触发器开始时间，时间戳表示触发器生效时间',
    end_time       bigint(13)   null comment '触发器结束时间，时间戳表示触发器失效时间',
    calendar_name  varchar(200) null comment '关联的日历名称',
    misfire_instr  smallint(2)  null comment '错过触发时的处理指令',
    job_data       blob         null comment '作业数据',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group) references DELIVER_QRTZ_JOB_DETAILS (sched_name, job_name, job_group)
) comment = '触发器表，用于存储 Quartz 调度器的触发器信息';

-- 创建简单触发器表
drop table if exists DELIVER_QRTZ_SIMPLE_TRIGGERS;
create table if not exists DELIVER_QRTZ_SIMPLE_TRIGGERS
(
    sched_name      varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    trigger_name    varchar(200) not null comment '触发器名称，唯一标识触发器',
    trigger_group   varchar(200) not null comment '触发器分组，触发器的分组标识',
    repeat_count    bigint(7)    not null comment '触发器重复的次数',
    repeat_interval bigint(12)   not null comment '触发器重复的间隔时间（毫秒）',
    times_triggered bigint(10)   not null comment '触发器已触发的次数',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references DELIVER_QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) comment = '简单触发器表，用于存储 Quartz 调度器的简单类型触发器信息';

-- 创建 Cron 触发器表
drop table if exists DELIVER_QRTZ_CRON_TRIGGERS;
create table if not exists DELIVER_QRTZ_CRON_TRIGGERS
(
    sched_name      varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    trigger_name    varchar(200) not null comment '触发器名称，唯一标识触发器',
    trigger_group   varchar(200) not null comment '触发器分组，触发器的分组标识',
    cron_expression varchar(200) not null comment 'Cron 表达式，定义触发器的触发规则',
    time_zone_id    varchar(80) comment '时区标识',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references DELIVER_QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) comment = 'Cron 触发器表，用于存储基于 Cron 表达式的触发器信息';

-- 创建属性触发器表
drop table if exists DELIVER_QRTZ_SIMPROP_TRIGGERS;
create table if not exists DELIVER_QRTZ_SIMPROP_TRIGGERS
(
    sched_name    varchar(120)   not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    trigger_name  varchar(200)   not null comment '触发器名称，唯一标识触发器',
    trigger_group varchar(200)   not null comment '触发器分组，触发器的分组标识',
    str_prop_1    varchar(512)   null comment '字符串属性 1',
    str_prop_2    varchar(512)   null comment '字符串属性 2',
    str_prop_3    varchar(512)   null comment '字符串属性 3',
    int_prop_1    int            null comment '整型属性 1',
    int_prop_2    int            null comment '整型属性 2',
    long_prop_1   bigint         null comment '长整型属性 1',
    long_prop_2   bigint         null comment '长整型属性 2',
    dec_prop_1    numeric(13, 4) null comment '十进制属性 1',
    dec_prop_2    numeric(13, 4) null comment '十进制属性 2',
    bool_prop_1   varchar(1)     null comment '布尔属性 1',
    bool_prop_2   varchar(1)     null comment '布尔属性 2',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references DELIVER_QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) comment = '属性触发器表，用于存储 Quartz 调度器的属性型触发器信息';

-- 创建 Blob 触发器表
drop table if exists DELIVER_QRTZ_BLOB_TRIGGERS;
create table if not exists DELIVER_QRTZ_BLOB_TRIGGERS
(
    sched_name    varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    trigger_name  varchar(200) not null comment '触发器名称，唯一标识触发器',
    trigger_group varchar(200) not null comment '触发器分组，触发器的分组标识',
    blob_data     blob         null comment 'Blob 类型的数据，存储触发器的附加数据',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references DELIVER_QRTZ_TRIGGERS (sched_name, trigger_name, trigger_group)
) comment = 'Blob 触发器表，用于存储 Quartz 调度器的 Blob 类型触发器数据';

-- 创建日历表
drop table if exists DELIVER_QRTZ_CALENDARS;
create table if not exists DELIVER_QRTZ_CALENDARS
(
    sched_name    varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    calendar_name varchar(200) not null comment '日历名称，用于唯一标识日历',
    calendar      blob         not null comment '日历数据',
    primary key (sched_name, calendar_name)
) comment = '日历表，用于存储 Quartz 调度器的日历信息';

-- 创建暂停触发器分组表
drop table if exists DELIVER_QRTZ_PAUSED_TRIGGER_GRPS;
create table if not exists DELIVER_QRTZ_PAUSED_TRIGGER_GRPS
(
    sched_name    varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    trigger_group varchar(200) not null comment '暂停的触发器组',
    primary key (sched_name, trigger_group)
) comment = '暂停触发器分组表，用于存储暂停的触发器组信息';

-- 创建已触发触发器表
drop table if exists DELIVER_QRTZ_FIRED_TRIGGERS;
create table if not exists DELIVER_QRTZ_FIRED_TRIGGERS
(
    sched_name        varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    entry_id          varchar(95)  not null comment '触发器条目 ID',
    trigger_name      varchar(200) not null comment '触发器名称',
    trigger_group     varchar(200) not null comment '触发器组',
    instance_name     varchar(200) not null comment '实例名称',
    fired_time        bigint(13)   not null comment '触发时间，时间戳表示触发器被执行的时间',
    sched_time        bigint(13)   not null comment '调度时间，时间戳表示触发器调度的时间',
    priority          integer      not null comment '触发器的优先级',
    state             varchar(16)  not null comment '触发器当前状态',
    job_name          varchar(200) null comment '关联的作业名称',
    job_group         varchar(200) null comment '关联的作业组',
    is_nonconcurrent  varchar(1)   null comment '作业是否不可并发执行',
    requests_recovery varchar(1)   null comment '作业是否请求恢复',
    primary key (sched_name, entry_id)
) comment = '已触发触发器表，用于存储 Quartz 调度器已触发的触发器记录';

-- 创建调度器状态表
drop table if exists DELIVER_QRTZ_SCHEDULER_STATE;
create table if not exists DELIVER_QRTZ_SCHEDULER_STATE
(
    sched_name        varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    instance_name     varchar(200) not null comment '实例名称，用于标识 Quartz 调度器的实例',
    last_checkin_time bigint(13)   not null comment '最后检查时间，时间戳表示最后一次检查实例的时间',
    checkin_interval  bigint(13)   not null comment '检查间隔，表示实例之间的时间间隔',
    primary key (sched_name, instance_name)
) comment = '调度器状态表，用于存储 Quartz 调度器实例的状态信息';

-- 创建锁表
drop table if exists DELIVER_QRTZ_LOCKS;
create table if not exists DELIVER_QRTZ_LOCKS
(
    sched_name varchar(120) not null comment '调度器名称，用于区分不同的 Quartz 调度器实例',
    lock_name  varchar(40)  not null comment '锁的名称',
    primary key (sched_name, lock_name)
) comment = '锁表，用于存储 Quartz 调度器的锁信息';

commit;