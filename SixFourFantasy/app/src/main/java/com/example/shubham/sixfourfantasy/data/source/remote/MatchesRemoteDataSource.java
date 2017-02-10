package com.example.shubham.sixfourfantasy.data.source.remote;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.MatchesDataSource;
import com.example.shubham.sixfourfantasy.data.model.Match;

import java.util.List;

import rx.Observable;


public class MatchesRemoteDataSource implements MatchesDataSource {
    @Override
    public Observable<List<Match>> getMatches() {
        return null;
    }

    @Override
    public Observable<Match> getMatch(@NonNull String matchId) {
        return null;
    }
}
