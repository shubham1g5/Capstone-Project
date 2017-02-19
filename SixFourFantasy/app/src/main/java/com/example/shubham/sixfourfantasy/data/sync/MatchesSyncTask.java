package com.example.shubham.sixfourfantasy.data.sync;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.shubham.sixfourfantasy.MyApplication;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepository;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepositoryComponent;


public class MatchesSyncTask {

    private static final String TAG = MatchesSyncTask.class.getSimpleName();
    public static final String ACTION_DATA_UPDATED = "com.example.shubham.sixfourfantasy.ACTION_DATA_UPDATED";

    public static void syncMatches(Application application) {
        MatchesRepositoryComponent matchesRepositoryComponent = ((MyApplication) application).getMatchesRepositoryComponent();
        MatchesRepository matchesRepository = matchesRepositoryComponent.getMatchesRepository();
        matchesRepository.refreshTasks();
        matchesRepository.getMatches()
                .subscribe(
                        matches -> {
                            Intent dataUpdatedIntent = new Intent(ACTION_DATA_UPDATED);
                            application.sendBroadcast(dataUpdatedIntent);
                            Log.d(TAG, "Successfully updated DB with latest match list");
                        },
                        Throwable::printStackTrace
                );
    }
}
