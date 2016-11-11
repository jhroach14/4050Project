angular.module("officerIndexApp").controller('homeCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerHome");
        window.document.title = "VRS Home";
    }
]);