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

    public StringBuffer createListToPrint(java.util.List<Point> coordinatesList) {

        StringBuffer coordinatesListToPrint = new StringBuffer();

        int numOfPoint = 10;
        int x;
        int y;

        coordinatesListToPrint.append("PROC main() \n");

        for (int i = 0; i < coordinatesList.size(); i++) {
            if (i == 0) {
                coordinatesListToPrint.append("     !Idź do punktu pierwszego p10 \n");
                coordinatesListToPrint.append("     MoveL p10, v1000, z0, tool0; \n");

                numOfPoint += 10;
            } else if (i >= 1 && coordinatesList.get(i).x > 0) {

                x = coordinatesList.get(i).x;
                y = coordinatesList.get(i).y;

                coordinatesListToPrint.append("     MoveL Offs (" + "p").append(numOfPoint).append(", ").append(x).append(", ").append(y).append(", ").append("0), v1000, z0, tool0;").append("\n");

            } else if (coordinatesList.get(i).x < 0) {
                coordinatesListToPrint.append("     !znalazłem -1 \n");

                if (i < coordinatesList.size() - 1) {
                    x = coordinatesList.get(i + 1).x;
                    y = coordinatesList.get(i + 1).y;
                    coordinatesListToPrint.append("     !Idź do punktu ").append(x).append(" ").append(y).append("będzie to p").append(numOfPoint).append("\n");
                    coordinatesListToPrint.append("     MoveL p10, v1000, z0, tool0;" + "\n");
                    i++;
                } else if (i == coordinatesList.size() - 1) {
                    coordinatesListToPrint.append("     !Zakończ" + "\n");
                    coordinatesListToPrint.append("     MoveL p10, v1000, z0, tool0;" + "\n");
                    coordinatesListToPrint.append("ENDPROC");
                }
            }
        }
        System.out.println(coordinatesListToPrint);
        return coordinatesListToPrint;
    }
}