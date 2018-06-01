package jamify;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class AllSongs extends JPanel implements ActionListener {
    AudioPlay audioPlayObj;  //surya
    FancyStuff fs = new FancyStuff();
    JButton addNewSongBtn = new JButton("Add New Song");

    JButton playAllBtn = new JButton("Play All");
    JPanel allSongsPanel;
    ArrayList<String> allSongsList = new ArrayList<>();
    final static String allSongsFileLocation = MusicPlayer.commonPath + "AllSongs.csv";
    File allSongsFile = new File(allSongsFileLocation);
    static ArrayList<PlaylistNamePath> playlistNamePathsList = new ArrayList<>();
    //to keep track of the last song number
    int lastSongNumber = 0;
    int y = 40;

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
        setBounds(20, 20, 350, 550);
        add(playAllBtn);
        playAllBtn.setBounds(100, 10, 100, 20);
        add(addNewSongBtn);
        playAllBtn.addActionListener(this);
        add(addNewSongBtn);
        addNewSongBtn.setBounds(280, 10, 100, 20);
        addNewSongBtn.addActionListener(this);
        setVisible(true);
        this.revalidate();
        this.repaint();
    }

    //    static volatile Queue<String> songNameQueue=new LinkedList<String>();
//getters and setters
    public void addToQueueStart(String file) {
        Queue<String> tempSongQueue = new LinkedList<String>();
        tempSongQueue.add(file);
        while (songQueue.size() > 0) {
            tempSongQueue.add(songQueue.poll());
        }
        songQueue = tempSongQueue;
    }

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
                    eachSongPanel.setBackground(Color.WHITE);
                    isWhite = false;
                } else {
                    eachSongPanel.setBackground(Color.lightGray);
                    isWhite = true;
                }
//                eachSongPanel.setLayout(null);
                JLabel songName = new JLabel(allSongsList.get(i).split(",")[1]);
                JLabel deleteSongLabel = new JLabel("-");
                JLabel addLabel = new JLabel("+");
                lastSongNumber = Integer.parseInt(allSongsList.get(i).split(",")[0]);

                deleteSongLabel.setForeground(Color.BLUE);
                addLabel.setForeground(Color.BLUE);

