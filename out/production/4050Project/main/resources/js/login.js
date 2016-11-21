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

app.controller('registerCtrl', function($scope) {

});