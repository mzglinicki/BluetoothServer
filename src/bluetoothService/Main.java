package bluetoothService;

/**
 * Created by mzglinicki.96 on 17.04.2016.
 */
public class Main {

    public static void main(String[] args) {
        Thread waitThread = new Thread(new WaitThread());
        waitThread.start();
    }
}
