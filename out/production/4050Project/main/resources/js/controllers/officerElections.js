angular.module("officerIndexApp").controller('electionsCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerElections");
        window.document.title = "VRS Elections";
    }
]);