import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(8189);
        Socket socket = server.accept();
        System.out.println("Client accepted");
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        String fileName = in.readUTF();
        System.out.println("fileName: " + fileName);
        long length = in.readLong();
        System.out.println("fileLength: " + length);
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        byte [] buffer = new byte[8192];
        for (long i = 0; i < length; i++) {
            fos.write(in.read());
        }
        fos.close();
        out.writeUTF("File: " + fileName + ", downloaded!");
    }
}
