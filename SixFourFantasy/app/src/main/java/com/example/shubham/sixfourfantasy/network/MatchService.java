package com.example.shubham.sixfourfantasy.network;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.model.Match;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

interface MatchService    {

    @NonNull
    @GET("matches.php")
    Observable<Match> listMatches();
}
