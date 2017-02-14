package com.example.shubham.sixfourfantasy.network;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.jsonmodel.MatchResponse;
import com.example.shubham.sixfourfantasy.data.jsonmodel.PlayersByMatch;
import com.example.shubham.sixfourfantasy.data.jsonmodel.PlayersByTeam;
import com.example.shubham.sixfourfantasy.data.jsonmodel.ScoresByMatch;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MatchService {

    @NonNull
    @GET("matches.php")
    Observable<MatchResponse> listMatches();

    @NonNull
    @GET("playersbyteam.php")
    Observable<PlayersByTeam> listTeamPlayers(@Query("teamid") int teamId);

    @NonNull
    @GET("playersbymatch.php")
    Observable<PlayersByMatch> listMatchPlayers(@Query("matchid") int matchId, @Query("seriesid") int seriesId);

    @NonNull
    @GET("scorecards.php")
    Observable<ScoresByMatch> listMatchScores(@Query("matchid") int matchId, @Query("seriesid") int seriesId);
}
