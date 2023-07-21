package View;

import Control.*;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;;

public class GamePanel extends JPanel implements GamePanelListener {
    private JLabel scoreLabel;
    private GameListener game;
    public GamePanel(GameListener game){
        this.game = game;
        setLayout(new BorderLayout());
        scoreLabel = new JLabel("Score: " + 0);
        GameTable gameTable = new GameTable(game);
        add(gameTable, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.PAGE_START);
        game.setListener(this);
    }

    public void refresh(){
        repaint();
        revalidate();
    }

    public void setScoreLabel(ScoreEvent evt){
        scoreLabel.setText("Score: " + evt.getScore());
    }

    public String askForName(){
        String name = JOptionPane.showInputDialog("Enter name:");
        if(name != null && !name.isEmpty())
            return name;
        return "";
    }

    public void showLeaderBoard(LeaderBoardEvent evt){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame jFrame = new JFrame("LeaderBoard");
                jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                JPanel infoPanel = new JPanel();
                infoPanel.setLayout(new FlowLayout());
                infoPanel.add(new JLabel("Rank"));
                infoPanel.add(new JLabel("Name"));
                infoPanel.add(new JLabel("Score"));
                jPanel.add(infoPanel);

                List<Player> leaderBoard = evt.getLeaderBoard();
                for(int i=0; i<leaderBoard.size(); i++){
                    JPanel playerPanel = new JPanel();
                    playerPanel.setLayout(new FlowLayout());
                    playerPanel.add(new JLabel(""+(i+1)+"."));
                    playerPanel.add(new JLabel(leaderBoard.get(i).getName()));
                    playerPanel.add(new JLabel(""+leaderBoard.get(i).getScore()));
                    jPanel.add(playerPanel);
                }

                JButton jButton = new JButton("Try again");
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jFrame.dispose();
                        game.restart();
                        GamePanel.this.refresh();
                    }
                });

                jFrame.add(jPanel, BorderLayout.CENTER);
                jFrame.add(jButton, BorderLayout.PAGE_END);
                jFrame.setSize(640, 480);
                jFrame.setLocationByPlatform(true);
                jFrame.setVisible(true);
                jFrame.setResizable(false);
            }
        });
    }
}
