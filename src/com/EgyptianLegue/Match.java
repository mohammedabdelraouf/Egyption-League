package com.EgyptianLegue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Match {
    int matchID;
    Date date;
    List<Team> teams;
    String footballReferee;
    int score;
    String stadiumName;
    score scoreTemp;
    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public String getFootballReferee() {
        return footballReferee;
    }

    public void setFootballReferee(String footballReferee) {
        this.footballReferee = footballReferee;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }

    public Match(int matchID, Date date, List<Team> teams, String footballReferee, score  score, String stadiumName) {
        this.matchID = matchID;
        this.date = date;
        this.teams = teams;
        this.footballReferee = footballReferee;
        this.scoreTemp = score;
        this.stadiumName = stadiumName;
    }


    // Method to enter match information
    public void enterMatchInformation(int matchID, Date date, List<Team> teams, String footballReferee, int score, String stadiumName) {
        this.matchID = matchID;
        this.date = date;
        this.teams = teams;
        this.footballReferee = footballReferee;
        this.score = score;
        this.stadiumName = stadiumName;
    }

    // Method to return match with details
    public String getMatchDetails() {
        return toString();
    }

    // Method to update match information
    public boolean updateMatch(String updateType, Object updateValue) {
        switch (updateType.toLowerCase()) {
            case "date": {
                return updateDate((Date) updateValue);
            }
            case "teams":
                return updateTeams((List<Team>) updateValue);
            case "football_referee":
                return updateFootballReferee((String) updateValue);
            case "score":
                return updateScore((score) updateValue);
            case "stadium_name":
                return updateStadiumName((String) updateValue);
            default:
                System.out.println("Invalid update type");
                return false;
        }
    }

    private boolean updateDate(Date newDate) {
        // Check if the new date is an upcoming date
        Date currentDate = new Date();
        if (newDate.before(currentDate)) {
            System.out.println("Error: The new date must be an upcoming date.");
            return false;
        }
        this.date = newDate;
        return true;
    }

    private boolean updateTeams(List<Team> newTeams) {
        this.teams = newTeams;
        return true;
    }

    private boolean updateFootballReferee(String newReferee) {
        this.footballReferee = newReferee;
        return true;
    }

    private boolean updateScore(score s) {
        // Add additional validation if needed
        this.scoreTemp.setTeam1(s.getTeam1());
        this.scoreTemp.setTeam2(s.getTeam2());
        this.scoreTemp.setWinner();
        return true;
    }

    private boolean updateStadiumName(String newStadiumName) {
        // Check stadium availability for the new date (if applicable)
        // Additional logic can be added based on your requirements
        this.stadiumName = newStadiumName;
        return true;
    }
    public static void setMatchById(List<Match> matches, int matchID,Match newMatch) {
        for (Match match : matches) {
            if (match.matchID == matchID) {
                match = newMatch;
                return; // Match found and updated, exit the loop
            }
        }
        System.out.println("Match not found.");
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);

        StringBuilder teamData = new StringBuilder();
        for (Team team : teams) {
            teamData.append(team.name).append("|");
        }
        StringBuilder scores = new StringBuilder();
        scores.append(this.scoreTemp.getTeam1()).append("|");
        scores.append(this.scoreTemp.getTeam2());
        teamData.deleteCharAt(teamData.length()-1);
        return String.format("%d,%s,%s,%s,%s,%s", matchID, formattedDate, teamData.toString(),footballReferee, scores, stadiumName);
    }

    public static Match fromString(String matchString) {
        String[] matchData = matchString.split(",");
        String[] teamStrings = matchData[2].split("\\|");
        List<Team> teams = new ArrayList<>();
        for (String teamString : teamStrings) {
            teams.add(new Team(teamString));
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(matchData[1]);
        } catch (ParseException e) {
            date = new Date(); // Handle parsing error by using the current date
        }
        String[] scores = matchData[4].split("\\|");
        score s = new score(teamStrings[0], Integer.parseInt(scores[0]),teamStrings[1],Integer.parseInt(scores[1]));
        return new Match(
                Integer.parseInt(matchData[0]),
                date,
                teams,
                matchData[3],
                s,
               matchData[5]
        );
    }

    public static void writeMatchesToFile(List<Match> matches, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Match match : matches) {
                writer.write(match.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file writing error
        }
    }

}

