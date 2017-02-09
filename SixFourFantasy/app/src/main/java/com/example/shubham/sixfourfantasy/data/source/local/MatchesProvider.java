package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MatchesProvider extends ContentProvider {

    private static final int MATCH = 100;
    private static final int MATCH_ITEM = 101;
    private static final int SCORE = 200;
    private static final int RUN = 300;
    private static final int WICKET = 400;
    private static final int TEAM = 500;
    private static final int PLAYER = 600;
    private static final int TEAM_HAS_PLAYER = 700;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MatchesDbHelper mMatchesDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MatchesPersistenceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MatchesPersistenceContract.MatchEntry.TABLE_NAME, MATCH);
        matcher.addURI(authority, MatchesPersistenceContract.MatchEntry.TABLE_NAME + "/*", MATCH_ITEM);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mMatchesDbHelper = new MatchesDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MATCH:
                return MatchesPersistenceContract.MatchEntry.CONTENT_TYPE;
            case MATCH_ITEM:
                return MatchesPersistenceContract.MatchEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MATCH:
                retCursor = mMatchesDbHelper.getReadableDatabase().query(
                        MatchesPersistenceContract.MatchEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MATCH_ITEM:
                String[] where = {uri.getLastPathSegment()};
                retCursor = mMatchesDbHelper.getReadableDatabase().query(
                        MatchesPersistenceContract.MatchEntry.TABLE_NAME,
                        projection,
                        MatchesPersistenceContract.MatchEntry.COL_MATCH_ID + " = ?",
                        where,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mMatchesDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        long _id;
        switch (match) {
            case MATCH:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.MatchEntry.TABLE_NAME,
                        values,
                        MatchesPersistenceContract.MatchEntry.COL_MATCH_ID
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.MatchEntry.buildMatchesUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case SCORE:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.ScoreEntry.TABLE_NAME,
                        values,
                        MatchesPersistenceContract.ScoreEntry._ID
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.ScoreEntry.buildScoresUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case RUN:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.RunEntry.TABLE_NAME,
                        values,
                        MatchesPersistenceContract.RunEntry._ID
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.RunEntry.buildRunsUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case WICKET:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.WicketEntry.TABLE_NAME,
                        values,
                        MatchesPersistenceContract.WicketEntry._ID
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.WicketEntry.buildWicketsUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case TEAM:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.TeamEntry.TABLE_NAME,
                        values,
                        MatchesPersistenceContract.TeamEntry.COL_TEAM_ID
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.TeamEntry.buildTeamsUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case PLAYER:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.PlayerEntry.TABLE_NAME,
                        values,
                        MatchesPersistenceContract.PlayerEntry.COL_PLAYER_ID
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.PlayerEntry.buildPlayersUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case TEAM_HAS_PLAYER:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.TeamHasPlayerEntry.TABLE_NAME,
                        values,
                        MatchesPersistenceContract.TeamHasPlayerEntry._ID
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.TeamHasPlayerEntry.buildTeamHasPlayersUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    private long updateOrInsert(SQLiteDatabase db, String tableName, ContentValues values, String uniqueKey) {
        long _id;
        Cursor exists = db.query(
                tableName,
                new String[]{uniqueKey},
                uniqueKey + " = ?",
                new String[]{values.getAsString(uniqueKey)},
                null,
                null,
                null
        );
        if (exists.moveToLast()) {
            _id = db.update(
                    tableName,
                    values,
                    uniqueKey + " = ?",
                    new String[]{values.getAsString(uniqueKey)}
            );
        } else {
            _id = db.insert(tableName, null, values);
        }
        exists.close();
        return _id;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMatchesDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case MATCH:
                rowsDeleted = db.delete(MatchesPersistenceContract.MatchEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case SCORE:
                rowsDeleted = db.delete(MatchesPersistenceContract.ScoreEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RUN:
                rowsDeleted = db.delete(MatchesPersistenceContract.RunEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case WICKET:
                rowsDeleted = db.delete(MatchesPersistenceContract.WicketEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TEAM:
                rowsDeleted = db.delete(MatchesPersistenceContract.TeamEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case PLAYER:
                rowsDeleted = db.delete(MatchesPersistenceContract.PlayerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TEAM_HAS_PLAYER:
                rowsDeleted = db.delete(MatchesPersistenceContract.TeamHasPlayerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mMatchesDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MATCH:
                rowsUpdated = db.update(MatchesPersistenceContract.MatchEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case SCORE:
                rowsUpdated = db.update(MatchesPersistenceContract.ScoreEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case RUN:
                rowsUpdated = db.update(MatchesPersistenceContract.RunEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case WICKET:
                rowsUpdated = db.update(MatchesPersistenceContract.WicketEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TEAM:
                rowsUpdated = db.update(MatchesPersistenceContract.TeamEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case PLAYER:
                rowsUpdated = db.update(MatchesPersistenceContract.PlayerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case TEAM_HAS_PLAYER:
                rowsUpdated = db.update(MatchesPersistenceContract.TeamHasPlayerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
