package model.codeCreator;

import controller.ButtonManager;
import model.Constants;
import model.Tool;
import model.ToolManager;
import view.ServerPanel;
import view.SettingsPanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mzglinicki.96 on 30.05.2016.
 */
public class CodeCreator {

    private static CodeCreator codeCreator = null;
    private SettingsPanel settingsPanel;
    private List<Tool> tools;
    private StringBuffer coordinatesListToPrint;
    private List<Integer> usedTools;
    private int currentColor;
    private int paperCornerXCoordinate;
    private int paperCornerYCoordinate;
    private int paperWidth;
    private int paperHeight;
    private int paperWidthInPixels;
    private int paperHeightInPixels;
    private int xFromBt;
    private int yFromBt;
    private int xForDelta;
    private int yForDelta;
    private boolean changeColor = true;
    private String comma = ", ";
    private String endOfLineWithBracket = ")\n";
    private String colon = ": ";

    private CodeCreator() {
        setupPaperFormat();
    }

    public static CodeCreator getInstance() {
        if (codeCreator == null) {
            codeCreator = new CodeCreator();
        }
        return codeCreator;
    }

    public void prepareInputData(final String listOfPoints) {

        final List<Point> originalCoordinates = splitString(listOfPoints);
        final List<Point> rescaledCoordinates = rescaleDraw(originalCoordinates);
        creteCode(rescaledCoordinates);
    }

    private void creteCode(final List<Point> rescaledCoordinates) {
        coordinatesListToPrint = new StringBuffer();
        coordinatesListToPrint = createListToPrint(rescaledCoordinates);

        final JTextArea codeTextArea = ServerPanel.getInstance().getCodeTextArea();
        codeTextArea.setText(coordinatesListToPrint.toString());
        printSummary(codeTextArea);
    }

    public void writeDataToFile() {

        final String file = "data_json.txt";
        try {
            final FileOutputStream fileStream = new FileOutputStream(file);
            final OutputStreamWriter outputStream = new OutputStreamWriter(fileStream);
            final Writer writer = new BufferedWriter(outputStream);

            writer.write(coordinatesListToPrint.toString());
            writer.close();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupPaperFormat() {

        settingsPanel = SettingsPanel.getInstance();

        final int a4PaperWidth = 210;
        final int a4PaperHeight = 297;
        final int a4PaperWidthInPixels = 2480;
        final int a4PaperHeightInPixels = 3508;
        final int a3PaperWidth = 297;
        final int a3PaperHeight = 420;
        final int a3PaperWidthInPixels = 3508;
        final int a3PaperHeightInPixels = 4961;

        if (settingsPanel.getA4PaperFormat().isSelected()) {
            paperWidth = a4PaperWidth;
            paperHeight = a4PaperHeight;
            paperWidthInPixels = a4PaperWidthInPixels;
            paperHeightInPixels = a4PaperHeightInPixels;
        } else {
            paperWidth = a3PaperWidth;
            paperHeight = a3PaperHeight;
            paperWidthInPixels = a3PaperWidthInPixels;
            paperHeightInPixels = a3PaperHeightInPixels;
        }
    }

    public List<Point> rescaleDraw(final List<Point> listOfPoints) {

        final List<Point> rescaledCoordinates = new ArrayList<>();

        for (final Point point : listOfPoints) {

            if (point.getX() > 0) {
                xFromBt = (int) ((point.getX() * paperWidth)) / paperWidthInPixels;
                yFromBt = (int) ((point.getY() * paperHeight)) / paperHeightInPixels;
            } else {
                xFromBt = (int) point.getX();
                yFromBt = (int) point.getY();
            }

            checkOrientation();
            rescaledCoordinates.add(new Point(xForDelta, yForDelta));
        }
        rescaledCoordinates.remove(rescaledCoordinates.size() - 1);
        return rescaledCoordinates;
    }

    private void checkOrientation() {

        if (settingsPanel.getVertically().isSelected()) {
            xForDelta = xFromBt;
            yForDelta = yFromBt;
        } else {
            xForDelta = yFromBt;
            yForDelta = xFromBt;
        }
    }

    public List<Point> splitString(final String listOfPoints) {

        final List<Point> coordinatesList = new ArrayList<>();

        if (listOfPoints != null) {
            Pattern pattern = Pattern.compile("(\\d+)|(\\-\\d)");
            Matcher matcher = pattern.matcher(listOfPoints);
            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group().trim());
                matcher.find();
                int y = Integer.parseInt(matcher.group().trim());
                coordinatesList.add(new Point(x, y));
            }
        }
        return coordinatesList;
    }

