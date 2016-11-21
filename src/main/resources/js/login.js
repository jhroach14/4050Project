var app = angular.module('loginApp', []);



app.controller('loginCtrl', ['$scope', '$http',function($scope,$http) {
    $scope.submit = function () {
        var request = "http://localhost:9001/auth?user="+$scope.email+"&pass="+$scope.password;
        console.log("sending :\n"+request);
        $http.get(request).success( 
            function (response) {
                console.log("received :\n"+response);
                window.location = response;
            }
        );
    }
}]);

app.controller('registerCtrl', ['$scope', '$http',function($scope,$http) {
    $scope.submit = function () {
            var request = "http://localhost:9001/auth?firstName="+$scope.firstName+"&lastName="+$scope.lastName+"&user="+$scope.email2+"&password="+$scope.password+"&email="+$scope.email2+"&address="+$scope.address+"&age="+$scope.age+"&state="+$scope.state+"&zip="+$scope.zip+"&city="+$scope.city;
            console.log("sending :\n"+request);
            $http.get(request).success(
                function (response) {
                    console.log("received :\n"+response);
                    window.location = "http://localhost:9001/voterIndex.html";
                }

            );
        }
});