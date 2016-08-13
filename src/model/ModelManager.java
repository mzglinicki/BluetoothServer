package model;

import model.btConection.ConnectionManager;
import model.codeCreator.CodeCreator;
import view.MainGUIFrame;
import view.ServerPanel;
import view.SettingsPanel;

import javax.swing.*;
import java.net.URL;

/**
 * Created by mzglinicki.96 on 29.07.2016.
 */
public class ModelManager {

    public static ModelManager modelManager = null;
    //view
    private final ServerPanel serverPanel = ServerPanel.getInstance();
    private final SettingsPanel settingsPanel = SettingsPanel.getInstance();
    private MainGUIFrame mainFrame;
    //model
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final CodeCreator codeCreator = CodeCreator.getInstance();

    private ModelManager() {
    }

    public static ModelManager getInstance() {

        if (modelManager == null) {
            modelManager = new ModelManager();
        }
        return modelManager;
    }

    public void setMainGUIFrame(final MainGUIFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void changeView(final JPanel jPanel) {
        mainFrame.changeView(jPanel);
    }

    public void closeApp() {
        System.exit(0);
    }

    public void saveData() {
        serverPanel.writeMessage(Constants.SAVED_DATA_INFO);
        codeCreator.writeDataToFile();
    }

    public void turnOnServer() {
        connectionManager.startWaitThread();
    }

    public void clearTextArea() {
        serverPanel.writeMessage(Constants.WAITING_FOR_INPUT_DATA);
        serverPanel.setTextToCodeTextArea("" + '\u0000');
    }

    public ImageIcon rotateIcon(final RotatedIcon.Rotate rotateDirection, final ImageIcon icon) {
        return new RotatedIcon(icon, rotateDirection);
    }

    public void setupNewIcon(final URL iconURL, final boolean horizontalOrientation) {

        settingsPanel.setIconInImageLabel(iconURL);
        if (horizontalOrientation) {
            settingsPanel.setIconInImageLabel(rotateIcon(RotatedIcon.Rotate.UP, new ImageIcon(iconURL)));
        }
    }
}