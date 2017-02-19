package com.example.shubham.sixfourfantasy.matchdetail;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;

import com.example.shubham.sixfourfantasy.data.model.RunsCard;

public class RunCardViewModel extends BaseObservable {

    public final ObservableField<String> player = new ObservableField<>();
    public final ObservableField<Integer> runs = new ObservableField<>();
    public final ObservableField<Integer> balls = new ObservableField<>();
    public final ObservableField<Integer> fours = new ObservableField<>();
    public final ObservableField<Integer> sixes = new ObservableField<>();
    public final ObservableField<Integer> strikeRate = new ObservableField<>();

    private final ObservableField<RunsCard> mRunsCardObservable = new ObservableField<>();

    public RunCardViewModel() {
        mRunsCardObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                RunsCard runsCard = mRunsCardObservable.get();
                player.set("" + runsCard.player.name);
                runs.set(runsCard.runs);
                balls.set(runsCard.balls);
                fours.set(runsCard.fours);
                sixes.set(runsCard.sixes);
                strikeRate.set(((Double) runsCard.strikeRate).intValue());
            }
        });
    }

    public void setRunsCard(RunsCard runsCard) {
        mRunsCardObservable.set(runsCard);
    }
}
