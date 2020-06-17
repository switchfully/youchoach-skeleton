alter table session drop coach_feedback;
alter table session drop feedback;
alter table session add coach_feedback_free_text varchar(255);
alter table session add coach_feedback_on_time varchar(255);
alter table session add coachee_feedback_free_text varchar(255);
alter table session add coachee_feedback_on_time varchar(255);
