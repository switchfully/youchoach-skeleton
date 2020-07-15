--INSERT INTO coaches (id) SELECT id FROM profiles WHERE id NOT IN (SELECT id FROM coaches);
update profile
set role = 'COACH',
    xp           = 100,
    availability = 'Whenever you want.',
    introduction = 'Endorsed by your mom.'
where id = 20;

INSERT INTO topic (id, name, profile_id) VALUES (topic_seq.nextval, 'Algebra', 20);
INSERT INTO grade (grade, topic_id) values (3, (select id from topic where name = 'Algebra' and profile_id = '20'));
INSERT INTO grade (grade, topic_id) values (4, (select id from topic where name = 'Algebra' and profile_id = '20'));
