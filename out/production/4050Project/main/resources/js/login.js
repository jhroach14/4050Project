var app = angular.module('loginApp', []);



app.controller('loginCtrl', ['$scope', '$http',function($scope,$http) {
    $scope.submit = function () {
        var request = "http://localhost:9001/auth?user="+$scope.UserName+"&pass="+$scope.password;
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

        var voter = toVoter($scope.firstName,$scope.lastName,$scope.userName,$scope.userPassword,$scope.emailAddress,$scope.address,$scope.state,$scope.zip,$scope.city,$scope.age);
        var url = "http://localhost:9001/data/register/Voter?sourced=true";
        console.log("sending \n"+url);
        $http.post(url,voter).success(
            function (response) {
                console.log("received :\n"+response);
                var request = "http://localhost:9001/auth?user="+$scope.userName+"&pass="+$scope.userPassword;
                console.log("sending :\n"+request);
                $http.get(request).success(
                    function (response) {
                        console.log("received :\n"+response);
                        window.location = response;
                    }
                );
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

var toVoter = function (firstName,lastName,userName,userPassword,emailAddress,address,state,zip,city,age) {

    var voter = {
        firstName: firstName,
        lastName: lastName,
        userName: userName,
        userPassword: userPassword,
        emailAddress: emailAddress,
        address: address,
        state: state,
        zip: zip,
        city: city,
        age: age
    };
    return voter;
};