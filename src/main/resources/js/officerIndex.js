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
        .when("/candidates", {
            templateUrl : "templates/OfficerCandidates.html?token="+token,
            controller : "candidatesCtrl"
        })
        .when("/parties", {
            templateUrl : "templates/OfficerParties.html?token="+token,
            controller : "partiesCtrl"
        });
});

$(document).on('click', '.nav li', function() {
    $(".nav li").removeClass("active");
    $(this).addClass("active");
});