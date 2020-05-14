DELETE FROM members;
DELETE FROM coaches;
DELETE FROM admins;
DELETE FROM coaching_topics;

-- INSERT INTO members (email, first_name, last_name, password,school_year, photo_url) VALUES ('coach', 'jef', 'klak', 'coach','','');
-- INSERT INTO members (email, first_name, last_name, password, school_year, photo_url) VALUES ('student', 'hans', 'worst', 'student','','');

INSERT INTO members (id, email, first_name, last_name, password,school_year, photo_url) VALUES (111, 'coachee1@school.org', 'coachee1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (id, email, first_name, last_name, password,school_year, photo_url) VALUES (112, 'coachee2@school.org', 'coachee2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (id, email, first_name, last_name, password,school_year, photo_url) VALUES (113, 'coach1@school.org', 'coach1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (id, email, first_name, last_name, password, school_year, photo_url) VALUES (114, 'coach2@school.org', 'coach2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (id, email, first_name, last_name, password, school_year, photo_url) VALUES (115, 'admin1@school.org', 'admin1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (id, email, first_name, last_name, password, school_year, photo_url) VALUES (116, 'admin2@school.org', 'admin2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');

INSERT INTO coaches (user_id, availability, introduction, xp) VALUES (113,'my availability here','my introduction here', 100);
INSERT INTO coaches (user_id, availability, introduction, xp) VALUES (114,'my availability here','my introduction here', 100);

INSERT INTO coaching_topics(user_id, topic) VALUES (113, 'Coaching topic');
INSERT INTO coaching_topics(user_id, topic) VALUES (114, 'Coaching topic');

INSERT INTO admins (user_id) VALUES(115) ,(116);
