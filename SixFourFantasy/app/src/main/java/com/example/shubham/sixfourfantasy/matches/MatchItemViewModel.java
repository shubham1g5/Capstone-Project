package com.example.shubham.sixfourfantasy.matches;

import com.example.shubham.sixfourfantasy.MatchViewModel;

public class MatchItemViewModel extends MatchViewModel {

    private final MatchItemNavigator mMatchItemNavigator;

    public MatchItemViewModel(MatchItemNavigator itemNavigator) {
        super();
        mMatchItemNavigator = itemNavigator;
    }

    /**
     * Called by the Data Binding library when the row is clicked.
     */
    public void matchClicked() {
        int matchId = getMatchId();
        mMatchItemNavigator.openMatchDetails(matchId);
    }
}
