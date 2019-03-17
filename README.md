# Traffic and weather applicaiton

Application helps drivers given them information about traffic and road condition. Also, application gives weather informaiton for next 48 hours for particular place on map.
The goal of the application is to provide information about road work, closed streets, slow traffic, incidents and weather condition. All information about the traffic that is being collected is entered by applicaiton users. 
Weather data is obtained from [Dark Sky API][6]. 

## Prerequisites

You will need [Leiningen][1] 2.0.0 or above and MySQL installed.

[1]: https://github.com/technomancy/leiningen
[2]: https://github.com/ring-clojure/ring 
[3]: https://github.com/weavejester/compojure
[4]: https://github.com/yogthos/migratus
[5]: https://github.com/funcool/buddy-auth
[6]: https://darksky.net/dev
[7]: https://github.com/krisajenkins/yesql
[8]: https://funcool.github.io/struct/latest/
## Setup
To run applicaiton, the steps are:
1. Login to MySQL server and create database call traffic
   
    CREATE DATABASE traffic DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
2. To create database schema navigate to project directory and execute migrations files using command
   
    lein migratus migrate
3. To start a web server for the application, run:

    lein ring server


##Libraries 
  Clojure project was generated using:
  
  [Leiningen][1] - dependency management library for building and configure Clojure project
  
  [Ring][2] - library for building web application in the Clojure programming language
  
  [Compojure][3] - routing library for Ring
  
  [Migratus][4] - library for database migration and schema management
  
  [Buddy-auth][5] - is a module of Buddy secuirty library that provides authentication and authorization facilites

  [YeSQL][7] - library which generates functions out of SQL, pulls sql files and turns queries into a function.   
  
  [Struct][8] - a structural validation library for Clojure
    
##About project

The project consists of the following frontend pages:
1. Login and sign up page - The user must be authenticated to access the application. If the user does not have an account, it can be registered.
2. Home page - Display cards with report types (Slow traffic, Road close, Line closed, Incidents, Congestion,...). Click on some card, user can see all reports by specific type.
3. Reports page - Display all traffic reports sorting them by reporting date and time. For every traffic report user can see the username of the person who reported it, time, street and detail information.
4. Add new report page - User can add new traffic report providing information about report type, city, street and optionaly description. User can search for specific city and search streets based on chosen city.
5. Weather page - Consists of a javascript google map. When user click on specific place on map, based on latitude and longitude, API is called for getting weather data.
 Page displays hourly weather: 
    - Summary - text summary of that data point
    - Temperature (Degrees Celsius)
    - Apperent temperature (Feels like temperature, Degrees Celsius)
    - Probability of precipitation (between 0 and 1)
    - Wind speed (in meters per second)
    - Humidity (between 0 and 1)
6. User profile - User can change first name, last name and email. Also, user can change password providing old one and entering new password. In database password are hashed using bcrypt-clj. 
User can log out, by logging out, user is redirected to login page.
In addition to the above functionalities, user that is admin can: 
1. Edit street by changing street name and street community.
2. Delete street and all existing reports for that street.

##References
Clojure for the brave and true

Clojure Web Development Essentials

###### The project was developed as part of the assignment for the course Software Engineering Tools and Methodology at the Faculty of Organization Sciences, University of Belgrade, Serbia.