package model.btConection;

import model.Constants;
import model.btConection.ProcessConnectionThread;
import view.ServerPanel;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

/**
 * Created by mzglinicki.96 on 18.05.2016.
 */
public class WaitThread implements Runnable {

    private volatile boolean stopThread = false;

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

        // retrieve the local Bluetooth device object
        LocalDevice localDevice;
        StreamConnectionNotifier notifier;
        StreamConnection connection;

        // setup the server to listen for connection
        try {
            localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);

            final UUID uuid = new UUID(uuidValue, false);
            final String url = localhost + uuid.toString() + remoteBluetooth;
            notifier = (StreamConnectionNotifier) Connector.open(url);

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // waiting for connection
        while (!stopThread) {
            try {
                ServerPanel.getInstance().writeMessage(Constants.WAITING_FOR_INPUT_DATA);
                connection = notifier.acceptAndOpen();

                Thread processThread = new Thread(new ProcessConnectionThread(connection));
                processThread.start();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void stopWaitingThread() {
        stopThread = true;
    }
}