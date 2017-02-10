package com.example.shubham.sixfourfantasy.data.jsonadapters;

import com.example.shubham.sixfourfantasy.data.jsonmodels.MatchJson;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.squareup.moshi.FromJson;

public class MatchAdapter {

    @FromJson
    Match matchFromJson(MatchJson matchJson) {
//        Match match = new Match();
//        event.title = matchJson.title;
//        event.beginDateAndTime = matchJson.begin_date + " " + matchJson.begin_time;
//        return event;
    }
}
