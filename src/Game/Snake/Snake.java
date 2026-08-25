package Game.Snake;


import Game.Snake.Parts.*;
import Game.Snake.Parts.Exception.InValidMethodInvokeException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Snake {
    private ArrayList<Part> body;
    private int MOVE_SPEED;
    private int changeOnLen;
    private int tailLen;
    private int maxLen;
    private int bound;
    public Snake(int size) {
        MOVE_SPEED=1;
        bound=size;
        body = new ArrayList<>();
        changeOnLen = 0;
        Directions drt=decideDrt();
        body.add(new Head(drt));
        body.get(0).setLoc(size/2, size/2);
        body.add(new Tail(drt));
        tailLen = 60;
        maxLen = 120;
        setLocations();
        body.get(1).setLen(tailLen);
    }

    public void move(Directions drt) {
        if (drtChanged(drt)) {
            body.add(1, new Curve(drt, getOppositeDrt(getCurrDrt())));
            body.add(1, new Straight(drt));
            body.get(body.size() - 1).setLen(body.get(body.size() - 1).getLen() - 50);
            body.get(0).setTo(drt);
            body.get(0).setSrc();
            body.get(1).setLen(0);
            change_head_loc(50);
            setLocations();
        } else if (!hasCurve()) {
            change_head_loc(MOVE_SPEED);
            setLocations();
        } else if (body.get(body.size() - 1).getLen() > 0 && changeOnLen < tailLen) {
            changeOnLen += MOVE_SPEED;
            Point p0 = body.get(0).getLoc();
            Point p1 = body.get(1).getLoc();
            try {
                body.get(1).changeLen(MOVE_SPEED);
            } catch (InValidMethodInvokeException e) {
                e.printStackTrace();
            }
            int lastInd = body.size() - 1;
            Point pTail = body.get(lastInd).getLoc();
            try {
                body.get(lastInd).changeLen(MOVE_SPEED);
            } catch (InValidMethodInvokeException e) {
                e.printStackTrace();
            }
            switch (body.get(0).getTo()) {
                case LEFT -> {
                    body.get(0).setLoc(p0.x - MOVE_SPEED, p0.y);
                    body.get(1).setLoc(p1.x - MOVE_SPEED, p1.y);
                }
                case RIGHT -> {
                    body.get(0).setLoc(p0.x + MOVE_SPEED, p0.y);
                }
                case DOWN -> {
                    body.get(0).setLoc(p0.x, p0.y + MOVE_SPEED);

                }
                case UP -> {
                    body.get(0).setLoc(p0.x, p0.y - MOVE_SPEED);
                    body.get(1).setLoc(p1.x, p1.y - MOVE_SPEED);
                }
            }
            switch (body.get(lastInd).getTo()) {
                case RIGHT -> {
                    body.get(lastInd).setLoc(pTail.x + MOVE_SPEED, pTail.y);
                }
                case DOWN -> {
                    body.get(lastInd).setLoc(pTail.x, pTail.y + MOVE_SPEED);
                }
            }
        } else if (body.get(body.size() - 1).getLen() <= 0 || tailLen <= changeOnLen) {
            int lastInd = body.size() - 1;
            body.get(lastInd).setLen(0);
            body.get(lastInd).setTo(body.get(lastInd - 1).getTo());
            body.remove(lastInd - 1);
            lastInd--;
            body.get(lastInd).setFrom(getOppositeDrt(body.get(lastInd).getTo()));
            body.get(lastInd).setSrc();
            if (body.get(lastInd - 1).getType() != Parts.CURVE) {
                body.remove(lastInd - 1);
                lastInd--;
            }
            body.get(lastInd).setLen(Math.abs(sumOfLen() - maxLen));
            tailLen = body.get(lastInd).getLen();
            changeOnLen = 0;
            setLocations();
        }
    }
    private void change_head_loc(int d) {
        Point old = body.get(0).getLoc();
        switch (getCurrDrt()) {
            case LEFT -> {
                body.get(0).setLoc(old.x - d, old.y);
            }
            case DOWN -> {
                body.get(0).setLoc(old.x, old.y + d);
            }
            case RIGHT -> {
                body.get(0).setLoc(old.x + d, old.y);
            }
            case UP -> {
                body.get(0).setLoc(old.x, old.y - d);
            }
        }
    }

    private boolean hasCurve() {
        for (Part p : body) {
            if (p.getType() == Parts.CURVE) {
                return true;
            }
        }
        return false;
    }

    private void setLocations() {
        for (int i = 1; i < body.size(); i++) {
            Point p = body.get(i - 1).getLoc();
            int prvlen = body.get(i - 1).getLen();
            // if (body.get(i - 1).getType() == Parts.CURVE) {
            switch (body.get(i - 1).getFrom()) {
                case DOWN -> {
                    body.get(i).setLoc(p.x, p.y + prvlen);
                }
                case LEFT -> {
                    body.get(i).setLoc(p.x - body.get(i).getLen(), p.y);
                }
                case RIGHT -> {
                    body.get(i).setLoc(p.x + prvlen, p.y);

                }
                case UP -> {
                    body.get(i).setLoc(p.x, p.y - body.get(i).getLen());
                }
            }

        }
    }

    public Directions getCurrDrt() {
        return body.get(0).getTo();
    }

    public void feed() {
        maxLen += 50;
        tailLen += 50;
        body.get(body.size() - 1).setLen(body.get(body.size() - 1).getLen() + 50);
        setLocations();
    }

    private boolean drtChanged(Directions drt) {
        return body.get(0).getTo() != drt;
    }

    private int sumOfLen() {
        int result = 0;
        for (Part p : body) {
            result += p.getLen();
        }
        return result;
    }

    public Directions getOppositeDrt(Directions drt) {
        switch (drt) {
            case UP:
                return Directions.DOWN;
            case RIGHT:
                return Directions.LEFT;
            case DOWN:
                return Directions.UP;
            default:
                return Directions.RIGHT;
        }
    }

    public void draw(Graphics2D g2) {
        for (Part p : body) {
            p.draw(g2);
        }

    }

    public boolean gameOver() {
        Rectangle heads = body.get(0).hitBox();
        if (body.get(0).getLoc().x + 50 > bound+5 || body.get(0).getLoc().x < -5 || body.get(0).getLoc().y < -5 || body.get(0).getLoc().y + 50 > bound+5) {
            return true;
        }
        for (int i = 1; i < body.size(); i++) {
            Rectangle other = body.get(i).hitBox();
            if (heads.intersects(other)) {
                return true;
            }
        }
        return false;
    }

    private Directions decideDrt() {
        Random rnd = new Random();
        switch (rnd.nextInt(1, 4)) {
            case 1:
                return Directions.UP;
            case 2:
                return Directions.RIGHT;
            case 3:
                return Directions.DOWN;
            default:
                return Directions.LEFT;
        }
    }

    public void setMOVE_SPEED(int MOVE_SPEED) {
        this.MOVE_SPEED = MOVE_SPEED;
    }

    public int getMOVE_SPEED() {
        return MOVE_SPEED;
    }

    public ArrayList<Part> getBody() {
        return body;
    }
}
