package view;

import javax.swing.*;

/**
 * Created by mzglinicki.96 on 29.07.2016.
 */
public class StartPanel {

    private static StartPanel startPanel = null;

    private JButton startBtn;
    private JPanel mainStartJPanel;
    private JLabel imageLable;
    private JLabel textLabel;
    private JButton closeBtn;
    private JPanel buttonPanel;
    private MainGUIFrame mainFrame;

    private StartPanel() {
        setText();
    }

    public static StartPanel getInstance() {
        if (startPanel == null) {
            startPanel = new StartPanel();
        }
        return startPanel;
    }

    public void setMainGUIFrame(final MainGUIFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainStartJPanel() {
        return mainStartJPanel;
    }

    public JButton getStartBtn() {
        return startBtn;
    }

    public JButton getCloseBtn() {
        return closeBtn;
    }

    private void setText() {
        textLabel.setText("Server Delta Draw");
    }
}
