angular.module("officerIndexApp").controller('partiesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerParties");
        window.document.title = "VRS Parties";

        $scope.normalView = 1;
        $scope.formData = {};

        var token = getValue(location.search,"token");
        var url = "http://localhost:9001/data/find/PoliticalParty?sourced=true&token="+token;
        console.log("requesting parties");

        $http.post(url,toParty(null)).success(
            function (response) {
                if(response!="200 success"){
                    $scope.partyList = response;
                }else{
                    $scope.partyList = null;
                }

                console.log(angular.toJson(partyList));
            }
        );
        $scope.selectParty = function (party) {
            $scope.selectedCandidates = null;
            $scope.selectedParty = party;

            var url = "http://localhost:9001/data/traverse/getCandidatesGivenParty?sourced=true&token="+token;
            $http.post(url,party).success(
                function (response) {
                    if(response != "200 success"){
                        $scope.selectedCandidates = response;
                    }else{
                        $scope.selectedCandidates = toCandidate("none",0);
                    }
                }
            );

        };

        $scope.createNewParty = function () {
            var newName = $scope.formData.newName;
            var party = toParty(newName);
            var url = "http://localhost:9001/data/store/PoliticalParty?sourced=true&token="+token;
            $http.post(url,party).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/PoliticalParty?sourced=true&token="+token;
                    $http.post(url,toParty(null)).success(
                        function (response) {
                            $scope.partyList = response;
                        }
                    );
                }
            );

            $scope.normalView = 1;
        };

        $scope.modParty = function () {
            var url = "http://localhost:9001/data/store/PoliticalParty?sourced=true&token="+token;
            $http.post(url,$scope.selectedParty).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/PoliticalParty?sourced=true&token="+token;
                    $http.post(url,toParty(null)).success(
                        function (response) {
                            $scope.partyList = response;
                        }
                    );
                }
            );

            $scope.normalView = 1;
        };

        $scope.normal3 = function () {
            $scope.normalView = 3;
        };

        $scope.deleteParty = function(){
            var url = "http://localhost:9001/data/delete/PoliticalParty?sourced=true&token="+token;
            $http.post(url,$scope.selectedParty).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/PoliticalParty?sourced=true&token="+token;
                    $http.post(url,toParty(null)).success(
                        function (response) {
                            $scope.partyList = response;
                            $scope.selectedParty = null;
                        }
                    );
                }
            );
        };
    }
]);

$(document).on('click', '#partyList a', function() {
    $("#partyList a").removeClass("active");
    $(this).addClass("active");
});