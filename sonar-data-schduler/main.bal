
import ballerina/http;
import ballerina/task;
import ballerina/time;

service / on new http:Listener (port = 9090) {

//     Schedules the task to execute the job every second.
    task:JobId|task:Error result =  task:scheduleJobRecurByFrequency(
        new sonarProjectLoaderTask("SonarCloud project exrtactor job "),
        21600,
        maxCount = 10,
        startTime = time:utcToCivil(time:utcAddSeconds(time:utcNow(), 5))
    );


//         task:JobId[] ids = task:getRunningJobs();
//         io:println("job ids ", ids);

//          check task:resumeJob(result);
//     io:println("Resumed the 1st job.");
    // Unschedules the job.
    //check task:unscheduleJob(result);
}


