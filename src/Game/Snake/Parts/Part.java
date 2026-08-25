package Game.Snake.Parts;


import Game.Snake.Parts.Exception.InValidMethodInvokeException;

import java.awt.*;

public abstract class Part {
    protected Parts type;
    protected Point loc;
    protected Image src;
    protected Directions to;
    protected Directions from;
    protected int len;

    public Part(Directions to, Directions from) {
        this.to = to;
        this.from = from;
        len=50;
    }

    public Part(Directions to) {
        this.to = to;
        len=50;
    }
    public abstract void draw(Graphics2D g2);
    public abstract void setSrc();
    public abstract void changeLen(int a) throws InValidMethodInvokeException;

    public void setLoc(int x, int y) {
        this.loc = new Point(x, y);
    }
    public Point getLoc() {
        return loc;
    }
    public void setTo(Directions to) {
        this.to = to;
        from= getOppositeDrt(to);
    }

    public void setFrom(Directions from) {
        this.from = from;
    }

    public Parts getType() {
        return type;
    }

    public Directions getTo() {
        return to;
    }
    public Directions getFrom() {
        return from;
    }
    public int getLen() {
        return len;
    }
    public Directions getOppositeDrt(Directions drt){
        switch (drt){
            case UP:return Directions.DOWN;
            case RIGHT: return Directions.LEFT;
            case DOWN: return Directions.UP;
            default:return Directions.RIGHT;
        }
    }
    public Rectangle hitBox(){
        Rectangle r;
        if(to==Directions.UP||to==Directions.DOWN){
            r=new Rectangle(loc.x+5,loc.y+5, 50-10, len-10);
        }
        else{
            r=new Rectangle(loc.x+5,loc.y+5, len-10, 50-10);
        }
        return r;
    }
    public void setLen(int len) {
        this.len = len;
    }

}
