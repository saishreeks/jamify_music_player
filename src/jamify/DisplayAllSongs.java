package jamify;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class DisplayAllSongs {
// I commented the line below
//    public static void main(String[] args) {
        public void DisplayAllSongs(){
        

//        JFrame main = new JFrame();
       // JPanel allSongsPanel = new JPanel();
        AllSongs allSongs = new AllSongs();
        allSongs.displaySongsOnThePanel(new File(MusicPlayerUI.commonPath+"Allsongs.csv"), true);

        /* panel at the bottom for create and playlist buttons*/
        JPanel jPanel = new JPanel();
        jPanel.setBounds(30,500,300,30);
        JButton create = new JButton("Create");
        jPanel.add(create);
        create.addActionListener(new CreatePlayListActionListener());

//        /* Whenever a playlist is created its added to the array list playlistNamePathsList.
//         * If there are any playlist created, a button for each playlist should be shown */
//        //doesn't create button dynamically. When closed and opened it shows the buttons
//        if (AllSongs.playlistNamePathsList.size()>0) {
//            for (int i = 0; i < AllSongs.playlistNamePathsList.size(); i++) {
//                /* creates a button for each playlist*/
//                JButton button = new JButton(AllSongs.playlistNamePathsList.get(i).getPlayListName());
//                int finalI = i;
//
//                /* on click of playlist button we get new frame showing all the songs in the playlist*/
//                button.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        JFrame jFrame = new JFrame();
//                        JPanel jp = new JPanel();
//                        AllSongs allSongs = new AllSongs();
//                        allSongs.displaySongsOnThePanel(new File(AllSongs.playlistNamePathsList.get(finalI).getPlayListPath()), false);
//                        jFrame.add(allSongs);
//                        jFrame.setSize(400,600);
//                        jFrame.setVisible(true);
//                        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    }
//                });
//                jPanel.add(button);
//                Container parent = create.getParent();
//            }
//        }
//        main.add(jPanel);
//
//
//
//        main.add(allSongs);
//        main.setSize(400,600);
//        main.setVisible(true);
//        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        /* when we close the main frame, the playlist is saved*/
//        main.addWindowListener(new WindowAdapter(){
//            public void windowClosing(WindowEvent e){
//                savePlaylists.save();
//            }
//        });



    }
}
