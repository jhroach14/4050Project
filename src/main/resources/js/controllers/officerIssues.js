angular.module("officerIndexApp").controller('issuesCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("OfficerIssues");
        window.document.title = "VRS Issues";

        $scope.normalView = 1;
        $scope.formData = {};
        $scope.selectedIssue = null;

        var token = getValue(location.search,"token");
        var url = "http://localhost:9001/data/find/Issue?sourced=true&token="+token;
        console.log("requesting Issues");

        $http.post(url,toIssue(null)).success(
            function (response) {
                if(response != "200 success") {
                    $scope.issueList = response;
                    console.log(angular.toJson(issueList));
                }else{
                    $scope.issueList = null;

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

        $scope.selectIssue = function (issue) {
            $scope.selectedDistricts = null;
            $scope.selectedIssue = issue;

            var url = "http://localhost:9001/data/traverse/getDistrictsGivenIssue?sourced=true&token="+token;
            $http.post(url,issue).success(
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

        };

        $scope.createNewIssue = function () {
            var newQuestion = $scope.formData.newQuestion;
            var issue = toIssue(newQuestion);
            var url = "http://localhost:9001/data/store/Issue?sourced=true&token="+token;
            $http.post(url,issue).success(
                function (response) {
                    issue = response[0];
                    $scope.districtList.forEach(
                        function (district, index) {
                            if(typeof district.selected !== "undefined"){
                                if(district.selected == true){
                                    var url = "http://localhost:9001/data/traverse/getBallotGivenDistrict?sourced=true&token="+token;
                                    delete district.selected;
                                    $http.post(url,district).success(
                                        function (response) {
                                            var url = "http://localhost:9001/data/store/Ballot_Issue?sourced=true&token="+token;
                                            $http.post(url,(angular.toJson(response[0])+"**|**"+angular.toJson(issue)));
                                        }
                                    );
                                }
                            }
                        }
                    );
                    var url = "http://localhost:9001/data/find/Issue?sourced=true&token="+token;
                    $http.post(url,toIssue(null)).success(
                        function (response) {
                            $scope.issueList = response;
                        }
                    );
                }
            );

            $scope.normalView = 1;
        };

        $scope.modIssue = function () {

            var url = "http://localhost:9001/data/store/Issue?sourced=true&token="+token;
            $http.post(url,$scope.selectedIssue).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Issue?sourced=true&token="+token;
                    $http.post(url,toIssue(null)).success(
                        function (response) {
                            $scope.issueList = response;
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
                                    var url = "http://localhost:9001/data/store/Ballot_Issue?sourced=true&token="+token;
                                    $http.post(url,(angular.toJson(response[0])+"**|**"+angular.toJson($scope.selectedIssue)));
                                }
                            );
                        }else
                        if(district.selected == false){
                            delete district.selected;
                            var url = "http://localhost:9001/data/traverse/getBallotGivenDistrict?sourced=true&token="+token;
                            $http.post(url,district).success(
                                function (response) {
                                    var url = "http://localhost:9001/data/delete/Ballot_Issue?sourced=true&token="+token;
                                    $http.post(url,(angular.toJson(response[0])+"**|**"+angular.toJson($scope.selectedIssue)));
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

        $scope.deleteIssue = function(){
            var url = "http://localhost:9001/data/delete/Issue?sourced=true&token="+token;
            $http.post(url,$scope.selectedIssue).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Issue?sourced=true&token="+token;
                    $http.post(url,toIssue(null)).success(
                        function (response) {
                            if(response != "200 success") {
                                $scope.issueList = response;
                                $scope.selectedIssue = null;
                            }else{
                                $scope.issueList = null;
                                $scope.selectedIssue = null
                            }
                        }
                    );
                }
            );
        };
    }
]);

$(document).on('click', '#issueList a', function() {
    $("#issueList a").removeClass("active");
    $(this).addClass("active");
});