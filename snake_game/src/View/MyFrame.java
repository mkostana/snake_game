package View;

import Control.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame {
    private GameListener game;
    public MyFrame(GameListener game){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake Game");
        setLayout(new BorderLayout());
        setFocusable(true);
        setResizable(false);

        this.game = game;

        GamePanel gamePanel = new GamePanel(game);
        this.add(gamePanel, BorderLayout.CENTER);

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //super.keyPressed(e);
                int keyCode = e.getKeyCode();
                switch (keyCode){
                    case KeyEvent.VK_UP -> game.setDirection(0);
                    case KeyEvent.VK_RIGHT -> game.setDirection(1);
                    case KeyEvent.VK_DOWN -> game.setDirection(2);
                    case KeyEvent.VK_LEFT -> game.setDirection(3);
                }
            }
        };

        FocusListener focusListener = new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                MyFrame.this.addKeyListener(keyAdapter);
            }

            @Override
            public void focusLost(FocusEvent e) {
                MyFrame.this.removeKeyListener(keyAdapter);
            }
        };
        addFocusListener(focusListener);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game.saveFile();
            }
        });

        pack();
        setVisible(true);
    }
}
