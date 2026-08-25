package Game;

import Game.Snake.Parts.Directions;
import Game.Snake.Parts.Part;
import Game.Snake.Snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Field extends JPanel {
    private Snake snk;
    private final Timer timer;
    private final int FPS = 60;
    private Apple apple;
    private final int numOfRock = 3;
    private Rocks rocks;
    private int size;
    private int score;
    public Field(int size) {
        this.size = size;
        score=0;
        snk = new Snake(size);
        rocks = new Rocks( 3, snk, size, size);
        apple = new Apple(snk, size, size, rocks);
        setEvents();
        timer = new Timer(1000 / FPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                snkMoved(snk.getCurrDrt());
                repaint();
                if (snk.gameOver()) {
                    timer.stop();
                }
                for (Rock r : rocks.getRocks()) {
                    if (r.crashed(snk)) {
                        timer.stop();
                    }
                }
            }
        });
        timer.start();

    }
    private void setEvents(){
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed a");
        this.getActionMap().put("pressed a", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (snk.getOppositeDrt(snk.getCurrDrt()) != Directions.LEFT)
                    snkMoved(Directions.LEFT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed d");
        this.getActionMap().put("pressed d", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (snk.getOppositeDrt(snk.getCurrDrt()) != Directions.RIGHT)
                    snkMoved(Directions.RIGHT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (snk.getOppositeDrt(snk.getCurrDrt()) != Directions.UP) snkMoved(Directions.UP);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed s");
        this.getActionMap().put("pressed s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (snk.getOppositeDrt(snk.getCurrDrt()) != Directions.DOWN)
                    snkMoved(Directions.DOWN);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"),"pressed space");
        this.getActionMap().put("pressed space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (snk.getMOVE_SPEED()) {
                    case 1:
                        snk.setMOVE_SPEED(2);
                        break;
                    case 2:
                        snk.setMOVE_SPEED(1);
                        break;
                }
            }
        });
    }
    private void snkMoved(Directions drt) {
        snk.move(drt);
        snkAte();
    }

    private void snkAte() {
        for (Part p : snk.getBody()) {
            if (apple.hitBox().intersects(p.hitBox())) {
                snk.feed();
                apple = new Apple(snk, size, size, rocks);
                score++;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
//        try {
//            BufferedImage background = ImageIO.read(getClass().getResource("sprites/background_biztonsagi.jpg"));
//            g.drawImage(background, 0, 0, size, size, null);
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
        drawLines(g2);
        snk.draw(g2);
        for (Rock r : rocks.getRocks()) {
            r.draw(g2);
        }
        apple.draw(g2);
    }
    private void drawLines(Graphics2D g2){
        g2.setColor(new Color(121, 81, 25, 255));
        for (int i = 1; i < size/50; i++) {
            g2.drawLine(50*i,0,50*i,size);
        }
        for (int i = 1; i < size/50; i++) {
            g2.drawLine(0,50*i,size,50*i);
        }
    }
    public Boolean over(){
        if (snk.gameOver()) {
            return true;
        }
        for (Rock r : rocks.getRocks()) {
            if (r.crashed(snk)) {
                return true;
            }
        }
        return false;
    }
    public int getScore() {
        return score;
    }
}
