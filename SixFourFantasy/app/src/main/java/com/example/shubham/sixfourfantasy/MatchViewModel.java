package com.example.shubham.sixfourfantasy;

import android.databinding.BaseObservable;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.view.View;

import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.MatchStatus;
import com.example.shubham.sixfourfantasy.util.TimeUtils;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;

public class MatchViewModel extends BaseObservable {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> series = new ObservableField<>();
    public final ObservableField<String> team1 = new ObservableField<>();
    public final ObservableField<String> team2 = new ObservableField<>();
    public final ObservableField<String> team1Score = new ObservableField<>();
    public final ObservableField<String> team2Score = new ObservableField<>();
    public final ObservableField<String> venue = new ObservableField<>();
    public final ObservableField<String> result = new ObservableField<>();

    public final ObservableField<Boolean> resultAvailable = new ObservableField<>(false);

    private final ObservableField<Match> mMatchObservable = new ObservableField<>();

    public MatchViewModel() {

        mMatchObservable.addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                Match match = mMatchObservable.get();
                title.set(getTitle(match));
                series.set(match.series);
                team1.set(match.team1.symbol);
                team2.set(match.team2.symbol);
                team1Score.set(match.team1Score);
                team2Score.set(match.team2Score);
                result.set(match.result);
                venue.set(getTimeVenueString(match));
                resultAvailable.set(match.status != MatchStatus.UPCOMING);
            }

            private String getTitle(Match match) {
                String title = match.name;
                try {
                    title += "  ";
                    title += TimeUtils.getDisplayDate(match.startTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return title;
            }

            private String getTimeVenueString(Match match) {
                String timeVenueStr = match.venue;
                if (match.status == MatchStatus.UPCOMING) {
                    try {
                        timeVenueStr += "|";
                        timeVenueStr += TimeUtils.getTimeWithZ(match.startTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return timeVenueStr;
            }
        });
    }

    public void setMatch(@NotNull Match match) {
        mMatchObservable.set(match);
    }


    protected Match getMatch() {
        return mMatchObservable.get();
    }

    public void matchClicked(View view){
        // Do nothing by default
    }

}
