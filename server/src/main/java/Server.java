import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Server {

    private static boolean isRunning = true;
    public ConcurrentLinkedDeque<FileHandler> clients;

    public ConcurrentLinkedDeque<FileHandler> getClients() {
        return clients;
    }

    public static void stop() {
        isRunning = false;
    }

    public void removeFromClientsList(FileHandler handler) {
        clients.remove(handler);
    }

    public void start() {
        clients = new ConcurrentLinkedDeque<>();
        try (ServerSocket server = new ServerSocket(8189)) {
            System.out.println("Server started!");
            while (isRunning) {
                Socket socket = server.accept();
                System.out.println("Client with ip: " + socket.getInetAddress() + " accepted!");
                FileHandler connection = new FileHandler(this, socket);
                new Thread(connection).start();
                clients.add(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new Thread(() -> {
            Scanner in = new Scanner(System.in);
            while (true) {
                String com = in.next();
                if (com.equals("quit")) {
                    stop();
                    break;
                }
            }
        }).start();
        new Server().start();
//        ServerSocket server = new ServerSocket(8189);
//        Socket socket = server.accept();
//        System.out.println("Client accepted");
//        DataInputStream in = new DataInputStream(socket.getInputStream());
//        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//        String fileName = in.readUTF();
//        System.out.println("fileName: " + fileName);
//        long length = in.readLong();
//        System.out.println("fileLength: " + length);
//        File file = new File(fileName);
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        FileOutputStream fos = new FileOutputStream(file);
//        byte [] buffer = new byte[8192];
//        for (long i = 0; i < length; i++) {
//            fos.write(in.read());
//        }
//        fos.close();
//        out.writeUTF("File: " + fileName + ", downloaded!");
    }
}
