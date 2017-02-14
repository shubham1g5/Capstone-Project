package com.example.shubham.sixfourfantasy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

public class Inning implements Parcelable{

    @Json(name = "batsmen")
    public List<RunsCard> runCards;

    @Json(name = "bowlers")
    public List<WicketsCard> wicketCards;

    public Inning(List<RunsCard> runCards, List<WicketsCard> wicketCards){
        this.runCards = runCards;
        this.wicketCards = wicketCards;
    }

    protected Inning(Parcel in) {
        runCards = in.createTypedArrayList(RunsCard.CREATOR);
        wicketCards = in.createTypedArrayList(WicketsCard.CREATOR);
    }

    public static final Creator<Inning> CREATOR = new Creator<Inning>() {
        @Override
        public Inning createFromParcel(Parcel in) {
            return new Inning(in);
        }

        @Override
        public Inning[] newArray(int size) {
            return new Inning[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(runCards);
        dest.writeTypedList(wicketCards);
    }
}
