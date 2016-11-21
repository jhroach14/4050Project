angular.module("voterIndexApp").controller('homeCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("voter");
        window.document.title = "VRS Voter Home";
    }
]);