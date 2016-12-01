angular.module("officerIndexApp").controller('districtsCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerDistricts");
        window.document.title = "VRS Districts";
        
        $scope.normalView = true;
        $scope.formData = {};
        
        var token = getValue(location.search,"token");
        var url = "http://localhost:9001/data/find/ElectoralDistrict?sourced=true&token="+token;
        console.log("requesting districts");

        $http.post(url,toDistrict(null)).success(
            function (response) {
                $scope.districtList = response;
                console.log(JSON.stringify(districtList));
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
            url = "http://localhost:9001/data/traverse/getBallotsGivenDistrict?sourced=true&token="+token;
            $http.post(url,district).success(
                function (ballot) {
                    if(ballot != "200 success") {
                        url = "http://localhost:9001/data/traverse/getBallotItemsGivenBallot?sourced=true&token=" + token;
                        $http.post(url, ballot[0]).success(
                            function (response) {
                                $scope.districtBallotItems = response;
                            }
                        );
                    }
                }
            );
        };
        
        $scope.createNewDistrict = function () {
            var newName = $scope.formData.newName;
            var district = toDistrict(newName);
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

            $scope.normalView = true;
        }
    }
]);

$(document).on('click', '#districtList a', function() {
    $("#districtList a").removeClass("active");
    $(this).addClass("active");
});

