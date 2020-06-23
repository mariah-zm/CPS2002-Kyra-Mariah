package Team;

//Concrete Subject of Observer DP
public class Team extends Subject{

    //Team ID
    private int teamNum;
    //The index of current player
    public int playerTurn = 0;

    //Class Constructor
    public Team(int teamNo) {
        this.teamNum = teamNo;
    }

    public int getTeamNum(){
        return teamNum+1;
    }

    //Gives turn to following player in the Observers list
    public void setNextPlayer(){
        //The counter is reset once all players have played
        this.playerTurn = (this.playerTurn + 1) % this.observers.size();
    }

    //Returns current Player
    public Observer currentPlayer(){
        return this.observers.get(playerTurn);
    }

}