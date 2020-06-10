INSERT INTO members (id, email, first_name, last_name, password, class_year, photo_url, account_enabled) VALUES (111, 'coachee1@school.org', 'Connor', 'McGregor', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','6A - Economics','/assets/img/coachee1.jpg', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, class_year, photo_url, account_enabled) VALUES (112, 'coachee2@school.org', 'Billie', 'Ellish', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','4C - Electronics','/assets/img/coachee2.jpg', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, class_year, photo_url, account_enabled) VALUES (113, 'coach1@school.org', 'Philippe', 'Lambilliotee', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','2B - Trade','/assets/img/coach.jpg', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, class_year, photo_url, account_enabled) VALUES (114, 'coach2@school.org', 'Nicolas', 'Sougnez', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','1A - Latin','/assets/img/coach2.jpg', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, class_year, photo_url, account_enabled) VALUES (115, 'admin1@school.org', 'Elvis', 'Presley', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','Reception','/assets/img/admin1.jpg', TRUE);
INSERT INTO members (id, email, first_name, last_name, password, class_year, photo_url, account_enabled) VALUES (116, 'admin2@school.org', 'Hilary', 'Clinton', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','Teacher - French & English','/assets/img/admin2.jpg', TRUE);

INSERT INTO coaches (user_id, availability, introduction, xp) VALUES (113,'I am free every wednesday afternoon','Hello, my name is Frank and I can give coaching sessions on algebra and French', 100);
INSERT INTO coaches (user_id, availability, introduction, xp) VALUES (114,'I can make myself free every other weekend','Hello, my name is Warren and I am pretty good at Latin', 100);

INSERT INTO topics (topic_id, name) VALUES (100, 'Algebra');
INSERT INTO topics (topic_id, name) VALUES (101, 'French');
INSERT INTO topics (topic_id, name) VALUES (102, 'Biology');

INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1000, 100);
INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1001, 101);
INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1002, 102);

-- coach1
INSERT INTO coaches_topics (coach_user_id, topics_id) VALUES (113, 1000); -- Algebra

INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1000, 3);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1000, 4);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1000, 5);

INSERT INTO coaches_topics (coach_user_id, topics_id) VALUES (113, 1001); -- French
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1001, 1);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1001, 6);

--coach2
INSERT INTO coaches_topics (coach_user_id, topics_id) VALUES (114, 1002); -- Biology

INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1002, 7);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1002, 6);


INSERT INTO admins (ID) VALUES(115);
INSERT INTO admins (ID) VALUES(116);
