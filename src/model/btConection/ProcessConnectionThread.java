package model.btConection;

import model.codeCreator.CodeCreator;
import model.Constants;

import javax.microedition.io.StreamConnection;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mzglinicki.96 on 18.05.2016.
 */
public class ProcessConnectionThread extends Thread {

    private static final int EXIT_CMD = -1;
    private static final int BYTES = 2048;
    private final CodeCreator codeCreator = CodeCreator.getInstance();
    private final StreamConnection connection;

    public ProcessConnectionThread(final StreamConnection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            final InputStream inputStream = connection.openInputStream();
            final StringBuffer dataFromStream = new StringBuffer();
            byte[] data = new byte[BYTES];
            int bytesRead;

            while (true) {
                bytesRead = inputStream.read(data);
                dataFromStream.append(new String(data));
                if (isEndOfFile(dataFromStream)) {
                    processCommand(dataFromStream.toString());
                    inputStream.close();
                    connection.close();
                    break;
                } else {
                    data = new byte[BYTES];
                }
                if (bytesRead == EXIT_CMD) {
                    break;
                }
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    private void processCommand(final String command) {

        try {
            codeCreator.prepareInputData(command);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isEndOfFile(final StringBuffer dataFromStream) {

        final Pattern pattern = Pattern.compile("(\\-\\d+)");
        final Matcher matcher = pattern.matcher(dataFromStream);
        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group().trim());
            matcher.find();
            int y = Integer.parseInt(matcher.group().trim());
            if (x == -10 && y == -10) {
                return true;
            }
        }
        return false;
    }
}