import javax.swing.*;
import java.awt.*;


/**
 * Created by mzglinicki.96 on 18.07.2016.
 */
public class ServerPanel {

    private JPanel mainServerJPane;
    private JTextArea codeTextArea;
    private JButton clearBtn;
    private JButton saveBtn;
    private JButton backBtn;
    private JLabel messagesLabel;

    private final static int CODE_TEXT_AREA_MARGIN = 10;

    private static ServerPanel serverPanel = null;
    private MainGUIFrame mainFrame;

    private ServerPanel() {
        setupButtonsClickListeners();
        setupCodeTextArea();
    }

    public static ServerPanel getInstance() {
        if (serverPanel == null) {
            serverPanel = new ServerPanel();
        }
        return serverPanel;
    }

    public JTextArea getCodeTextArea() {
        return codeTextArea;
    }

    private void setupCodeTextArea() {
        codeTextArea.setMargin(new Insets(CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN));
    }

    public JPanel getMainPanel() {
        return mainServerJPane;
    }

    public void setMainGUIFrame(final MainGUIFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void writeMessage(final String message) {
        messagesLabel.setText(message);
    }

    private void setupButtonsClickListeners() {

        saveBtn.addActionListener(e -> onSaveBtnClick());
        clearBtn.addActionListener(e -> onClearBtnClick());
        backBtn.addActionListener(e -> onBackBtnClick());
    }

    private void onSaveBtnClick() {
        writeMessage(Constants.SAVED_DATA_INFO);
        CodeCreator.getInstance().writeDataToFile();
    }

    private void onClearBtnClick() {
        writeMessage(Constants.WAITING_FOR_INPUT_DATA);
        codeTextArea.setText("" + '\u0000');
    }

    private void onBackBtnClick() {
        ConnectionManager.getInstance().stopThread();
        mainFrame.changeView(SettingsPanel.getInstance().getMainSettingsJPanel());
    }
}