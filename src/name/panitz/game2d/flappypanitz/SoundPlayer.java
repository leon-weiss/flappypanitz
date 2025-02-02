package name.panitz.game2d.flappypanitz;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class SoundPlayer {

    private static Clip backgroundClip;

    /*
    Diese Methode ist von ChatGPT generiert, mit nur leichten Anpassungen von mir
    und ich bin begeistert, weil die mein Problem gelöst hat,
    für welches ich bei StackOverflow keine Antworten gefunden habe!
     */
    /**
     * Diese Methode liest den AudioInputStream ein und wandelt ihn in ein Format um,
     * das von der Audio-Hardware unterstützt wird.
     *
     * @param filePath Pfad zur Audiodatei (z. B. "/assets/audio/background.wav")
     * @return einen konvertierten AudioInputStream
     * @throws UnsupportedAudioFileException, IOException
     */
    private static AudioInputStream getDecodedAudioInputStream(String filePath)
            throws UnsupportedAudioFileException, IOException {
        // Original-Stream aus dem Klassenpfad lesen
        AudioInputStream in = AudioSystem.getAudioInputStream(Objects.requireNonNull(SoundPlayer.class.getResource(filePath)));
        AudioFormat baseFormat = in.getFormat();

        // Bestimmen der Ziel-Samplerate:
        // Falls die originale Samplerate zu hoch ist, verwenden wir 44100 Hz, ansonsten die Originalrate.
        float targetSampleRate = baseFormat.getSampleRate();
        if (targetSampleRate > 48000) {
            targetSampleRate = 44100;
        }

        // Erzeugen eines AudioFormats mit PCM_SIGNED, 16 Bit, gleichen Kanalanzahl und der Ziel-Samplerate.
        AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                targetSampleRate,
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                targetSampleRate,
                false
        );

        // Konvertierung: Der neue AudioInputStream liefert nun die Daten in dem gewünschten Format.
        return AudioSystem.getAudioInputStream(decodedFormat, in);
    }

    public static void playBackgroundMusic(String filePath) {
        try {
            AudioInputStream din = getDecodedAudioInputStream(filePath);
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
            AudioInputStream din = getDecodedAudioInputStream(filePath);
            Clip clip = AudioSystem.getClip();
            clip.open(din);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println(e.getMessage());
        }
    }
}

