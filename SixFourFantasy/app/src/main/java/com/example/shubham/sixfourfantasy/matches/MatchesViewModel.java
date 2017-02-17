package com.example.shubham.sixfourfantasy.matches;

import android.content.Context;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.shubham.sixfourfantasy.BR;
import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.MatchStatus;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepository;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

public class MatchesViewModel extends BaseObservable implements LoaderManager.LoaderCallbacks<Cursor> {

    // These observable fields will update Views automatically
    public final ObservableList<Match> items = new ObservableArrayList<>();

    public final ObservableBoolean dataLoading = new ObservableBoolean(false);

    public final ObservableField<String> noMatchesLabel = new ObservableField<>();

    private final MatchesRepository mMatchesRepository;

    private Context mContext; // To avoid leaks, this must be an Application Context.
    private MatchStatus mMatchType;
    private LoaderManager mLoaderManager;

    public MatchesViewModel(Context context, MatchStatus matchType, LoaderManager loaderManager, MatchesRepository matchesRepository) {
        mContext = context.getApplicationContext(); // Force use of Application Context.
        mMatchType = matchType;
        mLoaderManager = loaderManager;
        mMatchesRepository = matchesRepository;
    }

    @Bindable
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void start() {

        noMatchesLabel.set(mContext.getString(R.string.empty_matches_label, mMatchType.toString().toLowerCase()));

        // Create Loader
        dataLoading.set(true);
        mLoaderManager.initLoader(mMatchType.ordinal(), null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection = MatchesPersistenceContract.MatchEntry.COL_STATUS + " = ? ";
        String[] selectionArgs = new String[]{mMatchType.toString()};

        return new CursorLoader(
                mContext,
                MatchesPersistenceContract.MatchEntry.CONTENT_URI,
                MatchesPersistenceContract.MatchEntry.MATCHES_COLUMNS, selection, selectionArgs, null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        dataLoading.set(false);
        if (data != null && data.moveToFirst()) {
            List<Match> matches = new ArrayList<>();
            onDataLoaded(matches, data);
        }
    }

    private void onDataLoaded(List<Match> matches, Cursor data) {

        Match match = Match.from(data);

        Observable.zip(
                mMatchesRepository.getTeam(match.team1.teamId),
                mMatchesRepository.getTeam(match.team2.teamId),
                (team1, team2) -> {
                    match.team1 = team1;
                    match.team2 = team2;
                    return match;
                }
        ).subscribe(
                // doNext
                match1 -> {
                    matches.add(match1);
                    if (data.moveToNext())
                        onDataLoaded(matches, data);
                    else
                        setItems(matches);
                },
                // OnError
                Throwable::printStackTrace
        );
    }

    private void setItems(List<Match> matches) {
        items.clear();
        items.addAll(matches);
        notifyPropertyChanged(BR.empty); // It's a @Bindable so update manually
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
