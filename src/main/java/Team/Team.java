package Team;

//Concrete Subject of Observer DP
public class Team extends Subject {

    //Team ID
    private String teamID;
    //The index of current player
    public int playerTurn = 0;

    //Class Constructor
    public Team(int teamNo) {
        this.teamID = "Team " + teamNo;
    }

    public String getTeamID() {
        return teamID;
    }

    //Gives turn to following player in the Observers list
    public void setNextPlayer() {
        //The counter is reset once all players have played
        this.playerTurn = (this.playerTurn + 1) % this.observers.size();
    }

    //Returns current Player
    public Observer currentPlayer() { return this.observers.get(playerTurn); }

}