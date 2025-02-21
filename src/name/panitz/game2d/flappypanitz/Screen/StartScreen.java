package name.panitz.game2d.flappypanitz.Screen;

import name.panitz.game2d.flappypanitz.FlappyPanitz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StartScreen extends JPanel {

    private final int WIDTH = 1200;
    private final int HEIGHT = 800;

    private final FontLoader fontLoader;
    private PlayerSelector playerSelector;
    private final FlappyPanitz game;

    public StartScreen(FlappyPanitz game) {
        this.game = game;
        this.fontLoader = new FontLoader();

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 37: // Arrow Key Left
                        playerSelector.changeCharacterLeft();
                        break;
                    case 39:
                        playerSelector.changeCharacterRight();
                        break;
                    case 32:
                        startGame();
                    default:
                        break;
                }
                repaint();
            }
        });

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        Rectangle header = new Rectangle();
        header.width = WIDTH;
        header.height = 200;
        g.setColor(Color.BLACK);
        Font pixelHeaderFont = fontLoader.loadFont("pixel", "Poxast", "Arial", 64);
        Font f = fontLoader.loadFont("pixel", "Poxast", "Arial", 24);
        drawCenteredString(g, "Flappy Panitz", header, pixelHeaderFont);

        this.playerSelector = new PlayerSelector(WIDTH, header, g, f);
        StartButton startButton = new StartButton(WIDTH, playerSelector.getPlayerSelectRectangle());
        startButton.drawStartButton(g, f);

        setFocusable(true);
        requestFocus();
    }



    // Diese Methoden wurde von StackOverflow kopiert
    /**
     * Draw a String centered in the middle of a Rectangle.
     *
     * @param g    The Graphics instance.
     * @param text The String to draw.
     * @param rect The Rectangle to center the text in.
     */
    protected static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    protected static void drawXCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y;
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    public void startGame() {
        this.game.startGame(playerSelector.getCurrentCharacter());
    }
}
