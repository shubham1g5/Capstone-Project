package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.Player;
import com.example.shubham.sixfourfantasy.data.model.RunsCard;
import com.example.shubham.sixfourfantasy.data.model.Team;
import com.example.shubham.sixfourfantasy.data.model.WicketsCard;
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
        return null;
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
