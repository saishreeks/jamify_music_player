package jamify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreatePlayListActionListener implements ActionListener {
    public String playListName;
    int y = 50;
    Timer tm = new Timer(500, this);

    @Override
    public void actionPerformed(ActionEvent e) {

        playListName = JOptionPane.showInputDialog("Enter the Play List name");
        if (null != playListName && !playListName.trim().equals("")) {

            String playListPath = MusicPlayer.commonPath + playListName + ".csv";
            File playListFile = new File(playListPath);

            PlaylistNamePath playList1 = new PlaylistNamePath();
            playList1.setPlayListName(playListName);
            playList1.setPlayListPath(playListPath);
            AllSongs.playlistNamePathsList.add(playList1);
            JButton playlistButton = new JButton(playListName);
            playlistButton.setBounds(20, y, 100, 40);

            playlistButton.setContentAreaFilled(false);
            y = y + 30;
            MusicPlayer.playlist.add(playlistButton);
            MusicPlayer.playlist.revalidate();
            MusicPlayer.playlist.repaint();

            /** for deleting playlist*/
            playlistButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
                    {
                        System.out.println("You right clicked on the button");

                        JPopupMenu rightClickMenu = new JPopupMenu();
                        JMenuItem delete = new JMenuItem("Delete");
                        rightClickMenu.add(delete);
                        rightClickMenu.show(e.getComponent(), e.getX(), e.getY());

                        delete.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("clicked delete ");
                                MusicPlayer.playlist.remove(playlistButton);
                                PlaylistNamePath namePath = new PlaylistNamePath();
                                namePath.setPlayListPath(playListPath);
                                namePath.setPlayListName(playListName);
                                AllSongs.playlistNamePathsList.remove(AllSongs.playlistNamePathsList.indexOf(namePath));
                                MusicPlayer.playlist.revalidate();
                                MusicPlayer.playlist.repaint();
                            }
                        });

                    }
                }
            });

            playlistButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    MusicPlayer.allSongs.removeAll();
                    MusicPlayer.allSongs.displayAllSongsPanel();
//                    JPanel jp = MusicPlayer.allSongs.displaySongsOnThePanel(new File(AllSongs.playlistNamePathsList.get(finalI).getPlayListPath()), false);
                    JPanel jp = MusicPlayer.allSongs.displaySongsOnThePanel(new File(playListPath), false);
                    MusicPlayer.window.getContentPane().add(jp);
                    jp.validate();
                    jp.repaint();

                }
            });
        }
    }


}
