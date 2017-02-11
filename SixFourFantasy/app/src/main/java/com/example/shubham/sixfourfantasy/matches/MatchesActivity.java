package com.example.shubham.sixfourfantasy.matches;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockApplication;

import com.example.shubham.sixfourfantasy.MyApplication;
import com.example.shubham.sixfourfantasy.R;
import com.example.shubham.sixfourfantasy.data.sync.MatchesSyncTask;

public class MatchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matches_act);

        MatchesSyncTask.syncMatches(((MyApplication) getApplication()).getMatchesRepositoryComponent().getMatchesRepository());
    }
}
