angular.module("officerIndexApp").controller('candidatesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerCandidates");
        window.document.title = "VRS Candidates";

        $scope.normalView = 1;
        $scope.formData = {};
        $scope.data = {};
        var ogElection = toElection("none",false);
        var ogParty = toParty("none");

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
                var temporary = toElection("none",false);
                temporary.id = 0;
                if(response != "200 success") {
                    $scope.elections = response;
                    $scope.elections.push(temporary);
                }else{
                    $scope.elections = [temporary];
                }
            }
        );
        var url = "http://localhost:9001/data/find/PoliticalParty?sourced=true&token="+token;
        $http.post(url,toParty(null)).success(
            function (response) {
                var temporary = toParty("none");
                temporary.id = 0;
                if(response != "200 success") {
                    $scope.parties = response;
                    $scope.parties.push(temporary);
                }else{
                    $scope.parties = [temporary];
                }
            }
        );

        $scope.selectCandidate = function (candidate) {
            $scope.data.selectedParty = null;
            $scope.data.selectedElection = null;
            $scope.selectedCandidate = candidate;

            var url = "http://localhost:9001/data/traverse/getElectionGivenCandidate?sourced=true&token="+token;
            $http.post(url,candidate).success(
                function (response) {
                    if(response != "200 success"){
                        var tempElection = {};
                        $scope.elections.forEach(
                            function (election,index) {
                                if(election.id == response[0].id){
                                    tempElection = election;
                                }
                            }
                        );
                        $scope.data.selectedElection = tempElection;
                        ogElection = tempElection;
                    }else{
                        $scope.elections.forEach(
                            function (election, index) {
                                if(election.id == 0){
                                    $scope.data.selectedElection = election;
                                }
                            }
                        );

                    }
                }
            );

            var url = "http://localhost:9001/data/traverse/getPartyGivenCandidate?sourced=true&token="+token;
            $http.post(url,candidate).success(
                function (response) {
                    if(response != "200 success"){
                        var tempParty = {};
                        $scope.parties.forEach(
                            function (party, index) {
                                if(party.id == response[0].id){
                                    tempParty = party;
                                }
                            }
                        );
                        $scope.data.selectedParty = tempParty;
                        ogParty = tempParty;
                    }else{
                        $scope.parties.forEach(
                            function (party, index) {
                                if(party.id == 0){
                                    $scope.data.selectedParty = party;
                                }
                            }
                        );

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
                    candidate = response;
                    var url = "http://localhost:9001/data/find/Candidate?sourced=true&token="+token;
                    $http.post(url,toCandidate(null,null)).success(
                        function (response) {
                            $scope.candidateList = response;
                        }
                    );

                    if(typeof $scope.data.selectedParty !== "undefined" && $scope.data.selectedParty.name !== "none"){
                        var url = "http://localhost:9001/data/store/Party_Candidate?sourced=true&token="+token;
                        $http.post(url,(angular.toJson($scope.data.selectedParty)+"**|**"+angular.toJson(candidate[0])));
                    }
                    if(typeof $scope.data.selectedElection !== "undefined" && $scope.data.selectedElection.office !== "none"){
                        var url = "http://localhost:9001/data/store/Election_Candidate?sourced=true&token="+token;
                        $http.post(url,(angular.toJson($scope.data.selectedElection)+"**|**"+angular.toJson(candidate[0])));
                    }
                }
            );


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
            
            if(typeof $scope.data.selectedParty !== "undefined" && $scope.data.selectedParty.name !== "none"){
                var url = "http://localhost:9001/data/store/Party_Candidate?sourced=true&token="+token;
                $http.post(url,(angular.toJson($scope.data.selectedParty)+"**|**"+angular.toJson($scope.selectedCandidate)));
            }else
            if($scope.data.selectedParty.name == "none"){
                if(ogParty.name !== "none"){
                    var url = "http://localhost:9001/data/delete/Election_Candidate?sourced=true&token="+token;
                    $http.post(url,(angular.toJson(ogParty)+"**|**"+angular.toJson($scope.selectedCandidate)));
                }

            }

            if(typeof $scope.data.selectedElection !== "undefined" && $scope.data.selectedElection.office !== "none"){
                var url = "http://localhost:9001/data/store/Election_Candidate?sourced=true&token="+token;
                $http.post(url,(angular.toJson($scope.data.selectedElection)+"**|**"+angular.toJson($scope.selectedCandidate)));
            }else
            if($scope.data.selectedElection.office == "none"){
                if(ogElection.office !== "none"){
                    var url = "http://localhost:9001/data/delete/Election_Candidate?sourced=true&token="+token;
                    $http.post(url,(angular.toJson(ogElection)+"**|**"+angular.toJson($scope.selectedCandidate)));
                }

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
$(document).on('click', '#candidateList a', function() {
    $("#candidateList a").removeClass("active");
    $(this).addClass("active");
});