package com.example.shubham.sixfourfantasy.data.jsonmodel;

import com.example.shubham.sixfourfantasy.data.model.Inning;

import java.util.List;

public class ScoresByMatch {
    public FullScoreCard fullScorecard;

    public static class FullScoreCard {
        public List<Inning> innings;
    }
}
