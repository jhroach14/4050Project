angular.module("officerIndexApp", ["ngRoute"]);

angular.module("officerIndexApp").config(function($routeProvider) {
    var token = getValue(location.search,"token");
    $routeProvider
        .when("/", {
            templateUrl : "templates/OfficerHome.html?token="+token,
            controller : "homeCtrl"
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