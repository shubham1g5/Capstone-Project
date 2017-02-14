package com.example.shubham.sixfourfantasy.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.MatchFormat;
import com.example.shubham.sixfourfantasy.data.model.Player;
import com.example.shubham.sixfourfantasy.data.model.Team;
import com.example.shubham.sixfourfantasy.data.source.MatchesDataSource;
import com.example.shubham.sixfourfantasy.network.MatchService;
import com.example.shubham.sixfourfantasy.network.RetrofitServiceGenerator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

@Singleton
public class MatchesRemoteDataSource implements MatchesDataSource {

    private final MatchService mMatchService;

    @Inject
    public MatchesRemoteDataSource(@NonNull Context context) {
//        checkNotNull(context);
        mMatchService = RetrofitServiceGenerator.createService(MatchService.class);
    }

    @Override
    public Observable<List<Match>> getMatches() {
        return mMatchService.listMatches()
                .subscribeOn(Schedulers.io())
                .map(matchResponse -> matchResponse.matchList.matches)
                .flatMap(matches -> Observable.from(matches))
                .filter(match -> {
                    try {
                        return match.matchTypeId == 0 && match.format != MatchFormat.Test && match.isValid();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .toList();
    }

    @Override
    public Observable<Match> getMatch(@NonNull String matchId) {
        return null;
    }

    @Override
    public void saveMatch(Match match) {

    }

    @Override
    public Observable<List<Player>> getPlayersForTeam(int teamId) {
        return mMatchService.listTeamPlayers(teamId)
                .subscribeOn(Schedulers.io())
                .map(playersByTeam -> playersByTeam.teamPlayers.players);
    }

    @Override
    public Observable<List<Team>> getPlayersForMatch(Match match) {
        return mMatchService.listMatchPlayers(match.matchId, match.seriesId)
                .subscribeOn(Schedulers.io())
                .flatMap(playersByMatch -> {
                    List<Team> teams = new ArrayList<>();
                    teams.add(playersByMatch.playersInMatch.homeTeam);
                    teams.add(playersByMatch.playersInMatch.awayTeam);
                    return Observable.just(teams);
                });
    }

    @Nullable
    @Override
    public Observable<List<Inning>> getScoresForMatch(Match match) {
        return mMatchService.listMatchScores(match.matchId, match.seriesId)
                .subscribeOn(Schedulers.io())
                .map(scoresByMatch -> scoresByMatch.fullScorecard == null ?
                        null : scoresByMatch.fullScorecard.innings);
    }
}
