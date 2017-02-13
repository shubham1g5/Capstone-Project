package com.example.shubham.sixfourfantasy.data.jsonmodel;

import com.example.shubham.sixfourfantasy.data.model.Player;

import java.util.List;

public class PlayersByTeam {
    public TeamPlayers teamPlayers;

    public static class TeamPlayers {
        public List<Player> players;
    }
}
