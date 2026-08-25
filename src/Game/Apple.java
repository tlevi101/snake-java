package Game;

import Game.Snake.Parts.Part;
import Game.Snake.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Apple {
    private Point loc;
    private Image src;
    public Apple(Snake snk, int width, int height, Rocks rocks) {
        boolean found_a_place=false;
        while (!found_a_place){
            boolean isFree = true;
            Random rnd = new Random();
            int x = rnd.nextInt(width-50);
            x= x-x%50;
            int y = rnd.nextInt(height-50);
            y=y-y%50;
            Rectangle appleBox = new Rectangle(x, y, 50, 50);
            for (Part p : snk.getBody()) {
                Rectangle part = p.hitBox();
                isFree = isFree && !appleBox.intersects(part);
            }
            for (Rock r:rocks.getRocks()) {
                Rectangle rock = r.hitBox();
                isFree = isFree && !appleBox.intersects(rock);
            }
            found_a_place=isFree;
            if(found_a_place){
                loc=new Point(x,y);
            }
        }
        try {
            src= ImageIO.read(getClass().getResource("Snake/Parts/sprites/apple.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Rectangle hitBox(){
        return new Rectangle(loc.x, loc.y, 50, 50);
    }
    public boolean eaten(Snake snk){
        return hitBox().intersects(snk.getBody().get(0).hitBox());
    }
    public void draw(Graphics2D g2){
        g2.drawImage(src, loc.x, loc.y,50, 50, null);
    }
}
