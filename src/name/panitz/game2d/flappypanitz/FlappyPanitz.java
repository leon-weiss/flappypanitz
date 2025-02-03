package name.panitz.game2d.flappypanitz;

import name.panitz.game2d.*;
import name.panitz.game2d.flappypanitz.Screen.StartScreen;

import javax.swing.*;

public class FlappyPanitz {
    JFrame f;

    public static String playerNameForUpload;

    public static void main(String[] args) {
        int uploadOption = JOptionPane.showConfirmDialog(
                null,
                "Möchten Sie Ihre Spieldaten hochladen?",
                "Daten hochladen",
                JOptionPane.YES_NO_OPTION);

        if (uploadOption == JOptionPane.YES_OPTION) {
            String input = JOptionPane.showInputDialog(
                    null,
                    "Bitte geben Sie Ihren Spielernamen ein:",
                    "Spielername",
                    JOptionPane.PLAIN_MESSAGE);
            if (input != null && !input.trim().isEmpty()) {
                playerNameForUpload = truncateString(input.trim(), 100);
                System.out.println("Spielername für den Upload: " + playerNameForUpload);
            } else {
                playerNameForUpload = "Unbekannt";
                System.out.println("Kein Name eingegeben, Standardname wird verwendet: " + playerNameForUpload);
            }
        } else {
            System.out.println("Datenupload wird nicht durchgeführt.");
        }

        new FlappyPanitz();
    }

    public FlappyPanitz() {
        f = new JFrame("Flappy Panitz");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1200, 800);
        f.setLocationRelativeTo(null);

        StartScreen startScreen = new StartScreen(this);
        f.setContentPane(startScreen);

        f.setVisible(true);
    }

    public void startGame(Character selectedCharacter) {
        FlappyGame game = new FlappyGame(selectedCharacter);
        SwingScreen gameScreen = new SwingScreen(game);

        f.setIgnoreRepaint(true);
        f.createBufferStrategy(3);

        f.setContentPane(gameScreen);
        f.revalidate();
        f.repaint();

        gameScreen.requestFocusInWindow();
    }

    @SuppressWarnings("all") // Der Unicode-Character …
    public static String truncateString(String text, int length) {
        if (text.length() <= length) {
            return text;
        }

        return text.substring(0, length) + '\u2026';
    }

}
