INSERT INTO coaches (id) SELECT id FROM profile WHERE id NOT IN (SELECT id FROM coaches);
INSERT INTO admins (id) SELECT id FROM coaches WHERE id NOT IN (SELECT id FROM admins);
