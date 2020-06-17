create sequence profile_seq increment by 1 start with 20;

alter table account_verification rename column user_id to id;
