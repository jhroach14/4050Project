angular.module("officerIndexApp").controller('candidatesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerCandidates");
        window.document.title = "VRS Candidates";

        $scope.normalView = 1;
        $scope.formData = {};
        $scope.selectedParty = null;
        $scope.selectedElection = null;

        var token = getValue(location.search,"token");
        var url = "http://localhost:9001/data/find/Candidate?sourced=true&token="+token;
        console.log("requesting Candidates");

        $http.post(url,toCandidate(null,null)).success(
            function (response) {
                if(response != "200 success") {
                    $scope.candidateList = response;
                    console.log(angular.toJson(candidateList));
                }else{
                    $scope.candidateList = null;

                }
            }
        );
        var url = "http://localhost:9001/data/find/Election?sourced=true&token="+token;
        $http.post(url,toElection(null,null)).success(
            function (response) {
                if(response != "200 success") {
                    $scope.elections = response;
                }else{
                    $scope.elections = null;
                }
            }
        );
        var url = "http://localhost:9001/data/find/PoliticalParty?sourced=true&token="+token;
        $http.post(url,toParty(null)).success(
            function (response) {
                if(response != "200 success") {
                    $scope.parties = response;
                }else{
                    $scope.parties = null;
                }
            }
        );

        $scope.selectCandidate = function (candidate) {
            $scope.selectedParty = null;
            $scope.selectedElection = null;
            $scope.selectedCandidate = candidate;

            var url = "http://localhost:9001/data/traverse/getElectionGivenCandidate?sourced=true&token="+token;
            $http.post(url,candidate).success(
                function (response) {
                    if(response != "200 success"){
                        $scope.selectedElection = response.office;
                    }else{
                        $scope.selectedElection = "none";
                    }
                }
            );

            var url = "http://localhost:9001/data/traverse/getPartyGivenCandidate?sourced=true&token="+token;
            $http.post(url,candidate).success(
                function (response) {
                    if(response != "200 success"){
                        $scope.selectedParty = response.name;
                    }else{
                        $scope.selectedParty = "none";
                    }
                }
            );

        };

        $scope.createNewCandidate = function () {
            var newName = $scope.formData.newName;
            var candidate = toCandidate(newName,0);
            var url = "http://localhost:9001/data/store/Candidate?sourced=true&token="+token;
            $http.post(url,candidate).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Candidate?sourced=true&token="+token;
                    $http.post(url,toCandidate(null,null)).success(
                        function (response) {
                            $scope.candidateList = response;
                        }
                    );
                }
            );
            if(typeof $scope.selectedParty !== "undefined" && $scope.selectedParty !== "none"){
                var url = "http://localhost:9001/data/store/Party_Candidate?sourced=true&token="+token;
                $http.post(url,(angular.toJson($scope.selectedParty)+"**|**"+angular.toJson($scope.selectedCandidate)));
            }
            if(typeof $scope.selectedElection !== "undefined" && $scope.selectedElection != "none"){
                var url = "http://localhost:9001/data/store/Election_Candidate?sourced=true&token="+token;
                $http.post(url,(angular.toJson($scope.selectedElection)+"**|**"+angular.toJson($scope.selectedCandidate)));
            }

            $scope.normalView = 1;
        };

        $scope.modCandidate = function () {
            var url = "http://localhost:9001/data/store/Candidate?sourced=true&token="+token;
            $http.post(url,$scope.selectedCandidate).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Candidate?sourced=true&token="+token;
                    $http.post(url,toCandidate(null,null)).success(
                        function (response) {
                            $scope.candidateList = response;
                        }
                    );
                }
            );
            if(typeof $scope.selectedParty !== "undefined" && $scope.selectedElection != "none"){
                var url = "http://localhost:9001/data/store/Party_Candidate?sourced=true&token="+token;
                $http.post(url,(angular.toJson($scope.selectedParty)+"**|**"+angular.toJson($scope.selectedCandidate)));
            }
            if(typeof $scope.selectedElection !== "undefined" && $scope.selectedElection != "none"){
                var url = "http://localhost:9001/data/store/Election_Candidate?sourced=true&token="+token;
                $http.post(url,(angular.toJson($scope.selectedElection)+"**|**"+angular.toJson($scope.selectedCandidate)));
            }
            $scope.normalView = 1;
        };

        $scope.normal3 = function () {
            $scope.normalView = 3;
        };

        $scope.deleteCandidate = function(){
            var url = "http://localhost:9001/data/delete/Candidate?sourced=true&token="+token;
            $http.post(url,$scope.selectedCandidate).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Candidate?sourced=true&token="+token;
                    $http.post(url,toCandidate(null,null)).success(
                        function (response) {
                            $scope.candidateList = response;
                            $scope.selectedCandidate = null;
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