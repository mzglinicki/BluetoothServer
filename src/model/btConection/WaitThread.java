package model.btConection;

import model.Constants;
import view.ServerPanel;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;

/**
 * Created by mzglinicki.96 on 18.05.2016.
 */
public class WaitThread implements Runnable {

    private StreamConnection connection;

    public WaitThread() {
    }

    @Override
    public void run() {
        waitingForConnection();
    }

    private void waitingForConnection() {

        final String localhost = "btspp://localhost:";
        final String uuidValue = "04c6093b00001000800000805f9b34fb";
        final String remoteBluetooth = ";name=RemoteBluetooth";

        LocalDevice localDevice;
        StreamConnectionNotifier notifier;

        try {
//            localDevice = LocalDevice.getLocalDevice();
//            localDevice.setDiscoverable(DiscoveryAgent.GIAC);

            final UUID uuid = new UUID(uuidValue, false);
            final String url = localhost + uuid.toString() + remoteBluetooth;
            notifier = (StreamConnectionNotifier) Connector.open(url);

        } catch (final Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            ServerPanel.getInstance().writeMessage(Constants.WAITING_FOR_INPUT_DATA);
            connection = notifier.acceptAndOpen();

            final Thread processThread = new Thread(new ProcessConnectionThread(connection));
            processThread.start();
            notifier.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}