create table admins
(
    username  varchar(18)                  not null comment '管理员名称',
    password  varchar(255)                 not null comment '管理员密码',
    adminid   char(15)                     not null comment '管理员ID',
    ICode     char(10)                     not null comment '邀请码',
    ICodeS    tinyint unsigned default '0' null comment '邀请码是否使用 1为是 0为否',
    State     tinyint unsigned default '0' not null comment '状态 0正常 1封禁',
    token     char(20)                     null comment '管理员登录注释',
    tokentime datetime                     null comment 'token生成时间',
    constraint ICode
        unique (ICode),
    constraint adminid
        unique (adminid),
    constraint token
        unique (token),
    constraint username
        unique (username)
);

create table developer
(
    username        varchar(18)                  not null comment '开发者账号',
    password        varchar(255)                 not null comment '开发者密码',
    id              char(18)                     not null comment '开发者ID',
    Security_answer varchar(255)                 not null comment '开发者约定的密保答案',
    login_ip        varchar(17)                  null comment '登录IP',
    State           tinyint unsigned default '0' not null comment '开发者状态 0正常 1封禁',
    constraint id
        unique (id),
    constraint username
        unique (username)
)
    comment '开发者表';

create table users
(
    deveid    char(18)                     not null comment '开发者ID',
    username  varchar(18)                  not null comment '用户名',
    password  varchar(255)                 not null comment '密码',
    Points    int unsigned     default '0' null comment '用户积分',
    checktime datetime                     null comment '签到时间',
    State     tinyint unsigned default '0' not null comment '用户状态 0正常 1封禁'
)
    comment '开发者下属用户表';

create index userid
    on users (deveid);

