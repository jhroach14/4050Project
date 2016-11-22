//angular.module("officerIndexApp").controller('districtsCtrl', ['$scope', '$http',
//    function($scope, $http) {
//        setStyleSheet("OfficerDistricts");
//        window.document.title = "VRS Elections";
//    }
//]);



//angular.module("officerIndexApp").controller('newDistrictsCtrl', ['$scope', '$http',
//    function($scope, $http) {
//        setStyleSheet("OfficerElections");
//        window.document.title = "VRS Elections";
//
//            $scope.submit = function () {
//
//                var district = toDistrict($scope.districtName);
//                var url = "http://localhost:9001/data/store/ElectoralDistrict?sourced=true";
//                console.log("sending \n"+url);
//                $http.post(url,district).success(
//                    function (response) {
//                        console.log("received :\n"+response);
//        //                var request = "http://localhost:9001/auth?user="+$scope.userName+"&pass="+$scope.userPassword;
//        //                console.log("sending :\n"+request);
//        //                $http.get(request).success(
//        //                    function (response) {
//        //                        console.log("received :\n"+response);
//        //                        window.location = response;
//        //                    }
//        //                );
//                    }
//                );
//            }
//
//
//
//    }
//]);

angular.module("officerIndexApp").controller('districtsCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerDistricts");
        window.document.title = "VRS Districts";
    }
]);

var app = angular.module('officerDistrictApp', []);

app.controller('newDistrictsCtrl', ['$scope', '$http',
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
    }
]);

//var app = angular.module('officerDistrictApp', []);
//
////app.controller('districtsCtrl', ['$scope', '$http',
////    function($scope, $http) {
////        setStyleSheet("OfficerDistricts");
////        window.document.title = "VRS Districts";
////    }
////]);
//
//
//app.controller('newDistrictsCtrl', ['$scope', '$http',function($scope,$http) {
//
//    $scope.submit = function () {
//
//        var district = toDistrict($scope.districtName);
//        var url = "http://localhost:9001/data/store/ElectoralDistrict?sourced=true";
//        console.log("sending \n"+url);
//        $http.post(url,district).success(
//            function (response) {
//                console.log("received :\n"+response);
////                var request = "http://localhost:9001/auth?user="+$scope.userName+"&pass="+$scope.userPassword;
////                console.log("sending :\n"+request);
////                $http.get(request).success(
////                    function (response) {
////                        console.log("received :\n"+response);
////                        window.location = response;
////                    }
////                );
//            }
//        );
//    }
//}]);
//

var toDistrict = function (districtName) {
    var district = {
         districtName: districtName
    };
    return district;
};