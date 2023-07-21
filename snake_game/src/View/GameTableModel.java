package View;

import Control.*;
import Model.*;

import javax.swing.table.AbstractTableModel;

public class GameTableModel extends AbstractTableModel {
    private int[][] board;

    public GameTableModel(int[][] board){
        this.board = board;
    }
    @Override
    public int getRowCount() {
        return board.length;
    }

    @Override
    public int getColumnCount() {
        return board[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex];
    }
}
