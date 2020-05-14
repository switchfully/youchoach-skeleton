DELETE FROM members;
DELETE FROM coaches;
DELETE FROM admins;
DELETE FROM coaching_topics;

INSERT INTO members (email, first_name, last_name, password,school_year, photo_url) VALUES ('coach', 'jef', 'klak', 'coach','','');
INSERT INTO members (email, first_name, last_name, password, school_year, photo_url) VALUES ('student', 'hans', 'worst', 'student','','');

INSERT INTO members (email, first_name, last_name, password,school_year, photo_url) VALUES ('coachee1@school.org', 'coachee1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (email, first_name, last_name, password,school_year, photo_url) VALUES ('coachee2@school.org', 'coachee2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (email, first_name, last_name, password,school_year, photo_url) VALUES ('coach1@school.org', 'coach1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (email, first_name, last_name, password, school_year, photo_url) VALUES ('coach2@school.org', 'coach2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (email, first_name, last_name, password, school_year, photo_url) VALUES ('admin1@school.org', 'admin1', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');
INSERT INTO members (email, first_name, last_name, password, school_year, photo_url) VALUES ('admin2@school.org', 'admin2', 'person', '$2a$10$Px8bPIDgwpmiL6UuNtAp2.4BmSvlAGQF4gtY6/Wb7ZyKW5oSlz6oa','some class','my picture here');

INSERT INTO coaches (user_id, availability, introduction, xp) VALUES ((SELECT id FROM members WHERE email = 'coach1@school.org'),'my availability here','my introduction here', 100);
INSERT INTO coaches (user_id, availability, introduction, xp) VALUES ((SELECT id FROM members WHERE email = 'coach2@school.org'),'my availability here','my introduction here', 100);

INSERT INTO coaching_topics(user_id, topic) SELECT user_id, 'Coaching topic' FROM coaches;

INSERT INTO admins SELECT id FROM members WHERE first_name IN ('admin1', 'admin2');
