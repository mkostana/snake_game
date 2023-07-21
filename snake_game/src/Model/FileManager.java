package Model;

import View.*;
import Control.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public void saveFile(List<Player> leaderBoard){
        try{
            FileOutputStream fos = new FileOutputStream("leaderboard.bin");
            for(Player val : leaderBoard){
                byte[] nameBytes = val.getName().getBytes();
                int nameLength = nameBytes.length;
                int score = val.getScore();

                fos.write((byte) nameLength);
                fos.write(nameBytes);
                fos.write((score >>> 24) & 0xFF);
                fos.write((score >>> 16) & 0xFF);
                fos.write((score >>> 8) & 0xFF);
                fos.write(score & 0xFF);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Player> readFile(){
        List<Player> result = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream("leaderboard.bin");
            int nameLength;
            while((nameLength = fis.read()) != -1){
                byte[] nameBytes = new byte[nameLength];
                fis.read(nameBytes);
                String readPlayerName = new String(nameBytes);
                int readScore = (fis.read() << 24) | (fis.read() << 16) | (fis.read() << 8) | fis.read();
                result.add(new Player(readPlayerName, readScore));
            }
        }catch (Exception e){
            System.out.println("Problem with file.");
            e.printStackTrace();
        }
        return result;
    }
}
