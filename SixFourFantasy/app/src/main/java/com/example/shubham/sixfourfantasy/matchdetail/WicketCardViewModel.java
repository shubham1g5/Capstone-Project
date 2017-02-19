package com.example.shubham.sixfourfantasy.matchdetail;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.example.shubham.sixfourfantasy.data.model.WicketsCard;

public class WicketCardViewModel extends BaseObservable {

    public final ObservableField<String> player = new ObservableField<>();
    public final ObservableField<Integer> runs = new ObservableField<>();
    public final ObservableField<Double> overs = new ObservableField<>();
    public final ObservableField<Integer> maiden = new ObservableField<>();
    public final ObservableField<Integer> wickets = new ObservableField<>();
    public final ObservableField<Integer> economy = new ObservableField<>();

    private final ObservableField<WicketsCard> mWicketsCardObservable = new ObservableField<>();

    public WicketCardViewModel() {
        mWicketsCardObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                WicketsCard wicketsCard = mWicketsCardObservable.get();
                player.set("" + wicketsCard.player.name);
                runs.set(wicketsCard.runs);
                overs.set(wicketsCard.overs);
                maiden.set(wicketsCard.maiden);
                wickets.set(wicketsCard.wickets);
                economy.set(((Double) wicketsCard.economy).intValue());
            }
        });
    }

    public void setWicketsCard(WicketsCard wicketsCard) {
        mWicketsCardObservable.set(wicketsCard);
    }
}
