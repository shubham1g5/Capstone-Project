package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

public class MatchesProvider extends ContentProvider {

    private static final int MATCH = 100;
    private static final int MATCH_ITEM = 101;
    private static final int RUN = 300;
    private static final int RUN_BY_MATCH = 301;
    private static final int WICKET = 400;
    private static final int WICKET_BY_MATCH = 401;
    private static final int TEAM = 500;
    private static final int TEAM_ITEM = 501;
    private static final int PLAYER = 600;
    private static final int TEAM_HAS_PLAYER = 700;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MatchesDbHelper mMatchesDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MatchesPersistenceContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MatchesPersistenceContract.MatchEntry.TABLE_NAME, MATCH);
        matcher.addURI(authority, MatchesPersistenceContract.MatchEntry.TABLE_NAME + "/*", MATCH_ITEM);
        matcher.addURI(authority, MatchesPersistenceContract.RunEntry.TABLE_NAME, RUN);
        matcher.addURI(authority, MatchesPersistenceContract.RunEntry.TABLE_NAME + "/*", RUN_BY_MATCH);
        matcher.addURI(authority, MatchesPersistenceContract.WicketEntry.TABLE_NAME, WICKET);
        matcher.addURI(authority, MatchesPersistenceContract.WicketEntry.TABLE_NAME + "/*", WICKET_BY_MATCH);
        matcher.addURI(authority, MatchesPersistenceContract.TeamEntry.TABLE_NAME, TEAM);
        matcher.addURI(authority, MatchesPersistenceContract.TeamEntry.TABLE_NAME + "/*", TEAM_ITEM);
        matcher.addURI(authority, MatchesPersistenceContract.PlayerEntry.TABLE_NAME, PLAYER);
        matcher.addURI(authority, MatchesPersistenceContract.TeamHasPlayerEntry.TABLE_NAME, TEAM_HAS_PLAYER);

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
            case RUN:
                return MatchesPersistenceContract.RunEntry.CONTENT_TYPE;
            case RUN_BY_MATCH:
                return MatchesPersistenceContract.RunEntry.CONTENT_TYPE;
            case WICKET:
                return MatchesPersistenceContract.WicketEntry.CONTENT_TYPE;
            case WICKET_BY_MATCH:
                return MatchesPersistenceContract.WicketEntry.CONTENT_TYPE;
            case TEAM:
                return MatchesPersistenceContract.TeamEntry.CONTENT_TYPE;
            case TEAM_ITEM:
                return MatchesPersistenceContract.TeamEntry.CONTENT_ITEM_TYPE;
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
                retCursor = mMatchesDbHelper.getReadableDatabase().query(
                        MatchesPersistenceContract.MatchEntry.TABLE_NAME,
                        projection,
                        MatchesPersistenceContract.MatchEntry.COL_MATCH_ID + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null,
                        null,
                        sortOrder
                );
                break;
            case TEAM_ITEM:
                retCursor = mMatchesDbHelper.getReadableDatabase().query(
                        MatchesPersistenceContract.TeamEntry.TABLE_NAME,
                        projection,
                        MatchesPersistenceContract.TeamEntry.COL_TEAM_ID + " = ?",
                        new String[]{uri.getLastPathSegment()},
                        null,
                        null,
                        sortOrder
                );
                break;
            case RUN:
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
                        new String[]{MatchesPersistenceContract.MatchEntry.COL_MATCH_ID}
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.MatchEntry.buildMatchesUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            case RUN:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.RunEntry.TABLE_NAME,
                        values,
                        new String[]{MatchesPersistenceContract.RunEntry.COL_PLAYER_ID, MatchesPersistenceContract.RunEntry.COL_MATCH_ID,
                                MatchesPersistenceContract.RunEntry.COL_INNINGS_NO}
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
                        new String[]{MatchesPersistenceContract.WicketEntry.COL_PLAYER_ID, MatchesPersistenceContract.WicketEntry.COL_MATCH_ID,
                                MatchesPersistenceContract.WicketEntry.COL_INNINGS_NO}
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
                        new String[]{MatchesPersistenceContract.TeamEntry.COL_TEAM_ID}
                );
                if (_id > 0) {
                    returnUri = MatchesPersistenceContract.TeamEntry.buildTeamsUriWith(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri + values.toString());
                }
                break;
            case PLAYER:
                _id = updateOrInsert(
                        db,
                        MatchesPersistenceContract.PlayerEntry.TABLE_NAME,
                        values,
                        new String[]{MatchesPersistenceContract.PlayerEntry.COL_PLAYER_ID}
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
                        new String[]{MatchesPersistenceContract.TeamHasPlayerEntry.COL_PLAYER_ID, MatchesPersistenceContract.TeamHasPlayerEntry.COL_TEAM_ID}
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

    private long updateOrInsert(@NotNull SQLiteDatabase db, @NotNull String tableName, @NotNull ContentValues values, @NotNull String[] uniqueKeys) {

        long _id;

        String selection = "";
        String[] selctionArgs = new String[uniqueKeys.length];
        int i = 0;
        for (String uniqueKey : uniqueKeys) {
            selection += selection.length() != 0 ? " and " : "";
            selection += uniqueKey + " = ?";
            selctionArgs[i++] = values.getAsString(uniqueKey);
        }

        Cursor exists = db.query(
                tableName,
                new String[]{},
                selection,
                selctionArgs,
                null,
                null,
                null
        );
        if (exists.moveToLast()) {
            _id = db.update(
                    tableName,
                    values,
                    selection,
                    selctionArgs
            );
        } else {
            _id = db.insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
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
