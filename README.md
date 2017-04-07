Carbon Tracker Marking Guide: Iteration 3
 
A [20] Human-Relatable CO2 Unit
==============================
-	Option to select human-relatable units ie. Other measurements
-	Cows, Trees, Farts
-	Units between 0.1 and 200 for 20km 2010 Honda Accord journey
-	Requirements
    -	Settings Activity navigation from MainMenuActivity
    -	Save chosen settings accessible through application and life-spans [DONE]
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
-	Vehicle icons: select from 5+ built-in icons; used in vehicle list, and Journey list [DONE]
-	Route distances default to 0; not able to have both 0.
-	About screen [DONE]
-	When creating/editing a route, if user enters no data for the city or highway distance, default to 0 (but don’t let both be 0).
-	Add an about “screen” which shows: [DONE]
  * the group’s name (like “Orange”, or “Brass”), [DONE]
  * year the app was created, [DONE]
  * a version number (like 1.1, pulled from a resource file), [DONE]
  * link to SFU CS home page, and [DONE]
  * links to all sources for your images, icons, and other resources. [PENDING]
  * Optionally list each group member’s name (but no private data like student number or email address). [DONE]
 
Requirements:
    -	Able to select icons via spinner/list for all transport (5) each or global [DONE]
    -	Will need to redo how icons are displayed [DONE]
    -	Allow all objects to support image storing [DONE]
    -	Allow database to track this additional member [DONE]
    -	Add an about page [DONE]


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

