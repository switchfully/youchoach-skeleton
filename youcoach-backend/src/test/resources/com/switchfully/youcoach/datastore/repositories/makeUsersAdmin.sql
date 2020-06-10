INSERT INTO admins (id) SELECT id FROM profile WHERE id NOT IN (SELECT id FROM admins);
