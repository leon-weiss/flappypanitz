package name.panitz.game2d.flappypanitz;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.Vertex;

import java.awt.*;

public class Pipe implements GameObj {
    private final Vertex position;
    private final double w;
    private final double gapY;
    private final double gapHeight;
    private final double panelHeight;
    private final double speed;

    public boolean passed = false;

    public Pipe(double x, double gapY, double w, double gapHeight, double panelHeight, double speed) {
        this.position = new Vertex(x, 0);
        this.w = w;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
        this.panelHeight = panelHeight;
        this.speed = speed;
    }

    @Override
    public Vertex pos() {
        return position;
    }

    @Override
    public Vertex velocity() {
        return new Vertex(-speed, 0);
    }

    @Override
    public double width() {
        return w;
    }

    @Override
    public double height() {
        return panelHeight;
    }

    @Override
    public void paintTo(Graphics g) {
        g.setColor(Color.green.darker());
        // Oberer Teil
        g.fillRect((int) position.x, 0, (int) w, (int) gapY);

        // Unterer Teil
        g.fillRect((int) position.x, (int) (gapY + gapHeight), (int) w,
                (int) (panelHeight - (gapY + gapHeight)));
    }

    @Override
    public void move() {
        pos().add(velocity());
    }

    public Rectangle getTopRect() {
        return new Rectangle((int) position.x, 0, (int) w, (int) gapY);
    }

    public Rectangle getBottomRect() {
        return new Rectangle((int) position.x, (int) (gapY + gapHeight),
                (int) w, (int) (panelHeight - (gapY + gapHeight)));
    }
}