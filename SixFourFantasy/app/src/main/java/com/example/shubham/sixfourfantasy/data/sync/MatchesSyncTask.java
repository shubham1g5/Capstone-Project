package com.example.shubham.sixfourfantasy.data.sync;

import android.content.Context;

import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.MatchFormat;
import com.example.shubham.sixfourfantasy.data.source.DaggerMatchesRepositoryComponent;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepository;
import com.example.shubham.sixfourfantasy.data.source.MatchesRepositoryComponent;
import com.example.shubham.sixfourfantasy.network.MatchService;
import com.example.shubham.sixfourfantasy.network.RetrofitServiceGenerator;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

public class MatchesSyncTask {

    public static void syncMatches(MatchesRepository matchesRepository) {

        MatchService movieService = RetrofitServiceGenerator.createService(MatchService.class);
        movieService.listMatches()
                .subscribeOn(Schedulers.io())
                .map(matchResponse -> matchResponse.matchList.matches)
                .flatMap(matches -> Observable.from(matches))
                .filter(match -> match.matchTypeId == 0 && match.format != MatchFormat.Test)
                .subscribe(match -> matchesRepository.saveMatch(match),
                        Throwable::printStackTrace);
    }
}
