package name.panitz.game2d;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

public interface Game{


  int width();
  int height();

  GameObj player();

  List<List<? extends GameObj>> goss();

  void init();


  void doChecks();

  void keyPressedReaction(KeyEvent keyEvent);

  
  default void move(){
  	if (ended()) return;
    for (var gos:goss()) gos.forEach(go -> go.move());
    player().move();
  }    

  boolean won();
  boolean lost();

  default boolean ended() {
	return won()||lost();
  }


  default void paintTo(Graphics g){
    for (var gos:goss()) gos.forEach( go -> go.paintTo(g));
    player().paintTo(g);
  }


  default void play(){
    init();
    var f = new javax.swing.JFrame();
    f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    f.add(new SwingScreen(this));
    f.pack();
    f.setVisible(true);
  }
}

