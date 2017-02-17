package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.Player;
import com.example.shubham.sixfourfantasy.data.model.RunsCard;
import com.example.shubham.sixfourfantasy.data.model.Team;
import com.example.shubham.sixfourfantasy.data.model.WicketsCard;
import com.example.shubham.sixfourfantasy.data.source.MatchesDataSource;
import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MatchesLocalDataSource implements MatchesDataSource {

    private ContentResolver mContentResolver;
    BriteContentResolver resolver;

    @Inject
    public MatchesLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        resolver = sqlBrite.wrapContentProvider(contentResolver, Schedulers.io());
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
    public Observable<Team> getTeam(@NonNull int teamId) {
        return resolver.createQuery(
                MatchesPersistenceContract.TeamEntry.buildTeamsUriWith(teamId),
                MatchesPersistenceContract.TeamEntry.TEAMS_COLUMNS,
                null,
                null,
                null,
                false
        ).mapToOneOrDefault(cursor -> getTeam(cursor), null);
    }

    @NonNull
    private Team getTeam(@NonNull Cursor c) {
        int teamId = c.getInt(MatchesPersistenceContract.TeamEntry.COL_TEAM_ID_INDEX);
        String name = c.getString(MatchesPersistenceContract.TeamEntry.COL_NAME_INDEX);
        String image = c.getString(MatchesPersistenceContract.TeamEntry.COL_IMAGE_INDEX);
        String symbol = c.getString(MatchesPersistenceContract.TeamEntry.COL_SYMBOL_INDEX);

        return new Team(teamId, name, image, symbol, null);
    }

    @Override
    public void saveMatch(Match match) {
        checkNotNull(match);

        // first save teams
        saveTeam(match.team1);
        saveTeam(match.team2);

        // now save match
        mContentResolver.insert(MatchesPersistenceContract.MatchEntry.CONTENT_URI, match.toContentValues());

        // Finally save scores for the match
        saveScores(match);
    }

    private void saveScores(Match match) {
        if (match.innings != null) {
            for (int i = 0; i < match.innings.size(); i++) {
                Inning inning = match.innings.get(i);
                for (RunsCard runCard : inning.runCards) {
                    runCard.matchId = match.matchId;
                    mContentResolver.insert(MatchesPersistenceContract.RunEntry.CONTENT_URI, runCard.toContentValues());
                }

                for (WicketsCard wicketCard : inning.wicketCards) {
                    wicketCard.matchId = match.matchId;
                    mContentResolver.insert(MatchesPersistenceContract.WicketEntry.CONTENT_URI, wicketCard.toContentValues());
                }
            }
        }
    }

    @Override
    public Observable<List<Player>> getPlayersForTeam(int teamId) {
        return null;
    }

    @Override
    public Observable<List<Team>> getPlayersForMatch(Match match) {
        return null;
    }

    @Override
    public Observable<List<Inning>> getScoresForMatch(Match match) {

        // Emits runsCards for an inning
        Observable<List<RunsCard>> runsObservable = resolver.createQuery(
                MatchesPersistenceContract.RunEntry.buildRunsUriWith(match.matchId),
                MatchesPersistenceContract.RunEntry.RUNS_COLUMNS,
                null,
                null,
                MatchesPersistenceContract.RunEntry.COL_INNINGS_NO,
                false
        ).mapToList(cursor -> getRunsCard(cursor))
                .flatMap(runsCards -> Observable.from(runsCards))
                .groupBy(runsCard -> runsCard.inningsNo)
                .flatMap(integerRunsCardGroupedObservable -> integerRunsCardGroupedObservable.toList());


        // Emits wicketsCards for an inning
        Observable<List<WicketsCard>> wicketsObservable = resolver.createQuery(
                MatchesPersistenceContract.WicketEntry.buildWicketsUriWith(match.matchId),
                MatchesPersistenceContract.WicketEntry.WICKETS_COLUMNS,
                null,
                null,
                MatchesPersistenceContract.RunEntry.COL_INNINGS_NO,
                false
        ).mapToList(cursor -> getWicketsCard(cursor))
                .flatMap(wicketsCards -> Observable.from(wicketsCards))
                .groupBy(wicketsCard -> wicketsCard.inningsNo)
                .flatMap(integerRunsCardGroupedObservable -> integerRunsCardGroupedObservable.toList());

        // Combine runcards and wicketCards for an inning into an inning object
        // and emit it as list of innings
        return Observable.zip(
                runsObservable,
                wicketsObservable,
                (runsCards, wicketsCards) -> new Inning(runsCards, wicketsCards)
        ).toList();
    }

    @NotNull
    private RunsCard getRunsCard(@NotNull Cursor cursor) {
        RunsCard runsCard = new RunsCard();
        runsCard.matchId = cursor.getInt(MatchesPersistenceContract.RunEntry.COL_MATCH_ID_INDEX);
        runsCard.inningsNo = cursor.getInt(MatchesPersistenceContract.RunEntry.COL_INNINGS_NO_INDEX);
        runsCard.playerId = cursor.getInt(MatchesPersistenceContract.RunEntry.COL_PLAYER_ID_INDEX);
        runsCard.runs = cursor.getInt(MatchesPersistenceContract.RunEntry.COL_RUNS_INDEX);
        runsCard.balls = cursor.getInt(MatchesPersistenceContract.RunEntry.COL_BALLS_INDEX);
        runsCard.fours = cursor.getInt(MatchesPersistenceContract.RunEntry.COL_FOURS_INDEX);
        runsCard.sixes = cursor.getInt(MatchesPersistenceContract.RunEntry.COL_SIXES_INDEX);
        runsCard.strikeRate = cursor.getDouble(MatchesPersistenceContract.RunEntry.COL_STRIKE_RATE_INDEX);
        runsCard.fow = cursor.getString(MatchesPersistenceContract.RunEntry.COL_FOW_INDEX);
        runsCard.out = cursor.getString(MatchesPersistenceContract.RunEntry.COL_OUT_INDEX);
        return runsCard;
    }

    @NotNull
    private WicketsCard getWicketsCard(@NotNull Cursor cursor) {
        WicketsCard wicketsCard = new WicketsCard();
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_MATCH_ID_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_INNINGS_NO_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_PLAYER_ID_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_RUNS_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_OVERS_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_MAIDEN_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_WICKETS_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_ECONOMY_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_NO_BALLS_INDEX);
        wicketsCard.matchId = cursor.getInt(MatchesPersistenceContract.WicketEntry.COL_WIDE_BALLS_INDEX);
        return wicketsCard;
    }

    private void saveTeam(Team team) {
        ContentValues values = team.toContentValues();
        mContentResolver.insert(MatchesPersistenceContract.TeamEntry.CONTENT_URI, values);

        // Also save players
        if (team.players != null) {
            for (Player player : team.players) {
                mContentResolver.insert(MatchesPersistenceContract.PlayerEntry.CONTENT_URI, player.toContentValues());

                // Update Team Has Players
                ContentValues teamHasPlayerValues = new ContentValues();
                teamHasPlayerValues.put(MatchesPersistenceContract.TeamHasPlayerEntry.COL_PLAYER_ID, player.playerId);
                teamHasPlayerValues.put(MatchesPersistenceContract.TeamHasPlayerEntry.COL_TEAM_ID, team.teamId);
                mContentResolver.insert(MatchesPersistenceContract.TeamHasPlayerEntry.CONTENT_URI, teamHasPlayerValues);
            }
        }
    }
}
