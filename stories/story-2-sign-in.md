### Story 2: Sign in
**As a user I want to sign in to have access to You-coach**

- A user needs to provide
    - email
    - Password (<input type='password'/>)
    
- Validation
    - user is known in the system
    - combination of email and password is valid 
    
- in scope
    - Creation of the sign in screen
        - reset password button (not yet implemented --> alert: contact admin)
    - redirect to empty profile page upon success
    - redirect to home page after sign-out
       
- Open Questions
    - How will a password reset happen before story 31 is implemented?
        - test manual encryption of password (bkrypt)

- mockups
    - ![signin](..\img\signin.png)