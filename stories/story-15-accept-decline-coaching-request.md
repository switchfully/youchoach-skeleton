### Story 15: Accept/Decline coaching request
**As a coach I want to be able to accept or decline a coaching request.**

-  Coaching requests for Coach
    - Action button accept (coaching-sessions.button.accept)
    - Action button decline (coaching-sessions.button.decline)
   
    - Validation
        - ??
        
Tables (3) of Coaching sessions 
-  ~~Upcoming sessions(coaching-sessions.title.upcoming)~~ - out of scope (story 14)
    - List of upcoming sessions => stays here until *"when"* has passed.
-  Waiting for feedback(coaching-sessions.title.feedback)
    - List of sessions that require feedback => arrive after *"when"*, until both coach & coachee have provided feedback
-  ~~Archive(coaching-sessions.title.archive)~~ - out of scope (story 14)
    - Finished sessions => arrive after *"when"*
          
- in scope
    - Creation of the table containing the sessions requiring feedback
    - Transition of status REQUEST to status ACCEPTED to status DONE, WAITING FEEDBACK 
    - Transition of status REQUESTED to status FINISHED (DECLINED)
    - Statuses: REQUESTED -> ACCEPTED
    - Statuses: ACCEPTED -> DONE, WAITING FEEDBACK
    - Statuses: REQUESTED -> FINISHED (DECLINED)

- out of scope
    - Statuses out of scope: FINISHED (FEEDBACK GIVEN)

- mockups
    - ![xxx](../img/xxx.png)
              
    - ![xxx](../img/xxx.png)
   