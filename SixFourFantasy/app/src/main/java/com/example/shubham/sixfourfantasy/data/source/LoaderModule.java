package com.example.shubham.sixfourfantasy.data.source;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;

import dagger.Module;
import dagger.Provides;

@Module
public class LoaderModule {

    private final String matchType;

    public LoaderModule(String matchType){
        this.matchType = matchType;
    }

    @Provides
    CursorLoader provideLoader(Context context) {
        String selection = MatchesPersistenceContract.MatchEntry.COL_STATUS + " = ? ";
        String[] selectionArgs = new String[]{matchType.toString()};

        return new CursorLoader(
                context,
                MatchesPersistenceContract.MatchEntry.CONTENT_URI,
                MatchesPersistenceContract.MatchEntry.MATCHES_COLUMNS, selection, selectionArgs, null
        );
    }
}
