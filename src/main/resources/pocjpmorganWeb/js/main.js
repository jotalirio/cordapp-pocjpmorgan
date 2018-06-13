"use strict";

// Define your backend here.
angular.module('pocjpmorganAppModule', ['ui.bootstrap']).controller('pocjpmorganAppCtrl', function($http, $location, $uibModal) {

    //The pocjpmorgan Web App container Object
    const pocjpmorganApp = this;

    //Base URL for REST API
    const apiBaseURL = "/api/pocjpmorgan/";

    //Hardcode data: Challenges and Years
    let challenges = ["J.P.Morgan Corporate Challenge"];
    let years = ["2015", "2016", "2017", "2018"];

    // Retrieves the identity of this and other nodes.
    let nodes = [];
    $http.get(apiBaseURL + "me").then((response) => pocjpmorganApp.thisNode = response.data.me);
    $http.get(apiBaseURL + "nodes").then((response) => nodes = response.data.nodes);

    /** Displays the Competitor creation modal. */
    pocjpmorganApp.openCreateCompetitorModal = () => {
        const createCompetitorModal = $uibModal.open({
            templateUrl: 'createCompetitorModal.html',
            controller: 'CreateCompetitorModalCtrl',
            controllerAs: 'createCompetitorModal',
            resolve: {
                apiBaseURL: () => apiBaseURL,
                challenges: () => challenges,
                years: () => years,
                nodes: () => nodes
            }
        });

        // Ignores the modal result events.
        createCompetitorModal.result.then(() => {}, () => {});
    };

    /** Refreshes the front-end. */
    pocjpmorganApp.refresh = () => {
        // Update the list of Competitors.
        $http.get(apiBaseURL + "competitors").then((response) => pocjpmorganApp.competitors =
                                                                    Object.keys(response.data).map((key) => response.data[key].state.data));
    }
    pocjpmorganApp.refresh();
});

// Causes the webapp to ignore unhandled modal dismissals.
angular.module('pocjpmorganAppModule').config(['$qProvider', function($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);