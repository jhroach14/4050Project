angular.module("officerIndexApp").controller('partiesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerParties");
        window.document.title = "VRS Parties";
    }
]);