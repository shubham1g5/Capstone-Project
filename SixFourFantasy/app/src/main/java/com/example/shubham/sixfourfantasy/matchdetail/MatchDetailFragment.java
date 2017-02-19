package com.example.shubham.sixfourfantasy.matchdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shubham.sixfourfantasy.databinding.MatchDetailFragBinding;

import java.util.ArrayList;

public class MatchDetailFragment extends Fragment implements MatchDetailViewModel.OnStartFinishedCallback {
    private MatchDetailViewModel mMatchDetailViewModel;
    private MatchDetailFragBinding mMatchDetailFragBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMatchDetailFragBinding = MatchDetailFragBinding.inflate(inflater, container, false);
        mMatchDetailFragBinding.setViewmodel(mMatchDetailViewModel);
        View root = mMatchDetailFragBinding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupListAdapter(mMatchDetailFragBinding.runcard1Recyclerview, new MatchRunsAdapter(new ArrayList<>(0)));
        setupListAdapter(mMatchDetailFragBinding.runcard2Recyclerview, new MatchRunsAdapter(new ArrayList<>(0)));
        setupListAdapter(mMatchDetailFragBinding.wicketcard1Recyclerview, new MatchWicketsAdapter(new ArrayList<>(0)));
        setupListAdapter(mMatchDetailFragBinding.wicketcard2Recyclerview, new MatchWicketsAdapter(new ArrayList<>(0)));
    }

    private void setupListAdapter(RecyclerView recyclerView, RecyclerView.Adapter listAdapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMatchDetailViewModel.start(this);
    }

    public void setmMatchDetailViewModel(MatchDetailViewModel matchDetailViewModel) {
        this.mMatchDetailViewModel = matchDetailViewModel;
    }

    @Override
    public void onStartFinished() {
        if (getActivity() != null) {
            getActivity().supportStartPostponedEnterTransition();
        }
    }
}
