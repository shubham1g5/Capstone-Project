package com.example.shubham.sixfourfantasy.data.sync;

import com.example.shubham.sixfourfantasy.MyApplication;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepositoryComponent;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MatchesSyncFirebaseJobService extends JobService {


    @Override
    public boolean onStartJob(JobParameters job) {
        MatchesRepositoryComponent matchesRepositoryComponent = ((MyApplication) getApplication()).getMatchesRepositoryComponent();
        MatchesSyncTask.syncMatches(matchesRepositoryComponent.getMatchesRepository());
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
