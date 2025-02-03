package name.panitz.game2d.flappypanitz;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SoundPlayer {

    private static Clip backgroundClip;

    public static void playBackgroundMusic(String filePath) {
        try {
            AudioInputStream din = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource(filePath)));
            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(din);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    public static void playSoundEffect(String filePath) {
        try {
            AudioInputStream din = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource(filePath)));
            Clip clip = AudioSystem.getClip();
            clip.open(din);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }
}

