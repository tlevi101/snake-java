package Game.Snake.Parts;

import Game.Snake.Parts.Exception.InValidMethodInvokeException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Head extends Part {
    public Head(Directions to) {
        super(to);
        from= getOppositeDrt(to);
        type = Parts.HEAD;
        setSrc();
    }
    @Override
    public void setSrc() {
        try {
            switch (to) {
                case UP:
                    src = ImageIO.read(getClass().getResource("sprites/head_up.png"));
                    break;
                case RIGHT:
                    src = ImageIO.read(getClass().getResource("sprites/head_right.png"));
                    break;
                case LEFT:
                    src = ImageIO.read(getClass().getResource("sprites/head_left.png"));
                    break;
                case DOWN:
                    src = ImageIO.read(getClass().getResource("sprites/head_down.png"));
                    break;
            }
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
