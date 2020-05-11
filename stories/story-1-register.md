### Story 1: Register
**As a user I want to register myself to have access to You-coach.**

- A user needs to provide
    - First Name (register.label.first-name)
    - Last Name (register.label.last-name)
    - Email (used to sign in) (register.label.email)
    - Password  (register.label.email)
    - Password  (register.label.email)
   
- Validation
    - email is a valid email format (internal format validation, no external webservice)
    - email is not yet used within the system
    - password validation    
    
- in scope
    - Creation of the homepage 
    - Creation of the register user screen
    - redirect to empty profile page upon success 
    
- Open Questions
    - Password policy?
            - min 8 characters
            - min one number
            - min one capital

- mockups
    - ![Homepage](../img/homepage.png)
              
    - ![Register](../img/register.png)
   