package com.example.shubham.sixfourfantasy.matches;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.shubham.sixfourfantasy.MyApplication;
import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.ViewModelHolderFragment;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.MatchStatus;
import com.example.shubham.sixfourfantasy.data.sync.MatchesSyncUtils;
import com.example.shubham.sixfourfantasy.matchdetail.MatchDetailActivity;
import com.example.shubham.sixfourfantasy.util.ActivityUtils;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import static com.example.shubham.sixfourfantasy.matches.MatchesFragment.MATCH_TYPE_KEY;

public class MatchesActivity extends AppCompatActivity implements MatchItemNavigator {

    public static final String EXTRA_MATCH = "extra_match";
    private static final int UPCOMING_MATCH_CLICK_EVENT_ID = 1;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_act);

        MatchesSyncUtils.initialize(getApplication());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    public MatchesViewModel attachViewModel(MatchesFragment matchesFragment) {
        return findOrCreateViewModel(matchesFragment.getArguments().getString(MATCH_TYPE_KEY));
    }

    private MatchesViewModel findOrCreateViewModel(String tag) {

        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolderFragment<MatchesViewModel> retainedViewModel =
                (ViewModelHolderFragment<MatchesViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(tag);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            MatchesViewModel viewModel = new MatchesViewModel(MatchesActivity.this,
                    MatchStatus.valueOf(tag),
                    getSupportLoaderManager(),
                    ((MyApplication) getApplication()).getMatchesRepositoryComponent().getMatchesRepository());
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolderFragment.createContainer(viewModel),
                    tag);
            return viewModel;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private static final int LIVE_TAB_INDEX = 0;
        private static final int RESULTS_TAB_INDEX = 1;
        private static final int UPCOMING_TAB_INDEX = 2;


        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
            initialize();
        }

        private void initialize() {
            mFragmentTitleList.add(LIVE_TAB_INDEX, getString(R.string.live_tab_title));
            mFragmentTitleList.add(RESULTS_TAB_INDEX, getString(R.string.results_tab_title));
            mFragmentTitleList.add(UPCOMING_TAB_INDEX, getString(R.string.upcoming_tab_title));
        }

        @Override
        public Fragment getItem(int position) {
            return MatchesFragment.newInstance(getMatchTypeForPosition(position));
        }

        private MatchStatus getMatchTypeForPosition(int position) {
            switch (position) {
                case LIVE_TAB_INDEX:
                    return MatchStatus.LIVE;
                case RESULTS_TAB_INDEX:
                    return MatchStatus.COMPLETED;
                case UPCOMING_TAB_INDEX:
                    return MatchStatus.UPCOMING;
                default:
                    throw new IndexOutOfBoundsException("Position " + position + "is not implemented");
            }
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void openMatchDetails(Match match, View summaryView) {
        if (match.status != MatchStatus.UPCOMING) {
            Intent intent = new Intent(this, MatchDetailActivity.class);
            intent.putExtra(EXTRA_MATCH, match);

            ActivityOptionsCompat activityOptions =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                            new Pair<>(summaryView, getString(R.string.score_summary_transition_name)));

            startActivity(intent, activityOptions.toBundle());
        }else {
            // Log the event to Firebase Analytics
            Bundle bundle = new Bundle();
            bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, UPCOMING_MATCH_CLICK_EVENT_ID);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        }
    }

}
