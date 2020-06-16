package Team;

public class Team extends Subject{

    private int teamNo;
    int playerTurn =0;

    public Team(int teamNo) {
        this.teamNo = teamNo;
    }

    public int getTeamNo(){
        return teamNo;
    }
    public void setNextPlayer(){
        //resetting counter once all players have played
        if(this.observers.size() == this.playerTurn){
            this.playerTurn =0;
        }
        this.playerTurn++;
    }

    public Observer currentPlayer(){
        return this.observers.get(playerTurn);
    }





}
