package jamify;

import javafx.scene.media.Media;

import javax.swing.*;

public class PlayingTimer extends Thread {
    private boolean isRunning = false;
    private boolean isPause = false;
    private boolean isReset = false;
    private long startTime;
    private long pauseTime;

    private JSlider slider;
    private Media audioClip;

    public void setAudioClip(Media mediaPlayer) {
        this.audioClip = mediaPlayer;
    }

    PlayingTimer(JSlider slider) {
        this.slider = slider;
    }

    long currentTime = 0;

    public void run() {
        isRunning = true;

        startTime = System.currentTimeMillis();

        while (isRunning) {
            try {
                Thread.sleep(100);

                if (!isPause) {

                    if (audioClip != null && audioClip.getDuration().toMillis() >= currentTime) {
                        currentTime += 1;
                        slider.setValue((int) currentTime);
                    }
                } else {
                    pauseTime += 100;
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
                if (isReset) {
                    slider.setValue(0);
                    isRunning = false;
                    break;
                }
            }
        }
    }


    /**
     * Reset counting to "00:00:00"
     */

    void reset() {
        isReset = true;
        if (isReset) {
            slider.setValue(0);
            currentTime = 0;
            isReset = false;
        }
    }

    void pauseTimer() {
        isPause = true;
    }

    void resumeTimer() {
        isPause = false;

    }

    void forwardSeek(double v) {
        currentTime = (long) (currentTime * v);
    }

    void rewindSeek(double v) {
        currentTime = (long) (currentTime / v);
    }

    void moveSeek (long value){
        currentTime = (long) value;
    }
}


