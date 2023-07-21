package Control;

import Model.*;
import View.*;

public interface GameListener {
    int setDirection(int direction);
    void setListener(GamePanelListener gamePanelListener);
    void restart();
    int[][] getBoard();
    void saveFile();
}
