var app = angular.module('newDistrictApp', []);

app.controller('newDistrictCtrl', ['$scope', '$http',function($scope,$http) {
    $scope.submit = function () {

        var district = toDistrict($scope.districtName);
        var url = "http://localhost:9001/data/store/ElectoralDistrict?sourced=true";
        console.log("sending \n"+url);
        $http.post(url,district).success(
            function (response) {
                console.log("received :\n"+response);
//                var request = "http://localhost:9001/auth?user="+$scope.userName+"&pass="+$scope.userPassword;
//                console.log("sending :\n"+request);
//                $http.get(request).success(
//                    function (response) {
//                        console.log("received :\n"+response);
//                        window.location = response;
//                    }
//                );
            }
        );

        /*var request = "http://localhost:9001/auth?firstName="+$scope.firstName+"&lastName="+$scope.lastName+"&user="+$scope.email2+"&password="+$scope.password+"&email="+$scope.email2+"&address="+$scope.address+"&age="+$scope.age+"&state="+$scope.state+"&zip="+$scope.zip+"&city="+$scope.city;
        console.log("sending :\n"+request);
        $http.get(request).success(
            function (response) {
                console.log("received :\n"+response);
                window.location = "http://localhost:9001/voterIndex.html";
            }

        );*/
    }
}]);

var toDistrict = function (districtName) {

    var district = {
         districtName: districtName
    };
    return district;
};