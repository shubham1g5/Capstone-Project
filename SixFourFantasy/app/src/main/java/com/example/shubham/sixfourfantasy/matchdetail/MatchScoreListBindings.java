package com.example.shubham.sixfourfantasy.matchdetail;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.shubham.sixfourfantasy.data.model.RunsCard;
import com.example.shubham.sixfourfantasy.data.model.WicketsCard;

import java.util.List;

public class MatchScoreListBindings {

    @BindingAdapter("app:runcards")
    public static void setRunCards(RecyclerView recyclerView, List<RunsCard> runsCards) {
        MatchRunsAdapter adapter = (MatchRunsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(runsCards);
        }
    }

    @BindingAdapter("app:wicketcards")
    public static void setWicketCards(RecyclerView recyclerView, List<WicketsCard> wicketsCards) {
        MatchWicketsAdapter adapter = (MatchWicketsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(wicketsCards);
        }
    }
}
