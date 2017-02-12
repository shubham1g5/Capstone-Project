package com.example.shubham.sixfourfantasy.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.PlayerEntry;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.RunEntry;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.ScoreEntry;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.TeamEntry;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.TeamHasPlayerEntry;
import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.WicketEntry;

public class MatchesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Matches.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String BOOLEAN_TYPE = " INTEGER";
    private static final String FLOAT_TYPE = " REAL";

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String COMMA_SEP = ",";
    private static final String OPENING_BRACKET = " (";
    private static final String CLOSING_BRACKET = ")";
    private static final String CLOSING_BRACKET_SEMICOLON = ");";
    private static final String CLOSING_BRACKET_COMMA = "), ";
    private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";


    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String UNIQUE = " UNIQUE";
    private static final String NOT_NULL = " NOT NULL";
    private static final String FOREIGN_KEY = " FOREIGN KEY (";
    private static final String REFERENCES = ") REFERENCES ";


    public MatchesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_MATCH_TABLE = CREATE_TABLE + MatchEntry.TABLE_NAME + OPENING_BRACKET +
                MatchEntry._ID + INTEGER_TYPE + UNIQUE + COMMA_SEP +
                MatchEntry.COL_MATCH_ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                MatchEntry.COL_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_TEAM1_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_TEAM2_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_IS_WOMEN + BOOLEAN_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_STATUS + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_VENUE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_SERIES + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_START_TIME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                MatchEntry.COL_WINNING_TEAM_ID + INTEGER_TYPE + COMMA_SEP +
                MatchEntry.COL_RESULT + TEXT_TYPE + COMMA_SEP +
                MatchEntry.COL_TEAM1_SCORE + TEXT_TYPE + COMMA_SEP +
                MatchEntry.COL_TEAM2_SCORE + TEXT_TYPE + COMMA_SEP +
                MatchEntry.COL_FORMAT + TEXT_TYPE + NOT_NULL + COMMA_SEP +

                FOREIGN_KEY + MatchEntry.COL_TEAM1_ID + REFERENCES +
                TeamEntry.TABLE_NAME + OPENING_BRACKET + TeamEntry.COL_TEAM_ID + CLOSING_BRACKET_COMMA +

                FOREIGN_KEY + MatchEntry.COL_TEAM2_ID + REFERENCES +
                TeamEntry.TABLE_NAME + OPENING_BRACKET + TeamEntry.COL_TEAM_ID + CLOSING_BRACKET + CLOSING_BRACKET_SEMICOLON;

        final String SQL_CREATE_SCORE_TABLE = CREATE_TABLE + ScoreEntry.TABLE_NAME + OPENING_BRACKET +
                ScoreEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                ScoreEntry.COL_MATCH_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                ScoreEntry.COL_INNINGS_NO + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                ScoreEntry.COL_BATTING_TEAM_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                ScoreEntry.COL_BOWLING_TEAM_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                ScoreEntry.COL_BATTING_SCORE_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                ScoreEntry.COL_BOWLING_SCORE_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +

                FOREIGN_KEY + ScoreEntry.COL_MATCH_ID + REFERENCES +
                MatchEntry.TABLE_NAME + OPENING_BRACKET + MatchEntry.COL_MATCH_ID + CLOSING_BRACKET_COMMA +

                FOREIGN_KEY + ScoreEntry.COL_BATTING_TEAM_ID + REFERENCES +
                TeamEntry.TABLE_NAME + OPENING_BRACKET + TeamEntry.COL_TEAM_ID + CLOSING_BRACKET_COMMA +

                FOREIGN_KEY + ScoreEntry.COL_BOWLING_TEAM_ID + REFERENCES +
                TeamEntry.TABLE_NAME + OPENING_BRACKET + TeamEntry.COL_TEAM_ID + CLOSING_BRACKET_COMMA +

                FOREIGN_KEY + ScoreEntry.COL_BATTING_SCORE_ID + REFERENCES +
                RunEntry.TABLE_NAME + OPENING_BRACKET + RunEntry._ID + CLOSING_BRACKET_COMMA +

                FOREIGN_KEY + ScoreEntry.COL_BOWLING_SCORE_ID + REFERENCES +
                WicketEntry.TABLE_NAME + OPENING_BRACKET + WicketEntry._ID + CLOSING_BRACKET + CLOSING_BRACKET_SEMICOLON;

        final String SQL_CREATE_RUN_TABLE = CREATE_TABLE + RunEntry.TABLE_NAME + OPENING_BRACKET +
                RunEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                RunEntry.COL_PLAYER_ID + INTEGER_TYPE + COMMA_SEP +
                RunEntry.COL_RUNS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                RunEntry.COL_BALLS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                RunEntry.COL_FOURS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                RunEntry.COL_SIXES + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                RunEntry.COL_STRIKE_RATE + FLOAT_TYPE + NOT_NULL + COMMA_SEP +
                RunEntry.COL_FOW + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                RunEntry.COL_OUT + TEXT_TYPE + NOT_NULL + COMMA_SEP +

                FOREIGN_KEY + RunEntry.COL_PLAYER_ID + REFERENCES +
                PlayerEntry.TABLE_NAME + OPENING_BRACKET + PlayerEntry.COL_PLAYER_ID + CLOSING_BRACKET + CLOSING_BRACKET_SEMICOLON;

        final String SQL_CREATE_WICKET_TABLE = CREATE_TABLE + WicketEntry.TABLE_NAME + OPENING_BRACKET +
                WicketEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                WicketEntry.COL_PLAYER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                WicketEntry.COL_RUNS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                WicketEntry.COL_OVERS + FLOAT_TYPE + NOT_NULL + COMMA_SEP +
                WicketEntry.COL_MAIDEN + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                WicketEntry.COL_WICKETS + FLOAT_TYPE + NOT_NULL + COMMA_SEP +
                WicketEntry.COL_ECONOMY + FLOAT_TYPE + NOT_NULL + COMMA_SEP +
                WicketEntry.COL_NO_BALLS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                WicketEntry.COL_WIDE_BALLS + INTEGER_TYPE + NOT_NULL + COMMA_SEP +

                FOREIGN_KEY + WicketEntry.COL_PLAYER_ID + REFERENCES +
                PlayerEntry.TABLE_NAME + OPENING_BRACKET + PlayerEntry.COL_PLAYER_ID + CLOSING_BRACKET + CLOSING_BRACKET_SEMICOLON;

        final String SQL_CREATE_TEAM_TABLE = CREATE_TABLE + TeamEntry.TABLE_NAME + OPENING_BRACKET +
                TeamEntry._ID + INTEGER_TYPE + UNIQUE + COMMA_SEP +
                TeamEntry.COL_TEAM_ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                TeamEntry.COL_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                TeamEntry.COL_IMAGE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                TeamEntry.COL_SYMBOL + TEXT_TYPE + NOT_NULL + CLOSING_BRACKET_SEMICOLON;

        final String SQL_CREATE_PLAYER_TABLE = CREATE_TABLE + PlayerEntry.TABLE_NAME + OPENING_BRACKET +
                PlayerEntry._ID + INTEGER_TYPE + UNIQUE + COMMA_SEP +
                PlayerEntry.COL_PLAYER_ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                PlayerEntry.COL_NAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                PlayerEntry.COL_IMAGE + TEXT_TYPE + NOT_NULL + UNIQUE + COMMA_SEP +
                PlayerEntry.COL_TYPE + TEXT_TYPE + NOT_NULL + CLOSING_BRACKET_SEMICOLON;

        final String SQL_CREATE_TEAM_HAS_PLAYER_TABLE = CREATE_TABLE + TeamHasPlayerEntry.TABLE_NAME + OPENING_BRACKET +
                TeamHasPlayerEntry._ID + INTEGER_TYPE + PRIMARY_KEY + COMMA_SEP +
                TeamHasPlayerEntry.COL_TEAM_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                TeamHasPlayerEntry.COL_PLAYER_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +

                FOREIGN_KEY + TeamHasPlayerEntry.COL_TEAM_ID + REFERENCES +
                TeamEntry.TABLE_NAME + OPENING_BRACKET + TeamEntry.COL_TEAM_ID + CLOSING_BRACKET_COMMA +

                FOREIGN_KEY + TeamHasPlayerEntry.COL_PLAYER_ID + REFERENCES +
                PlayerEntry.TABLE_NAME + OPENING_BRACKET + PlayerEntry.COL_PLAYER_ID + CLOSING_BRACKET + CLOSING_BRACKET_SEMICOLON;

        String[] createStmts = new String[]{
                SQL_CREATE_MATCH_TABLE,
                SQL_CREATE_SCORE_TABLE,
                SQL_CREATE_RUN_TABLE,
                SQL_CREATE_WICKET_TABLE,
                SQL_CREATE_TEAM_TABLE,
                SQL_CREATE_PLAYER_TABLE,
                SQL_CREATE_TEAM_HAS_PLAYER_TABLE
        };

        for (String createStmt : createStmts) {
            db.execSQL(createStmt);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data
        // Simply discard the data and start over

        String[] tables = new String[]{
                ScoreEntry.TABLE_NAME,
                ScoreEntry.TABLE_NAME,
                RunEntry.TABLE_NAME,
                WicketEntry.TABLE_NAME,
                TeamEntry.TABLE_NAME,
                PlayerEntry.TABLE_NAME,
                TeamHasPlayerEntry.TABLE_NAME
        };

        for (String table : tables) {
            db.execSQL(DROP_TABLE_IF_EXISTS + table);
        }
        onCreate(db);
    }
}
