package MusicPlayer;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * This class represents a audio player
 *
 * @author Feij
 */
public class AudioPlayer{

    private Clip clip;

    /**
     * A constructor to create a new audio player
     * @param filePath path of the music file to be played
     * @param repetition number of times this audio should be played
     */
    public AudioPlayer(String filePath, int repetition){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if(repetition == -1)
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            else
                clip.loop(repetition);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Audio did not play correctly!");
        }
    }


    /**
     * A method to stop audio playing
     */
    public void stop(){
        clip.stop();
        clip.close();
    }

}
