import javax.swing.*;
import Model.Game;
import View.MyFrame;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MyFrame myFrame = new MyFrame(game);
            }
        });
    }
}
