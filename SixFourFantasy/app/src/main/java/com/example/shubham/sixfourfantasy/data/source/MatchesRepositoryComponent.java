package com.example.shubham.sixfourfantasy.data.source;

import com.example.shubham.sixfourfantasy.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MatchesRepositoryModule.class, ApplicationModule.class})
public interface MatchesRepositoryComponent {
    MatchesRepository getMatchesRepository();
}
