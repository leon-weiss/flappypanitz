package name.panitz.game2d;
public interface GameObj{
  Vertex pos();
  Vertex velocity();
  double width();
  double height();

  void paintTo(java.awt.Graphics g);

  default void move(){pos().add(velocity());}

  default boolean isAbove(double y){return pos().y+height()<y;}
  default boolean isAbove(GameObj that){return isAbove(that.pos().y);}

  default boolean isUnderneath(GameObj that){return that.isAbove(this);}

  default boolean isLeftOf(double x){return pos().x+width()<x;}
  default boolean isLeftOf(GameObj that){return isLeftOf(that.pos().x);}
  default boolean isRightOf(GameObj that){return that.isLeftOf(this);}

  default boolean touches(GameObj that){
    return 
     ! (    isAbove(that)  || isUnderneath(that)
         || isLeftOf(that) || isRightOf(that)    );
  }

  default boolean isStandingOnTopOf(GameObj that) {
	    return 
	    		 !(isLeftOf(that) || isRightOf(that)) 
	    		&& isAbove(that)
	            && pos().y + height() + velocity().y+1.5 > that.pos().y;
	  }

}

