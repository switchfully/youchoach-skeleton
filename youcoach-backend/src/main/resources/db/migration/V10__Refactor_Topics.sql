drop table if exists topics;
drop table if exists coaches_topics;
drop table if exists coaching_topic;
drop table if exists coaching_topic_grades;

create sequence topic_seq increment by 1 start with 1;
create table topic (
    id integer not null,
    name varchar(255) not null,
    profile_id integer,
    primary key (id)
);

create sequence grade_seq increment by 1 start with 1;
create table grade(
    grade varchar(255) not null,
    topic_id integer not null,
    primary key (grade, topic_id)
);

INSERT INTO topic (id, name, profile_id) VALUES (nextval('topic_seq'), 'Algebra', 113);
INSERT INTO topic (id, name, profile_id) VALUES (nextval('topic_seq'), 'French', 113);
INSERT INTO topic (id, name, profile_id) VALUES (nextval('topic_seq'), 'Biology', 114);

INSERT INTO grade (grade, topic_id) values (3, (select id from topic where name = 'Algebra' and profile_id = '113'));
INSERT INTO grade (grade, topic_id) values (4, (select id from topic where name = 'Algebra' and profile_id = '113'));
INSERT INTO grade (grade, topic_id) values (5, (select id from topic where name = 'Algebra' and profile_id = '113'));

INSERT INTO grade (grade, topic_id) values (1, (select id from topic where name = 'French' and profile_id = '113'));
INSERT INTO grade (grade, topic_id) values (6, (select id from topic where name = 'French' and profile_id = '113'));

INSERT INTO grade (grade, topic_id) values (7, (select id from topic where name = 'Biology' and profile_id = '114'));
INSERT INTO grade (grade, topic_id) values (6, (select id from topic where name = 'Biology' and profile_id = '114'));
