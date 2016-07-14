package bluetoothService;

import codeCreator.CodeCreator;

import javax.microedition.io.StreamConnection;
import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * Created by mzglinicki.96 on 18.05.2016.
 */
public class ProcessConnectionThread extends Thread {

    private static final int EXIT_CMD = -1;
    private final StreamConnection connection;

    public ProcessConnectionThread(final StreamConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = connection.openInputStream();
            byte[] data = new byte[2048];
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

    private void processCommand(String command) {

        try {
            createCode(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeDataToFile(final StringBuffer code) {

        try {
            final FileOutputStream fileStream = new FileOutputStream("data_json.txt");
            final OutputStreamWriter outputStream = new OutputStreamWriter(fileStream);
            final Writer writer = new BufferedWriter(outputStream);

            writer.write(code.toString());
            writer.close();
            fileStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createCode(final String listOfPoints) {

        final CodeCreator codeCreator = CodeCreator.getInstance();
        final List<Point> rescaledCoordinates = codeCreator.rescaleDraw(listOfPoints);
        final StringBuffer coordinatesListToPrint = codeCreator.createListToPrint(rescaledCoordinates);
        writeDataToFile(coordinatesListToPrint);
    }
}