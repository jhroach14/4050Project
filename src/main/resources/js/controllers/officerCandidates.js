angular.module("officerIndexApp").controller('candidatesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerCandidates");
        window.document.title = "VRS Candidates";
    }
]);