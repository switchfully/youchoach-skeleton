create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to mncmxmjmrpepth;
-- create schema 'youcoach';

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



alter table members
    owner to mncmxmjmrpepth;

