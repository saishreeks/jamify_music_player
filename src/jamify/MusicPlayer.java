package jamify;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MusicPlayer extends JFrame implements ActionListener {

    static String commonPath = "/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify";
    static JFrame window = new JFrame ("Jamify");
    static AllSongs allSongs;
    JPanel display = new JPanel();
    static JPanel playlist = new JPanel();
    static JPanel showqueue = new JPanel();
    JButton allSongsButton = new JButton("All Songs");
    JButton createPlaylistButton = new JButton("Create Playlist");
    JLabel label = new JLabel("Jamify");
    static JButton play = new JButton("Play");
    static JButton pause = new JButton("Pause");
    static JButton stop = new JButton("Stop");
    static JButton rewind = new JButton("Rewind");
    static JButton forward= new JButton("Forward");
    static JButton repeat = new JButton("Repeat");
    static JButton next = new JButton("Next");
    static JButton previous = new JButton("Previous");
    static JButton shuffle = new JButton("Shuffle Songs");
    static JPanel songdetailParentPanel = new JPanel();
    static JPanel songdetail = new JPanel();
    JPanel allSongsPanel;

    public MusicPlayer() {

        allSongs = new AllSongs();
        display.setBackground(Color.WHITE);
        playlist.setBackground(Color.black);
        playlist.setLayout(new BoxLayout(playlist,BoxLayout.Y_AXIS));
        showqueue.setBackground(Color.white);;
        window.setSize(1000,500);
        try {
            window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/background.jpg")))));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //window.setResizable(false);
        display.setPreferredSize(new Dimension(400,200));
        playlist.setPreferredSize(new Dimension(200,100));
        showqueue.setPreferredSize(new Dimension(300,200));
        showqueue.setLayout(new BoxLayout(showqueue, BoxLayout.Y_AXIS));
        songdetailParentPanel.setLayout(new BorderLayout());
        songdetailParentPanel.setOpaque(false);
        playlist.add(allSongsButton);
        allSongsButton.setBounds(10,10,30,20);

        ActionListener Action1 = (ActionEvent e) -> {
            try {
                allSongs.removeAll();
                allSongs.displayAllSongsPanel();
                JPanel allSongsPanel = allSongs.displaySongsOnThePanel(new File("/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/Allsongs.csv"), true);
                allSongsPanel.setOpaque(false);
                window.getContentPane().add(allSongsPanel);
                allSongsPanel.revalidate();
                allSongsPanel.repaint();
            } catch (Exception p){
                System.out.println(p);
            }
        };

        allSongsButton.addActionListener(Action1);
        playlist.add(createPlaylistButton);
        createPlaylistButton.addActionListener(new CreatePlayListActionListener());
//		allSongsButton.addActionListener(new showAllSongs);
        window.add(display);
        display.add(label);
//		window.add(jp);
        window.setLayout(new BorderLayout(3,3));
        window.add(playlist);


//		  Insets insets = window.getInsets();
//		  Dimension sizePlaylist = playlist.getPreferredSize();
//		  Dimension sizeDisplay = display.getPreferredSize();
//		  playlist.setBounds(20+insets.left,5+insets.top,
//				  sizePlaylist.width,sizePlaylist.height);
        play.setPreferredSize(new Dimension(40,40));
        play.setIcon(new ImageIcon("/Users/satishrambhatla/Documents/workspace/Jamify/src/jamify/play.jpg"));
        stop.setPreferredSize(new Dimension(40,40));
        pause.setPreferredSize(new Dimension(40,40));
        rewind.setPreferredSize(new Dimension(40,40));
        forward.setPreferredSize(new Dimension(40,40));
        repeat.setPreferredSize(new Dimension(40,40));
        shuffle.setPreferredSize(new Dimension(40,40));
        next.setPreferredSize(new Dimension(40,40));
        previous.setPreferredSize(new Dimension(40,40));
        display.setLayout(new FlowLayout());

        display.add(play);
        display.add(stop);
        display.add(pause);
        display.add(rewind);
        display.add(forward);
        display.add(repeat);
        display.add(shuffle);
        display.add(next);
        display.add(previous);
        window.setLocation(100,100);
        window.add(playlist,BorderLayout.WEST);
        window.add(showqueue,BorderLayout.EAST);
        window.add(display,BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.pack();
        window.setVisible(true);
//		display.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
