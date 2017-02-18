package com.example.shubham.sixfourfantasy.matchdetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.shubham.sixfourfantasy.BindingHolder;
import com.example.shubham.sixfourfantasy.data.model.RunsCard;
import com.example.shubham.sixfourfantasy.databinding.MatchRunItemBinding;
import com.example.shubham.sixfourfantasy.databinding.ScoreHeaderItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MatchRunsAdapter extends RecyclerView.Adapter<BindingHolder> {

    private static final int RUNCARD_HEADER_ITEM_VIEW_TYPE = 1;
    private static final int RUNCARD_ITEM_VIEW_TYPE = 2;
    private List<RunsCard> mRunsCards;

    public MatchRunsAdapter(@NotNull List<RunsCard> runsCards) {
        setRunCards(runsCards);
    }

    public void replaceData(@NotNull List<RunsCard> runsCards) {
        setRunCards(runsCards);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return RUNCARD_HEADER_ITEM_VIEW_TYPE;
        } else {
            return RUNCARD_ITEM_VIEW_TYPE;
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case RUNCARD_HEADER_ITEM_VIEW_TYPE:
                return new BindingHolder(ScoreHeaderItemBinding.inflate(inflater, parent, false));
            case RUNCARD_ITEM_VIEW_TYPE:
                return new BindingHolder(MatchRunItemBinding.inflate(inflater, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        switch (getItemViewType(position)) {
            case RUNCARD_HEADER_ITEM_VIEW_TYPE:
                ScoreHeaderViewModel scoreHeaderViewModel = new ScoreHeaderViewModel(ScoreHeaderViewModel.RUN_CARD_HEADER_TYPE);
                holder.bind(scoreHeaderViewModel);
                break;
            case RUNCARD_ITEM_VIEW_TYPE:
                RunsCard runsCard = mRunsCards.get(position - 1);
                RunCardViewModel runCardViewModel = new RunCardViewModel();
                runCardViewModel.setRunsCard(runsCard);
                holder.bind(runCardViewModel);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mRunsCards.size() + 1;
    }

    public void setRunCards(List<RunsCard> runsCards) {
        mRunsCards = runsCards;
        notifyDataSetChanged();
    }
}
