import javafx.animation.ScaleTransition;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class FileClient {

    private static DataInputStream in;
    private static DataOutputStream out;
    private static boolean isRunning = true;
    private final static int DEFAULT = -1, U = 0, D = 1;
    private static int currentState;

    public void stop() {
        isRunning = false;
    }

    public void init() {
        currentState = DEFAULT;
        try (Socket socket = new Socket("localhost", 8189)) {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (isRunning) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FileClient().init();
        new Thread(() -> {
            try {
                Scanner sc = new Scanner(System.in);
                while (isRunning) {
                    String command = sc.next();
                    if (command.equals("/upload")) {
                        out.writeUTF(command);

                    }
                    if (command.equals("/download")) {
                        out.writeUTF(command);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
