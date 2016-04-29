package bluetoothService;

import externalFile.ExternalFileManager;
import org.codehaus.jettison.json.JSONObject;

import javax.microedition.io.StreamConnection;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mzglinicki.96 on 18.04.2016.
 */
public class ProcessConnectionThread extends Thread {

    private StreamConnection connection;
    private JSONObject listInJSON = new JSONObject();
    private String listOfPoints;
    private List<Point> coordinatesList = new ArrayList<>();

    private static final int EXIT_CMD = -1;

    public ProcessConnectionThread(StreamConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {

        // prepare to receive data
        try {
            InputStream inputStream = connection.openInputStream();
            byte[] data = new byte[204800];
            int bytesRead;

            System.out.println("waiting for input");

            while (true) {
                bytesRead = inputStream.read(data);
                if (bytesRead == EXIT_CMD) {
                    System.out.println("finished process");
                    break;
                }
                processCommand(new String(data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Process the command from client
     *
     * @param command the command code
     */

    private void processCommand(String command) {

        try {
            this.listOfPoints = command;
            createCode(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public List<Point> rescaleDraw(String listOfPoints) {

//        rescale draw to A4
        List<Point> originalCoordinates = splitString(listOfPoints);
        List<Point> rescaledCoordinates = new ArrayList<>();

        double scale = 1.26463964;
        int x = 0;
        int y = 0;


        for (Point point : originalCoordinates) {

            if (point.getX() > 0) {
                x = (int) ((point.getX() * 297))/1123;
                y = (int) ((point.getY() * 210))/794;
            } else {
                x = (int) point.getX();
                y = (int) point.getY();
            }
            rescaledCoordinates.add(new Point(x, y));
        }
        rescaledCoordinates.remove(rescaledCoordinates.size() - 1);
        return rescaledCoordinates;
    }

    public void writeDataToFile(StringBuffer code) {

        try {
            FileOutputStream fileStream = new FileOutputStream("data_json.txt");

            OutputStreamWriter outputStream = new OutputStreamWriter(fileStream);

            Writer writer = new BufferedWriter(outputStream);

            writer.write(code.toString());

            writer.close();

            fileStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCode(String listOfPoints) {

        List<Point> rescaledCoordinates = rescaleDraw(listOfPoints);

        StringBuffer coordinatesListToPrint = createListToPrint(rescaledCoordinates);

        writeDataToFile(coordinatesListToPrint);
    }

    public StringBuffer createListToPrint(List<Point> coordinatesList) {

        StringBuffer coordinatesListToPrint = new StringBuffer();

        int numOfPoint = 10;
        int x;
        int y;

        for (int i = 0; i < coordinatesList.size(); i++) {
            if (i == 0) {
                coordinatesListToPrint.append("Idź do punktu pierwszego p10 \n");

                numOfPoint += 10;
            } else if (i >= 1 && coordinatesList.get(i).x > 0) {
//                x = coordinatesList.get(i).x - coordinatesList.get(i - 1).x;
//                y = coordinatesList.get(i).y - coordinatesList.get(i - 1).y;

                x = coordinatesList.get(i).x;
                y = coordinatesList.get(i).y;

                coordinatesListToPrint.append("MoveL Offs (" + "p").append(numOfPoint).append(", ").append(x).append(", ").append(y).append(", ").append("0), v1000, z0, tool0;").append("\n");

            } else if (coordinatesList.get(i).x < 0) {
                coordinatesListToPrint.append("znalazłem -1 \n");

                if (i < coordinatesList.size() - 1) {
                    x = coordinatesList.get(i + 1).x;
                    y = coordinatesList.get(i + 1).y;

//                    coordinatesListToPrint.append("");

                    coordinatesListToPrint.append("Idź do punktu " + x + " " + y + "będzie to p" + numOfPoint + "\n");
                    i++;
                } else if (i == coordinatesList.size() - 1) {
                    coordinatesListToPrint.append("Zakończ" + "\n");
                }
            }
        }
        System.out.println(coordinatesListToPrint);
        return coordinatesListToPrint;
    }
}