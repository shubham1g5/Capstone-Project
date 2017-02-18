package com.example.shubham.sixfourfantasy.matchdetail;

import android.content.Context;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.example.shubham.sixfourfantasy.MatchViewModel;
import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.RunsCard;
import com.example.shubham.sixfourfantasy.data.model.WicketsCard;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepository;


public class MatchDetailViewModel extends MatchViewModel {
    private final MatchesRepository mMatchesRepository;
    private final Context mContext;

    public final ObservableList<RunsCard> runsCards1 = new ObservableArrayList<>();
    public final ObservableList<RunsCard> runsCards2 = new ObservableArrayList<>();
    public final ObservableList<WicketsCard> wicketsCards1 = new ObservableArrayList<>();
    public final ObservableList<WicketsCard> wicketsCards2 = new ObservableArrayList<>();

    public MatchDetailViewModel(Context context, MatchesRepository matchesRepository) {
        super();
        mMatchesRepository = matchesRepository;
        mContext = context.getApplicationContext();
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


    public void start() {
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
                        },
                        Throwable::printStackTrace);
    }

    private void setScores(int inningNo, Inning inning) {
        switch (inningNo){
//            case 1:
//                runsCards1.clear();
//                runsCards1.addAll(inning.runCards);
//                notifyPropertyChanged(BR.emptyRuncards1);
//
//                wicketsCards1.clear();
//                wicketsCards1.addAll(inning.wicketCards);
//                notifyPropertyChanged(BR.emptyWicketcards1);
//                break;
//            case 2:
//
//                runsCards2.clear();
//                runsCards2.addAll(inning.runCards);
//                notifyPropertyChanged(BR.emptyRuncards2);
//
//                wicketsCards2.clear();
//                wicketsCards2.addAll(inning.wicketCards);
//                notifyPropertyChanged(BR.emptyWicketcards2);
//                break;
//            default:
//                break;
        }
    }


}
