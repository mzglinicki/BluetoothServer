import javax.microedition.io.StreamConnection;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * Created by mzglinicki.96 on 18.05.2016.
 */
public class ProcessConnectionThread extends Thread {

    private static final int EXIT_CMD = -1;
    private static final int BYTES = 2048;
    private final StreamConnection connection;

    public ProcessConnectionThread(final StreamConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            final InputStream inputStream = connection.openInputStream();
            byte[] data = new byte[BYTES];
            int bytesRead;

            while (true) {
                bytesRead = inputStream.read(data);
                if (bytesRead == EXIT_CMD) {
                    CodeCreator.getInstance().writeOnTextArea(new StringBuffer(Constants.FINISHED));
                    System.out.println(Constants.FINISHED);
                    break;
                }
                processCommand(new String(data));
                data = new byte[BYTES];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCommand(final String command) {

        try {
            CodeCreator.getInstance().createCode(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}