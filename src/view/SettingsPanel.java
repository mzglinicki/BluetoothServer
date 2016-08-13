package view;

import model.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.net.URL;

/**
 * Created by mzglinicki.96 on 23.07.2016.
 */
public class SettingsPanel {
    private JPanel mainSettingsJPanel;
    private JButton turnOnBtn;
    private JButton closeBtn;
    private JButton backBtn;
    private JRadioButton toolInstalled;
    private JRadioButton toolNotInstalled;
    private JRadioButton a4PaperFormat;
    private JRadioButton a3PaperFormat;
    private JRadioButton horizontally;
    private JRadioButton vertically;
    private JTable paperCornerCoordinatesTable;
    private JTable warehouseCoordinatesTable;
    private JLabel imageLabel;
    private JPanel warehouseCoordinates;
    private JPanel warehouseSettings;
    private JPanel gripsPanel;
    private JPanel paperSettings;
    private JPanel buttonsPanel;
    private JPanel paperFormatJPanel;
    private JPanel paperOrientationJPanel;
    private JPanel paperStartCoordinates;

    private static SettingsPanel settingsPanel = null;

    public static SettingsPanel getInstance() {
        if (settingsPanel == null) {
            settingsPanel = new SettingsPanel();
        }
        return settingsPanel;
    }

    private SettingsPanel() {

        setDefaultSettings();
        setupRadioButtonGroups();
    }

    public JButton getTurnOnBtn() {
        return turnOnBtn;
    }

    public JButton getCloseBtn() {
        return closeBtn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JPanel getMainSettingsJPanel() {
        return mainSettingsJPanel;
    }

    public JRadioButton getA4PaperFormat() {
        return a4PaperFormat;
    }

    public JRadioButton getA3PaperFormat() {
        return a3PaperFormat;
    }

    public JRadioButton getVertically() {
        return vertically;
    }

    public JRadioButton getHorizontally() {
        return horizontally;
    }

    public JRadioButton getToolNotInstalled() {
        return toolNotInstalled;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public JTable getPaperCornerCoordinatesTable() {
        return paperCornerCoordinatesTable;
    }

    public JTable getWarehouseCoordinatesTable() {
        return warehouseCoordinatesTable;
    }

    public void setIconInImageLabel(final URL iconURL) {
        imageLabel.setIcon(new ImageIcon(iconURL));
    }

    public void setIconInImageLabel(final ImageIcon icon) {
        imageLabel.setIcon(icon);
    }

    private void setDefaultSettings() {
        createWarehouseCoordinatesTable();
        createPaperCornerCoordinatesTable();
        setToolTips();

        toolNotInstalled.setSelected(true);
        a4PaperFormat.setSelected(true);
        vertically.setSelected(true);
    }

    private void setToolTips() {
        warehouseCoordinatesTable.setToolTipText(Constants.WAREHOUSE_TOOL_TIP);
        a4PaperFormat.setToolTipText(Constants.PAPER_FORMAT_TOOL_TIP);
        a3PaperFormat.setToolTipText(Constants.PAPER_FORMAT_TOOL_TIP);
        horizontally.setToolTipText(Constants.PAPER_ORIENTATION_TOOLO_TIP);
        vertically.setToolTipText(Constants.PAPER_ORIENTATION_TOOLO_TIP);
        paperCornerCoordinatesTable.setToolTipText(Constants.PAPER_CORNER_TOOL_TIP);
    }

    private void setupRadioButtonGroups() {

        createButtonGroup(toolInstalled, toolNotInstalled);
        createButtonGroup(a4PaperFormat, a3PaperFormat);
        createButtonGroup(horizontally, vertically);
    }

    private ButtonGroup createButtonGroup(final JRadioButton firstButton, final JRadioButton secondButton) {

        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(firstButton);
        buttonGroup.add(secondButton);
        return buttonGroup;
    }

    private void createWarehouseCoordinatesTable() {

        //TODO check data on Delta
        String[] columns = {"", "1", "2", "3", "4", "5", "6"};

        Object[][] data = {
                {"x", "10", "20", "30", "40", "50", "60"},
                {"y", "30", "30", "30", "30", "30", "30"},
                {"z", "20", "20", "20", "20", "20", "20"}};

        final DefaultTableModel model = new DefaultTableModel(data, columns);
        warehouseCoordinatesTable.setModel(model);
    }

    private void createPaperCornerCoordinatesTable() {

        //TODO check data on Delta
        String[] columns = {"x", "y"};

        Object[][] data = {
                {"10", "10"}};
        createTable(columns, data);
    }

    private void createTable(final String[] columns, final Object[][] data) {
        final DefaultTableModel model = new DefaultTableModel(data, columns);
        paperCornerCoordinatesTable.setModel(model);
        paperCornerCoordinatesTable.setDefaultRenderer(Object.class, setupCellRenderer());
    }

    private DefaultTableCellRenderer setupCellRenderer() {
        final DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        return cellRenderer;
    }
}