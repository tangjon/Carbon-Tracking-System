Carbon Tracker Marking Guide: Iteration 3
 
A [20] Human-Relatable CO2 Unit
==============================
-	Option to select human-relatable units ie. Other measurements
-	Cows, Trees, Farts
-	Units between 0.1 and 200 for 20km 2010 Honda Accord journey
-	Requirements
    -	Settings Activity navigation from MainMenuActivity
    -	Save chosen settings accessible through application and life-spans
    -	Change labels to be dynamic
    -	Get calculations from method based of mode
    -	One stupid mode : Tree

B [15] Notifications [ Notification and Time Tracker ]
==============================
-	Notifications shown at 9pm
-	Smart (show relevant data)
-	Notifications based on what hasn’t been entered
-	Display nightly notification to enter (more) journeys, or bills.
-	Communicate with database
-	If no journeys entered on the day, asks for journeys. 
If missing a bill in last 1.5 months, prompt for bill.
Otherwise, prompt for more journeys.
-	Feature to track how long since a journey or bill has been entered
-	Notification takes user to correct activity; back-button works correctly in workflow.
-	Journey notification to Transport Select Activity/ Journey List Activity
-	Bill Notification to Bill List Activity
Requirements
    -	Feature to track how long since a journey or bill (Gas and Electric) has been entered
    -	Notification method/notification
    -	Normal flow as usual (pressing back wont break the flow)

C [15] UI Improvements [
==============================
-	App icon, app name
-	Buttons have icons
-	Action bar / Tool bar supports back button. [DONE]
-	Activity backgrounds
-	UI Polish
-	Hide navigation bar(bottom)
-	Icons with optional text in replacement of standard button
 

D [15] UI-Related Features
==============================
-	Vehicle icons: select from 5+ built-in icons; used in vehicle list, and Journey list
-	Route distances default to 0; not able to have both 0.
-	About screen
-	When creating/editing a route, if user enters no data for the city or highway distance, default to 0 (but don’t let both be 0).
-	Add an about “screen” which shows:
  * the group’s name (like “Orange”, or “Brass”), 
  * year the app was created, 
  * a version number (like 1.1, pulled from a resource file), 
  * link to SFU CS home page, and 
  * links to all sources for your images, icons, and other resources. 
  * Optionally list each group member’s name (but no private data like student number or email address).
 
Requirements:
    -	Carinfo to accept only one zero
    -	Able to select icons via spinner/list for all transport (5) each or global
    -	Will need to redo how icons are displayed
    -	Allow all objects to support image storing
    -	Allow database to track this additional member
    -	Add an about page


E [20] Graphs
==============================
- stacked-bar/line: line for national average and national target.
- pie: 1day, 28 day, 365 day
- pie: able to group by transportation mode, and route.
- Able to group by transportation
 

F [5] Translate
==============================
- App displays in English, Spanish, or French (when matching the phone's language).
Requirements
    -Research how translation work
    -Teach how to do it
    -Or do both(above) yourself


[10] Correct use of Git and GitLab
==============================
   - Using GitLab issues to track features to work on.
   - Each team member must have 2 feature branches merged via GitLab
   - Almost all commits are done on feature branches.
   - Correctly builds when cloned from GitLab.
 

[0] Good quality code (worth 0, but up to a 20 mark penalty may be applied).
==============================
   - Good class, method, variable names.
   - Perfectly formatted code.
   - Comments on all classes (not needed *inside* classes, just on the class level)
   - All strings which end user may see on UI (not LogCat) are in strings.xml

