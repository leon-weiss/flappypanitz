package name.panitz.game2d;
public abstract class AbstractGameObj implements GameObj{
  protected Vertex pos;
  protected Vertex velocity;
  protected double width;
  protected double height;

  public Vertex pos(){return pos;}
  public Vertex velocity(){return velocity;}
  public double width(){return width;}
  public double height(){return height;}

  public AbstractGameObj(Vertex p, Vertex v, double w, double h){
    pos=p; velocity=v; width=w; height=h;
  }
}

