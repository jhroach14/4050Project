angular.module("officerIndexApp").controller('issuesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("officerIssues");
        window.document.title = "VRS Issues";
    }
]);