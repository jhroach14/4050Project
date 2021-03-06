var getValue = function (url,key) {

    var start = url.indexOf(key)+key.length+1;
    var end = url.indexOf("&",start);
    if(end == -1){
        end = url.length;
    }else{
        end-1;
    }
    var value = url.substr(start,end);
    return value;
};

var setStyleSheet = function (name) {

    var head  = document.getElementsByTagName('head')[0];
    var old =document.getElementById("link");
    if(old){
        head.removeChild(old);
    }

    var link  = document.createElement('link');
    link.id   = "link";
    link.rel  = 'stylesheet';
    link.type = 'text/css';
    link.href = 'css/'+name+'.css';
    link.media = 'all';
    head.appendChild(link);
};

var toParty = function (name) {
    var party = {
        name: name
    };
    
    return party;
};

var toVoter = function (firstName,lastName,userName,userPassword,emailAddress,address,state,zip,city,age) {

    var voter = {
        firstName: firstName,
        lastName: lastName,
        userName: userName,
        userPassword: userPassword,
        emailAddress: emailAddress,
        address: address,
        state: state,
        zip: zip,
        city: city,
        age: age
    };
    return voter;
};

var toUser = function (firstName,lastName,userName,userPassword,emailAddress,address,state,zip,city) {

    var user = {
        firstName: firstName,
        lastName: lastName,
        userName: userName,
        userPassword: userPassword,
        emailAddress: emailAddress,
        address: address,
        state: state,
        zip: zip,
        city: city
    };
    return user;
};

var toElectionsOfficer = function (firstName,lastName,userName,userPassword,emailAddress,address,state,zip,city) {

    var electionsOfficer = {
        firstName: firstName,
        lastName: lastName,
        userName: userName,
        userPassword: userPassword,
        emailAddress: emailAddress,
        address: address,
        state: state,
        zip: zip,
        city: city
    };
    return electionsOfficer;
};

var toCandidate = function (name, voteCount) {
    var candidate = {
        name: name,
        voteCount: voteCount
    };
    return candidate;
};

var toElection = function (office, isPartisan) {
    var election = {
        office: office,
        isPartisan: isPartisan
    };
    return election;
};

var toDistrict = function (name, zip) {
    var district = {
        name: name,
        zip: zip
    };
    return district;
};

var toIssue = function (question) {
    var issue = {
        question: question
    };
    return issue;
};

var toToken = function (tokenValue) {
    var token = {
        tokenValue: tokenValue
    };
    return token;
};

var toRecord = function (date, voter, ballot) {
    var record = {
        date: date,
        voter: voter,
        ballot: ballot
    };
    return record;
};

//var toBallot = function () {
//
//};
