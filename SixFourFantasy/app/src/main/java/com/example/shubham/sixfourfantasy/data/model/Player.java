package com.example.shubham.sixfourfantasy.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.shubham.sixfourfantasy.data.source.local.MatchesPersistenceContract;
import com.squareup.moshi.Json;

public class Player implements Parcelable {

    public String playerId;

    @Json(name = "fullName")
    public String name;

    @Nullable
    @Json(name = "imageURL")
    public String image;

    @Nullable
    @Json(name = "playerType")
    public PlayerType type;

    protected Player(Parcel in) {
        playerId = in.readString();
        name = in.readString();
        image = in.readString();
        type = PlayerType.valueOf(in.readString());
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playerId);
        dest.writeString(name);
        dest.writeString(image);
        if (type != null) {
            dest.writeString(type.toString());
        }
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(MatchesPersistenceContract.PlayerEntry.COL_PLAYER_ID, playerId);
        values.put(MatchesPersistenceContract.PlayerEntry.COL_NAME, name);
        values.put(MatchesPersistenceContract.PlayerEntry.COL_IMAGE, image);
        if (type != null) {
            values.put(MatchesPersistenceContract.PlayerEntry.COL_TYPE, type.toString());
        }
        return values;
    }
}
