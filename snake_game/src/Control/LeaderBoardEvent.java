package Control;

import Model.*;
import View.*;

import java.util.EventObject;
import java.util.List;

public class LeaderBoardEvent extends EventObject {
    private List<Player> leaderBoard;

    public LeaderBoardEvent(Object source, List<Player> leaderBoard){
        super(source);
        this.leaderBoard = leaderBoard;
    }

    public List<Player> getLeaderBoard(){
        return leaderBoard;
    }
}
