
create schema IF NOT EXISTS youcoach;
set schema 'youcoach';

create sequence hibernate_sequence;
create table members
(
    id         bigint not null
        constraint members_pkey
            primary key,
    email      varchar(255),
    first_name varchar(255),
    last_name  varchar(255),
    password   varchar(255)
);



