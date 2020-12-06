## Description
A booking system with a service layer and a datalayer. NO FRONTEND!

## Docker Setup
Type this command in Powershell or cmd to run the mysql databaser on a docker container:
docker run -d --rm --name mysql-test-db -e MYSQL_ROOT_PASSWORD=testuser123 -p 3307:3306 mysql

## Authors
Simon Schønberg Bojesen, Kenneth Leo Hansen, Martin Høigaard Rasmussen

##The Selenium UI
Selenium ui testsare in the folder src/test/seleniumtest 
<br />
To run them you must install selenium IDE first.
<br />
Then open the IDE and run the tests.

## WebDriver
You must have firefox installed to run this program.
<br />
To run the tasks for WebDriver you must locate the file in path: src/main/java/webdriver/WebMain.java
<br />
Then just run WebMain.java.
<br />
A browser will open where each link will be clicked
Check console to see the correct table values that will be printed




