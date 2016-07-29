package view;

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

    private ServerPanel() {
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

    public JButton getClearBtn() {
        return clearBtn;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public void setTextToCodeTextArea(String text) {
        codeTextArea.setText(text);
    }

    private void setupCodeTextArea() {
        codeTextArea.setMargin(new Insets(CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN, CODE_TEXT_AREA_MARGIN));
    }

    public void writeMessage(final String message) {
        messagesLabel.setText(message);
    }
}