package com.example.shubham.sixfourfantasy.data.jsonmodel;

import com.example.shubham.sixfourfantasy.data.model.Team;

public class PlayersByMatch {
    public MatchPlayers playersInMatch;

    public static class MatchPlayers {
        public Team homeTeam;
        public Team awayTeam;
    }
}
