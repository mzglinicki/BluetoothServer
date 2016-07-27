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
    private JRadioButton toolInstalled;
    private JRadioButton toolNotInstalled;
    private JRadioButton paperInstalled;
    private JRadioButton paperNotInstalled;
    private JRadioButton a4PaperFormat;
    private JRadioButton a3PaperFormat;
    private JRadioButton horizontally;
    private JRadioButton vertically;
    private JTable paperCornerCoordinates;
    private JTable warehouseCoordinatesTable;
    private JLabel imageLabel;
    private JCheckBox warehouseCheckBox;
    private JCheckBox paperCoordinatesCheckBox;
    private JPanel warehouseCoordinates;
    private JPanel warehouseSettings;
    private JPanel gripsPanel;
    private JPanel paperSettings;
    private JPanel buttonsPanel;
    private JPanel paperPresentPanel;
    private JPanel paperFormatJPanel;
    private JPanel paperOrientationJPanel;
    private JPanel paperStartCoordinates;

    private static SettingsPanel settingsPanel = null;
    private MainGUIFrame mainFrame;
    private ServerPanel serverPanel;

    public static SettingsPanel getInstance() {
        if (settingsPanel == null) {
            settingsPanel = new SettingsPanel();
        }
        return settingsPanel;
    }

    private SettingsPanel() {

        setDefaultSettings();
        setupRadioButtonGroups();
        setupButtonsListeners();
        checkSettings();
    }

    public void setMainGUIFrame(final MainGUIFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainSettingsJPanel() {
        return mainSettingsJPanel;
    }

    private void checkSettings() {

        if (paperInstalled.isSelected() && warehouseCheckBox.isSelected() && paperCoordinatesCheckBox.isSelected()) {
            turnOnBtn.setEnabled(true);
        } else {
            turnOnBtn.setEnabled(false);
        }
    }

    private void setDefaultSettings() {

        createWarehouseCoordinatesTable();
        createPaperCornerCoordinatesTable();

        toolNotInstalled.setSelected(true);
        paperNotInstalled.setSelected(true);
        a4PaperFormat.setSelected(true);
        vertically.setSelected(true);
        turnOnBtn.setEnabled(false);
    }

    private void setupRadioButtonGroups() {

        createButtonGroup(toolInstalled, toolNotInstalled);
        createButtonGroup(paperInstalled, paperNotInstalled);
        createButtonGroup(a4PaperFormat, a3PaperFormat);
        createButtonGroup(horizontally, vertically);

        setupRadioButtonChangeStateListener();
    }

    private ButtonGroup createButtonGroup(final JRadioButton firstButton, final JRadioButton secondButton) {

        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(firstButton);
        buttonGroup.add(secondButton);
        return buttonGroup;
    }

    private void onToolPresenceBGClick() {

    }

    private void onPaperPresenceBGClick() {
        paperInstalled.addActionListener(e -> checkSettings());
        paperNotInstalled.addActionListener(e -> checkSettings());
    }

    private void onPaperFormatBGClick() {

        final String a4Icon = "images/paperA4.png";
        final String a3Icon = "images/paperA3.png";

        final boolean[] a4Format = {true};

        a4PaperFormat.addActionListener(e -> {

            if (a4PaperFormat.isSelected() && !a4Format[0]) {
                setupNewIcon(getClass().getResource(a4Icon));
                a4Format[0] = true;
            }
        });

        a3PaperFormat.addActionListener(e -> {

            if (a3PaperFormat.isSelected() && a4Format[0]) {
                setupNewIcon(getClass().getResource(a3Icon));
                a4Format[0] = false;
            }
        });
    }

    private void setupNewIcon(final URL iconURL) {

        imageLabel.setIcon(new ImageIcon(iconURL));
        if (horizontally.isSelected()) {
            imageLabel.setIcon(rotateIcon(RotatedIcon.Rotate.UP));
        }
    }

    private Icon rotateIcon(final RotatedIcon.Rotate rotateDirection) {
        return new RotatedIcon(imageLabel.getIcon(), rotateDirection);
    }

    private void onPaperOrientationBGClick() {

        final boolean[] orientationVertically = {true};

        horizontally.addActionListener(e -> {
            if (horizontally.isSelected() && orientationVertically[0]) {
                imageLabel.setIcon(rotateIcon(RotatedIcon.Rotate.UP));
                orientationVertically[0] = false;
            }
        });

        vertically.addActionListener(e ->
        {
            if (vertically.isSelected() && !orientationVertically[0]) {
                imageLabel.setIcon(rotateIcon(RotatedIcon.Rotate.DOWN));
                orientationVertically[0] = true;
            }
        });
    }

    private void setupRadioButtonChangeStateListener() {

        onToolPresenceBGClick();
        onPaperPresenceBGClick();
        onPaperFormatBGClick();
        onPaperOrientationBGClick();

        warehouseCheckBox.addActionListener(e -> checkSettings());
        paperCoordinatesCheckBox.addActionListener(e -> checkSettings());
    }

    private void setupButtonsListeners() {

        turnOnBtn.addActionListener(e -> {
            ConnectionManager.getInstance().startWaitThread();
            serverPanel = ServerPanel.getInstance();
            mainFrame.changeView(serverPanel.getMainPanel());
        });

        closeBtn.addActionListener(e -> System.exit(0));
    }

    private void createWarehouseCoordinatesTable() {

        String[] columns = {"", "1", "2", "3", "4", "5", "6"};

        Object[][] data = {
                {"x", "10", "20", "30", "40", "50", "60"},
                {"y", "30", "30", "30", "30", "30", "30"},
                {"z", "20", "20", "20", "20", "20", "20"}};

        final DefaultTableModel model = new DefaultTableModel(data, columns);
        warehouseCoordinatesTable.setModel(model);
    }

    private void createPaperCornerCoordinatesTable() {

        String[] columns = {"x", "y"};

        Object[][] data = {
                {"10", "10"},
                {"10", "10"}};
        createTable(columns, data);
    }

    private void createTable(final String[] columns, final Object[][] data) {
        final DefaultTableModel model = new DefaultTableModel(data, columns);
        paperCornerCoordinates.setModel(model);
        paperCornerCoordinates.setDefaultRenderer(Object.class, setupCellRenderer());
    }

    private DefaultTableCellRenderer setupCellRenderer() {
        final DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        return cellRenderer;
    }
}