/**
 * Created by mzglinicki.96 on 24.07.2016.
 */
public class ConnectionManager {

    private static ConnectionManager manager = null;
    private Thread waitThread;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (manager == null) {
            manager = new ConnectionManager();
        }
        return manager;
    }

    public void startWaitThread() {
        waitThread = new Thread(new WaitThread());
        waitThread.start();
    }

    public void stopThread() {
        WaitThread waitThread = new WaitThread();
        waitThread.stopWaitingThread();
    }
}