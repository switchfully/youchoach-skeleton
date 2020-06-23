alter table profile
    add (
        role varchar(255) default 'COACHEE' not null,
        availability varchar(255),
        introduction varchar(255),
        xp integer
        );

update profile
    set role = 'COACH',
        availability = 'I am free every wednesday afternoon',
        introduction = 'Hello, my name is Frank and I can give coaching sessions on algebra and French',
        xp = 100
    where id = 113;

update profile
set role = 'COACH',
    availability = 'I can make myself free every other weekend',
    introduction = 'Hello, my name is Warren and I am pretty good at Latin',
    xp = 100
where id = 114;

update profile
set role = 'ADMIN'
where id in (115, 116);

drop table admins;
drop table coaches;
