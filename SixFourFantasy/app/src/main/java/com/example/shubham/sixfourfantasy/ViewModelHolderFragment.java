package com.example.shubham.sixfourfantasy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Non-UI Fragment used to retain ViewModels.
 */
public class ViewModelHolderFragment<VM> extends Fragment {

    private VM mViewModel;

    public ViewModelHolderFragment() { }

    public static <M> ViewModelHolderFragment createContainer(@NonNull M viewModel) {
        ViewModelHolderFragment<M> mViewModelHolder = new ViewModelHolderFragment<>();
        mViewModelHolder.setViewModel(viewModel);
        return mViewModelHolder;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable public VM getViewmodel() {
        return mViewModel;
    }

    public void setViewModel(@NonNull VM viewModel) {
        mViewModel = viewModel;
    }
}

