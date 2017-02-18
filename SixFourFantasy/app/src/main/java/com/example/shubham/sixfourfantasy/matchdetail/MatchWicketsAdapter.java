package com.example.shubham.sixfourfantasy.matchdetail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.shubham.sixfourfantasy.BindingHolder;
import com.example.shubham.sixfourfantasy.data.model.WicketsCard;
import com.example.shubham.sixfourfantasy.databinding.MatchWicketItemBinding;
import com.example.shubham.sixfourfantasy.databinding.ScoreHeaderItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MatchWicketsAdapter extends RecyclerView.Adapter<BindingHolder> {

    private static final int WICKETCARD_HEADER_ITEM_VIEW_TYPE = 1;
    private static final int WICKETCARD_ITEM_VIEW_TYPE = 2;
    private List<WicketsCard> mWicketsCards;

    public MatchWicketsAdapter(@NotNull List<WicketsCard> wicketsCards) {
        setWicketCards(wicketsCards);
    }

    public void replaceData(@NotNull List<WicketsCard> wicketsCards) {
        setWicketCards(wicketsCards);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return WICKETCARD_HEADER_ITEM_VIEW_TYPE;
        } else {
            return WICKETCARD_ITEM_VIEW_TYPE;
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case WICKETCARD_HEADER_ITEM_VIEW_TYPE:
                return new BindingHolder(ScoreHeaderItemBinding.inflate(inflater, parent, false));
            case WICKETCARD_ITEM_VIEW_TYPE:
                return new BindingHolder(MatchWicketItemBinding.inflate(inflater, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        switch (getItemViewType(position)) {
            case WICKETCARD_HEADER_ITEM_VIEW_TYPE:
                ScoreHeaderViewModel scoreHeaderViewModel = new ScoreHeaderViewModel(ScoreHeaderViewModel.WICKET_CARD_HEADER_TYPE);
                holder.bind(scoreHeaderViewModel);
                break;
            case WICKETCARD_ITEM_VIEW_TYPE:
                WicketsCard wicketsCard = mWicketsCards.get(position - 1);
                WicketCardViewModel wicketCardViewModel = new WicketCardViewModel();
                wicketCardViewModel.setWicketsCard(wicketsCard);
                holder.bind(wicketCardViewModel);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mWicketsCards.size() + 1;
    }

    public void setWicketCards(List<WicketsCard> wicketsCards) {
        mWicketsCards = wicketsCards;
        notifyDataSetChanged();
    }
}
