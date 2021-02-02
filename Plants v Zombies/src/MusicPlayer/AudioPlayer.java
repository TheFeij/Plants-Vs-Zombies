package MusicPlayer;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer{

    private Clip clip;

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
