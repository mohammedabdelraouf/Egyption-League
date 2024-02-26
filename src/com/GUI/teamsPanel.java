package com.GUI;

import com.EgyptianLegue.Match;
import com.EgyptianLegue.Player;
import com.EgyptianLegue.Team;
import com.EgyptianLegue.score;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class teamsPanel extends JPanel {

    private List<Team> teams;  // The list of teams associated with this panel
    private JComboBox<Team> teamDropdown;

    // Components
    private JTextField nameField;
    private JTextField captainField;
    private JTextField totalScoreField;
    private JTextArea outputArea;
    private JButton addPlayerButton;
    private JButton updateTeamButton;
    private JButton displayMatchesButton;
    private JButton addTeamButton; // New button for adding a team
    private  JButton deletePlayerButton;
    private  JButton backButton ;


    public JButton getBackButton() {
        return backButton;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
        teamDropdown.setModel(new DefaultComboBoxModel<>(teams.toArray(new Team[0])));
    }

    public teamsPanel() {
        // Initialize components
        teamDropdown = new JComboBox<>();
        nameField = new JTextField(15);
        captainField = new JTextField(15);
        totalScoreField = new JTextField(5);
        outputArea = new JTextArea(10, 30);
        addPlayerButton = new JButton("Add Player");
        updateTeamButton = new JButton("Update Team");
        displayMatchesButton = new JButton("Display Matches");
        addTeamButton = new JButton("Add New Team"); // Initialize the new button
        deletePlayerButton = new JButton("Delete Player");
        backButton = new JButton("Back To Home");
        // Set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Add components to the panel
        add(backButton);
        add(new JLabel("Select Team:"));
        add(teamDropdown);
        add(new JLabel("Team Name:"));
        add(nameField);
        add(new JLabel("Captain Name:"));
        add(captainField);
        add(new JLabel("Total Score:"));
        add(totalScoreField);
        add(addPlayerButton);
        add(updateTeamButton);
        add(displayMatchesButton);
        add(addTeamButton); // Add the new button to the panel
        add(new JScrollPane(outputArea));
        // Initialize the new button
        add(deletePlayerButton);

        // Add action listener for the delete player button
        deletePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePlayer();
            }
        });
        // Add action listeners
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer();
            }
        });

        updateTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTeam();
            }
        });

        displayMatchesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMatches();
            }
        });

        addTeamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewTeam();
            }
        });

    }

    private void addPlayer() {
        Team selectedTeam = (Team) teamDropdown.getSelectedItem();
        if (selectedTeam != null) {
            try {
                String playerName = getValidatedInput("Enter Player Name:", "Player Name cannot be empty");
                int playerId = Integer.parseInt(getValidatedInput("Enter Player ID:", "Player ID cannot be empty"));
                int playerNumber = Integer.parseInt(getValidatedInput("Enter Player Number:", "Player Number cannot be empty"));
                int playerAge = Integer.parseInt(getValidatedInput("Enter Player Age:", "Player Age cannot be empty"));
                int playerScore = Integer.parseInt(getValidatedInput("Enter Player Score:", "Player Score cannot be empty"));
                int playerRank = Integer.parseInt(getValidatedInput("Enter Player Rank:", "Player Rank cannot be empty"));

                Player newPlayer = new Player(playerName, playerId, playerNumber, selectedTeam.getName(), playerAge, playerScore, playerRank);
                selectedTeam.addPlayerToTeam(newPlayer);

                updateOutputArea("Player added to " + selectedTeam.getName() + ": " + newPlayer);
            } catch (NumberFormatException | NullPointerException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numeric values for ID, Number, Age, Score, and Rank.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private String getValidatedInput(String message, String errorMessage) {
        String input;
        do {
            input = JOptionPane.showInputDialog(message);
            if (input == null) {
                return null; // User pressed cancel
            }
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (input.isEmpty());
        return input;
    }

    private void updateTeam() {
        Team selectedTeam = (Team) teamDropdown.getSelectedItem();
        if (selectedTeam != null) {
            String newName = nameField.getText();
            String newCaptainName = captainField.getText();
            int newTotalScore = Integer.parseInt(totalScoreField.getText());

            Player newCaptain = new Player(newCaptainName);
            selectedTeam.updateTeamInformation(newName, newCaptain, newTotalScore);
            updateOutputArea("Team information updated for " + selectedTeam.getName() + ": " + selectedTeam.getTeamInformation());
        }
    }

    private void displayMatches() {
        Team selectedTeam = (Team) teamDropdown.getSelectedItem();
        if (selectedTeam != null) {
            // For simplicity, use the current date as a reference
            Date currentDate = new Date();
            List<Match> heldMatches = new ArrayList<>();
            List<Match> toBeHeldMatches = new ArrayList<>();

            for (Match match : selectedTeam.getMatches()) {
                if (match.getDate().before(currentDate)) {
                    heldMatches.add(match);
                } else {
                    toBeHeldMatches.add(match);
                }
            }

            outputArea.setText("Held Matches:\n");
            for (Match heldMatch : heldMatches) {
                outputArea.append(heldMatch.toString()+"\n");
            }

            outputArea.append("To be Held Matches:\n");
            for (Match toBeHeldMatch : toBeHeldMatches) {
                outputArea.append(toBeHeldMatch.toString()+"\n");
            }
        }
    }

    private void addNewTeam() {
        try {
            // Get team name
            String teamName = getValidatedInput("Enter Team Name:", "Team Name cannot be empty");

            // Get team ID
            int teamID = Integer.parseInt(getValidatedInput("Enter Team ID:", "Team ID cannot be empty"));

            // Get captain name
            String captainName = getValidatedInput("Enter Captain Name:", "Captain Name cannot be empty");
            Player captain = new Player(captainName);

            // Get total score
            int totalScore = Integer.parseInt(getValidatedInput("Enter Total Score:", "Total Score cannot be empty"));

            // Create a new team
            Team newTeam = new Team(teamName, teamID, captain, totalScore);

            // Ask the user if they want to add players
            int addPlayersOption = JOptionPane.showConfirmDialog(this, "Do you want to add players?", "Add Players", JOptionPane.YES_NO_OPTION);
            if (addPlayersOption == JOptionPane.YES_OPTION) {
                // Get the number of players
                int numberOfPlayers = Integer.parseInt(getValidatedInput("Enter the number of players:", "Please enter a valid number."));
                for (int i = 0; i < numberOfPlayers; i++) {
                    // Get player details
                    String playerName = getValidatedInput("Enter Player Name for Player " + (i + 1) + ":", "Player Name cannot be empty");
                    int playerID = Integer.parseInt(getValidatedInput("Enter Player ID for Player " + (i + 1) + ":", "Player ID cannot be empty"));
                    int playerNumber = Integer.parseInt(getValidatedInput("Enter Player Number for Player " + (i + 1) + ":", "Player Number cannot be empty"));
                    int playerAge = Integer.parseInt(getValidatedInput("Enter Player Age for Player " + (i + 1) + ":", "Player Age cannot be empty"));
                    int playerScore = Integer.parseInt(getValidatedInput("Enter Player Score for Player " + (i + 1) + ":", "Player Score cannot be empty"));
                    int playerRank = Integer.parseInt(getValidatedInput("Enter Player Rank for Player " + (i + 1) + ":", "Player Rank cannot be empty"));

                    // Create a new player and add it to the team
                    Player player = new Player(playerName, playerID, playerNumber, teamName, playerAge, playerScore, playerRank);
                    newTeam.addPlayer(player);
                }
            }

            // Ask the user if they want to add matches
            int addMatchesOption = JOptionPane.showConfirmDialog(this, "Do you want to add matches?", "Add Matches", JOptionPane.YES_NO_OPTION);
            if (addMatchesOption == JOptionPane.YES_OPTION) {
                // Get the number of matches
                int numberOfMatches = Integer.parseInt(getValidatedInput("Enter the number of matches:", "Please enter a valid number."));
                for (int i = 0; i < numberOfMatches; i++) {
                    // Get match details
                    int matchID = Integer.parseInt(getValidatedInput("Enter Match ID for Match " + (i + 1) + ":", "Match ID cannot be empty"));
                    // Assuming Date is a java.util.Date object
                    Date date = new Date();
                    try
                    {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(JOptionPane.showInputDialog("Enter new date (yyyy-MM-dd):"));
                    }
                    catch (ParseException e)
                    {
                        JOptionPane.showMessageDialog(teamsPanel.this,e.toString());
                    }
                    String[] teamNames = JOptionPane.showInputDialog("Enter new team names (Separated by '|'):").split("\\|");
                    List<Team> matchTeams = new ArrayList<>();
                    for (String team : teamNames) {
                        matchTeams.add(new Team(team));
                    }
                    String footballReferee = getValidatedInput("Enter Football Referee for Match " + (i + 1) + ":", "Football Referee cannot be empty");
                    int score = Integer.parseInt(getValidatedInput("Enter Score for Match " + (i + 1) + ":", "Score cannot be empty"));
                    String stadiumName = getValidatedInput("Enter Stadium Name for Match " + (i + 1) + ":", "Stadium Name cannot be empty");

                    // Create a new match and add it to the team
                    Match match = new Match(matchID, date, matchTeams, footballReferee,new score() , stadiumName);
                    newTeam.addMatch(match);
                }
            }

            // Add the new team to the list of teams
            teams.add(newTeam);
            setTeams(teams);
            updateOutputArea("New team added: " + teamName);
        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numeric values for ID, Number, Age, Score, and Rank.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void updateOutputArea(String text) {
        outputArea.append(text + "\n");
    }

    private void deletePlayer() {
        Team selectedTeam = (Team) teamDropdown.getSelectedItem();
        if (selectedTeam != null) {
            try {
                // Get player details to delete
                String playerName = getValidatedInput("Enter Player Name to delete:", "Player Name cannot be empty");
                int playerID = Integer.parseInt(getValidatedInput("Enter Player ID to delete:", "Player ID cannot be empty"));

                // Call the deletePlayerFromTeam method
                selectedTeam.deletePlayerFromTeam(playerName, playerID);

                updateOutputArea("Player deleted from " + selectedTeam.getName() + ": " + playerName);
            } catch (NumberFormatException | NullPointerException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numeric values for ID.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
