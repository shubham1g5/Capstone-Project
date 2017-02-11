package com.example.shubham.sixfourfantasy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Match implements Parcelable {

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


    public Match() {
        // Empty Constructor for MatchJsonAdapter
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
    }
}
