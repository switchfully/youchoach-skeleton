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

- (Full) Lifecycle of a Coaching session
[![](https://mermaid.ink/img/eyJjb2RlIjoic3RhdGVEaWFncmFtXG5cblx0WypdIC0tPiBSRVFVRVNURUQ6IHJlcXVlc3Qgc2Vzc2lvblxuXHRSRVFVRVNURUQgLS0-IFsqXSA6IEF1dG9tYXRpY2FsbHkgY2xvc2VkXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBDYW5jZWxsZWQgYnkgQ29hY2hlZVxuICAgIFJFUVVFU1RFRCAtLT4gQUNDRVBURUQ6IEFjY2VwdFxuICAgIEFDQ0VQVEVEIC0tPiBET05FX1dBSVRJTkdfRkVFREJBQ0s6IChhdXRvbWF0aWMpXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBEZWNsaW5lXG4gICAgRE9ORV9XQUlUSU5HX0ZFRURCQUNLIC0tPiBbKl06IEZlZWRiYWNrIHByb3ZpZGVkXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoZWVcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoic3RhdGVEaWFncmFtXG5cblx0WypdIC0tPiBSRVFVRVNURUQ6IHJlcXVlc3Qgc2Vzc2lvblxuXHRSRVFVRVNURUQgLS0-IFsqXSA6IEF1dG9tYXRpY2FsbHkgY2xvc2VkXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBDYW5jZWxsZWQgYnkgQ29hY2hlZVxuICAgIFJFUVVFU1RFRCAtLT4gQUNDRVBURUQ6IEFjY2VwdFxuICAgIEFDQ0VQVEVEIC0tPiBET05FX1dBSVRJTkdfRkVFREJBQ0s6IChhdXRvbWF0aWMpXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBEZWNsaW5lXG4gICAgRE9ORV9XQUlUSU5HX0ZFRURCQUNLIC0tPiBbKl06IEZlZWRiYWNrIHByb3ZpZGVkXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoZWVcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)
[Excel with examples state changes](youcoach-examples-state-changes.xlsx)
   