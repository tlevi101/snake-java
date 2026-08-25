package Game;

import Game.Snake.Parts.Part;
import Game.Snake.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Rock {
    private Point loc;
    private Image src;

    public Rock(int x, int y) {
        Random rnd = new Random();
        String path = "Snake/Parts/sprites/rock" + rnd.nextInt(0, 2) + ".png";
        loc = new Point(x, y);
        try {
            src = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean crashed(Snake snk) {
        return this.hitBox().intersects(snk.getBody().get(0).hitBox());
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(src, loc.x, loc.y, 50, 50, null);
    }

    public Rectangle hitBox() {
        return new Rectangle(loc.x + 5, loc.y + 5, 50 - 10, 50 - 10);
    }

}
