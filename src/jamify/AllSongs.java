package jamify;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class AllSongs extends JPanel implements ActionListener {
    AudioPlay audioPlayObj;
    SongMetaData fs = new SongMetaData();
    JButton addNewSongBtn = new JButton("Add New Song");



    JButton playAllBtn = new JButton("Play All");
//    Not able to add eachsongpanel to this scrollpane
//    JScrollPane paneScroll = new JScrollPane();
    JPanel allSongsPanel;
    //Queue<String> tempSongQueue = new LinkedList<String>();
    //List<String> tempAllSongs = new ArrayList<>();

    ArrayList<String> allSongsList = new ArrayList<>();
    final static String allSongsFileLocation = MusicPlayer.commonPath + "AllSongs.csv";
    File allSongsFile = new File(allSongsFileLocation);
    static ArrayList<PlaylistNamePath> playlistNamePathsList = new ArrayList<>();
    //to keep track of the last song number
    int lastSongNumber = 0;
    int y = 40;
    PlayingTimer playingTimer = MusicPlayer.playingTimer;

    JPanel eachSongPanel;
    JPanel songForQueue;
    //initialization
    static volatile Queue<String> songQueue = new LinkedList<String>();
    static volatile Stack<String> songStack = new Stack();

    /* Constructor, sets up the panel and Sets the position and size of each component by calling
     its setBounds() method. */
    public AllSongs() {
//    	displayAllSongsPanel();


    }

    public void displayAllSongsPanel() {
        setLayout(null);
        setBounds(20, 20, 890, 550);
        add(playAllBtn);
        playAllBtn.setBounds(30, 10, 100, 20);
        playAllBtn.setBorder(null);
        add(addNewSongBtn);
        playAllBtn.addActionListener(this);
        playAllBtn.setFont(new Font("Calibri", Font.BOLD, 15));
        addNewSongBtn.setFont(new Font("Calibri", Font.BOLD, 15));
        add(addNewSongBtn);
        addNewSongBtn.setBounds(500, 10, 150, 20);
        addNewSongBtn.setBorder(null);
        addNewSongBtn.addActionListener(this);
//        paneScroll.setBounds(100,60,400,200);
//        paneScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        add(paneScroll);
        setVisible(true);
        this.revalidate();
        this.repaint();
    }

    //    static volatile Queue<String> songNameQueue=new LinkedList<String>();
//getters and setters
    // Surya asked me to add this method
    public void addToQueueStart(String file) {
        Queue<String> tempSongQueue = new LinkedList<String>();
        tempSongQueue.add(file);
        while (songQueue.size() > 0) {
            tempSongQueue.add(songQueue.poll());
        }
        songQueue = tempSongQueue;
    }
    // The method ends here

    public Stack<String> getSongStack() {
        return songStack;
    }

    public void addToQueue(String file) {
        songQueue.add(file);
    }

    public void addToSongStack(String file) {
        songStack.push(file);
    }

    public String popFromSongStack() {
        return songStack.pop();
    }

    public Queue<String> getSongQ() {
        return songQueue;
    }

    public void setSongQ(Queue<String> songQ) {
        songQueue = songQ;
    }


    /**
     * this method displays the songs present in the backend file on the UI, it takes the file as an argument
     */

    public AllSongs displaySongsOnThePanel(File inputFile, boolean displayAddSong) {

        String line;


        /* flag to display the ADD NEW SONG Button or not. It should be displayed only in the main panel and not in the play lists*/
        if (!displayAddSong)
            this.remove(addNewSongBtn);

        /* reads the songs from the file and stores it in an arrayList(allSongsList) */
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            allSongsList.clear();

            while ((line = br.readLine()) != null) {
                line.trim();
                if (line.isEmpty()) continue;
                allSongsList.add(line);
            }
            boolean isWhite = false;
            /* for each song in the list, create a JLabels for song name, '+' and '-' and set the to the song name. */
            for (int i = 0, yIndex = 40; i < allSongsList.size(); i++, yIndex += 30) {
                eachSongPanel = new JPanel();
                if (isWhite) {
                    eachSongPanel.setBackground(new Color(0,0,0,35));
                    isWhite = false;
                } else {
                    eachSongPanel.setBackground(new Color(0,0,0,65));
                    isWhite = true;
                }
//                eachSongPanel.setLayout(null);
                JLabel songName = new JLabel(allSongsList.get(i).split(",")[1]);
                songName.setFont(new Font("Calibri", Font.ITALIC, 15));
                JLabel deleteSongLabel = new JLabel("-");
                JLabel addLabel = new JLabel("+");
                lastSongNumber = Integer.parseInt(allSongsList.get(i).split(",")[0]);

                deleteSongLabel.setForeground(Color.black);
                addLabel.setForeground(Color.black);

//                songName.setBounds(30, y+20,200,20);
//                addLabel.setBounds(200, y+20, 10,20);
//                deleteSongLabel.setBounds(220, y+20, 10,20);
                eachSongPanel.setBounds(20, yIndex, 890, 30);
                y = y + 30;

                songName.setMinimumSize(new Dimension(30,30));
                songName.setPreferredSize(new Dimension(200,30));
                songName.setMaximumSize(new Dimension(300,30));
                addLabel.setBorder(new EmptyBorder(0, 500, 0, 0));
                deleteSongLabel.setBorder(new EmptyBorder(0, 30, 0, 0));
                deleteSongLabel.setFont(new Font("Calibri", Font.BOLD, 15));
                addLabel.setFont(new Font("Calibri", Font.BOLD, 15));

                eachSongPanel.add(songName);
                eachSongPanel.add(addLabel);
                eachSongPanel.add(deleteSongLabel);
                this.add(eachSongPanel);
//                paneScroll.add(eachSongPanel);

                int finalI = i;
                String songPath = allSongsList.get(finalI).split(",")[2];

                /**@Saishree adds the listener to the song name and '-' and '+' jlabels */
                addListener(songName,deleteSongLabel,addLabel,songPath,inputFile,finalI,displayAddSong, this);

            }

        } catch (FileNotFoundException e1) {
            System.out.println("File not fount: " + inputFile.getName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return this;
    }


    /**
     * @Saishree The listeners to the song name and '-' and '+' jlabels
     */

    public void addListener(JLabel songName, JLabel deleteSongLabel, JLabel addLabel, String songPath, File inputFile, int finalI, boolean displayAddSong, AllSongs allSongs) {

        /**  @Saishree has been modified
         * on click of the song name, it starts playing*/
        songName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                try {
                    fs.displaySongDetails(fs.fancy(songPath));
                    MusicPlayer.window.getContentPane().add(MusicPlayer.songdetailParentPanel);
                    MusicPlayer.songdetailParentPanel.revalidate();
                    MusicPlayer.songdetailParentPanel.repaint();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//

                if(songQueue.size() >0){
                    songQueue.clear();  //clearing because everytime a song is clicked, the Q & stack should be deleted.
                    songStack.clear();
                    setSongQ(songQueue);
                }
                if (audioPlayObj == null) {
                    audioPlayObj = new AudioPlay();
//                        	songNameQueue.clear();
                    songQueue.clear();  //clearing because everytime a song is clicked, the Q & stack should be deleted.
                    songStack.clear();
                } else {
                    audioPlayObj.mediaPlayer.stop();
                    playingTimer.pauseTimer();
                    playingTimer.reset();
                    audioPlayObj.mediaPlayer.dispose();
//                            songNameQueue.clear();
                    songQueue.clear();
                    songStack.clear();
                }
                try {
                    try {
                        fs.displaySongDetails(fs.fancy(songPath));
                        MusicPlayer.window.getContentPane().add(MusicPlayer.songdetailParentPanel);
                        MusicPlayer.songdetailParentPanel.revalidate();
                        MusicPlayer.songdetailParentPanel.repaint();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    audioPlayObj.setupCtrlSong(songPath);
                    playingTimer.setAudioClip(audioPlayObj.mediaPlayer.getMedia());
                    playingTimer.resumeTimer();
                    firstsongprint(songPath);
                    audioPlayObj.mediaPlayer.play();
                    audioPlayObj.autoNext(getSongQ(), audioPlayObj.mediaFile);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        /*On click of '-' removes the song from the all songs panel as well as from the file */
        /** @Saishree has been modified*/
        deleteSongLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Container parent = songName.getParent();
                parent.remove(songName);
                parent.remove(deleteSongLabel);
                parent.remove(addLabel);
                parent.remove(deleteSongLabel);
                parent.validate();
                parent.repaint();


                if (getSongQ().contains(songPath)) {
                    songQueue.remove(songPath);
                    setSongQ(songQueue);
                }

                try {
                    File tempFile = new File(MusicPlayer.commonPath + "tempFile.txt");
                    BufferedReader newBR = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
                    String lineToRemove = songName.getText();
                    String currentLine;

                    while ((currentLine = newBR.readLine()) != null) {
                        currentLine.trim();
                        if (currentLine.isEmpty()) continue;
                        String match = currentLine.split(",")[1];
                        if (match.equals(lineToRemove)) continue;
                        bw.write(currentLine + System.getProperty("line.separator"));
                    }
                    bw.close();
                    tempFile.renameTo(inputFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                allSongs.removeAll();
                displayAllSongsPanel();
                JPanel allSongsPanel = displaySongsOnThePanel(inputFile,displayAddSong);
                MusicPlayer.window.getContentPane().add(allSongsPanel);

            }
        });

        /* on click of '+', menu pops up showing the play lists*/
        final JPopupMenu popup = new JPopupMenu();

        addLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                popup.removeAll();

                /* Menu item - Queue is added to '+' and on click of queue that particular song is added to the queue and sent to the MP3 */
                JMenuItem queue = new JMenuItem("Queue");
                queue.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        songQueue.add(songPath);
                        setSongQ(songQueue);
                        printQueue(songQueue);

                    }

                    private void printQueue(Queue songQueue) {
                        String songName;
                        String songNamelist[] = new String[500];
                        int y1 = 150;
                        // TODO Auto-generated method stub
                        if (songQueue.isEmpty()) {
                            System.out.println("The queue is empty ");
                        } else {
                            MusicPlayer.showqueue.removeAll();
                            JLabel heading = new JLabel("Songs");
                            MusicPlayer.showqueue.add(heading);
                            songForQueue = new JPanel();
                            songForQueue.setPreferredSize(new Dimension(100, 30));
//        							songForQueue.setOpaque(false);
                            songForQueue.setBackground(new Color(0,0,0,35));
                            songForQueue.setLocation(650, 300);
                            y1 = y1 + 30;
                            MusicPlayer.showqueue.add(songForQueue);
                            JLabel labelPlaying = new JLabel(fs.getSongName(audioPlayObj.mediaFile[0].getSource().toString().replace("file:","").replace("%20"," ")));
                            songForQueue.add(labelPlaying);
                            MusicPlayer.showqueue.validate();
                            MusicPlayer.showqueue.repaint();
                            for (Object s : songQueue) {
                                songForQueue = new JPanel();
                                songForQueue.setPreferredSize(new Dimension(100, 30));
//        							songForQueue.setOpaque(false);
                                songForQueue.setBackground(new Color(0,0,0,35));
                                songForQueue.setLocation(650, 300);
                                y1 = y1 + 30;
                                MusicPlayer.showqueue.add(songForQueue);
                                songName = fs.getSongName(s.toString());

                                JLabel label = new JLabel(songName);
                                songForQueue.add(label);
                                MusicPlayer.showqueue.validate();
                                MusicPlayer.showqueue.repaint();

                            }

                        }
                    }


                });
                popup.add(queue);

                /* add the menu item - playlist to '+'. */
                for (int j = 0; j < playlistNamePathsList.size(); j++) {
                    JMenuItem playList = new JMenuItem(playlistNamePathsList.get(j).getPlayListName());
                    int finalJ = j;


                    playList.addActionListener(e12 -> {
                        try {
                            try (FileWriter playListFile = new FileWriter(playlistNamePathsList.get(finalJ).getPlayListPath(), true)) {
                                StringBuilder sb = new StringBuilder();
                                sb.append(finalI + 1).append(",").append(songName.getText()).append(",").append(songPath);
                                playListFile.write(sb.toString());
                                playListFile.write("\n");
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
                    popup.add(playList);
                }

                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        });
    }

    /**
     * Respond to user's click on one of the two buttons.
     */
    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == addNewSongBtn)
            listenerForAddNewSongBtn();
        else if (src == playAllBtn)
            listenerForPlayAllBtn();
    }

    /**
     * @Saishree on click of play all, it starts playing the songs in the order its is being displayed
     */
    private void listenerForPlayAllBtn() {
        List<String> tempAllSongs = new ArrayList<>();
        for (String s : allSongsList) {
            String[] temp = s.split(",");
            tempAllSongs.add(temp[2]);
        }
        if(songQueue.size()>0)
        {
            songQueue.clear();
            songStack.clear();
            setSongQ(songQueue);
        }
        songQueue.addAll(tempAllSongs);

        setSongQ(songQueue);
        printPlaylist(tempAllSongs);

        if (audioPlayObj != null) {
//            songQueue.clear();  //clearing because everytime a song is clicked, the Q & stack should be deleted.
            audioPlayObj.mediaPlayer.stop();
            audioPlayObj.mediaPlayer.dispose();
            playingTimer.pauseTimer();
            playingTimer.reset();
//                            songNameQueue.clear();
        }else {
            audioPlayObj = new AudioPlay();
        }
        try {
            songQueue=getSongQ();
            String path = songQueue.poll();
            audioPlayObj.setupCtrlSong(path);

            fs.displaySongDetails(fs.fancy(path));
            MusicPlayer.window.getContentPane().add(MusicPlayer.songdetailParentPanel);
            MusicPlayer.songdetailParentPanel.revalidate();
            MusicPlayer.songdetailParentPanel.repaint();
            audioPlayObj.mediaPlayer.play();
            playingTimer.setAudioClip(audioPlayObj.mediaPlayer.getMedia());
            playingTimer.resumeTimer();
            audioPlayObj.autoNext(getSongQ(), audioPlayObj.mediaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //surya
    public void printPlaylist(List<String> playlist) {

        // final static String allSongsFileLocation = MusicPlayer.commonPath + "AllSongs.csv";
        //File allSongsFile = new File(allSongsFileLocation);
       int y1 = 150;
      /*  String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(allSongsFile));
            allSongsList.clear();
            while ((line = br.readLine()) != null) {
                line.trim();
                if (line.isEmpty()) continue;
                allSongsList.add(line);
            }
        } catch (FileNotFoundException e1) {
        // ???? is it allSongsFile?
            System.out.println("File not fount: " + allSongsFile.getName());
        } catch (IOException e1) {
            e1.printStackTrace();
        }*/

            String songName;
        MusicPlayer.showqueue.removeAll();
        JLabel heading = new JLabel("Songs");
        MusicPlayer.showqueue.add(heading);

        for (int i=0;i<playlist.size();i++) {

                songForQueue = new JPanel();
                songForQueue.setPreferredSize(new Dimension(100, 30));
                songForQueue.setBackground(new Color(0,0,0,35));
                songForQueue.setLocation(650, 300);
                y1 = y1 + 15;
           // System.out.println("From Playlist" + playlist);
//               System.out.println(playlist.get(i).split(",")[2]);
                songName = fs.getSongName(playlist.get(i));
                JLabel label = new JLabel(songName);
                songForQueue.add(label);
                MusicPlayer.showqueue.add(songForQueue);
            MusicPlayer.showqueue.validate();
            MusicPlayer.showqueue.repaint();
            }



        }

    public void firstsongprint(String namr) {

         int y1 = 150;

        String songName;
        MusicPlayer.showqueue.removeAll();
        JLabel heading = new JLabel("Songs");
        MusicPlayer.showqueue.add(heading);

        songForQueue = new JPanel();
        songForQueue.setPreferredSize(new Dimension(100, 30));
        songForQueue.setBackground(new Color(0,0,0,35));
        songForQueue.setLocation(650, 300);
        y1 = y1 + 15;
      //  System.out.println("First Song"+ namr);
            songName = fs.getSongName(namr);
            JLabel label = new JLabel(songName);
            songForQueue.add(label);
            MusicPlayer.showqueue.add(songForQueue);
            MusicPlayer.showqueue.validate();
            MusicPlayer.showqueue.repaint();
        }



    /**
     * @Saishree adds a new song to the All songs playlist
     */

    private void listenerForAddNewSongBtn() {
        String path = " ";
        String songNameUserEntered = JOptionPane.showInputDialog("Enter the Song name");

        JFileChooser chooser = new JFileChooser();

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            // Make sure the user didn't choose a directory.
            if (f != null) {
                //get the absolute path to selected file

                try {
                    path = f.getAbsolutePath();
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(allSongsFile, true));
                    StringBuilder sb = new StringBuilder();
                    sb.append(lastSongNumber + 1).append(",").append(songNameUserEntered).append(",").append(path);
                    bufferedWriter.newLine();
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }

        JLabel newSongLabel = new JLabel(songNameUserEntered);
        JLabel deleteSongLabel = new JLabel("-");
        JLabel addLabel = new JLabel("+");

        deleteSongLabel.setForeground(Color.black);
        addLabel.setForeground(Color.black);

        eachSongPanel = new JPanel();
        eachSongPanel.setBounds(20, y, 890, 30);
        y=y+eachSongPanel.getHeight();
        eachSongPanel.setBackground(new Color(0,0,0,30));
//        eachSongPanel.setOpaque(false);
        this.add(eachSongPanel);

        newSongLabel.setFont(new Font("Calibri", Font.ITALIC, 15));

        newSongLabel.setMinimumSize(new Dimension(30,30));
        newSongLabel.setPreferredSize(new Dimension(200,30));
        newSongLabel.setMaximumSize(new Dimension(300,30));
        addLabel.setBorder(new EmptyBorder(0, 500, 0, 0));
        deleteSongLabel.setBorder(new EmptyBorder(0, 30, 0, 0));
        deleteSongLabel.setFont(new Font("Calibri", Font.BOLD, 15));
        addLabel.setFont(new Font("Calibri", Font.BOLD, 15));

        eachSongPanel.add(newSongLabel);
        eachSongPanel.add(addLabel);
        eachSongPanel.add(deleteSongLabel);
        Container parent = eachSongPanel.getParent();

        parent.validate();
        parent.repaint();





        String finalPath = path;
        newSongLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //path to be passed to the Mp3
                System.out.println(finalPath);
            }
        });

        allSongsList.add(path);

        addListener(newSongLabel,deleteSongLabel,addLabel,path,allSongsFile,allSongsList.size()+1, true, this);
    }
}
