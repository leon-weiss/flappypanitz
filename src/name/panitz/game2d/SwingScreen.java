package name.panitz.game2d;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class SwingScreen extends JPanel{
  private static final long serialVersionUID = 1403492898373497054L;
  Game logic;
  Timer t;

  private static SwingScreen instance;

  public SwingScreen(Game gl) {
    this.logic = gl;

    instance = this;

    t = new Timer(13, (ev)->{
        logic.move();
        logic.doChecks();
        repaint();
        getToolkit().sync();
        requestFocus();
      });
      t.start();

		
    addKeyListener(new KeyAdapter() {	
        @Override public void keyPressed(KeyEvent e) {
          logic.keyPressedReaction(e);
        }
      });
    setFocusable(true);
    requestFocus();
    }

    public static SwingScreen getInstance() {
      return instance;
    }

	
  @Override public Dimension getPreferredSize() {
    return new Dimension((int)logic.width(),(int)logic.height());
  }

	
  @Override protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    logic.paintTo(g);
  }
}