    public StringBuffer createListToPrint(final java.util.List<Point> coordinatesList) {

        coordinatesListToPrint.append(Constants.MAIN_MODULE);
        coordinatesListToPrint.append(Constants.PROC_MAIN);

        getToolIfGripperEmpty();

        for (int i = 0; i < coordinatesList.size(); i++) {
            int currentXValue = coordinatesList.get(i).x;
            int currentYValue = coordinatesList.get(i).y;

            startDrawNewShape(currentXValue);

            if (checkFirstPoint(i, currentXValue)) {
                continue;
            }

            if (checkIfLastPoint(i, coordinatesList.size() - 1)) {
                break;
            }

            if (currentXValue < 0) {
                changeAction(currentXValue);
            } else {
                coordinatesListToPrint.append(setNextPoint(currentXValue, currentYValue)).append("\n");
            }
        }
        System.out.println(coordinatesListToPrint);//TODO to delete
        getToolFromWarehouse();
        return coordinatesListToPrint;
    }

    private boolean checkIfLastPoint(final int i, final int lastPoint) {
        if (i == lastPoint) {
            coordinatesListToPrint.append(getExitCode());
            return true;
        }
        return false;
    }

    private boolean checkFirstPoint(final int i, final int currentXValue) {
        if (i == 0) {
            coordinatesListToPrint.append(getStartCode());
            return checkInitChangeTool(currentXValue);
        }
        return false;
    }

    private void getToolIfGripperEmpty() {

        tools = ToolManager.getInstance().getTools();
        if (settingsPanel.getToolNotInstalled().isSelected()) {
            Tool defaultTool = tools.get(0);
            coordinatesListToPrint.append(getGetToolMethod(defaultTool.getToolXCoordinate(), defaultTool.getToolYCoordinate(), defaultTool.getToolZCoordinate()));

        }
    }

    private void startDrawNewShape(final int currentXValue) {
        if (!changeColor && currentXValue > 0) {
            coordinatesListToPrint.append(Constants.START_NEW_SHAPE);
            changeColor = true;
        }
    }

    private boolean checkInitChangeTool(final int currentXValue) {
        return currentXValue <= 0;
    }

    private void changeAction(final int currentXValue) {

        if (currentXValue != currentColor) {
            for (final CodeCreatorHelper codeCreatorHelper : CodeCreatorHelper.values()) {

                if (codeCreatorHelper.getCommandId() == currentXValue) {

                    if (currentXValue == CodeCreatorHelper.EndOfShape.getCommandId()) {
                        changeColor = false;
                    } else {
                        changeTool(currentXValue);
                    }
                }
            }
        }
    }

    private void changeTool(final int currentXValue) {
        coordinatesListToPrint.append(Constants.CHANGED_TOOL_GO_TO_START_POINT);
        if (usedTools == null) {
            usedTools = new ArrayList<>();
        }

        if (!usedTools.contains(currentXValue)) {
            usedTools.add(currentXValue);
        }
        tools.stream().filter(tool ->
                currentXValue == tool.getToolId()).forEach(tool ->
                coordinatesListToPrint.append(getGetToolMethod(tool.getToolXCoordinate(), tool.getToolYCoordinate(), tool.getToolZCoordinate())));
        coordinatesListToPrint.append(getStartCode());
        changeColor = true;
        currentColor = currentXValue;
    }

    private StringBuffer getGetToolMethod(final int x, final int y, final int z) {
        final StringBuffer getToolMethod = new StringBuffer();
        getToolMethod.append(Constants.GET_TOOL_START)
                .append(x).append(comma)
                .append(y).append(comma)
                .append(x).append(endOfLineWithBracket);
        return getToolMethod;
    }

    private StringBuffer getStartCode() {

        final List<Integer> paperCoordinates = ButtonManager.getInstance().getPaperCornerCoordinates();
        paperCornerXCoordinate = paperCoordinates.get(0);
        paperCornerYCoordinate = paperCoordinates.get(1);

        final StringBuffer startPoint = new StringBuffer();
        startPoint.append(Constants.FIRST_POINT_START)
                .append(paperCornerXCoordinate)
                .append(comma)
                .append(paperCornerYCoordinate)
                .append(Constants.FIRST_POINT_END);
        return startPoint;
    }

    private StringBuffer setNextPoint(final int x, final int y) {

        final int xToPrint = paperCornerXCoordinate + x;
        final int yToPrint = paperCornerYCoordinate + y;

        final StringBuffer nextPoint = new StringBuffer();
        nextPoint.append(Constants.MOVE_L_OFFS_P20_START_COMMAND)
                .append(xToPrint)
                .append(comma)
                .append(yToPrint)
                .append(Constants.MOVE_L_OFFS_P20_END_COMMAND);
        return nextPoint;
    }

