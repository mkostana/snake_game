package Model;

import View.*;
import Control.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game extends Thread implements GameListener{
    private int[][] board;
    private int score;
    private boolean gameOver;
    private Point food;
    private List<Point> snake;
    private int direction;
    private GamePanelListener gamePanelListener;
    private boolean start;
    private boolean closeGame;
    private List<Player> leaderBoard;
    private FileManager fileManager;

    public Game() {
        fileManager = new FileManager();
        leaderBoard = fileManager.readFile();
        closeGame = false;
        board = new int[25][16];
        snake = new ArrayList<>();
        initialize();
        start();
    }

    private void initialize() {
        score = 0;
        gameOver = false;
        direction = 4;
        start = false;

        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                board[i][j] = 0;

        snake.clear();
        snake.add(new Point(board.length / 2, board[0].length / 2));
        board[board.length / 2][board[0].length / 2] = 2;

        generateFood();
    }

    private void generateFood() {
        int x, y;

        do {
            y = (int) (Math.random() * board[0].length);
            x = (int) (Math.random() * board.length);
        } while (board[x][y] != 0);

        food = new Point(x, y);
        board[x][y] = 3;
    }

    @Override
    public void run() {
        do {
            if (!start) {
                try {
                    synchronized (this) {
                        wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            while (!gameOver) {
                moveSnake();
                gamePanelListener.refresh();
                try {
                    sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }while(!closeGame);
    }

    private void moveSnake() {
        int headX = snake.get(0).x;
        int headY = snake.get(0).y;

        switch (direction) {
            case 0 -> headX--;
            case 1 -> headY++;
            case 2 -> headX++;
            case 3 -> headY--;
        }

        Point newHead = new Point(headX, headY);
        snake.add(0, newHead);
        if (!checkCollisions()) {
            for (Point val : snake)
                board[val.x][val.y] = 1;
            board[snake.get(0).x][snake.get(0).y] = 2;
        }
    }

    private boolean checkCollisions() {
        Point head = snake.get(0);

        //Check wall collision
        if (head.x < 0 || head.x >= board.length || head.y < 0 || head.y >= board[0].length) {
            gameOver = true;
            if(isScoreEnough()){
                String name = gamePanelListener.askForName();
                addToLeaderBoard(name);
            }
            fireShowLeaderBoard(leaderBoard);
            return true;
        }

        //Check food collision
        if (head.equals(food)) {
            score++;
            fireScoreSet(score);
            generateFood();
            return false;
        }
        Point removal = snake.get(snake.size() - 1);
        board[removal.x][removal.y] = 0;
        snake.remove(snake.size() - 1);

        //Check self-collision
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).equals(head)) {
                gameOver = true;
                if(isScoreEnough()){
                    String name = gamePanelListener.askForName();
                    addToLeaderBoard(name);
                }
                fireShowLeaderBoard(leaderBoard);
                return true;
            }
        }

        return false;
    }

    public int setDirection(int direction) {
        if ((this.direction == 0 && direction == 2) || (this.direction == 2 && direction == 0) || (this.direction == 1 && direction == 3) || (this.direction == 3 && direction == 1))
            return 1;
        this.direction = direction;
        if (!start) {
            synchronized (this) {
                notify();
            }
            start = true;
        }
        return 0;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setListener(GamePanelListener gamePanelListener) {
        this.gamePanelListener = gamePanelListener;
    }

    public int getScore() {
        return score;
    }

    public boolean isScoreEnough(){
        if(leaderBoard.size()<10)
            return true;
        else if(score>leaderBoard.get(9).getScore())
            return true;
        else
            return false;
    }

    public int addToLeaderBoard(String name){
        for(int i=0; i<leaderBoard.size(); i++){
            if(score>leaderBoard.get(i).getScore()) {
                leaderBoard.add(i, new Player(name, score));
                if(leaderBoard.size()==11)
                    leaderBoard.remove(10);
                return 0;
            }
        }
        leaderBoard.add(new Player(name, score));
        return 0;
    }

    public List<Player> getLeaderBoard(){
        return leaderBoard;
    }

    public void restart(){
        initialize();
        synchronized (this) {
            notify();
        }
        gamePanelListener.refresh();
        fireScoreSet(score);
    }

    private void fireScoreSet(int score){
        ScoreEvent evt = new ScoreEvent(this, score);
        gamePanelListener.setScoreLabel(evt);
    }

    private void fireShowLeaderBoard(List<Player> leaderBoard){
        LeaderBoardEvent evt = new LeaderBoardEvent(this, leaderBoard);
        gamePanelListener.showLeaderBoard(evt);
    }

    public void saveFile(){
        fileManager.saveFile(leaderBoard);
    }
}
