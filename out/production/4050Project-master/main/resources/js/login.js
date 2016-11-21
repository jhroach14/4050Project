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
            var request = "http://localhost:9001/auth?user="+$scope.firstName+"&pass="+$scope.password;
            $scope.age = parseInt(age, 10);
            $scope.zip = parseInt(zip, 10);
            Voter voter1 = new VoterImpl($scope.firstName,$scope.lastName,$scope.email2, $scope.password,$scope.email2, $scope.address, $scope.age,$scope.state, $scope.zip,$scope.city);
            voter1= (Voter) stringToEntity(writeToServer("store","Voter",entityToString(voter1)),VoterImpl.class);
            console.log("sending :\n"+request);
            $http.get(request).success(
                function (response) {
                    console.log("received :\n"+response);
                    window.location = "http://localhost:9001/voterIndex.html";
                }

            );
        }
});