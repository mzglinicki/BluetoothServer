package codeCreator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mzglinicki.96 on 30.05.2016.
 */
public class CodeCreator {

    private static CodeCreator codeCreator = null;
    private List<Point> coordinatesList = new ArrayList<>();
    private int currentColor;

    private CodeCreator() {
    }

    public static CodeCreator getInstance() {
        if (codeCreator == null) {
            codeCreator = new CodeCreator();
        }
        return codeCreator;
    }

    public List<Point> rescaleDraw(String listOfPoints) {

        List<Point> originalCoordinates = splitString(listOfPoints);
        List<Point> rescaledCoordinates = new ArrayList<>();

        int x = 0;
        int y = 0;

        for (Point point : originalCoordinates) {

            if (point.getX() > 0) {
                x = (int) ((point.getX() * 297)) / 1123;
                y = (int) ((point.getY() * 210)) / 794;
            } else {
                x = (int) point.getX();
                y = (int) point.getY();
            }
            rescaledCoordinates.add(new Point(x, y));
        }
        rescaledCoordinates.remove(rescaledCoordinates.size() - 1);
        return rescaledCoordinates;
    }

    public List<Point> splitString(String listOfPoints) {

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

    private StringBuffer coordinatesListToPrint = new StringBuffer();

    public StringBuffer createListToPrint(java.util.List<Point> coordinatesList) {

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
        return coordinatesListToPrint;
    }

    private void changeAction(int currentXValue) {

        if (currentXValue != currentColor) {
            for (CodeCreatorHelper codeCreatorHelper : CodeCreatorHelper.values()) {
                if (codeCreatorHelper.getCommandId() == currentXValue) {
                    coordinatesListToPrint.append(codeCreatorHelper.getCommand());
                    if (currentXValue != CodeCreatorHelper.EndOfShape.getCommandId()) {
                        currentColor = currentXValue;
                    }
                }
            }
        }
    }

    private boolean checkFirstPoint(int currentXValue) {
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

    private StringBuffer setNextPoint(int x, int y) {
        StringBuffer nextPoint = new StringBuffer();
        nextPoint.append(Constants.MOVE_L_OFFS_P20_START_COMMAND).append(x).append(", ").append(y).append(Constants.MOVE_L_OFFS_P20_END_COMMAND);
        return nextPoint;
    }

    private StringBuffer exitCode() {
        StringBuffer exitCode = new StringBuffer();
        exitCode.append(Constants.END_DRAWING);
        return exitCode;
    }
}