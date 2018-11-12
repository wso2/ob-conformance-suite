CREATE DATABASE obsuit-reports;

USE obsuite;

CREATE TABLE User (userID VARCHAR(50) not NULL, name VARCHAR(100) not NULL, password VARCHAR(512) not NULL, regDate DATETIME not NULL, PRIMARY KEY ( userID ));

CREATE TABLE TestPlan (testID INT not NULL AUTO_INCREMENT, userID VARCHAR(50), testConfig CLOB, creationTime DATETIME, PRIMARY KEY ( testID ), foreign key (userID) references user(userID));

CREATE TABLE Report (reportID INT not NULL AUTO_INCREMENT, testID VARCHAR(100) not NULL, userID VARCHAR(50), report CLOB, passed INT, failed INT, passRate INT, runTime DATETIME, PRIMARY KEY ( reportID ), foreign key (userID) references user(userID), foreign key (testID) references TestPlan(testID));
