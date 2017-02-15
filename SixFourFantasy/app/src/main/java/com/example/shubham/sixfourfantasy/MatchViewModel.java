package com.example.shubham.sixfourfantasy;

import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.example.shubham.sixfourfantasy.data.model.Match;

import org.jetbrains.annotations.NotNull;

public class MatchViewModel extends BaseObservable implements LoaderManager.LoaderCallbacks<Cursor>{

    public final ObservableField<String> title = new ObservableField<>();

    private final ObservableField<Match> mMatchObservable = new ObservableField<>();

    public MatchViewModel() {

        mMatchObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Match match = mMatchObservable.get();
                title.set(match.name);
            }
        });
    }

    public void setMatch(@NotNull Match match) {
        mMatchObservable.set(match);
    }


    protected int getMatchId() {
        return mMatchObservable.get().matchId;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
