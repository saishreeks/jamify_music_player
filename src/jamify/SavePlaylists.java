package jamify;

import java.io.*;
import java.util.ArrayList;

public class SavePlaylists  {

    public void save (){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("t.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(AllSongs.playlistNamePathsList);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void retrieve(){

        File f = new File("t.tmp");
        if(!f.exists() && !f.isDirectory()) {
            return;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("t.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            AllSongs.playlistNamePathsList = (ArrayList<PlaylistNamePath>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
