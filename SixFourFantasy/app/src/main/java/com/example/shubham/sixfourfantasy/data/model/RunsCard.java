package com.example.shubham.sixfourfantasy.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;
import com.squareup.moshi.Json;

public class RunsCard implements Parcelable {

    @Json(name = "id")
    public int playerId = -1;
    public int runs = -1;
    public int balls = -1;
    public int fours = -1;
    public int sixes = -1;
    public double strikeRate = 0.0;

    @Json(name = "fallOfWicket")
    public String fow;

    @Json(name = "howOut")
    public String out;

    public transient int matchId = -1;
    public transient int inningsNo = -1;

    public transient Player player;

    public RunsCard() {
    }

    protected RunsCard(Parcel in) {
        matchId = in.readInt();
        inningsNo = in.readInt();
        playerId = in.readInt();
        runs = in.readInt();
        balls = in.readInt();
        fours = in.readInt();
        sixes = in.readInt();
        strikeRate = in.readDouble();
        fow = in.readString();
        out = in.readString();
        player = in.readParcelable(Player.class.getClassLoader());
    }

    public static final Creator<RunsCard> CREATOR = new Creator<RunsCard>() {
        @Override
        public RunsCard createFromParcel(Parcel in) {
            return new RunsCard(in);
        }

        @Override
        public RunsCard[] newArray(int size) {
            return new RunsCard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(matchId);
        dest.writeInt(inningsNo);
        dest.writeInt(playerId);
        dest.writeInt(runs);
        dest.writeInt(balls);
        dest.writeInt(fours);
        dest.writeInt(sixes);
        dest.writeDouble(strikeRate);
        dest.writeString(fow);
        dest.writeString(out);
        dest.writeParcelable(player, 0);
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MatchesPersistenceContract.RunEntry.COL_MATCH_ID, matchId);
        values.put(MatchesPersistenceContract.RunEntry.COL_INNINGS_NO, inningsNo);
        values.put(MatchesPersistenceContract.RunEntry.COL_PLAYER_ID, playerId);
        values.put(MatchesPersistenceContract.RunEntry.COL_RUNS, runs);
        values.put(MatchesPersistenceContract.RunEntry.COL_BALLS, balls);
        values.put(MatchesPersistenceContract.RunEntry.COL_FOURS, fours);
        values.put(MatchesPersistenceContract.RunEntry.COL_SIXES, sixes);
        values.put(MatchesPersistenceContract.RunEntry.COL_STRIKE_RATE, strikeRate);
        values.put(MatchesPersistenceContract.RunEntry.COL_FOW, fow);
        values.put(MatchesPersistenceContract.RunEntry.COL_OUT, out);
        return values;
    }
}
