package com.example.shubham.sixfourfantasy.matches;

import android.view.View;

import com.example.shubham.sixfourfantasy.data.model.Match;

public interface MatchItemNavigator {
    void openMatchDetails(Match match, View summaryView);
}
