package com.example.shubham.sixfourfantasy.matches;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.example.shubham.sixfourfantasy.data.model.Match;

import java.util.List;

/**
 * Contains {@link BindingAdapter}s for the {@link Match} list.
 */
public class MatchesListBindings {

    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, List<Match> items) {
        MatchesListAdapter adapter = (MatchesListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }
}
