package jamify;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * Created by SuryaRajasekaran on 5/27/18.
 */
public class AudioPlay extends AllSongs {

    //variables
    public MediaPlayer mediaPlayer;
    public Media[] mediaFile;

    //constructor
    public AudioPlay() {
        mediaFile = new Media[]{};
    }

    //Media controller
    public void setupCtrlSong(String path) throws Exception {

        final JFXPanel fxPanel = new JFXPanel(); //needed for mediaplayer to function

        mediaFile = new Media[]{new Media(new File(path).toURI().toString())};
        mediaPlayer = new MediaPlayer(mediaFile[0]);

        /*plays the song and continues to play next song after first song is completed*/
        MusicPlayer.play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /* try {
                    fs.fancy(mediaFile[0].getSource().toString().replace("file:","").replace("%20"," "));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } */
                mediaPlayer.play();
                autoNext(AudioPlay.this.getSongQ(), mediaFile);
            }
        });

        MusicPlayer.pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.pause();
            }
        });

        MusicPlayer.forward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.5));
            }
        });

        MusicPlayer.rewind.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.5));
            }
        });

        MusicPlayer.stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.stop();
            }
        });

        /*next song is played when Songs are there in Queue*/
        if((MusicPlayer.next.getActionListeners().length<1)) {
            MusicPlayer.next.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!getSongQ().isEmpty()) {
                        addToSongStack(mediaFile[0].getSource()); //add current song path to stack
                        mediaPlayer.stop();
                        mediaPlayer.dispose();
                        String playSong = getSongQ().poll(); //get next song from Q and delete it from Q
                        if (playSong.contains("file")) {
                            mediaFile[0] = new Media(new File(playSong).toString());
                        } else {
                            mediaFile[0] = new Media(new File(playSong).toURI().toString());
                        }
                        mediaPlayer = new MediaPlayer(mediaFile[0]);   //play the next song from Q
                        try {
                            fs.fancy(mediaFile[0].getSource().toString().replace("file:", "").replace("%20", " "));
                            fs.displaySongDetails(fs.fancy(path));
                            MusicPlayer.window.getContentPane().add(MusicPlayer.songdetailParentPanel);
                            MusicPlayer.songdetailParentPanel.revalidate();
                            MusicPlayer.songdetailParentPanel.repaint();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        mediaPlayer.play();
                        autoNext(getSongQ(), mediaFile);  //ensures that next song in Q is played automatically without clicking next
                    } else {
                        //POPUP Saying no songs to play next in the queue?
                    }
                }
            });
        }
        if((MusicPlayer.previous.getActionListeners().length<1)) {
            MusicPlayer.previous.addActionListener(e -> {
                if (!getSongStack().isEmpty()) {
                    addToQueueStart(mediaFile[0].getSource());  //add the current song to Q
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                    String playSong = popFromSongStack();  //get prev song from stack
                    if (playSong.contains("file")) {
                        mediaFile[0] = new Media(new File(playSong).toString());
                    } else {
                        mediaFile[0] = new Media(new File(playSong).toURI().toString());
                    }
                    mediaPlayer = new MediaPlayer(mediaFile[0]);
                    try {
                        fs.fancy(mediaFile[0].getSource().toString().replace("file:", "").replace("%20", " "));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    mediaPlayer.play();
                    autoNext(getSongQ(), mediaFile);
                } else {
                    //Should we have a POPUP Saying no previous songs to play?
                }
            });
        }
        /*Clicking on repeat plays the same song repeatly forever till Stopped.*/
        MusicPlayer.repeat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayer.setOnEndOfMedia(new Runnable() {
                    public void run() {
                        mediaPlayer.seek(Duration.ZERO);
                    }
                });
            }
        });

        /*Songs are shuffled randomly*/
        //shuffle is the button here
        MusicPlayer.shuffle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Queue shuffledQ = getSongQ();
                Collections.shuffle((List<?>) shuffledQ);
                setSongQ(shuffledQ);
                if (!getSongQ().isEmpty()) {
                    addToSongStack(mediaFile[0].getSource());
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                    //String playSong = getSongQ().peek();
                    String playSong = getSongQ().poll();
                    if (playSong.contains("file")){
                        mediaFile[0] = new Media(new File(playSong).toString());
                    } else {
                        mediaFile[0] = new Media(new File(playSong).toURI().toString());
                    }
                    mediaPlayer = new MediaPlayer(mediaFile[0]);
                   /* try {
                        fs.fancy(mediaFile[0].getSource().toString().replace("file:","").replace("%20"," "));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }*/
                    //CHECK IF AUTONEXT IN SHUFFLE SHOWS METDATA AFTER REVOVING PREV CODE LINEES :YES
                    mediaPlayer.play();
                    autoNext(getSongQ(), mediaFile);
                } else {
                    //POPUP Saying no songs in Q to shuffle?
                }
            }
        });

    }

    /*autonext function*/
    public void autoNext(Queue<String> songQueue, Media[] mediaFile) {
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (!getSongQ().isEmpty()) {
                    addToSongStack(mediaFile[0].getSource());
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                    String playSong = getSongQ().poll();
                    if (playSong.contains("file")){
                        mediaFile[0] = new Media(new File(playSong).toString());
                    } else {
                        mediaFile[0] = new Media(new File(playSong).toURI().toString());
                    }
                    mediaPlayer = new MediaPlayer(mediaFile[0]);
                    try {
                        fs.fancy(mediaFile[0].getSource().toString().replace("file:","").replace("%20"," "));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.play();
                    autoNext(getSongQ(), mediaFile);
                } else {
                    //POPUP Saying no songs to play next in the queue?
                }
            }
        });
    }
}