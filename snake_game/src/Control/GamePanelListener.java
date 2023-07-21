package Control;

import Model.*;
import View.*;

public interface GamePanelListener {
    void refresh();
    void setScoreLabel(ScoreEvent evt);
    String askForName();
    void showLeaderBoard(LeaderBoardEvent evt);
}
