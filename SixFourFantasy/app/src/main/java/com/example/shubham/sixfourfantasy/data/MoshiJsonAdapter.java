package com.example.shubham.sixfourfantasy.data;

import com.example.shubham.sixfourfantasy.data.jsonmodel.InningJson;
import com.example.shubham.sixfourfantasy.data.jsonmodel.MatchJson;
import com.example.shubham.sixfourfantasy.data.jsonmodel.MatchTeam;
import com.example.shubham.sixfourfantasy.data.model.Inning;
import com.example.shubham.sixfourfantasy.data.model.Match;
import com.example.shubham.sixfourfantasy.data.model.RunsCard;
import com.example.shubham.sixfourfantasy.data.model.Team;
import com.example.shubham.sixfourfantasy.data.model.WicketsCard;
import com.squareup.moshi.FromJson;

public class MoshiJsonAdapter {

    @FromJson
    Match matchFromJson(MatchJson matchJson) {
        Match match = new Match();
        match.matchId = matchJson.id;
        match.name = matchJson.name;
        match.isWomen = matchJson.isWomensMatch;
        match.status = matchJson.status;
        match.venue = matchJson.venue.name;
        match.series = matchJson.series.name;
        match.startTime = matchJson.startDateTime;
        match.winningTeamId = matchJson.winningTeamId;
        match.result = matchJson.matchSummaryText;
        match.team1Score = matchJson.scores != null ? matchJson.scores.homeScore : null;
        match.team2Score = matchJson.scores != null ? matchJson.scores.awayScore : null;
        match.team1 = new Team(matchJson.homeTeam.id, matchJson.homeTeam.name, matchJson.homeTeam.logoUrl, matchJson.homeTeam.shortName, null);
        match.team2 = new Team(matchJson.awayTeam.id, matchJson.awayTeam.name, matchJson.awayTeam.logoUrl, matchJson.awayTeam.shortName, null);
        match.matchTypeId = matchJson.matchTypeId;
        match.format = matchJson.cmsMatchAssociatedType;
        match.seriesId = matchJson.series.id;
        match.isAbandoned = matchJson.isMatchAbandoned;
        return match;
    }

    @FromJson
    Team teamFromMatchTeam(MatchTeam matchTeam) {
        Team team = new Team(matchTeam.team.id, matchTeam.team.name, matchTeam.team.logoUrl, matchTeam.team.shortName, matchTeam.players);
        return team;
    }

    @FromJson
    Inning inningFromInningJson(InningJson inningJson) {
        Inning inning = new Inning(inningJson.batsmen, inningJson.bowlers);
        for (RunsCard runCard : inning.runCards) {
            runCard.inningsNo = inningJson.id;
        }
        for (WicketsCard wicketCard : inning.wicketCards) {
            wicketCard.inningsNo = inningJson.id;
        }
        return inning;
    }

}
