DELETE FROM coaching_topic_grades;
DELETE FROM coaches_topics;
DELETE FROM coaching_topic;
DELETE FROM topics;

--INSERT INTO coaches (user_id) SELECT id FROM members WHERE id NOT IN (SELECT user_id FROM coaches);
INSERT INTO coaches (user_id) values (1);

UPDATE coaches SET xp = 100, availability = 'Whenever you want.', introduction = 'Endorsed by your mom.';

INSERT INTO topics (topic_id, name) VALUES (1, 'Algebra');
INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1, 1);
INSERT INTO coaches_topics (coach_user_id, topics_id) VALUES (1, 1); -- Algebra
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1, 4);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1, 3);
