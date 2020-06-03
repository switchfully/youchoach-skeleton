### Story 16: Provide feedback as a coachee
**As a coachee I want to be able to provide feedback to the coach (feedback form).**

-  Feedback on coaching session by Coachee
    - Action button Provide Feedback (coaching-sessions.button.feedback)
    
- Form 
    - Was your Coach on time at the coaching session? (smiles)
    - Were the explanations given by your Coach clear? (smiles)
    - Was the coaching session useful ? (smiles)
    - Comments: (textarea)
        - What did you like in the coaching session?
        - How could your Coach get better ?
   
    - Validation
        - answer to the first three questions mandatory
        - comments are optional
          
- in scope
    - Feedback action button
    - Feedback form for coachee

- out of scope
    - Statuses: DONE, WAITING FEEDBACK -> FINISHED (FEEDBACK GIVEN)
        - Status can only change to FINISHED when both Coach and Coachee have provided feedback
    
- Open Questions

- mockups
    - ![xxx](../img/xxx.png)
              
    - ![xxx](../img/xxx.png)            

- (Full) Lifecycle of a Coaching session
[![](https://mermaid.ink/img/eyJjb2RlIjoic3RhdGVEaWFncmFtXG5cblx0WypdIC0tPiBSRVFVRVNURUQ6IHJlcXVlc3Qgc2Vzc2lvblxuXHRSRVFVRVNURUQgLS0-IFsqXSA6IEF1dG9tYXRpY2FsbHkgY2xvc2VkXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBDYW5jZWxsZWQgYnkgQ29hY2hlZVxuICAgIFJFUVVFU1RFRCAtLT4gQUNDRVBURUQ6IEFjY2VwdFxuICAgIEFDQ0VQVEVEIC0tPiBET05FX1dBSVRJTkdfRkVFREJBQ0s6IChhdXRvbWF0aWMpXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBEZWNsaW5lXG4gICAgRE9ORV9XQUlUSU5HX0ZFRURCQUNLIC0tPiBbKl06IEZlZWRiYWNrIHByb3ZpZGVkXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoZWVcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoic3RhdGVEaWFncmFtXG5cblx0WypdIC0tPiBSRVFVRVNURUQ6IHJlcXVlc3Qgc2Vzc2lvblxuXHRSRVFVRVNURUQgLS0-IFsqXSA6IEF1dG9tYXRpY2FsbHkgY2xvc2VkXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBDYW5jZWxsZWQgYnkgQ29hY2hlZVxuICAgIFJFUVVFU1RFRCAtLT4gQUNDRVBURUQ6IEFjY2VwdFxuICAgIEFDQ0VQVEVEIC0tPiBET05FX1dBSVRJTkdfRkVFREJBQ0s6IChhdXRvbWF0aWMpXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBEZWNsaW5lXG4gICAgRE9ORV9XQUlUSU5HX0ZFRURCQUNLIC0tPiBbKl06IEZlZWRiYWNrIHByb3ZpZGVkXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoZWVcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)
[Excel with examples state changes](youcoach-examples-state-changes.xlsx)