package com.example.shubham.sixfourfantasy.matchdetail;

import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.shubham.sixfourfantasy.BR;
import com.example.shubham.sixfourfantasy.MatchViewModel;
import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.RunsCard;
import com.example.shubham.sixfourfantasy.data.model.WicketsCard;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepository;
import com.google.firebase.crash.FirebaseCrash;


public class MatchDetailViewModel extends MatchViewModel {
    private final MatchesRepository mMatchesRepository;

    public final ObservableList<RunsCard> runsCards1 = new ObservableArrayList<>();
    public final ObservableList<RunsCard> runsCards2 = new ObservableArrayList<>();
    public final ObservableList<WicketsCard> wicketsCards1 = new ObservableArrayList<>();
    public final ObservableList<WicketsCard> wicketsCards2 = new ObservableArrayList<>();

    public MatchDetailViewModel(MatchesRepository matchesRepository) {
        super();
        mMatchesRepository = matchesRepository;
    }

    @Bindable
    public boolean isEmptyRunsCards1() {
        return runsCards1.isEmpty();
    }

    @Bindable
    public boolean isEmptyRunsCards2() {
        return runsCards2.isEmpty();
    }

    @Bindable
    public boolean isEmptyWicketsCards1() {
        return wicketsCards1.isEmpty();
    }

    @Bindable
    public boolean isEmptyWicketsCards2() {
        return wicketsCards2.isEmpty();
    }


    public void start(OnStartFinishedCallback onStartFinishedCallback) {
        Match match = getMatch();
        mMatchesRepository.getScoresForMatch(match)
                .doOnNext(innings -> match.innings = innings)
                .subscribe(innings -> {
                            if (innings.size() > 0) {
                                setScores(1, innings.get(0));
                            }

                            if (innings.size() > 1) {
                                setScores(2, innings.get(1));
                            }
                            onStartFinishedCallback.onStartFinished();
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            // reporting it to Firebase
                            FirebaseCrash.report(throwable);
                        });
    }

    private void setScores(int inningNo, Inning inning) {
        switch (inningNo) {
            case 1:
                runsCards1.clear();
                runsCards1.addAll(inning.runCards);
                notifyPropertyChanged(BR.emptyRunsCards1);

                wicketsCards1.clear();
                wicketsCards1.addAll(inning.wicketCards);
                notifyPropertyChanged(BR.emptyWicketsCards1);
                break;
            case 2:

                runsCards2.clear();
                runsCards2.addAll(inning.runCards);
                notifyPropertyChanged(BR.emptyRunsCards2);

                wicketsCards2.clear();
                wicketsCards2.addAll(inning.wicketCards);
                notifyPropertyChanged(BR.emptyWicketsCards2);
                break;
            default:
                break;
        }
    }

    public interface OnStartFinishedCallback {
        void onStartFinished();
    }
}