//                songName.setBounds(30, y+20,200,20);
//                addLabel.setBounds(200, y+20, 10,20);
//                deleteSongLabel.setBounds(220, y+20, 10,20);
                eachSongPanel.setBounds(20, yIndex, 350, 30);
                y = y + 30;

                eachSongPanel.add(songName);
                eachSongPanel.add(deleteSongLabel);
                eachSongPanel.add(addLabel);
                this.add(eachSongPanel);

                int finalI = i;
                String songPath = allSongsList.get(finalI).split(",")[2];

                /**@Saishree adds the listener to the song name and '-' and '+' jlabels */
                addListener(songName, deleteSongLabel, addLabel, songPath, inputFile, finalI);

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

    public void addListener(JLabel songName, JLabel deleteSongLabel, JLabel addLabel, String songPath, File inputFile, int finalI) {

        /**  @Saishree has been modified
         * on click of the song name, it starts playing*/
        songName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                try {
                    JPanel songDetailsPanel = displaySongDetails(fs.fancy(songPath));
                    MusicPlayer.window.getContentPane().add(songDetailsPanel);
                    songDetailsPanel.revalidate();
                    songDetailsPanel.repaint();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
//                String Name = fs.getSongName(songPath);
//                JLabel songName = new JLabel(Name);
//                songName.setBounds(100, 300, 30, 10);
//                MusicPlayer.window.add(songName);
                if (audioPlayObj == null) {
//                        	songNameQueue.clear();
                    songQueue.clear();  //clearing because everytime a song is clicked, the Q & stack should be deleted.
                    songStack.clear();
                    audioPlayObj = new AudioPlay();
                } else {
                    audioPlayObj.mediaPlayer.stop();
                    audioPlayObj.mediaPlayer.dispose();
//                            songNameQueue.clear();
                    songQueue.clear();
                    songStack.clear();
                    audioPlayObj = new AudioPlay();
                }
                try {
                    audioPlayObj.setupCtrlSong(songPath);


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
                    File tempFile = new File(MusicPlayerUI.commonPath + "tempFile.txt");
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
                        // fs.getSongName();
//                                songNameQueue.add((allSongsList.get(finalI1).split(",")[1]));

//                              System.out.println(songNameQueue.peek()+"---------thisssssssss");
//                              // call a function which will have parameterSongNameQueue
//                              printQueue(songNameQueue);

//                              System.out.println(songNameQueue.peek());

                        setSongQ(songQueue);
                        printQueue(songQueue);
                        System.out.println("test" + songQueue.peek());
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
                            for (Object s : songQueue) {
                                songForQueue = new JPanel();
                                songForQueue.setPreferredSize(new Dimension(100, 30));
//        							songForQueue.setOpaque(false);
                                songForQueue.setLocation(650, 300);
                                y1 = y1 + 30;
                                MusicPlayer.showqueue.add(songForQueue);
//     							        System.out.println(s.toString());
                                songName = fs.getSongName(s.toString());

                                JLabel label = new JLabel(songName);
                                songForQueue.add(label);
                                MusicPlayer.showqueue.validate();
                                MusicPlayer.showqueue.repaint();

                                // there is function in the same class called, songName.addmouselistener
                                // in this case we can use the same function as label.addmouselisener,
                                //instead when Surya, u use the songname, please use the same variable as songName, then we
                                // we might be ableto use same function

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

    private JPanel displaySongDetails(SongDetails songDetails) {

        JPanel songdetailPanel = new JPanel();
        songdetailPanel.setLocation(100, 500);
        songdetailPanel.setPreferredSize(new Dimension(50, 50));
        songdetailPanel.setLayout(new FlowLayout());
        songdetailPanel.setForeground(Color.WHITE);
        songdetailPanel.setOpaque(true);
        songdetailPanel.setVisible(true);
        JLabel labelTitle = new JLabel(songDetails.getTitle());
        JLabel labelArtist = new JLabel(songDetails.getArtist());
        JLabel labelComposer = new JLabel(songDetails.getComposer());
        JLabel labelGenre = new JLabel(songDetails.getGenre());
        JLabel labelAlbum = new JLabel(songDetails.getAlbum());
        JLabel labelYear = new JLabel(songDetails.getYear());

        songdetailPanel.add(labelTitle);
        songdetailPanel.add(labelArtist);
        songdetailPanel.add(labelComposer);
        songdetailPanel.add(labelGenre);
        songdetailPanel.add(labelAlbum);
        songdetailPanel.add(labelYear);
        return songdetailPanel;

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
        songQueue.addAll(tempAllSongs);
        if (audioPlayObj == null) {
//                        	songNameQueue.clear();
            audioPlayObj = new AudioPlay();
        } else {
            audioPlayObj.mediaPlayer.stop();
            audioPlayObj.mediaPlayer.dispose();
//                            songNameQueue.clear();
            audioPlayObj = new AudioPlay();
        }
        try {
            String path = songQueue.poll();
            audioPlayObj.setupCtrlSong(path);
            fs.fancy(path);

            audioPlayObj.mediaPlayer.play();
            audioPlayObj.autoNext(getSongQ(), audioPlayObj.mediaFile);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * @Saishree adds a new song to the All songs playlist
     */

    private void listenerForAddNewSongBtn() {
        String path = " ";
        String songNameUserEntered = JOptionPane.showInputDialog("Enter the Song name");
        JLabel newSongLabel = new JLabel(songNameUserEntered);
        JLabel deleteSongLabel = new JLabel("-");
        JLabel addLabel = new JLabel("+");

        deleteSongLabel.setForeground(Color.BLUE);
        addLabel.setForeground(Color.BLUE);

        eachSongPanel = new JPanel();
        eachSongPanel.setBounds(20, y, 350, 30);
        this.add(eachSongPanel);

        eachSongPanel.add(newSongLabel);
        eachSongPanel.add(deleteSongLabel);
        eachSongPanel.add(addLabel);
        Container parent = eachSongPanel.getParent();

        parent.validate();
        parent.repaint();

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

        addListener(newSongLabel, deleteSongLabel, addLabel, path, allSongsFile, allSongsList.size() + 1);
    }
}
