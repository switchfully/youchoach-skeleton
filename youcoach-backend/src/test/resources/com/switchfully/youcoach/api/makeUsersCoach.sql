INSERT INTO coaches (user_id) SELECT id FROM members;
UPDATE coaches SET xp = 100, availability = 'Whenever you want.', introduction = 'Endorsed by your mom.';