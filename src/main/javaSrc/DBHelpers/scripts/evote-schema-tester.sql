# noinspection SqlNoDataSourceInspectionForFile
DROP TABLE Ballot_Elections, Party_Candidates, District_Voters, District_Ballots, Ballot_Issues, Election_Candidates, ElectionsOfficer, Party, Candidate, District, Election, Record, Issue, Ballot, Voter, Token;

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
 Is_Alternate BOOLEAN,
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
 Alternate_Allowed BOOLEAN,
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

insert into Token values ("TestUser", "testToken123");
insert into ElectionsOfficer ( First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip)
  values ( "John", "smith", "officerJS", "1234", "jsmith@gmail.com", "1 street", "athens", "GA", "30067" );
insert into Candidate (Candidate_Name, Vote_Count) values ("Jim", 0);
insert into Candidate (Candidate_Name, Vote_Count) values ("Bob", 0);
insert into Ballot (Start_Date, End_Date) values ('1976-05-13', '1976-06-13');
insert into District (District_Name) values ("First District");
insert into Election (Office_Name, Is_Partisan) values ('President', 1);
insert into Election_Candidates (Candidate_ID, Election_ID) values (1, 1);
insert into Election_Candidates (Candidate_ID, Election_ID) values (2, 1);
insert into Ballot_Elections (Ballot_ID, Election_ID) values (1, 1);
insert into Voter ( First_Name, Last_Name, Username, User_Password, Email_Address, Address, City, State, Zip)
  values ( "Bro", "buddy", "voter4lyfe", "1234", "voter@gmail.com", "1 street", "athens", "GA", "30067" );
insert into Issue (Question, Vote_Count) values ('What Question?', 0);
insert into Issue (Question, Vote_Count) values ('This Question?', 0);
insert into Ballot_Issues (Ballot_ID, Issue_ID) values (1, 1);
insert into Ballot_Issues (Ballot_ID, Issue_ID) values (1, 2);
insert into District_Ballots (District_ID, Ballot_ID) values (1, 1);
insert into District_Voters (District_ID, Voter_ID) values (1, 1);
insert into Party (Party_Name) values ("Democrat");
insert into Party (Party_Name) values ("Republican");
insert into Party_Candidates (Party_ID, Candidate_ID) values (1, 1);
insert into Party_Candidates (Party_ID, Candidate_ID) values (2, 2);
insert into Record (Record_Date, Voter_ID, Ballot_ID) values ('1976-05-13', 1, 1);

