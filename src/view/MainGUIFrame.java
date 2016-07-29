package view;

import model.ModelManager;

import javax.swing.*;


/**
 * Created by mzglinicki.96 on 23.07.2016.
 */
public class MainGUIFrame extends JFrame {

    private static MainGUIFrame guiFrame = null;

    private final static int WINDOW_X_POSITION = 20;
    private final static int WINDOW_y_POSITION = 20;
    private final static int WINDOW_WIDTH = 700;
    private final static int WINDOW_HEIGHT = 730;

    private StartPanel startPanel;

    private MainGUIFrame() {

        startPanel = StartPanel.getInstance();
        startPanel.setMainGUIFrame(this);
        ModelManager.getInstance().setMainGUIFrame(this);
        setupMainPane();
    }

    public static MainGUIFrame getInstance() {
        if (guiFrame == null) {
            guiFrame = new MainGUIFrame();
        }
        return guiFrame;
    }

    private void setupMainPane() {

        final String logoImage = "delta_logo.png";
        final String appTitle = "Delta Draw";
        final ImageIcon img = new ImageIcon(logoImage);

        this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(WINDOW_X_POSITION, WINDOW_y_POSITION, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle(appTitle);
        this.setResizable(false);
        this.add(startPanel.getMainStartJPanel());
        this.setVisible(true);
    }

    public void changeView(final JPanel panel) {
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.setVisible(true);
        this.repaint();
    }
}