create sequence session_seq START WITH 1 increment by 1;

create table session
(
    id            integer   not null,
    coach_id      integer   not null,
    coachee_id    integer   not null,
    date_and_time timestamp not null,
    location      varchar2(255),
    remarks       varchar2(255),
    status        varchar2(255),
    subject       varchar2(255)
)
