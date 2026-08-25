package Game.Snake.Parts;

import Game.Snake.Parts.Exception.InValidMethodInvokeException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Curve extends Part {
    public Curve(Directions to, Directions from) {
        super(to, from);
        type=Parts.CURVE;
        setSrc();
    }
    @Override
    public void setSrc() {
        String path="sprites/curve";
            switch (from) {
                case UP:
                    if(to==Directions.LEFT){
                        path+="1";
                    }
                    else if(to==Directions.RIGHT){
                        path+="3";
                    }
                    break;
                case RIGHT:
                    if(to==Directions.UP){
                        path+="3";
                    }
                    else if(to==Directions.DOWN){
                        path+="2";
                    }
                    break;
                case LEFT:
                    if(to==Directions.UP){
                        path+="1";
                    }
                    else if(to==Directions.DOWN){
                        path+="0";
                    }
                    break;
                case DOWN:
                    if(to==Directions.LEFT){
                        path+="0";
                    }
                    else if(to==Directions.RIGHT){
                        path+="2";
                    }
                    break;
            }
            path+=".png";
        try {
            src = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(src, loc.x, loc.y, len, len, null);
    }

    @Override
    public void changeLen(int a) throws InValidMethodInvokeException {
        throw new InValidMethodInvokeException();
    }
}
