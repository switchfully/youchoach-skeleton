--INSERT INTO coaches (id) SELECT id FROM profiles WHERE id NOT IN (SELECT id FROM coaches);
INSERT INTO coaches (id) values (20);

UPDATE coaches SET xp = 100, availability = 'Whenever you want.', introduction = 'Endorsed by your mom.';

INSERT INTO topics (topic_id, name) VALUES (1, 'Algebra');
INSERT INTO coaching_topic (id, topic_topic_id) VALUES (1, 1);
INSERT INTO coaches_topics (coach_id, topics_id) VALUES (20, 1); -- Algebra
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1, 4);
INSERT INTO coaching_topic_grades (coaching_topic_id, grades) VALUES (1, 3);
