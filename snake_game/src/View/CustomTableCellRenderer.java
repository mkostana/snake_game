package View;

import Control.*;
import Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private int[][] board;
    public CustomTableCellRenderer(int[][] board){
        this.board = board;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        int cellValue = board[row][column];
        Color color;

        switch (cellValue) {
            case 0:
                color = Color.GRAY;
                break;
            case 1:
                color = new Color(19, 182, 19);
                break;
            case 2:
                color = new Color(33, 82, 28);
                break;
            case 3:
                color = Color.RED;
                break;
            default:
                color = Color.BLACK;
                break;
        }

        component.setBackground(color);
        component.setForeground(color);
        return component;
    }
}
