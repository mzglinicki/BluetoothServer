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
    private int currentColor;
    private StringBuffer coordinatesListToPrint = new StringBuffer();
    private static final int paperWidth = 210;
    private static final int paperHeight = 297;
    private static final int paperWidthInPixels = 794;
    private static final int paperHeightInPixels = 1123;

    private CodeCreator() {
    }

    public static CodeCreator getInstance() {
        if (codeCreator == null) {
            codeCreator = new CodeCreator();
        }
        return codeCreator;
    }

    public void createCode(final String listOfPoints) {

        final List<Point> rescaledCoordinates = rescaleDraw(listOfPoints);
        coordinatesListToPrint = createListToPrint(rescaledCoordinates);
        writeOnTextArea(coordinatesListToPrint);
    }

    public void writeDataToFile() {

        try {
            final String file = "data_json.txt";
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

    public void writeOnTextArea(final StringBuffer toPrint) {
        final JTextArea codeTextArea = ServerPanel.getInstance().getCodeTextArea();
        codeTextArea.setText(toPrint.toString());
    }

    public List<Point> rescaleDraw(String listOfPoints) {

        final List<Point> originalCoordinates = splitString(listOfPoints);
        final List<Point> rescaledCoordinates = new ArrayList<>();

        int x = 0;
        int y = 0;

        for (final Point point : originalCoordinates) {

            if (point.getX() > 0) {
                x = (int) ((point.getX() * paperWidth)) / paperWidthInPixels;
                y = (int) ((point.getY() * paperHeight)) / paperHeightInPixels;
            } else {
                x = (int) point.getX();
                y = (int) point.getY();
            }
            rescaledCoordinates.add(new Point(x, y));
        }
        rescaledCoordinates.remove(rescaledCoordinates.size() - 1);
        return rescaledCoordinates;
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
        printConstPoints();
        coordinatesListToPrint.append(Constants.PROC_MAIN);

        for (int i = 0; i < coordinatesList.size(); i++) {
            int currentXValue = coordinatesList.get(i).x;
            int currentYValue = coordinatesList.get(i).y;

            if (i == 0) {
                boolean changeDefaultColor = checkFirstPoint(currentXValue);
                if (changeDefaultColor) {
                    continue;
                }
            }
            if (i == coordinatesList.size() - 1) {
                coordinatesListToPrint.append(exitCode());
                break;
            }
            if (currentXValue < 0) {
                changeAction(currentXValue);
            } else {
                coordinatesListToPrint.append(setNextPoint(currentXValue, currentYValue)).append("\n");
            }
        }
        System.out.println(coordinatesListToPrint);
        getToolFromWarehouse();
        return coordinatesListToPrint;
    }

    private void changeAction(final int currentXValue) {

        if (currentXValue != currentColor) {
            for (CodeCreatorHelper codeCreatorHelper : CodeCreatorHelper.values()) {
                if (codeCreatorHelper.getCommandId() == currentXValue) {
                    coordinatesListToPrint.append(codeCreatorHelper.getCommand());
                    if (currentXValue != CodeCreatorHelper.EndOfShape.getCommandId()) {
                        coordinatesListToPrint.append(Constants.GET_TOOL);
                        currentColor = currentXValue;
                    }
                }
            }
        }
    }

    private boolean checkFirstPoint(final int currentXValue) {

        //TODO
        if (currentXValue > 0) {
            coordinatesListToPrint.append(getStartCode());
            return false;
        } else {

            coordinatesListToPrint.append(getStartCode());
            return true;
        }
    }

    private StringBuffer getStartCode() {
        StringBuffer startPoint = new StringBuffer();
        startPoint.append(Constants.GO_TO_FIRST_POINT_COMMENT);
        return startPoint;
    }

    private StringBuffer setNextPoint(final int x, final int y) {
        StringBuffer nextPoint = new StringBuffer();
        nextPoint.append(Constants.MOVE_L_OFFS_P20_START_COMMAND).append(x).append(", ").append(y).append(Constants.MOVE_L_OFFS_P20_END_COMMAND);
        return nextPoint;
    }

    private StringBuffer exitCode() {
        StringBuffer exitCode = new StringBuffer();
        exitCode.append(Constants.END_DRAWING);
        exitCode.append(Constants.ENDPROC);
        return exitCode;
    }

    private void getToolFromWarehouse() {

        coordinatesListToPrint.append(Constants.PROC_GET_TOOL);
        coordinatesListToPrint.append(Constants.SAVE_POSITION);
        coordinatesListToPrint.append(Constants.ABOVE_WAREHOUSE);
        coordinatesListToPrint.append(Constants.INSIDE_WAREHOUSE);
        coordinatesListToPrint.append(Constants.WAIT_TIME);
        coordinatesListToPrint.append(Constants.CLOSE_GRIPPER);
        coordinatesListToPrint.append(Constants.WAIT_TIME);
        coordinatesListToPrint.append(Constants.ABOVE_WAREHOUSE);
        coordinatesListToPrint.append(Constants.SAVE_POSITION);
        coordinatesListToPrint.append(Constants.ENDPROC);
    }

    private void leaveToolInWarehouse(){

    }

    private void changeTool(){

    }

    public void printConstPoints() {

        //TODO improve me
        coordinatesListToPrint.append("     CONST robtarget p10:=[[35.06,-46.07,-865.31],[0,0.401409,-0.915899,0],[0,1,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];\n");
        coordinatesListToPrint.append("     CONST robtarget p20:=[[30.26,121.72,-865.31],[0,0.40142,-0.915894,0],[0,1,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];\n");
        coordinatesListToPrint.append("     CONST robtarget p30:=[[30.25,121.72,-914.80],[0,0.401409,-0.915899,0],[0,1,0,0],[9E+09,9E+09,9E+09,9E+09,9E+09,9E+09]];\n");
    }
}