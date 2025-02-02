package name.panitz.game2d.flappypanitz.Screen;

import name.panitz.game2d.ImageObject;
import name.panitz.game2d.Vertex;
import name.panitz.game2d.flappypanitz.Character;
import name.panitz.game2d.flappypanitz.Player;

import java.awt.*;

import static name.panitz.game2d.flappypanitz.Screen.StartScreen.drawXCenteredString;

public class PlayerSelector {
    private Player[] players;
    private static int currentSelect;
    private final Rectangle playerSelect;

    public PlayerSelector(int screenWidth, Rectangle upperRect, Graphics g, Font font) {
        super();

        playerSelect = new Rectangle();
        playerSelect.width = screenWidth;
        playerSelect.y = upperRect.height;
        playerSelect.height = 400;
        drawXCenteredString(g, "Wähle deine Spielfigur!", playerSelect, font);

        Vertex center = new Vertex(playerSelect.x + (double) (playerSelect.width - 128) / 2, playerSelect.y + 200);
        Vertex left = new Vertex((playerSelect.x + (double) (playerSelect.width - 128) / 2) - 300, playerSelect.y + 200);
        Vertex right = new Vertex((playerSelect.x + (double) (playerSelect.width - 128) / 2) + 300, playerSelect.y + 200);
        ImageObject downarrow = new ImageObject("assets/edits/exports/scaled/128px/downarrow.png");
        downarrow.pos().moveTo(new Vertex(playerSelect.x + (double) (playerSelect.width - 128) / 2, playerSelect.y + 45));
        downarrow.paintTo(g);
        drawCharacterSelector(left, center, right, g, font);
    }

    public Rectangle getPlayerSelectRectangle() {
        return playerSelect;
    }

    private Player[] loadCharacters() {
        if (this.players != null) {
            return this.players;
        }

        Player[] players = new Player[Character.values().length];

        for (int i = 0; i < name.panitz.game2d.flappypanitz.Character.values().length; i++) {
            players[i] = new Player(Character.values()[i], new Vertex(0, 0));
        }

        this.players = players;
        return players;
    }

    private void drawCharacterSelector(Vertex left, Vertex center, Vertex right, Graphics g, Font pixelTextFont) {
        Player[] loadedPlayers = loadCharacters();

        int previousIndex = (currentSelect - 1 + loadedPlayers.length) % loadedPlayers.length;
        int nextIndex = (currentSelect + 1) % loadedPlayers.length;

        Player prev = loadedPlayers[previousIndex];
        Player current = loadedPlayers[currentSelect];
        Player next = loadedPlayers[nextIndex];

        g.drawImage(
                prev.getImage(),
                (int)left.x,
                (int)left.y,
                128,
                128,
                null
        );
        g.drawImage(
                current.getImage(),
                (int)center.x,
                (int)center.y,
                128,
                128,
                null
        );
        g.drawImage(
                next.getImage(),
                (int)right.x,
                (int)right.y,
                128,
                128,
                null
        );



        g.setFont(pixelTextFont);
        g.setColor(Color.BLACK);
        FontMetrics metrics = g.getFontMetrics(pixelTextFont);

        int textYOffset = 5;

        int prevTextX = (int) (left.x + (prev.width() - metrics.stringWidth(prev.getName())) / 2);
        int prevTextY = (int) (left.y + 128 + textYOffset + metrics.getAscent());

        int currentTextX = (int) (center.x + (current.width() - metrics.stringWidth(current.getName())) / 2);
        int currentTextY = (int) (center.y + 156 + textYOffset + metrics.getAscent());

        int nextTextX = (int) (right.x + (next.width() - metrics.stringWidth(next.getName())) / 2);
        int nextTextY = (int) (right.y + 128 + textYOffset + metrics.getAscent());

        g.drawString(prev.getName(), prevTextX, prevTextY);
        g.drawString(current.getName(), currentTextX, currentTextY);
        g.drawString(next.getName(), nextTextX, nextTextY);
    }


    public Character getCurrentCharacter() {
        return Character.values()[currentSelect];
    }

    public void changeCharacterLeft() {
        currentSelect = (currentSelect - 1 + loadCharacters().length) % loadCharacters().length;
    }

    public void changeCharacterRight() {
        currentSelect = (currentSelect + 1) % loadCharacters().length;
    }

}
