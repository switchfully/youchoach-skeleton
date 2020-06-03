INSERT INTO admins (user_id) SELECT id FROM members WHERE id NOT IN (SELECT user_id FROM admins);
