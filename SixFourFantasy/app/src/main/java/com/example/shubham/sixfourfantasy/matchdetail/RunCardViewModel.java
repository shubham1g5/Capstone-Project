package com.example.shubham.sixfourfantasy.matchdetail;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.example.shubham.sixfourfantasy.data.model.RunsCard;

public class RunCardViewModel extends BaseObservable {

    private final ObservableField<String> player = new ObservableField<>();
    private final ObservableField<Integer> runs = new ObservableField<>();
    private final ObservableField<Integer> balls = new ObservableField<>();
    private final ObservableField<Integer> fours = new ObservableField<>();
    private final ObservableField<Integer> sixes = new ObservableField<>();
    private final ObservableField<Double> strikeRate = new ObservableField<>();

    private final ObservableField<RunsCard> mRunsCardObservable = new ObservableField<>();

    public RunCardViewModel() {
        mRunsCardObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                RunsCard runsCard = mRunsCardObservable.get();
                player.set("R Dravid");
                runs.set(runsCard.runs);
                balls.set(runsCard.balls);
                fours.set(runsCard.fours);
                sixes.set(runsCard.sixes);
                strikeRate.set(runsCard.strikeRate);
            }
        });
    }

    public void setRunsCard(RunsCard runsCard) {
        mRunsCardObservable.set(runsCard);
    }
}
