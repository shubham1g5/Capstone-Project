package com.example.shubham.sixfourfantasy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable{

    public final int matchId;
    public final String name;
    public final int team1Id;
    public final int team2Id;
    public final boolean isWomen;
    public final MatchStatus status;
    public final String venue;
    public final String series;
    public final String startTime;
    public final int winningTeamId;
    public final String result;
    public final String team1Score;
    public final String team2Score;

    public Match(int matchId,
                 String name,
                 int team1Id,
                 int team2Id,
                 boolean isWomen,
                 MatchStatus status,
                 String venue,
                 String series,
                 String startTime,
                 int winningTeamId,
                 String result,
                 String team1Score,
                 String team2Score) {
        this.matchId = matchId;
        this.name = name;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.isWomen = isWomen;
        this.status = status;
        this.venue = venue;
        this.series = series;
        this.startTime = startTime;
        this.winningTeamId = winningTeamId;
        this.result = result;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    protected Match(Parcel in) {
        matchId = in.readInt();
        name = in.readString();
        team1Id = in.readInt();
        team2Id = in.readInt();
        isWomen = in.readByte() != 0;
        status = MatchStatus.valueOf(in.readString());
        venue = in.readString();
        series = in.readString();
        startTime = in.readString();
        winningTeamId = in.readInt();
        result = in.readString();
        team1Score = in.readString();
        team2Score = in.readString();
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
        dest.writeInt(team1Id);
        dest.writeInt(team2Id);
        dest.writeByte((byte) (isWomen ? 1 : 0));
        dest.writeString(status.toString());
        dest.writeString(venue);
        dest.writeString(series);
        dest.writeString(startTime);
        dest.writeInt(winningTeamId);
        dest.writeString(result);
        dest.writeString(team1Score);
        dest.writeString(team2Score);
    }
}
