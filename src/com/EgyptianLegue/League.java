package com.EgyptianLegue;

import java.util.List;

public final class League {
    protected List <Team> teams ;
    protected List<Match> matchList ;
    private List <Referee> referees ;
    private List <Player> players ;
    private List <admin> adminList;

    public League(List<Team> teams, List<Match> matchList, List<Referee> referees, List<Player> players) {
        this.teams = teams;
        this.matchList = matchList;
        this.referees = referees;
        this.players = players;
    }

    public League() {

    }

    public List<admin> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<admin> adminList) {
        this.adminList = adminList;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Match> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Match> matchList) {
        this.matchList = matchList;
    }

    public List<Referee> getReferees() {
        return referees;
    }


    public void setReferees(List<Referee> referees) {
        this.referees = referees;
    }
    public void addPlayer(Player player)
    {
        this.players.add(player);
    }
    public void addTeam(Team team)
    {
        this.teams.add(team);
    }
    public void removePlayerId(int playerId)
    {
        for(Player p : this.players)
        {
            if (p.getId()==playerId)
            {
                players.remove(p);
            }
        }
    }
    public  void updateMatchListTeamsName(String newName, String oldName)
    {
        List<Match> newList = this.matchList;
        for(int j = 0; j<this.matchList.size();j++)
        {
            Match m = matchList.get(j);
            for(int i =0; i<m.teams.size();i++)
            {
                if(m.teams.get(i).getName().equals(oldName))
                    m.teams.set(i,new Team(newName));

            }
            newList.set(j, m);
        }
        this.setMatchList(newList);
    }
    public  void updatePlayersTeamName(String newName, String oldName)
    {
        List<Player> newList = this.players;
        for(int j = 0; j<this.players.size();j++)
        {
            Player p = players.get(j);
            if(p.getTeam().equals(oldName))
                p.setTeam(newName);
            newList.set(j, p);
        }
        this.setPlayers(newList);
    }

}
