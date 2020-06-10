INSERT INTO admins (id) SELECT id FROM members WHERE id NOT IN (SELECT id FROM admins);
