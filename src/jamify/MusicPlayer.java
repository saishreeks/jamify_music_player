package jamify;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MusicPlayer extends JFrame implements ActionListener {

    JPanel windowPanel = new JPanel();

    static String commonPath = "/Users/saishree/Downloads/KindaFinal/src/jamify/resources/";
    static JFrame window = new JFrame ("Jamify");
    static AllSongs allSongs;
    JPanel display = new JPanel();
    static JPanel playlist = new JPanel();
    static JPanel showqueue = new JPanel();
    JButton allSongsButton = new JButton("All Songs");
    JButton createPlaylistButton = new JButton("Create Playlist");
//    JLabel label = new JLabel("Jamify");
    static JButton play = new JButton();
    static JButton pause = new JButton();
    static JButton stop = new JButton();
    static JButton rewind = new JButton();
    static JButton forward= new JButton();
    static JButton repeat = new JButton();
    static JButton next = new JButton();
    static JButton previous = new JButton();
    static JButton shuffle = new JButton();
    static JPanel songdetailParentPanel = new JPanel();
    static JPanel songdetail = new JPanel();

    JPanel allSongsPanel;
    static JSlider slider = new JSlider(0,0,1000,0);
    static PlayingTimer playingTimer = new PlayingTimer(slider);

    public MusicPlayer() {



        songdetail.setOpaque(false);

        allSongs = new AllSongs();

        JScrollPane scrollPane = new JScrollPane(allSongs, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setPreferredSize(new Dimension(600, 600));
        // Add panel to frame
        scrollPane.setVisible(true);
        window.add(scrollPane);

//        display.setBackground(new Color(0,0,0,65));
        display.setBackground(new Color(57, 205, 222));
        playlist.setBackground(new Color(80, 193, 222));
        display.setOpaque(false);
        playlist.setOpaque(false);


//        playlist.setBackground(Color.black);
        playlist.setLayout(new BoxLayout(playlist,BoxLayout.Y_AXIS));
        showqueue.setBackground(Color.white);



        playingTimer.start();
        playingTimer.pauseTimer();

        try {
            window.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File(commonPath+"pattern.png")))));
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        //window.setResizable(false);
        display.setPreferredSize(new Dimension(400,80));

        playlist.setPreferredSize(new Dimension(200,100));
//        playlist.setBackground(new Color(0,0,0,65));
        showqueue.setPreferredSize(new Dimension(300,200));
        showqueue.setLayout(new BoxLayout(showqueue, BoxLayout.Y_AXIS));
        showqueue.setOpaque(false);

        songdetailParentPanel.setLayout(new BorderLayout());

        songdetailParentPanel.setOpaque(false);
        allSongsButton.setBounds(10,10,30,400);

        allSongsButton.setContentAreaFilled(false);
        playlist.add(allSongsButton);

        ActionListener Action1 = (ActionEvent e) -> {
            try {
                allSongs.removeAll();
                allSongs.displayAllSongsPanel();
                JPanel allSongsPanel = allSongs.displaySongsOnThePanel(new File(commonPath+"Allsongs.csv"), true);
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

        createPlaylistButton.setBounds(50,400,50,40);

        createPlaylistButton.addActionListener(new CreatePlayListActionListener());
        createPlaylistButton.setContentAreaFilled(false);

        window.setLayout(new BorderLayout(3,3));



        play.setPreferredSize(new Dimension(60,60));
        play.setIcon(new ImageIcon(commonPath+"playButton.png"));
        play.setContentAreaFilled(false);
        stop.setPreferredSize(new Dimension(60,60));
        stop.setIcon(new ImageIcon(commonPath+"stopButton.png"));
        stop.setContentAreaFilled(false);
        pause.setPreferredSize(new Dimension(60,60));
        pause.setIcon(new ImageIcon(commonPath+"pauseButton.png"));
        pause.setContentAreaFilled(false);
        rewind.setPreferredSize(new Dimension(60,60));
        rewind.setIcon(new ImageIcon(commonPath+"rewindButton.png"));
        rewind.setContentAreaFilled(false);
        forward.setPreferredSize(new Dimension(60,60));
        forward.setIcon(new ImageIcon(commonPath+"forwardButton.png"));
        forward.setContentAreaFilled(false);
        repeat.setPreferredSize(new Dimension(60,60));
        repeat.setIcon(new ImageIcon(commonPath+"repeatButton.jpg"));
        repeat.setContentAreaFilled(false);
        shuffle.setPreferredSize(new Dimension(60,60));
        shuffle.setIcon(new ImageIcon(commonPath+"shuffleButton.png"));
        shuffle.setContentAreaFilled(false);
        next.setPreferredSize(new Dimension(60,60));
        next.setIcon(new ImageIcon(commonPath+"nextButton.png"));
        next.setContentAreaFilled(false);
        previous.setPreferredSize(new Dimension(60,60));
        previous.setIcon(new ImageIcon(commonPath+"previousButton.png"));
        previous.setContentAreaFilled(false);
        slider.setSize(200,50);
        display.setLayout(new FlowLayout());

        display.add(repeat);
        display.add(previous);
        display.add(rewind);
        display.add(play);
        display.add(pause);
        display.add(stop);
        display.add(forward);
        display.add(next);
        display.add(shuffle);

        display.add(slider);
//        window.setLocation(100,100);
        window.add(playlist,BorderLayout.WEST);
        window.add(showqueue,BorderLayout.EAST);
        window.add(display,BorderLayout.SOUTH);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        window.setSize(1000,800);


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.pack();
        window.setSize(screenSize.width,screenSize.height);


        window.pack();
        window.setVisible(true);
//		display.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
