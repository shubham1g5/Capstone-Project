package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.source.MatchesDataSource;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MatchesLocalDataSource implements MatchesDataSource {


    @Inject
    public MatchesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
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
        Log.d("WTF","save match in local called");
    }
}
