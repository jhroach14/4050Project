angular.module("officerIndexApp", ["ngRoute"]);

angular.module("officerIndexApp").config(function($routeProvider) {
    var token = getValue(location.search,"token");
    $routeProvider
        .when("/", {
            templateUrl : "templates/OfficerHome.html?token="+token,
            controller : "homeCtrl"
        })
        .when("/candidates", {
            templateUrl : "templates/OfficerCandidates.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/createcandidate", {
            templateUrl : "templates/CreateCandidate.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/createparty", {
            templateUrl : "templates/CreateParty.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/createdistrict", {
            templateUrl : "templates/CreateDistrict.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/createelection", {
            templateUrl : "templates/CreateElection.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/createissue", {
            templateUrl : "templates/CreateIssue.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/modifycandidate", {
            templateUrl : "templates/ModifyCandidate.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/modifyparty", {
            templateUrl : "templates/ModifyParty.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/addcandidates", {
                    templateUrl : "templates/AddCandidates.html?token="+token,
                    controller : "candidatesCtrl"
                })
        .when("/elections", {
            templateUrl : "templates/OfficerElections.html?token="+token,
            controller : "electionsCtrl"
        })
        .when("/districts", {
            templateUrl : "templates/OfficerDistricts.html?token="+token,
            controller : "districtsCtrl"
        })
        .when("/issues", {
            templateUrl : "templates/OfficerIssues.html?token="+token,
            controller : "issuesCtrl"
        })
        .when("/parties", {
            templateUrl : "templates/OfficerParties.html?token="+token,
            controller : "partiesCtrl"
        });
});

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
}
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
}
$(document).on('click', '.nav li', function() {
    $(".nav li").removeClass("active");
    $(this).addClass("active");
});