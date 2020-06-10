create table account_verification
(
    created_on        timestamp    not null,
    verification_code varchar(255) not null,
    user_id           number       not null,
    primary key (user_id)
);
create table admins
(
    id number not null,
    primary key (id)
);
create table coaches
(
    availability varchar(255),
    introduction varchar(255),
    xp           integer,
    user_id      number not null,
    primary key (user_id)
);
create table coaches_topics
(
    coach_user_id number not null,
    topics_id     number not null
);
create table coaching_topic
(
    id             number not null,
    topic_topic_id integer,
    primary key (id)
);
create table coaching_topic_grades
(
    coaching_topic_id number not null,
    grades            integer
);
create table members
(
    id              number       not null,
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
