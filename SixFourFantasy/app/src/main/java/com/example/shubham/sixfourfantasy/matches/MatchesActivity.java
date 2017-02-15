package com.example.shubham.sixfourfantasy.matches;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.ViewModelHolder;
import com.example.shubham.sixfourfantasy.data.sync.MatchesSyncUtils;
import com.example.shubham.sixfourfantasy.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public class MatchesActivity extends AppCompatActivity implements MatchItemNavigator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_act);

        MatchesSyncUtils.initialize(getApplication());

        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout))
                .setTitle(getString(R.string.app_name));

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(MatchesFragment.newInstance(), getString(R.string.live_tab_title));
        adapter.addFragment(MatchesFragment.newInstance(), getString(R.string.upcoming_tab_title));
        adapter.addFragment(MatchesFragment.newInstance(), getString(R.string.results_tab_title));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void openMatchDetails(int matchId) {
        Toast.makeText(this, "" + matchId, Toast.LENGTH_LONG).show();
    }

    private MatchesViewModel findOrCreateViewModel() {
        // In a configuration change we might have a ViewModel present. It's retained using the
        // Fragment Manager.
        @SuppressWarnings("unchecked")
        ViewModelHolder<MatchesViewModel> retainedViewModel =
                (ViewModelHolder<MatchesViewModel>) getSupportFragmentManager()
                        .findFragmentByTag(MATCHES_VIEWMODEL_TAG);

        if (retainedViewModel != null && retainedViewModel.getViewmodel() != null) {
            // If the model was retained, return it.
            return retainedViewModel.getViewmodel();
        } else {
            // There is no ViewModel yet, create it.
            MatchesViewModel viewModel = new MatchesViewModel(
                    Injection.provideMatchesRepository(getApplicationContext()),
                    getApplicationContext(),
                    this);
            // and bind it to this Activity's lifecycle using the Fragment Manager.
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    ViewModelHolder.createContainer(viewModel),
                    MATCHES_VIEWMODEL_TAG);
            return viewModel;
        }
    }

}
