INSERT INTO coaches (user_id) SELECT id FROM members;
UPDATE coaches SET xp = 100, availability = 'Whenever you want.', introduction = 'Endorsed by your mom.';
INSERT INTO coaching_topics (user_id, topic) SELECT user_id, 'topic placeholder' FROM coaches;
