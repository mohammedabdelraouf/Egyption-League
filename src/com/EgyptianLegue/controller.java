package com.EgyptianLegue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public  class controller {
    public static League L = new League() ;

    public static void read_files(String teamsPath, String playersPath, String matchesPath)
    {
        L.setTeams(readTeamsFromFile(teamsPath));
        L.setPlayers(readPlayersFromFile(playersPath));
        L.setMatchList(readMatchesFromFile(matchesPath));
        for(Player p : L.getPlayers())
        {
            for(int i = 0 ; i < L.teams.size() ; i++)
            {
                Team t = L.teams.get(i);
                if(p.getTeam().equals(t.getName()))
                {
                    t.addPlayer(p);
                    L.teams.set(i, t);
                    break;
                }
            }
        }
        for(Match m : L.matchList)
        {

            for(int i = 0 ; i < L.teams.size() ; i++)
            {
                Team t = L.teams.get(i);
                if(m.teams.get(0).getName().equals(t.getName()))
                {
                    t.addMatch(m);

                }
                if(m.teams.get(1).getName().equals(t.getName()))
                {
                    t.addMatch(m);
                }
                L.teams.set(i, t);
            }

        }

    }
    public static void writeToFiles()
    {
        Player.writePlayersToFile(controller.L.getPlayers(),"F:/players.txt");
        Match.writeMatchesToFile(controller.L.getMatchList() , "F:/matches.txt");
        Team.writeTeamsToFile(controller.L.getTeams(),"F:/teams.txt");
    }
    public  static boolean isEmpty()
    {
        if(L.getPlayers()==null ||L.getTeams()==null ||L.getMatchList()==null)
            return true;
        else
            return false;
    }

    public static List<Player> readPlayersFromFile(String filePath) {
        List<Player> players = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Player p = Player.fromString(line);
                players.add(p);

            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file reading error
        }
        return players;
    }
    public static List<Team> readTeamsFromFile(String filePath) {
        List<Team> teams = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                teams.add(Team.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file reading error
        }
        return teams;
    }
    public static List<Match> readMatchesFromFile(String filePath) {
        List<Match> matches = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                matches.add(Match.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file reading error
        }
        return matches;
    }
    public static List<admin> readAdminsFromFile(String filePath)
    {
        List<admin> adminList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                adminList.add(admin.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle file reading error
        }
        return  adminList;
    }

}
