package com.example.shubham.sixfourfantasy.data.sync;

import android.util.Log;

import com.example.shubham.sixfourfantasy.data.model.MatchFormat;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepository;
import com.example.shubham.sixfourfantasy.network.MatchService;
import com.example.shubham.sixfourfantasy.network.RetrofitServiceGenerator;

import rx.Observable;
import rx.schedulers.Schedulers;


public class MatchesSyncTask {

    private static final String TAG = MatchesSyncTask.class.getSimpleName();

    public static void syncMatches(MatchesRepository matchesRepository) {

        matchesRepository.refreshTasks();
        matchesRepository.getMatches().subscribe(
                matches -> {

                },
                Throwable::printStackTrace
        );
//        MatchService movieService = RetrofitServiceGenerator.createService(MatchService.class);
//        movieService.listMatches()
//                .subscribeOn(Schedulers.io())
//                .map(matchResponse -> matchResponse.matchList.matches)
//                .flatMap(matches -> Observable.from(matches))
//                .filter(match -> match.matchTypeId == 0 && match.format != MatchFormat.Test)
//                .subscribe(match -> {
//                            matchesRepository.saveMatch(match);
//                        },
//                        Throwable::printStackTrace,
//                        () -> Log.d(TAG, "Successfully updated DB with latest match list"));
    }
}
