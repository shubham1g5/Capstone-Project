package com.example.shubham.sixfourfantasy.data.source;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.MatchStatus;
import com.example.shubham.sixfourfantasy.data.model.Player;
import com.example.shubham.sixfourfantasy.data.model.Team;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MatchesRepository implements MatchesDataSource {

    private static final String TAG = MatchesRepository.class.getSimpleName();

    private static MatchesRepository INSTANCE = null;

    private final MatchesDataSource mMatchesRemoteDataSource;

    private final MatchesDataSource mMatchesLocalDataSource;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested.
     */
    private boolean mRefreshData = false;

    @Inject
    MatchesRepository(@Remote MatchesDataSource matchesRemoteDataSource,
                      @Local MatchesDataSource matchesLocalDataSource) {
        mMatchesRemoteDataSource = checkNotNull(matchesRemoteDataSource);
        mMatchesLocalDataSource = checkNotNull(matchesLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param matchesRemoteDataSource the backend data source
     * @param matchesLocalDataSource  the device storage data source
     * @return the {@link MatchesRepository} instance
     */
    public static MatchesRepository getInstance(MatchesDataSource matchesRemoteDataSource,
                                                MatchesDataSource matchesLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MatchesRepository(matchesRemoteDataSource, matchesLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Match>> getMatches() {
        if (mRefreshData) {
            return mMatchesRemoteDataSource.getMatches()
                    .flatMap(matches -> Observable.from(matches))
                    // Filtering international teams ODI and T20 matches
                    .flatMap(match -> setMatchPlayers(match))
                    .flatMap(match -> setMatchScores(match))
                    .doOnNext(match -> mMatchesLocalDataSource.saveMatch(match))
                    .toList()
                    .doOnCompleted(() -> mRefreshData = false);
        }
        return mMatchesLocalDataSource.getMatches();
    }

    private Observable<Match> setMatchScores(Match match) {
        if (match.status != MatchStatus.UPCOMING && !match.isAbandoned) {
            return mMatchesRemoteDataSource.getScoresForMatch(match)
                    .doOnNext(innings -> match.innings = innings)
                    .flatMap(innings -> Observable.just(match));
        }
        return Observable.just(match);
    }

    private Observable<Match> setMatchPlayers(Match match) {

        if (match.status == MatchStatus.UPCOMING) {

            return Observable.zip(
                    mMatchesRemoteDataSource.getPlayersForTeam(match.team1.teamId),
                    mMatchesRemoteDataSource.getPlayersForTeam(match.team2.teamId),
                    (players1, players2) -> {
                        match.team1.players = players1;
                        match.team2.players = players2;
                        return match;
                    });
        } else {

            return mMatchesRemoteDataSource.getPlayersForMatch(match)
                    .flatMap(teams -> Observable.from(teams))
                    .doOnNext(team -> {
                        if (match.team1.teamId == team.teamId) {
                            match.team1.players = team.players;
                        } else {
                            match.team2.players = team.players;
                        }
                    })
                    .toList()
                    .flatMap(teams -> Observable.just(match));
        }
    }

    @Override
    public Observable<Match> getMatch(@NonNull String matchId) {
        return null;
    }

    @Override
    public Observable<Team> getTeam(@NonNull int teamId) {
        return mMatchesLocalDataSource.getTeam(teamId);
    }

    @Override
    public void saveMatch(Match match) {

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
        return mMatchesLocalDataSource.getScoresForMatch(match);
    }

    public void refreshTasks() {
        mRefreshData = true;
    }
}
