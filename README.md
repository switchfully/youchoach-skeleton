# You-Coach Application

![Logo](img\PictoSiteWeb-04.png)

The final project of the FEB2020 track will consist of building the You-Coach application. 
The You-Coach application is an idea of the Make It Happen foundation (https://www.make-it-happen.org/) 
and aims to facilitate the peer-to-peer coaching sessions between students.

*Philippe Lambilliotte* & *Nicolas Sougnez* will be your product owners and explain their vision of the You-Coach application.

The plan is to launch the POC build by you in September as a pilot in some schools in Brussel.

## High level analysis

Before the start of the project we typically conduct a High Level Analysis (HLA) phase. 
The goal of this phase is to provide just enough upfront analysis to start the project without defining the complete blueprint of the application.

### C4 model

In order to communicate about the architecture of the applications we've chosen to use the C4 Model (Simon Brown).
The diagrams below are a materializitation of ou HLA phase. Feel free to challenge.

#### Context diagram

#### Container diagram

**TO DO**

#### Component diagram

**TO DO**

#### Class diagram

**TO DO**

## Non-functional requirements

### Project constraints

- Follow the SCRUM framework
    - Project kickoff
    - Sprint kickoff
    - Daily standup (rotating SCRUM master role)
    - Daily Scrum of Scrums at 12
    - Backlog refinement sessies 
    - Sprint review (Sprint backlog overview + Demo)
    - Retrospective 
- Deliverables (Physical or digitized)
    - Project backlog (up to date!) (user stories, technical tasks, ...)
    - Sprint backlog (estimated + commitment)
    - A clean Scrum board (sprint backlog, user stories, technical tasks, story leads/pairs, impediments, ... )
    - Definition Of Done
    - Evolving Domain model
    - C4 model (Context, Container, Component)
    - A build monitor dashboard
    
### Timing and agenda

- Kickoff (8/5)
- Sprint 1 (11/5 - 14/5)
    - sprint review: 14/5 (14h-16h)
- Sprint 2 (15/5 - 20/5)
    - sprint review: 20/5 (14h-16h)
- Sprint 3 (25/5 - 29/5)  
    - Sprint review: 29/5 (9-11)   

### Technology constraints

- Create a new GitHub repository (one per team)
    - Think about the files Git should ignore, do this before making your first commit...
- Provide a REST(ful) web API (with JSON as the message / body format)
- Use Spring Boot (latest release)
- Use Maven
- Setup a Jenkins or Travis or GitHub Actions for continuous integration
    - We'll help you with this (but honestly, it's really up to you to configure it!)...
- Perform logging (use spring-boot-starter's logging dependencies: logback and slf4j)
    - Certainly log all interactions with the application that can be defined as "errors"
        - E.g.: unauthorized access, illegal arguments, exceptions in general,...
- Include OpenAPI using Swagger(UI) to provide a readable documentation/manual of your REST(ful) web API
- use JPA (Hibernate or EclipseLink) in combination with a PostgreSQL or Oracle Database to store and access the data.
    - Setup a proper test configuration, which runs the integration tests against an in-memory database (e.g. H2)
        - Make it a separate technical story.
    - Correctly setup and handle the transactions.
    - Write your DDL (create tables,...) in a separate `.sql` file, which you also put under version control.
- Think about security (Basic Auth. or JWT), but is doesn't have to be a priority.
- Use materialize CSS and Angular for the front-end    


## Functional Stories

The functional requirements are written down as stories.

- The functional analyst/Product owner will be available to answer all your questions during the Scrum of Scrums
- The functional analyst made some design decisions, if you want to challenge those, you always can. Come prepared though. :grin:

### Story 1: Register
**As a user I want to register myself to have access to You-coach.**

- A user needs to provide
    - First Name
    - Last Name
    - Email (used to sign in)
    - Password (2 times)
    
- Validation
    - email is a valid email format
    - email is not yet used within the system      
    
- in scope
    - Creation of the homepage 
    - Creation of the register user screen
    - redirect to empty profile page upon success       

- Open Questions
    - Password policy?
    - How to become an administrator (via database)?
    
    
### Story 2: Sign in
**As a user I want to sign in to have access to You-coach**

- A user needs to provide
    - email
    - Password (not readable on screen)
    
- Validation
    - user is known in the system
    - combination of user and password is valid
    - user has not status: blocked     
    
- in scope
    - Creation of the homepage 
    - Creation of the sign in screen
        - reset password button (not yet implemented --> contact admin)
    - redirect to empty profile page upon success
    - redirect to home page after sign-out
       
- Open Questions
    - How will a password reset happen before story 31 is implemented?
    - adding status blocked via DB --> benefit?
    - adding user role at logon?
        - logged on as coachee
        - logged on as coach
        - logged on as admin
    
### Story 3: Profile information
 **As a coachee I want to have an overview of my 'profile information'**   
 
 - Profile information contains:
     - First name
     - Last name
     - Email
     - class  
     - You-coach role
     - Status 
     - picture   
      
 - in scope
     - Creation of the 'My Profile' menu
     - Creation of the profile information page     
 
 - Open Questions
     - picture in scope?
     - You-coach role displayed to coachee?
     - Status displayed to coachee?
        - or only to admin?
     - class entered at registration? or only after editing user profile?
     - own profile accessible by coachee only
     - all profiles accessible by administrator (via url)
     
### Story 3: Edit Profile information
 **As a coachee I want to be able to edit my 'profile information'**   
 
 - coachee can edit
     - First name
     - Last name
     - email (disabled)
     - class  
     - picture   
      
 - in scope
     - Creation of the edit 'My Profile' page
     - save button 
     - cancel button
      
 - Open Questions
     - edit picture in scope?
     - editing of user profiles by admin in scope?
        - admin can edit more fields (status, role, ...)
 
   
 4 - As a coachee I want to be able to edit my 'profile information'    
     