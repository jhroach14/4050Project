angular.module("officerIndexApp").controller('districtsCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerDistricts");
        window.document.title = "VRS Districts";
        
        $scope.normalView = 1;
        $scope.formData = {};
        
        var token = getValue(location.search,"token");
        var url = "http://localhost:9001/data/find/ElectoralDistrict?sourced=true&token="+token;
        console.log("requesting districts");

        $http.post(url,toDistrict(null)).success(
            function (response) {
                if(response!="200 success"){
                    $scope.districtList = response;
                }else{
                    $scope.districtList = null;
                }

                console.log(angular.toJson(districtList));
            }
        );
        $scope.selectDistrict = function (district) {
            $scope.registeredVoters = null;
            $scope.districtBallotItems = null;
            $scope.selectedDistrict = district;
            var url = "http://localhost:9001/data/traverse/getVotersGivenDistrict?sourced=true&token="+token;
            $http.post(url,district).success(
                function (response) {
                    if(response != "200 success"){
                        $scope.registeredVoters = response.length;
                    }else{
                        $scope.registeredVoters = 0;
                    }
                }
            );
            url = "http://localhost:9001/data/traverse/getBallotGivenDistrict?sourced=true&token="+token;
            $http.post(url,district).success(
                function (ballot) {
                    if(ballot != "200 success") {
                        url = "http://localhost:9001/data/traverse/getBallotItemsGivenBallot?sourced=true&token=" + token;
                        $http.post(url, ballot[0]).success(
                            function (response) {
                                if(response != "200 success") {
                                    $scope.districtBallotItems = response;
                                }else{
                                    $scope.districtBallotItems = null;
                                }
                            }
                        );
                    }
                }
            );
        };
        
        $scope.createNewDistrict = function () {
            var newName = $scope.formData.newName;
            var newZip = $scope.formData.newZip;
            var district = toDistrict(newName, newZip);
            var url = "http://localhost:9001/data/store/ElectoralDistrict?sourced=true&token="+token;
            $http.post(url,district).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/ElectoralDistrict?sourced=true&token="+token;
                    $http.post(url,toDistrict(null)).success(
                        function (response) {
                            $scope.districtList = response;
                        }
                    );
                }
            );

            $scope.normalView = 1;
        };

        $scope.modDistrict = function () {
            var url = "http://localhost:9001/data/store/ElectoralDistrict?sourced=true&token="+token;
            $http.post(url,$scope.selectedDistrict).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/ElectoralDistrict?sourced=true&token="+token;
                    $http.post(url,toDistrict(null)).success(
                        function (response) {
                            $scope.districtList = response;
                        }
                    );
                }
            );

            $scope.normalView = 1;
        };

        $scope.normal3 = function () {
            $scope.normalView = 3;
        };

        $scope.deleteDistrict = function(){
            var url = "http://localhost:9001/data/delete/ElectoralDistrict?sourced=true&token="+token;
            $http.post(url,$scope.selectedDistrict).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/ElectoralDistrict?sourced=true&token="+token;
                    $http.post(url,toDistrict(null)).success(
                        function (response) {
                            $scope.districtList = response;
                            $scope.selectedDistrict = null;
                        }
                    );
                }
            );
        };
    }
]);

$(document).on('click', '#districtList a', function() {
    $("#districtList a").removeClass("active");
    $(this).addClass("active");
});

