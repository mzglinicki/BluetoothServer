import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * Created by mzglinicki.96 on 23.07.2016.
 */
public class SettingsPanel {
    private JPanel mainSettingsJPanel;
    private JPanel warehouseSettings;
    private JButton turnOnBtn;
    private JButton closeBtn;
    private JRadioButton toolInstaled;
    private JRadioButton toolNotInstalled;
    private JRadioButton takRadioButton2;
    private JRadioButton nieRadioButton1;
    private JRadioButton a4RadioButton;
    private JRadioButton a3RadioButton;
    private JRadioButton poziomaRadioButton;
    private JRadioButton pionowaRadioButton;
    private JTable paperCornerCoordinates;
    private JTable warehouseCoordinatesTable;
    private JPanel paperSettings;
    private JPanel buttonsPanel;
    private JPanel warehouseCoordinates;
    private JPanel gripsPanel;
    private JPanel paperPresentPanel;
    private JPanel paperFormat;
    private JPanel paperOrientation;
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
        setupButtonListeners();
        createWarehouseCoordinatesTable();
    }

    public void setMainGUIFrame(MainGUIFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel getMainSettingsJPanel() {
        return mainSettingsJPanel;
    }

    private void setupButtonListeners() {

        turnOnBtn.addActionListener(e -> {
            ConnectionManager.getInstance().startWaitThread();
            serverPanel = ServerPanel.getInstance();
            mainFrame.changeView(serverPanel.getMainPanel());
        });

        closeBtn.addActionListener(e -> System.exit(0));
    }

    private void createWarehouseCoordinatesTable() {

        String[] columns = {"", "1", "2", "3", "4", "5,", "6"};

        Object[][] data = {
                {""},
                {"x", "10", "20", "30", "40", "50", "60"},
                {"y", "10", "20", "30", "40", "50", "60"},
                {"z", "10", "20", "30", "40", "50", "60"}};

        DefaultTableModel model = new DefaultTableModel(data, columns) {

            @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
        };
        warehouseCoordinatesTable.setModel(model);

    }
}