### Story 13: Request a session
**As a coachee I want to request a coaching session with a coach (contact form).**

- Request a session screen (request-session.title)
    - Subject (request-session.label.subject)
    - Date (request-session.label.date)
    - Time (used to sign in) (request-session.label.time)
    - Location  (request-session.label.location)
    - Remarks  (request-session.label.remarks)
   
    - Validation
        - date is either today or in the future
            - date validation (request-session.message.date-past)
          
- in scope
    - Creation of the request a session screen (redirect from detail of a coach profile)
    - redirect to profile page upon success 
    
- Open Questions

- mockups
    - ![Homepage](../img/request-session.png)              

   