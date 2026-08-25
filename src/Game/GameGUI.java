package Game;

import Game.Dialogs.GameEndDlg;
import Game.Dialogs.SavedResultsDlg;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Timer;

public class GameGUI {

    private JFrame frame;
    private Field fld;
    private Timer passedSec;
    private int passedTime;
    private JLabel passedSeconds;
    private JLabel score;
    private JPanel info;
    private JMenu saves, newGame;
    private JMenuItem top10in10, new10x10, top10in14, new14x14;
    private int tableSize;
    private JMenuBar mb;

    public GameGUI() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableSize = 500;

        mb = new JMenuBar();
        saves = new JMenu("Mentések");
        top10in10 = new JMenuItem("top10 a 10x10-es táblán");
        top10in10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SavedResultsDlg s = new SavedResultsDlg(new JFrame(), "Mentések", 500);
            }
        });
        top10in14 = new JMenuItem("top10 a 14x14-es táblán");
        top10in14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //SavedResultsDlg s = new SavedResultsDlg(new JFrame(), "Mentések", 700);
            }
        });
        saves.add(top10in10);
        saves.add(top10in14);
        newGame = new JMenu("Új játék");
        new10x10 = new JMenuItem("10x10-es tábla");
        new10x10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableSize = 500;
                buildFrame();
            }
        });
        new14x14 = new JMenuItem("14x14-es tábla");
        new14x14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableSize = 700;
                buildFrame();
            }
        });
        newGame.add(new10x10);
        newGame.add(new14x14);
        mb.add(saves);
        mb.add(newGame);
        buildFrame();
    }

    private void buildFrame() {
        frame.dispose();
        frame=new JFrame();
        fld = new Field(tableSize);
        fld.setPreferredSize(new Dimension(tableSize, tableSize));
        frame.getContentPane().add(BorderLayout.SOUTH, fld);
        info = new JPanel();
        String time = (int) Math.floor(passedTime / 1000) + "." + passedTime % 1000 + "s";
        try {
            Image hourglass = ImageIO.read(getClass().getResource("sprites/tile" + ((int) Math.floor(passedTime / 1000)) % 4 + ".png"));
            Image apple = ImageIO.read(getClass().getResource("sprites/apple.png"));
            passedSeconds = new JLabel(time, new ImageIcon(hourglass), JLabel.LEFT);
            score = new JLabel(((Integer) fld.getScore()).toString(), new ImageIcon(apple), JLabel.LEFT);
            info.setLayout(new FlowLayout());
            info.add(passedSeconds);
            info.add(score);
            info.setBackground(new Color(222, 184, 120));
            frame.getContentPane().add(BorderLayout.NORTH, info);
        } catch (IOException e) {
            e.printStackTrace();
        }
        passedTime = 0;
        passedSec = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (fld.over()) {
                    passedSec.stop();
                    //GameEndDlg dlg = new GameEndDlg(new JFrame(), "Game Over", fld.getScore(), passedTime, tableSize);
                } else {
                    passedTime += 10;
                    printInfo();
                }

            }
        });
        passedSec.start();
        frame.setJMenuBar(mb);
        frame.setSize(tableSize, tableSize);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private boolean frameHasFld() {
        for (int i = 0; i < frame.getContentPane().getComponentCount(); i++) {
            if (frame.getContentPane().getComponent(i) == fld) {
                return true;
            }
        }
        return false;
    }

    private void printInfo() {
        String time = (int) Math.floor(passedTime / 1000) + "." + passedTime % 1000 + "s";
        try {
            Image hourglass = ImageIO.read(getClass().getResource("sprites/tile" + ((int) Math.floor(passedTime / 1000)) % 4 + ".png"));
            Image apple = ImageIO.read(getClass().getResource("sprites/apple.png"));
            passedSeconds.setText(time);
            passedSeconds.setIcon(new ImageIcon(hourglass));
            score.setText(((Integer) fld.getScore()).toString());
            score.setIcon(new ImageIcon(apple));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
