create table account_verification
(
    created_on        timestamp    not null,
    verification_code varchar(255) not null,
    user_id           integer      not null,
    primary key (user_id)
);
create table admins
(
    id integer not null,
    primary key (id)
);
create table coaches
(
    id           integer not null,
    availability varchar(255),
    introduction varchar(255),
    xp           integer,
    primary key (id)
);
create table coaches_topics
(
    coach_id  integer not null,
    topics_id integer not null
);
create table coaching_topic
(
    id             integer not null,
    topic_topic_id integer,
    primary key (id)
);
create table coaching_topic_grades
(
    coaching_topic_id integer not null,
    grades            integer
);
create table profile
(
    id              integer      not null,
    account_enabled boolean      not null,
    email           varchar(255) not null,
    first_name      varchar(255),
    last_name       varchar(255),
    password        varchar(255),
    photo_url       varchar(255),
    class_year      varchar(255),
    primary key (id)
);
create table topics
(
    topic_id integer      not null,
    name     varchar(255) not null,
    primary key (topic_id)
);
