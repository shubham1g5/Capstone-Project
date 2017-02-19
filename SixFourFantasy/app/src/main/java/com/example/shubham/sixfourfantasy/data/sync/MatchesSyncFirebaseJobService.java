package com.example.shubham.sixfourfantasy.data.sync;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MatchesSyncFirebaseJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters job) {
        MatchesSyncTask.syncMatches(getApplication());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
