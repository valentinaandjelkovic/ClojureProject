# Traffic and weather applicaiton

The aim of this application is to help drivers by giving them information about traffic and road conditions. Also, application gives weather information for the next 48 hours of a particular place on the map. The goal is to provide all the information needed, which includes ongoing road works, closed streets, slow traffic, incidents and weather condition. All the information about the traffic that is being collected is entered by application users. Weather data is obtained from [Dark Sky API][6]. 

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
[9]: https://github.com/weavejester/crypto-password
## Setup
To run applicaiton, the steps are:
1. Login to MySQL server and create database call traffic
   
    CREATE DATABASE traffic DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
2. To create database schema navigate to project directory and execute migrations files using command
   
    lein migratus migrate
3. To start a web server for the application, run:

    lein ring server


## Libraries 
  Clojure project was generated using:
  
  [Leiningen][1] - dependency management library for building and configure Clojure project
  
  [Ring][2] - library for building web application in the Clojure programming language
  
  [Compojure][3] - routing library for Ring
  
  [Migratus][4] - library for database migration and schema management
  
  [Buddy-auth][5] - is a module of Buddy security library that provides authentication and authorization facilites

  [YeSQL][7] - library which generates functions out of SQL, pulls sql files and turns queries into a function.   
  
  [Struct][8] - a structural validation library for Clojure
    
## About project

The project consists of the following frontend pages:
1. Login and sign up page - The user must be authenticated to access the application. If the user does not have an account, he/she can register.
2. Home page - Displays cards with report types (Slow traffic, Road close, Line closed, Incidents, Congestion,...). By clicking on any card, the user can see all reports for that specific type.
3. Reports page - Displays all traffic reports sorting them by reporting date and time. For every traffic report user can see the username of the person who reported it, time, street and details information.
4. Add new report page - User can add new traffic report by providing information about report type, city, street and optionally description. User can search for specific city and search streets in the chosen city.
5. Weather page - Consists of a JavaScript google map. When user clicks on the specific place on the map, based on latitude and longitude, the API is called for fetching the weather data.
 This page displays hourly weather information: 
    - Summary - text summary of that data point
    - Temperature (Degrees Celsius)
    - Apparent temperature (Feels like temperature, Degrees Celsius)
    - Probability of precipitation (between 0 and 1)
    - Wind speed (in meters per second)
    - Humidity (between 0 and 1)
6. User profile - User can change first name, last name and email. Also, user can change password by providing the old one and entering a new password. In the database, passwords are hashed using [crypto-password][9]. 
User can also log out, and after logging out, the user is redirected to the login page.
In addition to the above functionalities, user that has admin privileges can: 
1. Edit street by changing street name and street community.
2. Delete street and all existing reports for that street.

## References
Clojure for the brave and true

Clojure Web Development Essentials

###### The project was developed as part of the assignment for the course Software Engineering Tools and Methodology at the Faculty of Organization Sciences, University of Belgrade, Serbia.