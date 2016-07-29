package controller;

import model.ModelManager;
import model.RotatedIcon;
import view.ServerPanel;
import view.SettingsPanel;
import view.StartPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mzglinicki.96 on 29.07.2016.
 */
public class ButtonManager {

    public static ButtonManager buttonManager = null;
    private final StartPanel startPanel = StartPanel.getInstance();
    private final SettingsPanel settingsPanel = SettingsPanel.getInstance();
    private final ServerPanel serverPanel = ServerPanel.getInstance();
    private final ModelManager modelManager = ModelManager.getInstance();

    private final JButton turnServerOnBtn = settingsPanel.getTurnOnBtn();
    private final JRadioButton a4PaperFormat = settingsPanel.getA4PaperFormat();
    private final JRadioButton a3PaperFormat = settingsPanel.getA3PaperFormat();
    private final JRadioButton horizontally = settingsPanel.getHorizontally();
    private final JRadioButton vertically = settingsPanel.getVertically();
    private final JCheckBox warehouseCheckBox = settingsPanel.getWarehouseCheckBox();
    private final JCheckBox paperCoordinatesCheckBox = settingsPanel.getPaperCoordinatesCheckBox();
    private final JLabel imageLabel = settingsPanel.getImageLabel();

    private final boolean[] orientationVertically = {true};
    private final boolean[] a4Format = {true};

    private ButtonManager() {
        setupJBtnClickListeners();
        setupRadioButtonsClickListeners();
        setupCheckBoxClickListeners();
    }

    public static ButtonManager getInstance() {
        if (buttonManager == null) {
            buttonManager = new ButtonManager();
        }
        return buttonManager;
    }

    private void setupJBtnClickListeners() {

        startPanel.getStartBtn().addActionListener(e -> onStartAppBtnClick());
        startPanel.getCloseBtn().addActionListener(e -> onCloseBtnClick());

        turnServerOnBtn.addActionListener(e -> onTurnOnBtnClick());
        settingsPanel.getBackBtn().addActionListener(e -> onBackToStartBtnClick());
        settingsPanel.getCloseBtn().addActionListener(e -> onCloseBtnClick());

        serverPanel.getSaveBtn().addActionListener(e -> onSaveBtnClick());
        serverPanel.getClearBtn().addActionListener(e -> onClearBtnClick());
        serverPanel.getBackBtn().addActionListener(e -> onBackToSettingsBtnClick());
    }

    private void setupRadioButtonsClickListeners() {

        horizontally.addActionListener(e -> onHorizontallyBtnClickListeners());
        vertically.addActionListener(e -> onVerticallyBtnClickListeners());
        a4PaperFormat.addActionListener(e -> onA4PaperFormatBtnClickListeners());
        a3PaperFormat.addActionListener(e -> onA3PaperFormatBtnClickListeners());
    }

    private void setupCheckBoxClickListeners() {

        warehouseCheckBox.addActionListener(e -> checkSettings());
        paperCoordinatesCheckBox.addActionListener(e -> checkSettings());
    }

    private void onStartAppBtnClick() {
        modelManager.changeView(settingsPanel.getMainSettingsJPanel());
    }

    private void onTurnOnBtnClick() {
        modelManager.changeView(serverPanel.getMainPanel());
        modelManager.turnOnServer();
    }

    private void onBackToStartBtnClick() {
        modelManager.changeView(startPanel.getMainStartJPanel());
    }

    private void onCloseBtnClick() {
        modelManager.closeApp();
    }

    private void onSaveBtnClick() {
        modelManager.saveData();
    }

    private void onClearBtnClick() {
        modelManager.clearTextArea();
    }

    private void onBackToSettingsBtnClick() {
        modelManager.turnOffServer();
        modelManager.changeView(settingsPanel.getMainSettingsJPanel());
    }

    private void onA3PaperFormatBtnClickListeners() {

        final String a3Icon = "/images/paperA3.png";

        if (a3PaperFormat.isSelected() && a4Format[0]) {
            modelManager.setupNewIcon(getClass().getResource(a3Icon), horizontally.isSelected());
            a4Format[0] = false;
        }
    }

    private void onA4PaperFormatBtnClickListeners() {

        final String a4Icon = "/images/paperA4.png";

        if (a4PaperFormat.isSelected() && !a4Format[0]) {
            modelManager.setupNewIcon(getClass().getResource(a4Icon), horizontally.isSelected());
            a4Format[0] = true;
        }
    }

    private void onHorizontallyBtnClickListeners() {
        if (horizontally.isSelected() && orientationVertically[0]) {
            imageLabel.setIcon(modelManager.rotateIcon(RotatedIcon.Rotate.UP, (ImageIcon) imageLabel.getIcon()));
            orientationVertically[0] = false;
        }
    }

    private void onVerticallyBtnClickListeners() {
        if (vertically.isSelected() && !orientationVertically[0]) {
            imageLabel.setIcon(modelManager.rotateIcon(RotatedIcon.Rotate.DOWN, (ImageIcon) imageLabel.getIcon()));
            orientationVertically[0] = true;
        }
    }

    private void checkSettings() {

        if (warehouseCheckBox.isSelected() && paperCoordinatesCheckBox.isSelected()) {
            turnServerOnBtn.setEnabled(true);
        } else {
            turnServerOnBtn.setEnabled(false);
        }
    }

    public List<Integer> getWarehouseCoordinates(final int toolId) {

        final JTable table = settingsPanel.getWarehouseCoordinatesTable();
        final List<Integer> toolCoordinates = new ArrayList<>();

        for (int i = 0; i < table.getRowCount(); i++) {
            toolCoordinates.add(Integer.valueOf((String) table.getModel().getValueAt(i, toolId)));
        }
        return toolCoordinates;
    }

    public List<Integer> getPaperCornerCoordinates() {

        final JTable table = settingsPanel.getPaperCornerCoordinatesTable();
        final List<Integer> paperCoordinates = new ArrayList<>();

        for (int i = 0; i < table.getColumnCount(); i++) {
            paperCoordinates.add(Integer.valueOf((String) table.getModel().getValueAt(0, i)));
        }
        return paperCoordinates;
    }
}