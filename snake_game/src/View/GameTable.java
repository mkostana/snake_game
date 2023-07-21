package View;

import Control.*;
import Model.*;

import javax.swing.*;

public class GameTable extends JTable {
    private GameListener game;
    public GameTable(GameListener game){
        super(new GameTableModel(game.getBoard()));
        this.game = game;
        setRowHeight(25);
        setDefaultRenderer(Object.class, new CustomTableCellRenderer(game.getBoard()));
        setEnabled(false);

        for(int i=0; i<getColumnCount(); i++){
            getColumnModel().getColumn(i).setPreferredWidth(25);
            getColumnModel().getColumn(i).setMaxWidth(25);
        }
    }
}
