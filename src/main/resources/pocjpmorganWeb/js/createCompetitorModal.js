"use strict";

angular.module('pocjpmorganAppModule').controller('CreateCompetitorModalCtrl', function($http, $uibModalInstance,
                                                                                        $uibModal, apiBaseURL,
                                                                                        challenges, years, nodes) {
    // Competitor Modal Object container Object
    const createCompetitorModal = this;
    createCompetitorModal.challenges = challenges;
    createCompetitorModal.years = years;
    createCompetitorModal.nodes = nodes;
    createCompetitorModal.form = {};
    createCompetitorModal.formError = false;

    /** Validate and create a Competitor. */
    createCompetitorModal.create = () => {
        if (invalidFormInput()) {
            createCompetitorModal.formError = true;
        } else {
            createCompetitorModal.formError = false;

            // Form fields
            const challengeName = createCompetitorModal.form.challengeName
            const challengeYear = createCompetitorModal.form.challengeYear
            const party = createCompetitorModal.form.counterparty; // Assistant
            const name = createCompetitorModal.form.name;
            const surname = createCompetitorModal.form.surname;
            const employee = createCompetitorModal.form.employee;
            const result = createCompetitorModal.form.result;

            // Closing the modal
            $uibModalInstance.close();

            // Define the Competitor creation endpoint.
            const submitCompetitorEndpoint = apiBaseURL + `submit-competitor?challengeName=${challengeName}
                                                                             &challengeYear=${challengeYear}
                                                                             &party=${party}
                                                                             &name=${name}
                                                                             &surname=${surname}
                                                                             &employee=${employee}
                                                                             &result=${result}`;

            // We hit the endpoint to create the Competitor and handle success/failure responses.
            $http.put(submitCompetitorEndpoint).then(
                (result) => createCompetitorModal.displayMessage(result),
                (result) => createCompetitorModal.displayMessage(result)
            );
        }
    };

    /** Displays the success/failure response from attempting to create a Competitor. */
    createCompetitorModal.displayMessage = (message) => {
        const createCompetitorMsgModal = $uibModal.open({
            templateUrl: 'createCompetitorMsgModal.html',
            controller: 'createCompetitorMsgModalCtrl',
            controllerAs: 'createCompetitorMsgModal',
            resolve: {
                message: () => message
            }
        });

        // No behaviour on close / dismiss.
        createCompetitorMsgModal.result.then(() => {}, () => {});
    };

    /** Closes the Competitor creation modal. */
    createCompetitorModal.cancel = () => $uibModalInstance.dismiss();

    // Validates the Competitor to submit.
    function invalidFormInput() {
        return isNaN(createCompetitorModal.form.result)
                || (createCompetitorModal.form.challengeName === undefined)
                || (createCompetitorModal.form.challengeYear === undefined)
                || (createCompetitorModal.form.counterparty === undefined)
                || (createCompetitorModal.form.name === undefined)
                || (createCompetitorModal.form.surname === undefined)
                || (createCompetitorModal.form.employee === undefined)
                || (createCompetitorModal.form.result === undefined);
    }
});

// Controller for the success/fail modal.
angular.module('pocjpmorganAppModule').controller('createCompetitorMsgModalCtrl', function($uibModalInstance, message) {
    const createCompetitorMsgModal = this;
    createCompetitorMsgModal.message = message.data;
});