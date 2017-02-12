package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.Team;
import com.example.shubham.sixfourfantasy.data.source.MatchesDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MatchesLocalDataSource implements MatchesDataSource {

    private ContentResolver mContentResolver;

    @Inject
    public MatchesLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    @Override
    public Observable<List<Match>> getMatches() {
        return null;
    }

    @Override
    public Observable<Match> getMatch(@NonNull String matchId) {
        return null;
    }

    @Override
    public void saveMatch(Match match) {
        checkNotNull(match);

        // first save teams
        saveTeam(match.team1);
        saveTeam(match.team2);

        // now save match
        ContentValues values = match.toContentValues();
        mContentResolver.insert(MatchesPersistenceContract.MatchEntry.CONTENT_URI, values);
    }

    private void saveTeam(Team team) {
        ContentValues values = team.toContentValues();
        mContentResolver.insert(MatchesPersistenceContract.TeamEntry.CONTENT_URI, values);
    }
}
