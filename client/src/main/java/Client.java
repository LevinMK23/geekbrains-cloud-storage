import java.io.*;
import java.net.Socket;
import java.sql.DriverManager;
import java.util.Arrays;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("client\\src\\main\\resources" +
                "\\Atlas-master.zip");
        Class.forName("org.sqlite.JDBC");
        try(Socket socket = new Socket("localhost", 8189)){
            System.out.println("Connected to server");
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("Atlas-master.zip");
            out.writeLong(file.length());
            FileInputStream fis = new FileInputStream(file);
            byte [] buffer = new byte[8192];
            int cnt = 0, x;
            while ((x = fis.read()) != -1) {
                out.write(x);
                out.flush();
                // Arrays.fill(buffer, (byte) 0);
            }
            String callBack = in.readUTF();
            System.out.println(callBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
