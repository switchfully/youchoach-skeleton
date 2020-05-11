# You-Coach Application

![Logo](img/logo.png)

The final project of the FEB2020 track will consist of building the You-Coach application. 
The You-Coach application is an idea of the Make It Happen foundation (https://www.make-it-happen.org/) 
and aims to facilitate the peer-to-peer coaching sessions between students.

*Philippe Lambilliotte* & *Nicolas Sougnez* will be your product owners and explain their vision of the You-Coach application.

The plan is to launch the POC build by you in September as a pilot in some schools in Brussel.

## The skeleton

This project is split in two the `youcoach-backend` and the `youcoach-frontend`.
This project is meant as an example to setup a few features, which are:
- Setup of a project with both backend and frontend
- JWT-token based authentication
- working with translations in Angular

We choose to work with a single codebase because:
- feature commits can combine frontend and backend code
- cloning a project shouldn't be a hassle

However this can have problems for:
- importing your project in intellij
- setting up a devops pipeline

### Youcoach-frontend
To import in intellij:
1. Go to project structure
1. Click on 'modules'
1. Create a new module (static web and angular cli)
1. Follow the wizard and don't forget to select the youcoach-frontend directory as a source!

The frontend supports following features:
- bootstrap
- i18n
- jwt-token based login

#### I18N
i18n or 'internationalization' is the term used to describe how your app adepts to the users locale (nationality).
We mainly use it for translation.

- We've made a translation project that you can find at `https://github.com/switchfully/youcoach-i18n`.
- The project is setup in such a way that the translations are loaded as soon as you serve or build the project.
- We use `ngx-translate` as a 3th party library to manage the translations.
- If you want to add new translations please take them to the scrum of scrums.
- The `youcoach-i18n` project will be updated based on your requests. Make sure to do a `npm install` regulary, to get the latest version!

The general structure of the translation file is:
```json
{
  "name-of-component": {
      "name-of-element-type(like: title, message, button, label, etc...)": {
          "id-of-element": "translation" 
      }
  }
}
```
- e.g. "login.message.loginsuccessful" 

- when you add `translate` to a html-tag `ngx-translate` will try translate the contents of that tag.
    - e.g. `<button translate>login.button.logout</button>`

#### Bootstrap
- Bootstrap is used as css framework but is not required to be used.
- The mockups are based on `angular-material`. So you can always switch to that framework

#### JWT-tokens
- The `login component` is setup that it will do a POST call with the username and body.
- The response will contain a header with a JWT-token.
- This token will be saved in your session.
- All other calls to the backend need to contain this JWT-token otherwise the call will be `not authorized`.
- An `interceptor` is made so that, if available, your JWT-token will be automatically attached to every http call.
- All this is managed in the `authentication` directory. Normally nothing in this directory needs to be changed (unless we made an oupsy).

### Youcoach-backend
The backend is written in Spring-Boot with JWT-token security written in Spring Security.

#### JWT-token
Security protocol that avoid sending username+password for every request. Implementation is copied from project-jericho in track-java/security.
Normally you shouldn't have to touch the classes in the jwt-package.

#### User package
Used for user authentication.
You should have at least have all the users defined in `secured-users`. You can used the SecuredUserRepositoryFromJson to load the json file.

In the final version of your project you should manage your users in the database.

#### Authorization package
Basic authorization package used for mapping roles to features. This whole package can be modified.

## High level analysis

Before the start of the project we typically conduct a High Level Analysis (HLA) phase. 
The goal of this phase is to provide just enough upfront analysis to start the project without defining the complete blueprint of the application.

### C4 model

In order to communicate about the architecture of the applications we've chosen to use the C4 Model (Simon Brown).
The diagrams below are a materialization of ou HLA phase. Feel free to challenge.

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


