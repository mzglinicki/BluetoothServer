import javax.swing.*;
import java.awt.*;


/**
 * Created by mzglinicki.96 on 18.07.2016.
 */
public class ServerPanel {

    private JPanel mainServerJPane;
    private JTextArea codeTextArea;
    private JButton clearBtn;
    private JButton writeToFileBtn;
    private JButton backBtn;
    private JLabel messagesLabel;

    private final static int CODE_TEXT_AREA_MARGIN = 10;

    private static ServerPanel serverPanel = null;
    private MainGUIFrame mainFrame;


    private ServerPanel() {
        setupButtonListeners();
        setupCodeTextArea();
    }

    public static ServerPanel getInstance() {
        if (serverPanel == null) {
            serverPanel = new ServerPanel();
        }
        return serverPanel;
    }

    public JPanel getMainPanel() {
        return mainServerJPane;
    }

    public JTextArea getCodeTextArea() {
        return codeTextArea;
    }

    public void writeMessage(String message) {
        messagesLabel.setText(message);
    }

    private void setupCodeTextArea() {
        codeTextArea.setMargin(new Insets(CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN));
    }

    public void setMainGUIFrame(MainGUIFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    private void setupButtonListeners() {

        writeToFileBtn.addActionListener(e -> {
            writeMessage(Constants.SAVED_DATA_INFO);
            CodeCreator.getInstance().writeDataToFile();
        });

        clearBtn.addActionListener(e1 -> {
            writeMessage(Constants.WAITING_FOR_INPUT_DATA);
            codeTextArea.setText("" + '\u0000');
        });

        backBtn.addActionListener(e -> {
            SettingsPanel settingsPanel = SettingsPanel.getInstance();
            ConnectionManager.getInstance().stopThread();
            mainFrame.changeView(settingsPanel.getMainSettingsJPanel());
        });
    }
}