angular.module("officerIndexApp").controller('electionsCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("officerElections");
        window.document.title = "VRS Elections";
    }
]);