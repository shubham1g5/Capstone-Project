package com.example.shubham.sixfourfantasy.matchdetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.example.shubham.sixfourfantasy.MyApplication;
import com.example.shubham.sixfourfantasy.ViewModelHolderFragment;
import com.example.shubham.sixfourfantasy.util.ActivityUtils;

import static com.example.shubham.sixfourfantasy.matches.MatchesActivity.EXTRA_MATCH;

public class MatchDetailActivity extends AppCompatActivity {

    private static final String DETAILFRAGMENT_TAG = "detailFragment";
    private static final String MATCHDETAIL_VIEWMODEL_TAG = "detailVM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MatchDetailFragment detailFragment = findOrCreateViewFragment();
        MatchDetailViewModel detailVM = findOrCreateViewModel();
        detailFragment.setmMatchDetailViewModel(detailVM);
        detailVM.setMatch(getIntent().getParcelableExtra(EXTRA_MATCH));
    }

    @NonNull
    private MatchDetailFragment findOrCreateViewFragment() {

        MatchDetailFragment matchDetailFragment = (MatchDetailFragment) getSupportFragmentManager()
                .findFragmentById(android.R.id.content);

        if (matchDetailFragment == null) {
            matchDetailFragment = new MatchDetailFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    matchDetailFragment, android.R.id.content);
        }
        return matchDetailFragment;
    }

    @NonNull
    private MatchDetailViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolderFragment<MatchDetailViewModel> retainedViewModel =
                (ViewModelHolderFragment<MatchDetailViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(MATCHDETAIL_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            MatchDetailViewModel viewModel = new MatchDetailViewModel(
                    getApplicationContext(),
                    ((MyApplication) getApplication()).getMatchesRepositoryComponent().getMatchesRepository());

            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolderFragment.createContainer(viewModel),
                    MATCHDETAIL_VIEWMODEL_TAG);
            return viewModel;
        }
    }
}
