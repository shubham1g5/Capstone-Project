package com.example.shubham.sixfourfantasy.data.jsonmodel;

import com.example.shubham.sixfourfantasy.data.model.Match;

import java.util.List;

public class MatchResponse {
    public MatchList matchList;

    public static class MatchList {
        public List<Match> matches;
    }
}
