angular.module("voterIndexApp", ["ngRoute"]);

angular.module("voterIndexApp").config(function($routeProvider) {
    var token = getValue(location.search,"token");
    $routeProvider
        .when("/", {
            templateUrl : "templates/VoterHome.html?token="+token,
            controller : "homeCtrl"
        })
        .when("/results", {
                     templateUrl : "templates/VoterResults.html?token="+token,
                     controller : "homeCtrl"
        })
});

$(document).on('click', '.nav li', function() {
    $(".nav li").removeClass("active");
    $(this).addClass("active");
});