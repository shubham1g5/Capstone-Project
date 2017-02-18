package com.example.shubham.sixfourfantasy.matchdetail;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.example.shubham.sixfourfantasy.data.model.WicketsCard;

public class WicketCardViewModel extends BaseObservable {

    private final ObservableField<String> player = new ObservableField<>();
    private final ObservableField<Integer> runs = new ObservableField<>();
    private final ObservableField<Double> overs = new ObservableField<>();
    private final ObservableField<Integer> maiden = new ObservableField<>();
    private final ObservableField<Integer> wickets = new ObservableField<>();
    private final ObservableField<Double> economy = new ObservableField<>();

    private final ObservableField<WicketsCard> mWicketsCardObservable = new ObservableField<>();

    public WicketCardViewModel() {
        mWicketsCardObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                WicketsCard wicketsCard = mWicketsCardObservable.get();
                player.set("R Dravid");
                runs.set(wicketsCard.runs);
                overs.set(wicketsCard.overs);
                maiden.set(wicketsCard.maiden);
                wickets.set(wicketsCard.wickets);
                economy.set(wicketsCard.economy);
            }
        });
    }

    public void setWicketsCard(WicketsCard wicketsCard) {
        mWicketsCardObservable.set(wicketsCard);
    }
}
