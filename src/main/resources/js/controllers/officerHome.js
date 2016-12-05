//angular.module("officerIndexApp").controller('homeCtrl', ['$scope', '$http',
//    function($scope, $http) {
//        setStyleSheet("OfficerHome");
//        window.document.title = "VRS Home";
//    }
//]);

//angular.module("officerIndexApp").controller('homeCtrl', ['$scope', '$http',
//    function($scope, $http) {
//        setStyleSheet("OfficerHome");
//        window.document.title = "VRS Home";
//    }
//]);

//This is all from officerDistricts.js
angular.module("officerIndexApp").controller('homeCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerHome");
        window.document.title = "VRS Home";

        $scope.normalView = 1;
        $scope.formData = {};
        $scope.User = null;

        //Get # Of Districts
        var token = getValue(location.search,"token");
        var url = "http://localhost:9001/data/find/ElectoralDistrict?sourced=true&token="+token;
        console.log("requesting districts");

        $http.post(url,toDistrict(null)).success(

            function (response) {
                if(response != "200 success"){
                $scope.numberOfDistricts = response.length;

                } else {
                $scope.numberOfDistricts = 0;
                }
            }
        );

        //Get # of Elections
        var url1 = "http://localhost:9001/data/find/Election?sourced=true&token="+token;
        console.log("requesting elections");

        $http.post(url1,toElection(null, null, null)).success(

            function (response) {
                if(response != "200 success"){
                $scope.numberOfElections = response.length;

                } else {
                $scope.numberOfElections = 0;
                }
            }
        );

        //Get # of Issues
        var url2 = "http://localhost:9001/data/find/Issue?sourced=true&token="+token;
        console.log("requesting issues");

        $http.post(url2,toIssue(null, null, null, null)).success(

            function (response) {
                if(response != "200 success"){
                $scope.numberOfIssues = response.length;

                } else {
                $scope.numberOfIssues = 0;
                }
            }
        );

        //Get User from token
        var url3 = "http://localhost:9001/data/traverse/getUserGivenToken?sourced=true&token="+token;
        console.log("requesting user");

        $http.post(url3,toToken(token)).success(

            function (response) {
                if(response != "200 success"){
                $scope.User = response[0];

                } else {
                $scope.User = null;
                }
            }
        );

        //Get voters Registered
        var url4 = "http://localhost:9001/data/find/Voter?sourced=true&token="+token;
        console.log("requesting voter");



        $http.post(url4,toVoter(null, null, null, null, null, null, null, null, null, null)).success(

            function (response) {
                if(response != "200 success"){
                $scope.registeredVoters = response.length;

                } else {
                $scope.registeredVoters = 0;
                }
            }
        );

        //Get votes cast
        var url5 = "http://localhost:9001/data/find/VoterRecord?sourced=true&token="+token;
        console.log("requesting voter records");

        $http.post(url5,toRecord(null, null, null)).success(

            function (response) {
                if(response != "200 success"){
                $scope.votesCast = response.length;

                } else {
                $scope.votesCast = 0;
                }
            }
        );

        $scope.open = function () {
            var url = "http://localhost:9001/data/store/sysOpen?token="+token;
            $http.get(url);
        };

        $scope.close = function () {
            var url = "http://localhost:9001/data/store/sysClosed?token="+token;
            $http.get(url);
        };


        $scope.editProfile = function () {

            var url = "http://localhost:9001/data/store/ElectionOfficer?sourced=true&token="+token;
            $http.post(url,$scope.User).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/ElectionOfficer?sourced=true&token="+token;
                    $http.post(url,$scope.User).success(
                        function (response) {

                        }
                    );
                }
            );

            $scope.normalView = 1;


        };

        $scope.normal2 = function () {
            $scope.normalView = 2;
        }
    }
]);

