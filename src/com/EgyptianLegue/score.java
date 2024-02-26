package com.EgyptianLegue;

public class  score{
    private int team1;
    private int team2;
    private String team_1 ;
    private String team_2;
    private String winner;
    private boolean drew;

    public score() {}
    public score(int team1, int team2) {
        this.team1 = team1;
        this.team2 = team2;
    }
    public score(String Team1 , int team1, String Team2, int team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.team_1 = Team1;
        this.team_2 =Team2;
        setWinner();
    }

    public String getTeam_1() {
        return team_1;
    }

    public void setTeam_1(String team_1) {
        this.team_1 = team_1;
    }

    public String getTeam_2() {
        return team_2;
    }

    public void setTeam_2(String team_2) {
        this.team_2 = team_2;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner() {
        if(team1>team2)
        {
            this.winner = this.team_1;
        }
        else if (team2>team1)
        {
            this.winner = this.team_2;
        }
        else
        {
            setDrew(true);
        }
    }

    public boolean isDrew() {
        return drew;
    }

    public void setDrew(boolean drew) {
        this.drew = drew;
    }

    public int getTeam1() {
        return team1;
    }

    public void setTeam1(int team1) {
        this.team1 = team1;
    }

    public int getTeam2() {
        return team2;
    }

    public void setTeam2(int team2) {
        this.team2 = team2;
    }
}
