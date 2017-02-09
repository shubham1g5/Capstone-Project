package com.example.shubham.sixfourfantasy.data;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Match;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface for common data functions such as get list of matches,
 * get a particular match by id, save a match into localDB, get scorecard for a match,
 * get current players for live matches, get players for a team
 *
 */
public interface MatchesDataSource {

    Observable<List<Match>> getMatches();

    Observable<Match> getMatch(@NonNull String matchId);

}
