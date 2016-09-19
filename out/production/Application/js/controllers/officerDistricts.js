angular.module("officerIndexApp").controller('districtsCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerDistricts");
        window.document.title = "VRS Districts";
    }
]);