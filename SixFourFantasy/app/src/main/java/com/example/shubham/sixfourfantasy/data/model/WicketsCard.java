package com.example.shubham.sixfourfantasy.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;
import com.squareup.moshi.Json;

public class WicketsCard implements Parcelable {

    @Json(name = "id")
    public int playerId = -1;
    public int runs;
    public double overs;
    public int maiden;
    public int wickets;
    public double economy;
    public int noBalls;
    public int wides;

    public transient int matchId = -1;
    public transient int inningsNo = -1;

    public transient Player player;

    public WicketsCard() {
    }

    protected WicketsCard(Parcel in) {
        matchId = in.readInt();
        inningsNo = in.readInt();
        playerId = in.readInt();
        runs = in.readInt();
        overs = in.readDouble();
        maiden = in.readInt();
        wickets = in.readInt();
        economy = in.readDouble();
        noBalls = in.readInt();
        wides = in.readInt();
        player = in.readParcelable(Player.class.getClassLoader());
    }

    public static final Creator<WicketsCard> CREATOR = new Creator<WicketsCard>() {
        @Override
        public WicketsCard createFromParcel(Parcel in) {
            return new WicketsCard(in);
        }

        @Override
        public WicketsCard[] newArray(int size) {
            return new WicketsCard[size];
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
        dest.writeDouble(overs);
        dest.writeInt(maiden);
        dest.writeInt(wickets);
        dest.writeDouble(economy);
        dest.writeInt(noBalls);
        dest.writeInt(wides);
        dest.writeParcelable(player, 0);
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MatchesPersistenceContract.WicketEntry.COL_MATCH_ID, matchId);
        values.put(MatchesPersistenceContract.WicketEntry.COL_INNINGS_NO, inningsNo);
        values.put(MatchesPersistenceContract.WicketEntry.COL_PLAYER_ID, playerId);
        values.put(MatchesPersistenceContract.WicketEntry.COL_RUNS, runs);
        values.put(MatchesPersistenceContract.WicketEntry.COL_OVERS, overs);
        values.put(MatchesPersistenceContract.WicketEntry.COL_MAIDEN, maiden);
        values.put(MatchesPersistenceContract.WicketEntry.COL_WICKETS, wickets);
        values.put(MatchesPersistenceContract.WicketEntry.COL_ECONOMY, economy);
        values.put(MatchesPersistenceContract.WicketEntry.COL_NO_BALLS, noBalls);
        values.put(MatchesPersistenceContract.WicketEntry.COL_WIDE_BALLS, wides);
        return values;
    }
}
