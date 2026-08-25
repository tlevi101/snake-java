package Game.Snake.Parts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Straight extends Part {
    public Straight(Directions to) {
        super(to);
        type=Parts.STRAIGHT;
        from= getOppositeDrt(to);
        setSrc();
    }
    @Override
    public void setSrc() {
        String path = "sprites/body";
        if(to==Directions.DOWN || to==Directions.UP){
            path += "1";
        }
        else{
            path += "0";
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
        if(to==Directions.UP||to==Directions.DOWN){
            g2.drawImage(src, loc.x, loc.y, 50, len, null);
        }
        else{
            g2.drawImage(src, loc.x, loc.y, len, 50, null);
        }
    }

    @Override
    public void changeLen(int a) {
        len+=a;
    }

    public int getLen() {
        return len;
    }
    public void setLen(int len) {
        this.len = len;
    }

}