    private StringBuffer getExitCode() {
        final StringBuffer exitCode = new StringBuffer();
        exitCode.append(Constants.END_DRAWING)
                .append(Constants.ENDPROC);
        return exitCode;
    }

    private void getToolFromWarehouse() {
        //TODO improve me
        coordinatesListToPrint.append(Constants.PROC_GET_TOOL)
                .append(Constants.SAVE_POSITION)
                .append(Constants.ABOVE_WAREHOUSE_WITH_PARAMETERS)
                .append(Constants.INSIDE_WAREHOUSE_WITH_PARAMETERS)
                .append(Constants.WAIT_TIME)
                .append(Constants.CLOSE_GRIPPER)
                .append(Constants.WAIT_TIME)
                .append(Constants.ABOVE_WAREHOUSE_WITH_PARAMETERS)
                .append(Constants.SAVE_POSITION)
                .append(Constants.ENDPROC);
    }

    private void printSummary(final JTextArea codeTextArea) {

        codeTextArea.append(Constants.CHECK_DATA);

        printGripperInfo(codeTextArea);
        printPaperFormatInfo(codeTextArea);
        printPaperOrientationInfo(codeTextArea);
        printWarehouseSettingsInfo(codeTextArea);
    }

    private void printWarehouseSettingsInfo(final JTextArea codeTextArea) {
        codeTextArea.append(Constants.WAREHOUSE_SETTINGS);

        if (!settingsPanel.getToolNotInstalled().isSelected()) {
            codeTextArea.append(Constants.FIRST_PLACE_EMPTY);
        }

        try {
            for (final Tool tool : tools) {
                int toolId = tool.getToolId();
                printEveryUsedTools(toolId, codeTextArea);
            }
        } catch (NullPointerException defaultTool) {
            printIfNoColorChanging(codeTextArea);
        }
    }

    private void printEveryUsedTools(final int toolId, final JTextArea codeTextArea) {
        if (usedTools.contains(toolId)) {
            for (final CodeCreatorHelper codeCreatorHelper : CodeCreatorHelper.values()) {
                if (codeCreatorHelper.getCommandId() == toolId) {
                    codeTextArea.append(Constants.WAREHOUSE + codeCreatorHelper.ordinal() + colon + codeCreatorHelper.getCommand());
                }
            }
        }
    }

    private void printIfNoColorChanging(final JTextArea codeTextArea) {
        if (settingsPanel.getToolNotInstalled().isSelected()) {
            codeTextArea.append(Constants.FIRST_WAHEHOUSE_BLACK);
        } else {
            codeTextArea.append(Constants.ONE_TOOL);
        }
    }

    private void printPaperOrientationInfo(final JTextArea codeTextArea) {
        if (settingsPanel.getVertically().isSelected()) {
            codeTextArea.append(Constants.PAPER_ORIENTATION + Constants.VERTICALLY);
        } else {
            codeTextArea.append(Constants.PAPER_ORIENTATION + Constants.HORIZONTALLY);
        }
    }

    private void printPaperFormatInfo(final JTextArea codeTextArea) {
        if (settingsPanel.getA4PaperFormat().isSelected()) {
            codeTextArea.append(Constants.PAPER_FORMAT + Constants.PAPER_FORMAT_A_4);
        } else {
            codeTextArea.append(Constants.PAPER_FORMAT + Constants.PAPER_FORMAT_A_3);
        }
    }

    private void printGripperInfo(final JTextArea codeTextArea) {
        if (settingsPanel.getToolNotInstalled().isSelected()) {
            codeTextArea.append(Constants.TOOL_INSTALLED);
        } else {
            codeTextArea.append(Constants.TOOL_NOT_INSTALLED);
        }
    }

    public void printConstPoints() {

        //TODO improve me
        coordinatesListToPrint.append("     CONST robtarget p5:=[[35.06,-46.07,-865.31],[0,0.401409,-0.915899,0],[0,1,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];\n");
        coordinatesListToPrint.append("     CONST robtarget p15:=[[30.26,121.72,-865.31],[0,0.40142,-0.915894,0],[0,1,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];\n");
        coordinatesListToPrint.append("     CONST robtarget p25:=[[30.25,121.72,-914.80],[0,0.401409,-0.915899,0],[0,1,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];\n");
        coordinatesListToPrint.append("     VAR num warehouseXPosition:=0");
        coordinatesListToPrint.append("     VAR num warehouseYPosition:=0");
        coordinatesListToPrint.append("     VAR num warehouseZPosition:=0");
    }
}