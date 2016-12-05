angular.module("officerIndexApp").controller('electionsCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerElections");
        window.document.title = "VRS Elections";

        $scope.normalView = 1;
        $scope.formData = {};
        $scope.selectedElection = null;

        var token = getValue(location.search,"token");
        var url = "http://localhost:9001/data/find/Election?sourced=true&token="+token;
        console.log("requesting Elections");

        $http.post(url,toElection(null,null)).success(
            function (response) {
                if(response != "200 success") {
                    $scope.electionList = response;
                    console.log(angular.toJson(electionList));
                }else{
                    $scope.electionList = null;

                }
            }
        );
        
        var url = "http://localhost:9001/data/find/ElectoralDistrict?sourced=true&token="+token;
        $http.post(url,toDistrict(null)).success(
            function (response) {
                if(response != "200 success") {
                    $scope.districtList = response;
                }else{
                    $scope.districtList = null;
                }
            }
        );

        $scope.selectElection = function (election) {
            $scope.selectedDistricts = null;
            $scope.selectedCandidates = null;
            $scope.selectedElection = election;

            var url = "http://localhost:9001/data/traverse/getDistrictsGivenElection?sourced=true&token="+token;
            $http.post(url,election).success(
                function (response) {
                    if(response != "200 success"){
                        $scope.selectedDistricts = response;
                        $scope.districtList.forEach(
                            function (district, index) {
                                $scope.selectedDistricts.forEach(
                                    function (sDistrict) {
                                        if(district.id == sDistrict.id){
                                            district.selected = true;
                                        }else{
                                            district.selected = false;
                                        }
                                    }
                                );
                            }
                        );
                    }else{
                        $scope.selectedDistricts = toDistrict("none");
                    }
                }
            );



            var url = "http://localhost:9001/data/traverse/getCandidatesGivenElection?sourced=true&token="+token;
            $http.post(url,election).success(
                function (response) {
                    if(response != "200 success"){
                        $scope.selectedCandidates = response;
                    }else{
                        $scope.selectedCandidates = toCandidate("none",0);
                    }
                }
            );

        };

        $scope.createNewElection = function () {
            var newOffice = $scope.formData.newOffice;
            var newIsPartisan = $scope.formData.newIsPartisan;
            var election = toElection(newOffice,newIsPartisan);
            var url = "http://localhost:9001/data/store/Election?sourced=true&token="+token;
            $http.post(url,election).success(
                function (response) {
                    election = response[0];
                    $scope.districtList.forEach(
                        function (district, index) {
                            if(typeof district.selected !== "undefined"){
                                if(district.selected == true){
                                    var url = "http://localhost:9001/data/traverse/getBallotGivenDistrict?sourced=true&token="+token;
                                    delete district.selected;
                                    $http.post(url,district).success(
                                        function (response) {
                                            var url = "http://localhost:9001/data/store/Ballot_Election?sourced=true&token="+token;
                                            $http.post(url,(angular.toJson(response[0])+"**|**"+angular.toJson(election)));
                                        }
                                    );
                                }
                            }
                        }
                    );
                    var url = "http://localhost:9001/data/find/Election?sourced=true&token="+token;
                    $http.post(url,toElection(null,null)).success(
                        function (response) {
                            $scope.electionList = response;
                        }
                    );
                }
            );

            

            $scope.normalView = 1;
        };

        $scope.modElection = function () {

            var url = "http://localhost:9001/data/store/Election?sourced=true&token="+token;
            $http.post(url,$scope.selectedElection).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Election?sourced=true&token="+token;
                    $http.post(url,toElection(null,null)).success(
                        function (response) {
                            $scope.electionList = response;
                        }
                    );
                }
            );
            
            $scope.districtList.forEach(
                function (district, index) {
                    if(typeof district.selected !== "undefined"){
                        if(district.selected == true){
                            delete district.selected;
                            var url = "http://localhost:9001/data/traverse/getBallotGivenDistrict?sourced=true&token="+token;
                            $http.post(url,district).success(
                                function (response) {
                                    var url = "http://localhost:9001/data/store/Ballot_Election?sourced=true&token="+token;
                                    $http.post(url,(angular.toJson(response[0])+"**|**"+angular.toJson($scope.selectedElection)));
                                }
                            );
                        }else
                        if(district.selected == false){
                            delete district.selected;
                            var url = "http://localhost:9001/data/traverse/getBallotGivenDistrict?sourced=true&token="+token;
                            $http.post(url,district).success(
                                function (response) {
                                    var url = "http://localhost:9001/data/delete/Ballot_Election?sourced=true&token="+token;
                                    $http.post(url,(angular.toJson(response[0])+"**|**"+angular.toJson($scope.selectedElection)));
                                }
                            );
                        }
                    }
                }
            );
            $scope.normalView = 1;
        };

        $scope.normal3 = function () {
            $scope.normalView = 3;
        };

        $scope.deleteElection = function(){
            var url = "http://localhost:9001/data/delete/Election?sourced=true&token="+token;
            $http.post(url,$scope.selectedElection).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Election?sourced=true&token="+token;
                    $http.post(url,toElection(null,null)).success(
                        function (response) {
                            $scope.electionList = response;
                            $scope.selectedElection = null;
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