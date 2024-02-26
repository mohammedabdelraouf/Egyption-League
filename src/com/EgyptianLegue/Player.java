package com.EgyptianLegue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Player {
    private String name;
    private int id;
    private int number;
    private String team;
    private int age;
    private int score;
    private int rank;

    public Player() {

    }
    public  Player(String name){
        this.name = name ;
    }
    public Player(String name, int id, int number, String team, int age, int score, int rank) {
        this.name = name;
        this.id = id;
        this.number = number;
        this.team = team;
        this.age = age;
        this.score = score;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    @Override
    public String toString() {
        return String.format("%s,%d,%d,%s,%d,%d,%d", name, id, number, team, age, score, rank);
    }

    public static Player fromString(String playerString) {
        String[] playerData = playerString.split(",");
        return new Player(
                playerData[0],
                Integer.parseInt(playerData[1]),
                Integer.parseInt(playerData[2]),
                playerData[3],
                Integer.parseInt(playerData[4]),
                Integer.parseInt(playerData[5]),
                Integer.parseInt(playerData[6])
        );
    }
    public static void writePlayersToFile(List<Player> players, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Player player : players) {
                writer.write(player.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file writing error
        }
    }

    // Method to enter player information
    public void enterPlayerInformation(int id, int number, String team, int age, int score, int rank) {
        setId(id);
        setNumber(number);
        setTeam(team);
        setAge(age);
        setScore(score);
        setRank(rank);
    }

    // Method to return player information
    public String getPlayerInformation() {
        return toString();
    }

    // Method to update player information
    public void updatePlayerInformation(int number, String team, int age, int score, int rank) {
        setNumber(number);
        setTeam(team);
        setAge(age);
        setScore(score);
        setRank(rank);
    }
    // Method to update the player's number
    public void updateNumber(int number) {
        setNumber(number);
    }

    // Method to update the player's team
    public void updateTeam(String team) {
        setTeam(team);
    }

    // Method to update the player's age
    public void updateAge(int age) {
        setAge(age);
    }

    // Method to update the player's score
    public void updateScore(int score) {
        setScore(score);
    }

    // Method to update the player's rank
    public void updateRank(int rank) {
        setRank(rank);
    }

    // Method to search for a player using only player name
    public static Player searchPlayerByName(List<Player> players, String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null; // Player not found
    }

    public static Player searchPlayerByID(List<Player> players, int playerId) {
        for (Player player : players) {
            if (player.getId() == playerId){
                return player;
            }
        }
        return null; // Player not found
    }
    // Method to search for a player using name and team name
    public static Player searchPlayerByNameAndTeam(List<Player> players, String playerName, String teamName) {
        for (Player player : players) {
            if (player.getName().equals(playerName) && player.getTeam().equals(teamName)) {
                return player;
            }
        }
        return null; // Player not found
    }

}
