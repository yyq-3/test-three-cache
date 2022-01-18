-- auto-generated definition
create table user
(
    id       int auto_increment
        primary key,
    username varchar(80) not null comment '用户唯一标识',
    password varchar(60) not null,
    name     varchar(80) not null,
    birthday varchar(40) null,
    address  varchar(80) not null,
    phone    varchar(11) not null,
    sex      tinyint(1)  not null,
    higth_cm int         null,
    width_kg int         null,
    constraint user_phone_uindex
        unique (phone),
    constraint user_username_uindex
        unique (username)
)
    comment '用户信息表';

