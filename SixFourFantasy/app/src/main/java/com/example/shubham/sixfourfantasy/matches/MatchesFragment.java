package com.example.shubham.sixfourfantasy.matches;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.databinding.MatchesFragBinding;

import java.util.ArrayList;

public class MatchesFragment extends Fragment {

    private MatchesViewModel mMatchesViewModel;

    private MatchesFragBinding mMatchesFragBinding;

    public static MatchesFragment newInstance() {
        return new MatchesFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMatchesViewModel.start();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMatchesFragBinding = MatchesFragBinding.inflate(inflater, container, false);
        mMatchesFragBinding.setView(this);
        mMatchesFragBinding.setViewmodel(mMatchesViewModel);
        View root = mMatchesFragBinding.getRoot();
        return root;
    }

    public void setViewModel(MatchesViewModel viewModel) {
        mMatchesViewModel = viewModel;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter();
        setupRefreshLayout();
    }


    private void setupListAdapter() {
        RecyclerView recyclerView = mMatchesFragBinding.matcheslistRecyclerview;
        GridLayoutManager gridLayoutManager =
                new GridLayoutManager(getActivity(),getResources().getInteger(R.integer.list_column_count));
        recyclerView.setLayoutManager(gridLayoutManager);

        MatchesListAdapter mListAdapter = new MatchesListAdapter(new ArrayList<>(0),(MatchItemNavigator) getActivity());
        recyclerView.setAdapter(mListAdapter);
    }

    private void setupRefreshLayout() {
        final SwipeRefreshLayout swipeRefreshLayout = mMatchesFragBinding.refreshLayout;
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
    }

}
