# quizapp
Quizapp made with java, full functionalities and admin CMS 

# How to run code
Clone project or download zip file and paste in NetbeansProjects 

# Issues
You'll have to re configure the jdbc with the latest driver

# Adding database
Check the src/quizapp, you'll see an sql file. 

1. Create a database in your phpMyAdmin or MySQL workbench with the name "quizapp".
2. Import the quizapp.sql file
3. Correct / Configure the issue for your jdbc driver, you are good to go.
4. If you have an issue with port find and replace the port in the files, look for localhost:3306 and replace with localhost:<your_port_number>
