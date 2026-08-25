package Game;

import Game.Snake.Parts.Part;
import Game.Snake.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Rocks {
    private ArrayList<Rock> rocks;

    public Rocks(int count, Snake snk, int width, int height) {
        this.rocks = new ArrayList<>();
        Random rnd=new Random();
        String path="Snake/Parts/sprites/rock"+rnd.nextInt(0,2)+".png";
        boolean found_a_place=false;
        while (rocks.size()!=count){
            boolean isFree = true;
            int x = rnd.nextInt(50,width-100);
            x= x-x%50;
            int y = rnd.nextInt(50,height-100);
            y=y-y%50;
            Rectangle rockBox = new Rectangle(x-50, y-50, 50+100, 50+100);
            for (Part p : snk.getBody()) {
                isFree = isFree && !rockBox.intersects(p.hitBox());
            }
            if(rocks.size()!=0){
                rockBox = new Rectangle(x-205, y-205, 50+410, 50+410);
                for (Rock o:rocks) {
                    isFree = isFree && !rockBox.intersects(o.hitBox());
                }
            }
            if(isFree){
                rocks.add(new Rock(x,y));
            }
        }
    }

    public ArrayList<Rock> getRocks() {
        return rocks;
    }
}
