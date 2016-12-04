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

var toVoter = function (firstName,lastName,userName,userPassword,emailAddress,address,state,zip,city) {

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

var toCandidate = function (name, isAlternate, voteCount) {
    var candidate = {
        name: name,
        isAlternate: isAlternate,
        voteCount: voteCount
    };
    return candidate;
};

var toElection = function (office, isPartisan, voteCount/*, alternateAllowed*/) {
    var election = {
        office: office,
        isPartisan: isPartisan,
        voteCount: voteCount,
        //alternateAllowed: alternateAllowed
    };
    return election;
};
var toDistrict = function (name) {
    var district = {
        name: name
    };
    return district;
};

var toIssue = function (question, voteCount, yesCount, noCount) {
    var issue = {
        question: question,
        voteCount: voteCount,
        yesCount: yesCount,
        noCount: noCount
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
