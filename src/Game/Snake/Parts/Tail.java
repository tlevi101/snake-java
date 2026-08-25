package Game.Snake.Parts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Tail extends Part {

    public Tail(Directions to) {
        super(to);
        type = Parts.TAIL;
        from= getOppositeDrt(to);
        setSrc();
    }

    @Override
    public void setSrc() {
        try {
            switch (to) {
                case UP:
                    src = ImageIO.read(getClass().getResource("sprites/tail_up.png"));
                    break;
                case RIGHT:
                    src = ImageIO.read(getClass().getResource("sprites/tail_right.png"));
                    break;
                case LEFT:
                    src = ImageIO.read(getClass().getResource("sprites/tail_left.png"));
                    break;
                case DOWN:
                    src = ImageIO.read(getClass().getResource("sprites/tail_down.png"));
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if (to == Directions.UP || to == Directions.DOWN) {
            g2.drawImage(src, loc.x, loc.y, 50, len, null);
        } else {
            g2.drawImage(src, loc.x, loc.y, len, 50, null);
        }
    }

    @Override
    public void changeLen(int a) {
        len-=a;
    }
}
