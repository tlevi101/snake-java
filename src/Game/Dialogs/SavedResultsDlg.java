package Game.Dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class SavedResultsDlg extends JDialog{
    private JTextArea top10;
    private JButton btnExit;
    private JFrame frame;
    public SavedResultsDlg(JFrame frame, String name) {
        super(frame, name, true);
        btnExit = new JButton(actionExit);
        btnExit.setText("Kilépés");
        btnExit.setMnemonic('O');
        btnExit.setPreferredSize(new Dimension(90, 25));
        KeyStroke mégsemKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().setDefaultButton(btnExit);

        top10=new JTextArea();


        frame.add(BorderLayout.SOUTH,btnExit);
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
    private AbstractAction actionExit = new AbstractAction("EXIT") {
        public void actionPerformed(ActionEvent e) {
            if (processOK()) {
                frame.setVisible(false);
            }
        }
    };
}
