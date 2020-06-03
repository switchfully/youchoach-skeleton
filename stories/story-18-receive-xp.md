### Story 18: Receive XP
**As a coach I want to be rewarded for the coaching session with XP points (10p)**
     
 - Coach information section of coach profile contains:
      - Coach XP (coach-information.label.xp)

 - in scope
      - Update coach profile, to show the XP points
      - The XP points are private, and only visible to the coach and the admins
      - 10 XP points are awarded after **both** the coach **and** the coachee have provided feedback
      - The coaching sessions moves to the Archived Sessions table on Coaching Sessions page (for both Coach & Coachee)
      - Statuses: (REQUESTED -> ACCEPTED -> DONE, WAITING FEEDBACK ->) FINISHED (FEEDBACK GIVEN)
     
  - Open Questions


 - mockups
     - ![coach-profile](../img/my-coach-profile.png)
 
- (Full) Lifecycle of a Coaching session
[![](https://mermaid.ink/img/eyJjb2RlIjoic3RhdGVEaWFncmFtXG5cblx0WypdIC0tPiBSRVFVRVNURUQ6IHJlcXVlc3Qgc2Vzc2lvblxuXHRSRVFVRVNURUQgLS0-IFsqXSA6IEF1dG9tYXRpY2FsbHkgY2xvc2VkXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBDYW5jZWxsZWQgYnkgQ29hY2hlZVxuICAgIFJFUVVFU1RFRCAtLT4gQUNDRVBURUQ6IEFjY2VwdFxuICAgIEFDQ0VQVEVEIC0tPiBET05FX1dBSVRJTkdfRkVFREJBQ0s6IChhdXRvbWF0aWMpXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBEZWNsaW5lXG4gICAgRE9ORV9XQUlUSU5HX0ZFRURCQUNLIC0tPiBbKl06IEZlZWRiYWNrIHByb3ZpZGVkXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoZWVcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)](https://mermaid-js.github.io/mermaid-live-editor/#/edit/eyJjb2RlIjoic3RhdGVEaWFncmFtXG5cblx0WypdIC0tPiBSRVFVRVNURUQ6IHJlcXVlc3Qgc2Vzc2lvblxuXHRSRVFVRVNURUQgLS0-IFsqXSA6IEF1dG9tYXRpY2FsbHkgY2xvc2VkXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBDYW5jZWxsZWQgYnkgQ29hY2hlZVxuICAgIFJFUVVFU1RFRCAtLT4gQUNDRVBURUQ6IEFjY2VwdFxuICAgIEFDQ0VQVEVEIC0tPiBET05FX1dBSVRJTkdfRkVFREJBQ0s6IChhdXRvbWF0aWMpXG4gICAgUkVRVUVTVEVEIC0tPiBbKl0gOiBEZWNsaW5lXG4gICAgRE9ORV9XQUlUSU5HX0ZFRURCQUNLIC0tPiBbKl06IEZlZWRiYWNrIHByb3ZpZGVkXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoXG4gICAgQUNDRVBURUQgLS0-IFsqXTogQ2FuY2VsbGVkIGJ5IENvYWNoZWVcblx0XHRcdFx0XHQiLCJtZXJtYWlkIjp7InRoZW1lIjoiZGVmYXVsdCJ9LCJ1cGRhdGVFZGl0b3IiOmZhbHNlfQ)
[Excel with examples state changes](youcoach-examples-state-changes.xlsx)