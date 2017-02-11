package com.example.shubham.sixfourfantasy.data.source;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.source.Local;
import com.example.shubham.sixfourfantasy.data.source.Remote;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class MatchesRepository implements MatchesDataSource {

    private static MatchesRepository INSTANCE = null;

    private final MatchesDataSource mMatchessRemoteDataSource;

    private final MatchesDataSource mMatchesLocalDataSource;

    @Inject
    MatchesRepository(@Remote MatchesDataSource matchesRemoteDataSource,
                            @Local MatchesDataSource matchesLocalDataSource) {
        mMatchessRemoteDataSource = checkNotNull(matchesRemoteDataSource);
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

    /**
     * Used to force {@link #getInstance(MatchesDataSource, MatchesDataSource)} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
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
        mMatchesLocalDataSource.saveMatch(match);
    }
}
