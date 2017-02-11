package com.example.shubham.sixfourfantasy.data.source;

import android.content.Context;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesLocalDataSource;
import com.example.shubham.sixfourfantasy.data.source.remote.MatchesRemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MatchesRepositoryModule {

    @Singleton
    @Provides
    @Local
    MatchesDataSource provideMatchesLocalDataSource(Context context){
        return new MatchesLocalDataSource(context);
    }

    @Singleton
    @Provides
    @Remote
    MatchesDataSource provideMatchesRemoteDataSource(Context context){
        return new MatchesRemoteDataSource(context);
    }
}
