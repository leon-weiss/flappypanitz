package name.panitz.game2d.flappypanitz.Screen;

import java.awt.*;

public class StartButton {

    private final int startButtonX;
    private final int startButtonY;
    private final int startButtonWidth;
    private final int startButtonHeight;

    public StartButton(int screenWidth, Rectangle upperRect) {
        super();

        startButtonWidth = 500;
        startButtonHeight = 80;
        startButtonX = (screenWidth - startButtonWidth) / 2;
        startButtonY = upperRect.y + upperRect.height + 70;

    }

    public void drawStartButton(Graphics g, Font font) {
        Rectangle startButton = new Rectangle();

        startButton.width = startButtonWidth;
        startButton.height = startButtonHeight;
        startButton.x = startButtonX;
        startButton.y = startButtonY;

        g.setColor(Color.decode("#F2D537"));
        g.fillRect(startButtonX, startButtonY, startButtonWidth, startButtonHeight);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRect(startButtonX - 2, startButtonY - 2, startButtonWidth + 4, startButtonHeight + 4);
        StartScreen.drawCenteredString (g2, "SPACE zum Starten", startButton, font);
    }
}
