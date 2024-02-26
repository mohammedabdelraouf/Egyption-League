package com.EgyptianLegue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Team {
    protected String name;
    protected int teamID;
    protected List<Player> players;
    protected Player captain;
    protected List<Match> matches;
    protected int totalScore;

    public Team(String name, int teamID, List<Player> players, Player captain, List<Match> matches, int totalScore) {
        this.name = name;
        this.teamID = teamID;
        this.players = players;
        this.captain = captain;
        this.matches = matches;
        this.totalScore = totalScore;
    }

    public Team(String name, int teamID, Player captain, int totalScore) {
        this.name = name;
        this.teamID = teamID;
        this.captain = captain;
        this.totalScore = totalScore;
        this.players = new ArrayList<Player>();
        this.matches = new ArrayList<Match>();
    }
    public  Team (String name)
    {
        this.name = name;
        this.players = new ArrayList<>();
        this.matches= new ArrayList<>();
        this.captain = new Player();
    }

    public String getName() {
        return name;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTeamID() {
        return teamID;
    }
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public Player getCaptain() {
        return captain;
    }
    public void setCaptain(Player captain) {
        this.captain = captain;
    }
    public List<Match> getMatches() {
        return matches;
    }
    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
    public int getTotalScore() {
        return totalScore;
    }
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
    public void addPlayer(Player player) {
        players.add(player);
    }
    public void addMatch(Match match){matches.add(match);}


    // Method to update team information
    public void updateTeamInformation(String name, Player captain, int totalScore) {
        String oldName = this.getName();
        setName(name);
        setCaptain(captain);
        setTotalScore(totalScore);
        if(!name.equals(oldName)) {
            controller.L.updateMatchListTeamsName(name, oldName);
            controller.L.updatePlayersTeamName(name,oldName);
            this.updateMatchListTeamsName();
        }
    }

    // Method to return team information
    public String getTeamInformation() {
        return toString();
    }

    // Method to return team players
    public List<Player> getTeamPlayers() {
        return getPlayers();
    }

    // Method to display team matches as held and to be held
    public void displayTeamMatches(Date currentDate) {
        List<Match> heldMatches = new ArrayList<>();
        List<Match> toBeHeldMatches = new ArrayList<>();

        for (Match match : matches) {
            if (match.date.before(currentDate)) {
                heldMatches.add(match);
            } else {
                toBeHeldMatches.add(match);
            }
        }

        System.out.println("Held Matches:");
        for (Match heldMatch : heldMatches) {
            System.out.println(heldMatch);
        }

        System.out.println("\nTo be Held Matches:");
        for (Match toBeHeldMatch : toBeHeldMatches) {
            System.out.println(toBeHeldMatch);
        }
    }

    // Method to add player to the team
    public void addPlayerToTeam(Player player) {
        addPlayer(player);
        controller.L.addPlayer(player);
    }

    // Method to delete player from the team given the player’s name and ID
    public void deletePlayerFromTeam(String playerName, int playerID) {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player player = iterator.next();
            if (player.getName().equals(playerName) && player.getId() == playerID) {
                iterator.remove();
                break; // Assuming there is only one player with the same name and ID
            }
        }
    }
    public  void updateMatchListTeamsName()
    {
        List<Match> newList = new ArrayList<>();
        for(Match m : controller.L.matchList)
        {

            if(m.teams.get(0).getName().equals(this.getName()))
            {
                newList.add(m);

            }
            else if(m.teams.get(1).getName().equals(this.getName()))
            {
                newList.add(m);
            }

        }
        this.setMatches(newList);
    }
    // Method to calculate the total number of created teams
    public static int getTotalCreatedTeams(List<Team> teams) {
        return teams.size();
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%s,%d", name, teamID, captain.getName(), totalScore);
    }

    public static Team fromString(String teamString) {
        String[] teamData = teamString.split(",");

        return new Team(
                teamData[0],
                Integer.parseInt(teamData[1]),
                new Player(teamData[2]),
                Integer.parseInt(teamData[3])
        );
    }

    public static void writeTeamsToFile(List<Team> teams, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Team team : teams) {
                writer.write(team.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file writing error
        }
    }


}

