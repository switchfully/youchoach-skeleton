### Story 1: Register
**As a user I want to register myself to have access to You-coach.**

- Register screen (register.title)
    - First Name (register.label.first-name)
    - Last Name (register.label.last-name)
    - Email (used to sign in) (register.label.email)
    - Password  (register.label.email)
    - Password  (register.label.email)
   
    - Validation
        - email is a valid email format (register.message.email-wrong-format)
            - internal format validation, no external webservice
        - email is not yet used within the system (register.message.email-in-use)
        - password validation (register.message.password-policy, register.message.password-match)
          
- in scope
    - Creation of the homepage 
    - Creation of the register screen
    - redirect to empty profile page upon success 
    
- Open Questions
    - Password policy?
            - min 8 characters
            - min one number
            - min one capital

- mockups
    - ![Homepage](../img/homepage.png)
              
    - ![Register](../img/register.png)
   