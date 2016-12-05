angular.module("voterIndexApp").controller('homeCtrl', ['$scope', '$http',
    function($scope, $http) {
        setStyleSheet("voter");
        window.document.title = "VRS Voter Home";

        $scope.normalView = 1;
        $scope.ballotView = 1;
        $scope.formData = {};
        $scope.User = null;
        $scope.districtBallotItems = null;
        $scope.district = null;
        $scope.clicked = false;
        $scope.candidateTemp = null;
        $scope.candidateVoteCount = null;
        $scope.ballot = null;
        $scope.date = "2016-12-25";
        $scope.buttonStyle = "voteBtn";
//        "2016-12-25"

        //Get # Of Districts
        var token = getValue(location.search,"token");

        //Get User from token
        var url3 = "http://localhost:9001/data/traverse/getUserGivenToken?sourced=true&token="+token;
        console.log("requesting user");

        $http.post(url3,toToken(token)).success(

            function (response) {
                if(response != "200 success"){
                $scope.User = response[0];

                        var url = "http://localhost:9001/data/traverse/getDistrictGivenVoter?sourced=true&token="+token;
                        console.log("requesting districts from voter");

                        $http.post(url,$scope.User).success(

                            function (response) {
                                if(response != "200 success"){
                                $scope.district = response[0];

                                        var url = "http://localhost:9001/data/traverse/getBallotGivenDistrict?sourced=true&token="+token;
                                        console.log("requesting ballot given district");

                                        $http.post(url,$scope.district).success(
                                            function (ballot) {
                                                if(ballot != "200 success") {

                                                    $scope.ballot = ballot[0];

                                                    url = "http://localhost:9001/data/traverse/getBallotItemsGivenBallot?sourced=true&token=" + token;
                                                    $http.post(url, $scope.ballot).success(
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

                                } else {
                                //$scope.numberOfDistricts = 0;
                                }
                            }
                        );

                } else {
                $scope.User = null;
                }
            }
        );

        //gets the candidate for an election
        $scope.getCandidates = function (ballotItem) {
        $scope.candidate1 = null;
        $scope.candidate2 = null;

            var url = "http://localhost:9001/data/traverse/getCandidatesGivenElection?sourced=true&token="+token;
            $http.post(url,ballotItem).success(
                function (response) {

                    if(response != "200 success") {

                    $scope.candidate1 = response[0];
                    $scope.candidate2 = response[1];

                    } else {

                    }

                }
            );
        };

        //gets the candidate for an election
        $scope.chooseCandidate = function (candidate, ballotItem) {

//            if($scope.buttonStyle === "voteBtn"){
            var candidateTemp = candidate;
            var candidateVoteCount = candidate.voteCount;
            candidateTemp.voteCount = candidateVoteCount + 1;

            var electionTemp = ballotItem;
            var electionVoteCount = ballotItem.voteCount;
            electionTemp.voteCount = electionVoteCount + 1;

//            //$scope.buttonStyle = "voteBtnGreen";
//
//            } else {
//            var candidateTemp = candidate;
//            var candidateVoteCount = candidate.voteCount;
//            var candidateTemp.voteCount = candidateVoteCount - 1;
//
//            var electionTemp = ballotItem;
//            var electionVoteCount = ballotItem.voteCount;
//            electionTemp.voteCount = electionVoteCount - 1;
//
//            //$scope.buttonStyle = "voteBtn";
//
//            }


            var url = "http://localhost:9001/data/store/Candidate?sourced=true&token="+token;
            $http.post(url,candidateTemp).success(
                function (response) {

                    if(response != "200 success") {

                        var url2 = "http://localhost:9001/data/store/Election?sourced=true&token="+token;
                        $http.post(url2,electionTemp).success(
                            function (response) {

                                if(response != "200 success") {
//                                        $scope.buttonStyle = "voteBtnGreen";

                                    } else {

                                }

                            }
                        );

                        } else {

                    }

                }
            );
        };


        //choose the for option
        $scope.chooseFor = function (ballotItem) {

            var ballotItemTemp = ballotItem;
            var ballotItemYesCount = ballotItem.yesCount;
            var ballotItemVoteCount = ballotItem.voteCount;
            ballotItemTemp.yesCount = ballotItemYesCount + 1;
            ballotItemTemp.voteCount = ballotItemVoteCount + 1;

            var url = "http://localhost:9001/data/store/Issue?sourced=true&token="+token;
            $http.post(url,ballotItemTemp).success(
                function (response) {

                    if(response != "200 success") {

                        } else {

                    }

                }
            );
        };

        //chooses the against vote
        $scope.chooseAgainst = function (ballotItem) {


            var ballotItemTemp = ballotItem;
            var ballotItemNoCount = ballotItem.noCount;
            var ballotItemVoteCount = ballotItem.voteCount;
            ballotItemTemp.noCount = ballotItemNoCount + 1;
            ballotItemTemp.voteCount = ballotItemVoteCount + 1;

            var url = "http://localhost:9001/data/store/Issue?sourced=true&token="+token;
            $http.post(url,ballotItemTemp).success(
                function (response) {

                    if(response != "200 success") {

                        } else {

                    }

                }
            );
        };

        //submits the ballot
        $scope.submitBallot = function () {

            $scope.ballotView = 2;
//            if(!$scope.clicked){
//            }
//            else {
//            var candidateTemp = candidate;
//            var candidateVoteCount = candidate.voteCount;
//            var candidateTemp.voteCount = candidateVoteCount - 1;
//            }
//            var url = "http://localhost:9001/data/create/VoterRecord?sourced=true&token="+token;
//            $http.post(url,toRecord($scope.date, $scope.User, $scope.ballot)).success(
//                function (response) {
//
//                    if(response != "200 success") {
////                        if(!$scope.clicked){
////                        $scope.clicked = true;
////                        }
////                        else {
////                        $scope.clicked = false;
//                        } else {
//
//                    }
//
//                }
//            );

        };



        //Handles the storing of the voter after editing profile info
        $scope.editProfile = function () {

            var url = "http://localhost:9001/data/store/Voter?sourced=true&token="+token;
            $http.post(url,$scope.User).success(
                function (response) {
                    var url = "http://localhost:9001/data/find/Voter?sourced=true&token="+token;
                    $http.post(url,$scope.User).success(
                        function (response) {

                        }
                    );
                }
            );

            $scope.normalView = 1;
        };

        //Switches view from 1 to 2 to handle showing the form
        $scope.normal2 = function () {
            $scope.normalView = 2;
        }
    }
]);
