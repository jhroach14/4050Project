-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile
# noinspection SqlNoDataSourceInspectionForFile
DROP TABLE IF EXISTS ElectionsOfficer, Party, Party_Candidates, District_Voters, Candidate, District_Ballots, District, Ballot, Election, Ballot_Elections, Voter, Record, Issue, Ballot_Issues, Election_Candidates, Token;

CREATE TABLE sysOpen(
    SysOpen_ID INT AUTO_INCREMENT not NULL,
    IsOpen VARCHAR(10),

    PRIMARY KEY (SysOpen_ID)
);
INSERT INTO sysOpen (IsOpen) VALUE ("closed");
/*Done.*/
CREATE TABLE ElectionsOfficer (
 Elections_Officer_ID INT AUTO_INCREMENT NOT NULL,
 First_Name VARCHAR(100) NOT NULL,
 Last_Name VARCHAR(100) NOT NULL,
 Username VARCHAR(100) NOT NULL,
 User_Password VARCHAR(100) NOT NULL,
 Email_Address VARCHAR(100),
 Address VARCHAR(100),
 City VARCHAR(100),
 State CHAR(2),
 Zip CHAR(5),

 PRIMARY KEY(Elections_Officer_ID)
);
insert into ElectionsOfficer ( First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip)
values ( "John", "smith", "officer", "1234", "jsmith@gmail.com", "1 street", "athens", "GA", "30067" );
/*Done.*/
CREATE TABLE Party (
 Party_ID INT AUTO_INCREMENT NOT NULL,
 Party_Name VARCHAR(100) NOT NULL,

 PRIMARY KEY(Party_ID)
);

/* Need to implement the count, but otherwise done. */
CREATE TABLE Candidate (
 Candidate_ID INT AUTO_INCREMENT NOT NULL,
 Candidate_Name VARCHAR(100) NOT NULL,
 Vote_Count INT,

 PRIMARY KEY(Candidate_ID)
);

/*Done.*/
CREATE TABLE District (
 District_ID INT AUTO_INCREMENT NOT NULL,
 District_Name VARCHAR(100) NOT NULL,
 District_Zip CHAR(5),

 PRIMARY KEY(District_ID)
);
/*insert into District ( District_Name )
values ( "District1" );
insert into District ( District_Name )
values ( "District2" );
insert into District ( District_Name )
values ( "District3" );
insert into District ( District_Name )
values ( "District4" );*/
/*Done.*/
CREATE TABLE Ballot (
 Ballot_ID INT AUTO_INCREMENT NOT NULL,
 Start_Date DATE NOT NULL,
 End_Date DATE NOT NULL,

 PRIMARY KEY(Ballot_ID)
);


/*Done.*/
CREATE TABLE Election (
 Election_ID INT AUTO_INCREMENT NOT NULL,
 Office_Name VARCHAR(100) NOT NULL,
 Is_Partisan BOOLEAN NOT NULL,
 Vote_Count INT,

 PRIMARY KEY(Election_ID)

);

/* To create a many-to-many relationship between election and candidates*/
CREATE TABLE Election_Candidates (
  Candidate_ID INT,
  Election_ID INT,

  FOREIGN KEY(Candidate_ID) REFERENCES Candidate(Candidate_ID),
  FOREIGN KEY(Election_ID) REFERENCES Election(Election_ID)
);

/* To create a many-to-many relationship between ballot and elections*/
CREATE TABLE Ballot_Elections (
  Ballot_ID INT,
  Election_ID INT,

  FOREIGN KEY(Ballot_ID) REFERENCES Ballot(Ballot_ID),
  FOREIGN KEY(Election_ID) REFERENCES Election(Election_ID)
);

/*Done.*/
CREATE TABLE Voter (
 Voter_ID INT AUTO_INCREMENT NOT NULL,
 First_Name VARCHAR(100) NOT NULL,
 Last_Name VARCHAR(100) NOT NULL,
 Username VARCHAR(100) NOT NULL,
 User_Password VARCHAR(100) NOT NULL,
 Email_Address VARCHAR(100),
 Address VARCHAR(100),
 City VARCHAR(100),
 State CHAR(2),
 Zip CHAR(5),

  PRIMARY KEY(Voter_ID)
);

/*Done.*/
CREATE TABLE Record (
 Record_ID INT AUTO_INCREMENT NOT NULL,
 Record_Date DATE NOT NULL,
 Voter_ID INT NOT NULL,
 Ballot_ID INT NOT NULL,

 PRIMARY KEY(Record_ID),
 FOREIGN KEY(Voter_ID) REFERENCES Voter(Voter_ID),
 FOREIGN KEY(Ballot_ID) REFERENCES Ballot(Ballot_ID)
);

/* Done.*/
CREATE TABLE Issue (
 Issue_ID INT AUTO_INCREMENT NOT NULL,
 Question VARCHAR(5000) NOT NULL,
 Vote_Count INT,
 Yes_Count INT,
 No_Count INT,

 PRIMARY KEY(Issue_ID)
);

/* To create a many-to-many relationship between ballot and issues*/
CREATE TABLE Ballot_Issues (
  Ballot_ID INT,
  Issue_ID INT,

  FOREIGN KEY(Ballot_ID) REFERENCES Ballot(Ballot_ID),
  FOREIGN KEY(Issue_ID) REFERENCES Issue(Issue_ID)
);

CREATE TABLE District_Ballots (
 District_ID INT,
 Ballot_ID INT,

 FOREIGN KEY(District_ID) REFERENCES District(District_ID),
 FOREIGN KEY(Ballot_ID) REFERENCES Ballot(Ballot_ID)
);

CREATE TABLE Party_Candidates (
 Party_ID INT,
 Candidate_ID INT,

 FOREIGN KEY(Party_ID) REFERENCES Party(Party_ID),
 FOREIGN KEY(Candidate_ID) REFERENCES Candidate(Candidate_ID)
);

CREATE TABLE District_Voters (
 District_ID INT,
 Voter_ID INT,

 FOREIGN KEY(District_ID) REFERENCES District(District_ID),
 FOREIGN KEY(Voter_ID) REFERENCES Voter(Voter_ID)
);

Create Table Token(
 User_Name VARCHAR(100) NOT NULL,
 Token VARCHAR(100) NOT NULL,

 PRIMARY KEY(User_Name)
);
INSERT INTO Token VALUES ("TestUser", "testToken123");



