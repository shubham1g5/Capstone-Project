package com.example.shubham.sixfourfantasy.data.source;

import android.support.v4.content.CursorLoader;

import com.example.shubham.sixfourfantasy.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {LoaderModule.class, ApplicationModule.class})
public interface LoaderComponent {
    CursorLoader getMatchLoader();
}
