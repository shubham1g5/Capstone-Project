package com.example.shubham.sixfourfantasy.data.jsonmodel;

import com.example.shubham.sixfourfantasy.data.model.MatchFormat;
import com.example.shubham.sixfourfantasy.data.model.MatchStatus;

public class MatchJson {
    public int id;
    public String name;
    public TeamJson awayTeam;
    public TeamJson homeTeam;
    public boolean isWomensMatch;
    public MatchStatus status;
    public VenueJson venue;
    public SeriesJson series;
    public String startDateTime;
    public int winningTeamId;
    public String matchSummaryText;
    public ScoresJson scores;
    public MatchFormat cmsMatchAssociatedType;
    public int matchTypeId;

    public static class VenueJson {
        public String name;
    }

    public static class SeriesJson {
        public int id;
        public String name;
    }

    public static class ScoresJson {
        public String awayScore;
        public String homeScore;
    }
}
