angular.module("officerIndexApp").controller('issuesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerIssues");
        window.document.title = "VRS Issues";
    }
]);