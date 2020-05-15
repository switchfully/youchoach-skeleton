-- create table account_verification (created_on timestamp not null, verification_code varchar(255) not null, user_id bigint not null, primary key (user_id))
-- create table admins (user_id bigint not null, primary key (user_id))
-- create table coaches (availability varchar(255), introduction varchar(255), xp integer, user_id bigint not null, primary key (user_id))
-- create table coaches_topics (coach_user_id bigint not null, topics_id bigint not null)
-- create table coaching_topic (id bigint not null, topic_topic_id integer, primary key (id))
-- create table coaching_topic_grades (coaching_topic_id bigint not null, grades integer)
-- create table members (id bigint not null, account_enabled boolean not null, email varchar(255) not null, first_name varchar(255), last_name varchar(255), password varchar(255), photo_url varchar(255), school_year varchar(255), primary key (id))
-- create table topics (topic_id integer not null, name varchar(255) not null, primary key (topic_id))
-- alter table coaches_topics add constraint UK_9l964dg81lk07ofqagvxbupow unique (topics_id)
-- alter table members add constraint UK_9d30a9u1qpg8eou0otgkwrp5d unique (email)
-- alter table topics add constraint UK_7tuhnscjpohbffmp7btit1uff unique (name)
-- alter table account_verification add constraint FKglx0w7njieyv9xprt465m2i6q foreign key (user_id) references members
-- alter table admins add constraint FKjoasa92wy96dlicsbfcdl5krg foreign key (user_id) references members
-- alter table coaches add constraint FK5h0cksgba49yg6apbiie5wlvy foreign key (user_id) references members
-- alter table coaches_topics add constraint FKhihfdn0xxsvr4bx9jaokql5pf foreign key (topics_id) references coaching_topic
-- alter table coaches_topics add constraint FKfhcuxfpmbn4inpk0vidwea8s6 foreign key (coach_user_id) references coaches
-- alter table coaching_topic add constraint FKl62wqdgeidhvwvp5j6qhpb8df foreign key (topic_topic_id) references topics
-- alter table coaching_topic_grades add constraint FK13ryigkrx2nw7bkg78irl6uqq foreign key (coaching_topic_id) references coaching_topic
DELETE FROM members;
DELETE FROM coaches;
DELETE FROM admins;

DELETE FROM coaches_topics;
DELETE FROM topics;

-- INSERT INTO members (email, first_name, last_name, password,school_year, photo_url) VALUES ('coach', 'jef', 'klak', 'coach','','');
-- INSERT INTO members (email, first_name, last_name, password, school_year, photo_url) VALUES ('student', 'hans', 'worst', 'student','','');

INSERT INTO members (id, email, first_name, last_name, password,school_year, photo_url, account_enabled) VALUES (111, 'coachee1@school.org', 'coachee1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here', TRUE);
INSERT INTO members (id, email, first_name, last_name, password,school_year, photo_url, account_enabled) VALUES (112, 'coachee2@school.org', 'coachee2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here', TRUE);
INSERT INTO members (id, email, first_name, last_name, password,school_year, photo_url, account_enabled) VALUES (113, 'coach1@school.org', 'coach1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, school_year, photo_url, account_enabled) VALUES (114, 'coach2@school.org', 'coach2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, school_year, photo_url, account_enabled) VALUES (115, 'admin1@school.org', 'admin1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, school_year, photo_url, account_enabled) VALUES (116, 'admin2@school.org', 'admin2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here', TRUE);

INSERT INTO coaches (user_id, availability, introduction, xp) VALUES (113,'my availability here','my introduction here', 100);
INSERT INTO coaches (user_id, availability, introduction, xp) VALUES (114,'my availability here','my introduction here', 100);

INSERT INTO topics (topic_id, name) VALUES (100, 'Algebra');
INSERT INTO topics (topic_id, name) VALUES (101, 'French');
INSERT INTO topics (topic_id, name) VALUES (102, 'Biology');

INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1000, 100);
INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1001, 101);
INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1002, 102);

-- coach1
INSERT INTO coaches_topics (coach_user_id, topics_id) VALUES (113, 1000); -- Algebra
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1000, 4);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1000, 3);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1000, 5);
INSERT INTO coaches_topics (coach_user_id, topics_id) VALUES (113, 1001); -- French
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1001, 1);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1001, 6);

--coach2
INSERT INTO coaches_topics (coach_user_id, topics_id) VALUES (114, 1002); -- Biology

INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1002, 7);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1002, 6);


INSERT INTO admins (user_id) VALUES(115) ,(116);
