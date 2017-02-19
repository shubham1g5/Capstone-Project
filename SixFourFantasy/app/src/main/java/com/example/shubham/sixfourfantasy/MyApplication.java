package com.example.shubham.sixfourfantasy;


import android.app.Application;

import com.example.shubham.sixfourfantasy.data.source.DaggerMatchesRepositoryComponent;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepositoryComponent;
import com.facebook.stetho.Stetho;

// TODO: 19/2/17 Live Match Scheduler
// TODO: 19/2/17 Tab Layout
// TODO: 19/2/17 signing configuration
public class MyApplication extends Application {

    private MatchesRepositoryComponent mRepositoryComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        mRepositoryComponent = DaggerMatchesRepositoryComponent.builder()
                .applicationModule(new ApplicationModule((getApplicationContext())))
                .build();
    }

    public MatchesRepositoryComponent getMatchesRepositoryComponent() {
        return mRepositoryComponent;
    }
}
