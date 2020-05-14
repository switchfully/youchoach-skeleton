INSERT INTO coaches (user_id) SELECT id FROM members WHERE id NOT IN (SELECT user_id FROM coaches);
UPDATE coaches SET xp = 100, availability = 'Whenever you want.', introduction = 'Endorsed by your mom.';
INSERT INTO coaching_topics (user_id, topic) SELECT user_id, 'topic placeholder' FROM coaches;
