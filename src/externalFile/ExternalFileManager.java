package externalFile;

import bluetoothService.ProcessConnectionThread;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by mzglinicki.96 on 23.04.2016.
 */
public class ExternalFileManager {

    private static ExternalFileManager fileManager = null;

    private ExternalFileManager() {
    }

    public static ExternalFileManager getInstance() {

        if (fileManager == null) {
            fileManager = new ExternalFileManager();
        }
        return fileManager;
    }

    private ProcessConnectionThread connectionThread;



}
