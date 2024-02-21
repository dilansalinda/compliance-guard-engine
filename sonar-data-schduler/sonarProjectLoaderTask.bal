import ballerina/task;
import ballerina/log;
import ballerina/http;


class sonarProjectLoaderTask {

    http:Client|error sonarCloud = new("");
    *task:Job;
    string jobIdentifier;

    public function execute() {
        log:printInfo("------------ Start "+ self.jobIdentifier +" Project data extraction --------------");

        log:printInfo("------------ End "+ self.jobIdentifier +" Project data extraction --------------");  
    }

    isolated function init(string jobIdentifier) {
        self.jobIdentifier = jobIdentifier;
    }
}