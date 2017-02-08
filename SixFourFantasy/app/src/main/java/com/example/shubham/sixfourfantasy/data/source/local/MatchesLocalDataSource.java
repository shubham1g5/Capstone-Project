package com.example.shubham.sixfourfantasy.data.source.local;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.MatchesDataSource;
import com.example.shubham.sixfourfantasy.data.model.Match;

import java.util.List;

import io.reactivex.Observable;


public class MatchesLocalDataSource implements MatchesDataSource {

    @Override
    public Observable<List<Match>> getMatches() {
        return null;
    }

    @Override
    public Observable<Match> getMatch(@NonNull String matchId) {
        return null;
    }
}
