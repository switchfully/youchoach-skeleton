### Story 14: Coaching sessions overview
**As a coach/coachee I want to have an overview of my coaching sessions ~~requests~~ and their status.**

> NOTE: There *IS NO DIFFERENCE* between a coaching request & a coaching session
>
> The only concept that exists is a coaching session with diffent statuses.

Tables (3) of Coaching sessions 
-  Upcoming sessions(coaching-sessions.title.upcoming)
    - List of upcoming sessions => stays here until "when" has passed.
-  ~~Waiting for feedback(coaching-sessions.title.feedback)~~ - out of scope (after story 15)
    - List of sessions that require feedback => arrive after *"when"*, until both coach & coachee have provided feedback
-  Archive(coaching-sessions.title.archive)
    - Finished sessions => arrive after *"when"*

Table columns for Coachee
    - Topic (coaching-sessions.label.topic)
    - Coach (coaching-sessions.label.coach)
    - Where and when (coaching-sessions.label.where-when)
    - Status (coaching-sessions.label.status)
    - Action (coaching-sessions.label.action)
    
Table columns for Coach
    - Topic (coaching-sessions.label.topic)
    - Coachee (coaching-sessions.label.coachee)
    - Where and when (coaching-sessions.label.where-when)
    - Status (coaching-sessions.label.status)
    - Action (coaching-sessions.label.action)
   
    - Validation
        - ??
          
- in scope
    - Creation of the coaching sessions overview screen, for both the coach & the coachee
    - Creation of the 2 tables containing the Upcoming sessions & the Archived sessions
    - Transition from status REQUESTED to status FINISHED (AUTOMATICALLY CLOSED) 
    - Statuses: REQUESTED
    - Statuses: FINISHED (AUTOMATICALLY CLOSED)

- out of scope
    - Statuses out of scope: (REQUESTED ->) ACCEPTED -> DONE, WAITING FEEDBACK -> FINISHED (FEEDBACK GIVEN)

- mockups
    - ![xxx](../img/xxx.png)
              
    - ![xxx](../img/xxx.png)
   