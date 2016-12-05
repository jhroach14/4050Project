
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
        $scope.candidates = null;
        $scope.data = {};
        //var ctrl = this;
//        "2016-12-25"

        //Get # Of Districts
        var token = getValue(location.search,"token");

        //Get User from token
        var url3 = "http://localhost:9001/data/traverse/getUserGivenToken?sourced=true&token="+token;
        console.log("requesting user");

        var urlx = "http://localhost:9001/data/find/sysOpen?token="+token;

        $http.get(urlx).success(
            function (response) {
                if(response[0].name == "open"){
                    $scope.data.open = true;
                }else{
                    $scope.data.open = false;
                }
            }
        );

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

                      $scope.candidates = response;

                    } else {

                    }

                }
            );
        };

        //gets the candidate for an election
        $scope.chooseCandidate = function (candidate, ballotItem) {


                if(candidate.name == $scope.candidates[0].name){
                    $scope.candidates[0].selected = true;
                    $scope.candidates[1].selected = false;
//                    $scope.buttonStyle1 = "voteBtnGreen";
//                    $scope.buttonStyle2 = "voteBtn";
                }
                else if(candidate.name == $scope.candidates[1].name){
                    $scope.candidates[1].selected = true;
                    $scope.candidates[0].selected = false;
//                    $scope.buttonStyle2 = "voteBtnGreen";
//                    $scope.buttonStyle1 = "voteBtn";
                }

        };


        //choose the for option
        $scope.chooseFor = function (ballotItem) {


                        ballotItem.selected = true;
//                        $scope.buttonStyle3 = "voteBtnGreen";
//                        $scope.buttonStyle4 = "voteBtn";


        };

        //chooses the against vote
        $scope.chooseAgainst = function (ballotItem) {


                        ballotItem.selected = false;
//                        $scope.buttonStyle4 = "voteBtnGreen";
//                        $scope.buttonStyle3 = "voteBtn";



        };

        //submits the ballot
        $scope.submitBallot = function () {

                    $scope.districtBallotItems.forEach(
                        function (ballotItem, index) {
                            if(typeof ballotItem.office !== "undefined"){

                            $scope.candidates.forEach(
                                function (candidate, index) {
                                if(candidate.selected == true){
                                    delete candidate.selected;

                                                var candidateTemp = candidate;
                                                var candidateVoteCount = candidate.voteCount;
                                                candidateTemp.voteCount = candidateVoteCount + 1;

                                    var url = "http://localhost:9001/data/store/Candidate?sourced=true&token="+token;
                                    $http.post(url,candidateTemp).success(
                                        function (response) {

                                                        var electionTemp = ballotItem;
                                                        var electionVoteCount = ballotItem.voteCount;
                                                        electionTemp.voteCount = electionVoteCount + 1;

                                            var url = "http://localhost:9001/data/store/Election?sourced=true&token="+token;
                                            $http.post(url,electionTemp);
                                        }
                                    );
                                }

                            }
                            )
                        } else if(typeof ballotItem.question !== "undefined"){
                                if(ballotItem.selected == true){
                                    delete ballotItem.selected;

                                        var ballotItemTemp = ballotItem;
                                        var ballotItemYesCount = ballotItem.yesCount;
                                        var ballotItemNoCount = ballotItem.noCount;
                                        var ballotItemVoteCount = ballotItem.voteCount;
                                        ballotItemTemp.noCount = ballotItemNoCount;
                                        ballotItemTemp.yesCount = ballotItemYesCount + 1;
                                        ballotItemTemp.voteCount = ballotItemVoteCount + 1;

                                    var url = "http://localhost:9001/data/store/Issue?sourced=true&token="+token;
                                    $http.post(url,ballotItemTemp).success(
                                        function (response) {

                                        }
                                    );
                                } else if(ballotItem.selected == false){
                                       delete ballotItem.selected;

                                           var ballotItemTemp = ballotItem;
                                           var ballotItemNoCount = ballotItem.noCount;
                                           var ballotItemYesCount = ballotItem.yesCount;
                                           var ballotItemVoteCount = ballotItem.voteCount;
                                           ballotItemTemp.yesCount = ballotItemYesCount;
                                           ballotItemTemp.noCount = ballotItemNoCount + 1;
                                           ballotItemTemp.voteCount = ballotItemVoteCount + 1;

                                       var url = "http://localhost:9001/data/store/Issue?sourced=true&token="+token;
                                       $http.post(url,ballotItemTemp).success(
                                           function (response) {

                                           }
                                       );
                                   }
                        }
                        }
                    );

            $scope.ballotView = 2;

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