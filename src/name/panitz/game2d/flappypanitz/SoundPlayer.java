package name.panitz.game2d.flappypanitz;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

// Die Methoden in dieser Klasse wurden teilweise mithilfe von StackOverflow entwickelt
public class SoundPlayer {

    private static Clip backgroundClip;
    private static Clip jumpClip;
    private static Clip gameOverClip;

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

    public static void playJumpSoundEffect() {
        try {
            if(jumpClip != null) {
                jumpClip.stop();
                jumpClip.setMicrosecondPosition(0);
                jumpClip.start();
                return;
            }
            AudioInputStream din = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource("/assets/audio/jump.wav")));
            jumpClip = AudioSystem.getClip();
            jumpClip.open(din);
            jumpClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
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

    public static void playGameOverSoundEffect() {
        try {
            if(gameOverClip != null) {
                gameOverClip.stop();
                gameOverClip.setMicrosecondPosition(0);
                gameOverClip.start();
                return;
            }
            AudioInputStream din = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource("/assets/audio/gameover.wav")));
            gameOverClip = AudioSystem.getClip();
            gameOverClip.open(din);
            gameOverClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }
}

