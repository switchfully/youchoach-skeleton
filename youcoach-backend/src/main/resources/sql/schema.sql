create schema IF NOT EXISTS youcoach;
set schema 'youcoach';

-- create sequence hibernate_sequence;
-- ALTER table members
-- (
--     id         bigint not null
--         constraint members_pkey
--             primary key,
--     email      varchar(255),
--     first_name varchar(255),
--     last_name  varchar(255),
--     password   varchar(255),
--
-- );
ALTER TABLE members
drop COLUMN account_enabled;
alter table members
    add column account_enabled
    boolean default true;
