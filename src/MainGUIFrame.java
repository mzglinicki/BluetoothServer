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

    private SettingsPanel settingsPanel;

    private MainGUIFrame() {
        settingsPanel = SettingsPanel.getInstance();
        settingsPanel.setMainGUIFrame(this);

        ServerPanel.getInstance().setMainGUIFrame(this);
        setupMainPane();
    }

    public static MainGUIFrame getInstance() {
        if (guiFrame == null) {
            guiFrame = new MainGUIFrame();
        }
        return guiFrame;
    }

    private void setupMainPane() {

        final ImageIcon img = new ImageIcon(Constants.LOGO);

        this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setBounds(WINDOW_X_POSITION, WINDOW_y_POSITION, WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setTitle(Constants.DELTA_TITLE);
        this.setResizable(false);
        this.add(settingsPanel.getMainSettingsJPanel());
        this.setVisible(true);
    }

    public void changeView(JPanel panel) {
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        this.setVisible(true);
        this.repaint();
    }
}