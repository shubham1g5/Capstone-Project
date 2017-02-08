package com.example.shubham.sixfourfantasy.data;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Match;

import java.util.List;

import io.reactivex.Observable;

public interface MatchesDataSource {

    Observable<List<Match>> getMatches();

    Observable<Match> getMatch(@NonNull String matchId);

}
