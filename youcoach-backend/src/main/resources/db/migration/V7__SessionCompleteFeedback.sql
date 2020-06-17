alter table session drop coach_feedback_on_time;
alter table session add coach_on_time varchar(255);
alter table session add coach_clear_explanation varchar(255);
alter table session add coach_useful_session varchar(255);
alter table session add coach_get_better varchar(255);
alter table session add coachee_like_what varchar(255);

alter table session drop coachee_feedback_on_time;
alter table session add coachee_on_time varchar(255);
alter table session add coachee_willing_to_learn varchar(255);
alter table session add coachee_well_prepared varchar(255);
alter table session add coachee_get_better varchar(255);
alter table session add coach_like_what varchar(255);
