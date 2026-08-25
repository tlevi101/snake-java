package Game.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class GameEndDlg  extends JDialog{
    private JTextField userName;
    private JLabel result;
    private int score;
    private int time;
    private JPanel btnPanel;
    private JButton btnOK;
    private JButton btnCancel;
    private JFrame frame;
    public GameEndDlg(JFrame frame, String name, int score, int time) {
        super(frame, name, true);
        this.score=score;
        this.time=time;
        userName=new JTextField();
        result=new JLabel("Megevett almák: "+score+"; Játék idő: "+ time);

        btnOK = new JButton(actionOK);
        btnOK.setText("Mentés");
        btnOK.setMnemonic('O');
        btnOK.setPreferredSize(new Dimension(90, 25));
        btnCancel = new JButton(actionCancel);
        btnCancel.setText("Kihagyás");
        KeyStroke mégsemKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        InputMap inputMap = btnCancel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = btnCancel.getActionMap();
        if (inputMap != null && actionMap != null) {
            inputMap.put(mégsemKeyStroke, "cancel");
            actionMap.put("cancel", actionCancel);
        }
        btnCancel.setPreferredSize(new Dimension(90, 25));
        getRootPane().setDefaultButton(btnOK);
        btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(btnOK);
        btnPanel.add(btnCancel);

        frame.add(BorderLayout.SOUTH,btnPanel);
        frame.add(BorderLayout.NORTH, userName);
        frame.add(BorderLayout.CENTER, result);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.frame=frame;
    }

    private boolean processOK() {
        return false;
    }

    private void processCancel() {

    }
    private AbstractAction actionOK = new AbstractAction("OK") {
        public void actionPerformed(ActionEvent e) {
            if (processOK()) {
                frame.setVisible(false);
            }
        }
    };
    private AbstractAction actionCancel = new AbstractAction("Mégsem") {
        public void actionPerformed(ActionEvent e) {
            processCancel();
            frame.setVisible(false);
        }
    };
}
