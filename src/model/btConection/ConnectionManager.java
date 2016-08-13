package model.btConection;

/**
 * Created by mzglinicki.96 on 24.07.2016.
 */
public class ConnectionManager {

    private static ConnectionManager manager = null;
    WaitThread waitThread = new WaitThread();

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        if (manager == null) {
            manager = new ConnectionManager();
        }
        return manager;
    }

    public void startWaitThread() {

        final Thread thread = new Thread(waitThread);
        thread.start();
    }

//    public void startProcessConnection(){
//        waitThread.startProcessConnection();
//    }
}