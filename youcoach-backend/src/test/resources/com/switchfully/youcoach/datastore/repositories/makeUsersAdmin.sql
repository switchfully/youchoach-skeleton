INSERT INTO admins (id) SELECT id FROM profiles WHERE id NOT IN (SELECT id FROM admins);
