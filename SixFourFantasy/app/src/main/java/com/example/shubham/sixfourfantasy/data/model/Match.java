package com.example.shubham.sixfourfantasy.data.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.icu.util.TimeUnit;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;
import com.example.shubham.sixfourfantasy.util.TimeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_FORMAT_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_IS_ABONDONE_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_IS_WOMEN_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_MATCH_ID_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_NAME_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_RESULT_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_SERIES_ID_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_SERIES_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_START_TIME_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_STATUS_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_TEAM1_ID_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_TEAM1_SCORE_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_TEAM2_ID_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_TEAM2_SCORE_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_VENUE_INDEX;
import static com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract.MatchEntry.COL_WINNING_TEAM_ID_INDEX;

public class Match implements Parcelable {

    private static final int MAX_INTERNATIONAL_TEAM_ID = 10;
    private static final int MIN_INTERNATIONAL_TEAM_ID = 1;

    public int matchId;
    public String name;
    public Team team1;
    public Team team2;
    public boolean isWomen;
    public MatchStatus status;
    public String venue;
    public String series;
    public String startTime;
    public int winningTeamId;
    public String result;
    public String team1Score;
    public String team2Score;
    public MatchFormat format;
    public int matchTypeId;
    public int seriesId;
    public boolean isAbandoned;

    public transient List<Inning> innings;


    public Match() {
        // Empty Constructor for MoshiJsonAdapter
    }

    protected Match(Parcel in) {
        matchId = in.readInt();
        name = in.readString();
        team1 = in.readParcelable(Team.class.getClassLoader());
        team2 = in.readParcelable(Team.class.getClassLoader());
        isWomen = in.readByte() != 0;
        status = MatchStatus.valueOf(in.readString());
        venue = in.readString();
        series = in.readString();
        startTime = in.readString();
        winningTeamId = in.readInt();
        result = in.readString();
        team1Score = in.readString();
        team2Score = in.readString();
        format = MatchFormat.valueOf(in.readString());
        matchTypeId = in.readInt();
        seriesId = in.readInt();
        in.readTypedList(innings, Inning.CREATOR);
        isAbandoned = in.readInt() == 1;

    }

    public static final Creator<Match> CREATOR = new Creator<Match>() {
        @Override
        public Match createFromParcel(Parcel in) {
            return new Match(in);
        }

        @Override
        public Match[] newArray(int size) {
            return new Match[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(matchId);
        dest.writeString(name);
        dest.writeParcelable(team1, flags);
        dest.writeParcelable(team2, flags);
        dest.writeByte((byte) (isWomen ? 1 : 0));
        dest.writeString(status.toString());
        dest.writeString(venue);
        dest.writeString(series);
        dest.writeString(startTime);
        dest.writeInt(winningTeamId);
        dest.writeString(result);
        dest.writeString(team1Score);
        dest.writeString(team2Score);
        dest.writeString(format.toString());
        dest.writeInt(matchTypeId);
        dest.writeInt(seriesId);
        dest.writeTypedList(innings);
        dest.writeInt(isAbandoned ? 1 : 0);
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MatchesPersistenceContract.MatchEntry.COL_MATCH_ID, matchId);
        values.put(MatchesPersistenceContract.MatchEntry.COL_NAME, name);
        values.put(MatchesPersistenceContract.MatchEntry.COL_TEAM1_ID, team1.teamId);
        values.put(MatchesPersistenceContract.MatchEntry.COL_TEAM2_ID, team2.teamId);
        values.put(MatchesPersistenceContract.MatchEntry.COL_IS_WOMEN, isWomen);
        values.put(MatchesPersistenceContract.MatchEntry.COL_STATUS, status.toString());
        values.put(MatchesPersistenceContract.MatchEntry.COL_VENUE, venue);
        values.put(MatchesPersistenceContract.MatchEntry.COL_SERIES, series);
        values.put(MatchesPersistenceContract.MatchEntry.COL_START_TIME, startTime);
        values.put(MatchesPersistenceContract.MatchEntry.COL_WINNING_TEAM_ID, winningTeamId);
        values.put(MatchesPersistenceContract.MatchEntry.COL_RESULT, result);
        values.put(MatchesPersistenceContract.MatchEntry.COL_TEAM1_SCORE, team1Score);
        values.put(MatchesPersistenceContract.MatchEntry.COL_TEAM2_SCORE, team2Score);
        values.put(MatchesPersistenceContract.MatchEntry.COL_FORMAT, format.toString());
        values.put(MatchesPersistenceContract.MatchEntry.COL_SERIES_ID, seriesId);
        values.put(MatchesPersistenceContract.MatchEntry.COL_SERIES_ID, seriesId);
        values.put(MatchesPersistenceContract.MatchEntry.COL_IS_ABONDONED, isAbandoned);
        return values;
    }



    private boolean isValidTeamId(int teamId) {
        return teamId >= MIN_INTERNATIONAL_TEAM_ID && teamId <= MAX_INTERNATIONAL_TEAM_ID;
    }

    public boolean isValid() throws ParseException {
        return isValidTeamId(team1.teamId) && isValidTeamId(team2.teamId) && TimeUtils.isInAMonth(startTime);
    }

    public static Match from(Cursor data) {
        Match match = new Match();

        match.matchId = data.getInt(COL_MATCH_ID_INDEX);
        match.name = data.getString(COL_NAME_INDEX);
        match.isWomen = data.getInt(COL_IS_WOMEN_INDEX) == 1;
        match.status = MatchStatus.valueOf(data.getString(COL_STATUS_INDEX));
        match.venue = data.getString(COL_VENUE_INDEX);
        match.series = data.getString(COL_SERIES_INDEX);
        match.startTime = data.getString(COL_START_TIME_INDEX);
        match.winningTeamId = data.getInt(COL_WINNING_TEAM_ID_INDEX);
        match.result = data.getString(COL_RESULT_INDEX);
        match.team1Score = data.getString(COL_TEAM1_SCORE_INDEX);
        match.team2Score = data.getString(COL_TEAM2_SCORE_INDEX);
        match.format = MatchFormat.valueOf(data.getString(COL_FORMAT_INDEX));
        match.seriesId = data.getInt(COL_SERIES_ID_INDEX);
        match.isAbandoned = data.getInt(COL_IS_ABONDONE_INDEX) == 1;

        return match;
    }
}
