package com.example.shubham.sixfourfantasy.network;

import android.support.annotation.NonNull;

import com.example.shubham.sixfourfantasy.data.jsonmodel.MatchResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface MatchService    {

    @NonNull
    @GET("matches.php")
    Observable<MatchResponse> listMatches();
}
