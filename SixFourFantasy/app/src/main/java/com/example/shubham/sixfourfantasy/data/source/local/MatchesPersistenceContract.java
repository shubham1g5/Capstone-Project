package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.shubham.sixfourfantasy.BuildConfig;

public class MatchesPersistenceContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    private static final String SEPARATOR = "/";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private MatchesPersistenceContract() {
    }

    public static abstract class MatchEntry implements BaseColumns {

        public static final String TABLE_NAME = "match";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String COLUMN_NAME_MATCH_ID = "match_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_TEAM1_ID = "team1_id";
        public static final String COLUMN_NAME_team2_ID = "team2_id";
        public static final String COLUMN_NAME_IS_WOMEN = "is_women";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_VENUE = "venue";
        public static final String COLUMN_NAME_SERIES = "series";
        public static final String COLUMN_NAME_START_TIME = "start_time";
        public static final String COLUMN_NAME_WINNING_TEAM_ID = "winning_team_id";
        public static final String COLUMN_NAME_RESULT = "result";
        public static final String COLUMN_NAME_TEAM1_SCORE = "team1_score";
        public static final String COLUMN_NAME_TEAM2_SCORE = "team2_score";


        public static String[] MATCHES_COLUMNS = new String[]{
                MatchEntry._ID,
                COLUMN_NAME_MATCH_ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_TEAM1_ID,
                COLUMN_NAME_team2_ID,
                COLUMN_NAME_IS_WOMEN,
                COLUMN_NAME_STATUS,
                COLUMN_NAME_VENUE,
                COLUMN_NAME_SERIES,
                COLUMN_NAME_START_TIME,
                COLUMN_NAME_WINNING_TEAM_ID,
                COLUMN_NAME_RESULT,
                COLUMN_NAME_TEAM1_SCORE,
                COLUMN_NAME_TEAM2_SCORE};

        public static Uri buildMatchesUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildMatchesUriWith(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

    }


    public static abstract class ScoreEntry implements BaseColumns {

        public static final String TABLE_NAME = "score";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;


        public static final String COLUMN_NAME_MATCH_ID = "match_id";
        public static final String COLUMN_NAME_INNINGS_NO = "innings_no";
        public static final String COLUMN_NAME_BATTING_TEAM_ID = "batting_team_id";
        public static final String COLUMN_NAME_BATTING_ID = "batting_id";
        public static final String COLUMN_NAME_BOWLING_ID = "bowling_id";

        public static String[] SCORES_COLUMNS = new String[]{
                ScoreEntry._ID,
                COLUMN_NAME_MATCH_ID,
                COLUMN_NAME_INNINGS_NO,
                COLUMN_NAME_BATTING_TEAM_ID,
                COLUMN_NAME_BATTING_ID,
                COLUMN_NAME_BOWLING_ID};

        public static Uri buildScoresUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildScoresUriWith(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

    }


    public static abstract class RunsEntry implements BaseColumns {

        public static final String TABLE_NAME = "runs";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String COLUMN_NAME_PLAYER_ID = "player_id";
        public static final String COLUMN_NAME_RUNS = "runs";
        public static final String COLUMN_NAME_BALLS = "balls";
        public static final String COLUMN_NAME_FOURS = "fours";
        public static final String COLUMN_NAME_SIXES = "sixes";
        public static final String COLUMN_NAME_STRIKE_RATE = "strike_rate";
        public static final String COLUMN_NAME_FOW = "fow";
        public static final String COLUMN_NAME_OUT = "out";

        public static String[] RUNS_COLUMNS = new String[]{
                RunsEntry._ID,
                COLUMN_NAME_PLAYER_ID,
                COLUMN_NAME_RUNS,
                COLUMN_NAME_BALLS,
                COLUMN_NAME_FOURS,
                COLUMN_NAME_SIXES,
                COLUMN_NAME_STRIKE_RATE,
                COLUMN_NAME_FOW,
                COLUMN_NAME_OUT};

        public static Uri buildRunsUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildRunsUriWith(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

    }

    public static abstract class WicketsEntry implements BaseColumns {

        public static final String TABLE_NAME = "wickets";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String COLUMN_NAME_PLAYER_ID = "player_id";
        public static final String COLUMN_NAME_RUNS = "runs";
        public static final String COLUMN_NAME_OVERS = "overs";
        public static final String COLUMN_NAME_MAIDEN = "maiden";
        public static final String COLUMN_NAME_WICKETS = "wickets";
        public static final String COLUMN_NAME_ECONOMY = "economy";
        public static final String COLUMN_NAME_NO_BALLS = "no_balls";
        public static final String COLUMN_NAME_WIDE_BALLS = "wide_balls";

        public static String[] WICKETS_COLUMNS = new String[]{
                WicketsEntry._ID,
                COLUMN_NAME_PLAYER_ID,
                COLUMN_NAME_RUNS,
                COLUMN_NAME_OVERS,
                COLUMN_NAME_MAIDEN,
                COLUMN_NAME_WICKETS,
                COLUMN_NAME_ECONOMY,
                COLUMN_NAME_NO_BALLS,
                COLUMN_NAME_WIDE_BALLS};

        public static Uri buildWicketsUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildWicketsUriWith(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

    }

    public static abstract class TeamEntry implements BaseColumns {

        public static final String TABLE_NAME = "team";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String COLUMN_NAME_TEAM_ID = "team_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_SYMBOL = "symbol";

        public static String[] WICKETS_COLUMNS = new String[]{
                TeamEntry._ID,
                COLUMN_NAME_TEAM_ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_IMAGE,
                COLUMN_NAME_SYMBOL};

        public static Uri buildTeamsUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTeamsUriWith(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

    }

    public static abstract class PlayerEntry implements BaseColumns {

        public static final String TABLE_NAME = "team";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String COLUMN_NAME_PLAYER_ID = "player_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_TYPE = "type";


        public static String[] WICKETS_COLUMNS = new String[]{
                PlayerEntry._ID,
                COLUMN_NAME_PLAYER_ID,
                COLUMN_NAME_NAME,
                COLUMN_NAME_IMAGE,
                COLUMN_NAME_TYPE};

        public static Uri buildPlayersUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPlayersUriWith(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }

    }

    public static abstract class TeamHasPlayersEntry implements BaseColumns {

        public static final String TABLE_NAME = "team_has_palyers";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + SEPARATOR + CONTENT_AUTHORITY + SEPARATOR + TABLE_NAME;

        public static final String COLUMN_NAME_TEAM_ID = "team_id";
        public static final String COLUMN_NAME_PLAYER_ID = "player_id";


        public static String[] TEAM_HAS_PLAYERS_COLUMNS = new String[]{
                TeamHasPlayersEntry._ID,
                COLUMN_NAME_TEAM_ID,
                COLUMN_NAME_PLAYER_ID};

        public static Uri buildTeamHasPlayersUriWith(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTeamHasPlayersUriWith(String id) {
            return CONTENT_URI.buildUpon().appendPath(id).build();
        }
    }
}
