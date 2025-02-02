package name.panitz.game2d.flappypanitz;

import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player implements GameObj {
    private String name;

    private Vertex position;
    private Vertex vel;

    private double w;
    private double h;

    private double gravity;

    private BufferedImage image;

    public BufferedImage getImage() {
        return image;
    }

    public Player(int x, int y, int width, int height, double gravity, Character selectedCharacter) {
        this.position = new Vertex(x, y);
        this.vel = new Vertex(0, 0);
        this.w = width;
        this.h = height;
        this.gravity = gravity;
        this.name = selectedCharacter.getName();

        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResource(selectedCharacter.get32pxResolution())));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    public Player(Character character, Vertex position) {
        try {
            this.image = ImageIO.read(Objects.requireNonNull(getClass().getResource(character.get128pxResolution())));
            this.name = character.getName();
            this.position = position;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void flap(double flapStrength) {
        vel.y = flapStrength;
    }

    @Override
    public Vertex pos() {
        return position;
    }

    @Override
    public Vertex velocity() {
        return vel;
    }

    @Override
    public double width() {
        return (image != null) ? image.getWidth() : 50;
    }

    @Override
    public double height() {
        return (image != null) ? image.getHeight() : 50;
    }

    @Override
    public void paintTo(Graphics g) {
        if (image != null) {
            g.drawImage(image, (int) position.x, (int) position.y, (int) w, (int) h, null);
        } else {
            // Ja, keine Ahnung...wenn das Bild nicht geladen werden kann,
            // dann ist halt irgendwie doof...
            // Dann muss man halt einen roten Blob spielen
            g.setColor(Color.red);
            g.fillRect((int) position.x, (int) position.y, (int) w, (int) h);
        }
    }

    @Override
    public void move() {
        vel.add(new Vertex(0, gravity));
        pos().add(velocity());
    }
}
