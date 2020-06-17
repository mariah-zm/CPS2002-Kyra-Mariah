package Team;

public class Team extends Subject{

    private int teamNo;
    public int playerTurn =0;

    public Team(int teamNo) {
        this.teamNo = teamNo;
    }

    public int getTeamNo(){
        return teamNo+1;
    }
    public void setNextPlayer(){
        //resetting counter once all players have played
        this.playerTurn = (this.playerTurn + 1) % this.observers.size();
/*
        if(this.observers.size() == this.playerTurn){
            this.playerTurn =0;
        }
        this.playerTurn++;*/
    }

    public Observer currentPlayer(){
        return this.observers.get(playerTurn);
    }





}
