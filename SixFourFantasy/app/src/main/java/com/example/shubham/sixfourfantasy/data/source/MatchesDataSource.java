package com.example.shubham.sixfourfantasy.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.shubham.sixfourfantasy.data.jsonmodel.PlayersByMatch;
import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.Player;
import com.example.shubham.sixfourfantasy.data.model.Team;

import java.util.List;

import rx.Observable;

/**
 * Interface for common data functions such as get list of matches,
 * get a particular match by id, save a match into localDB, get scorecard for a match,
 * get current players for live matches, get players for a team
 *
 */
public interface MatchesDataSource {

    Observable<List<Match>> getMatches();

    Observable<Match> getMatch(@NonNull String matchId);

    void saveMatch(Match match);

    Observable<List<Player>> getPlayersForTeam(int teamId);

    Observable<List<Team>> getPlayersForMatch(Match match);

    @Nullable
    Observable<List<Inning>> getScoresForMatch(Match match);
}
