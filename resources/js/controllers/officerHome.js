angular.module("officerIndexApp").controller('homeCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("officerHome");
        window.document.title = "VRS Home";
    }
]);