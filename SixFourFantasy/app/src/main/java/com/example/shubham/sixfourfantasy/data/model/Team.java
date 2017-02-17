package com.example.shubham.sixfourfantasy.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;

import java.util.List;

public class Team implements Parcelable {

    public int teamId;
    public String name;
    public String image;
    public String symbol;

    @Nullable
    public List<Player> players;

    public Team(int teamId){
        this.teamId = teamId;
    }

    public Team(int teamId, String name, String image, String symbol, List<Player> players) {
        this.teamId = teamId;
        this.name = name;
        this.image = image;
        this.symbol = symbol;
        this.players = players;
    }

    protected Team(Parcel in) {
        teamId = in.readInt();
        name = in.readString();
        image = in.readString();
        symbol = in.readString();
        in.readTypedList(players, Player.CREATOR);
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(teamId);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(symbol);
        dest.writeTypedList(players);
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MatchesPersistenceContract.TeamEntry.COL_TEAM_ID, teamId);
        values.put(MatchesPersistenceContract.TeamEntry.COL_NAME, name);
        values.put(MatchesPersistenceContract.TeamEntry.COL_IMAGE, image);
        values.put(MatchesPersistenceContract.TeamEntry.COL_SYMBOL, symbol);
        return values;
    }
}
