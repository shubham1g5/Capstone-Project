package com.example.shubham.sixfourfantasy.matches;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.shubham.sixfourfantasy.BindingHolder;
import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.databinding.MatchItemBinding;

import java.util.List;

public class MatchesListAdapter extends RecyclerView.Adapter<BindingHolder> {

    private final MatchItemNavigator mMatchItemNavigator;

    private List<Match> mMatches;

    public MatchesListAdapter(List<Match> matches, MatchItemNavigator matchItemNavigator) {
        mMatchItemNavigator = matchItemNavigator;
        setList(matches);
    }

    public void replaceData(List<Match> Matches) {
        setList(Matches);
    }


    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new BindingHolder(
                MatchItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        Match match = mMatches.get(position);
        MatchItemViewModel matchItemViewModel = new MatchItemViewModel(mMatchItemNavigator);
        matchItemViewModel.setMatch(match);
        holder.bind(matchItemViewModel);
        ViewCompat.setTransitionName(((MatchItemBinding) holder.getBinding()).matchSummaryCard,
                ((MatchesActivity) mMatchItemNavigator).getString(R.string.score_summary_transition_name) + position);
    }

    @Override
    public long getItemId(int i) {
        return mMatches.get(i).matchId;
    }

    @Override
    public int getItemCount() {
        return mMatches != null ? mMatches.size() : 0;
    }


    private void setList(List<Match> Matches) {
        mMatches = Matches;
        notifyDataSetChanged();
    }
}
