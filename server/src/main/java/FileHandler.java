import java.io.*;
import java.net.Socket;

public class FileHandler implements Runnable {

    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isRunning = true;
    private final int COMMAND = 0, UPLOAD = 1, DOWNLOAD = 2;
    private int currentOption;
    private String serverPath = "server/src/main/resources/";

    public void stop() {
        isRunning = false;
    }

    public FileHandler(Server server, Socket socket) throws IOException {
        this.socket = socket;
        this.server = server;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        currentOption = COMMAND;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                if (currentOption == COMMAND) {
                    String command = in.readUTF();
                    if (command.equals("/upload")) {
                        System.out.println("command upload!");
                        currentOption = UPLOAD;
                    }
                    if (command.equals("/download")) {
                        System.out.println("command download!");
                        currentOption = DOWNLOAD;
                    }
                }
                if (currentOption == DOWNLOAD) {
                    // wait file name from client
                    String fileName = in.readUTF();
                    System.out.println("fileName: " + fileName);
                    // need find file on server with fileName
                    File file = new File(serverPath + fileName);
                    if (!file.exists()) {
                        out.writeInt(-1);
                        out.flush();
                        currentOption = COMMAND;
                    } else {
                        long length = file.length();
                        out.writeLong(length);
                        out.flush();
                        FileInputStream fis = new FileInputStream(file);
                        byte[] buffer = new byte[1024];
                        while (fis.available() > 0) {
                            int read = fis.read(buffer);
                            out.write(buffer, 0, read);
                            out.flush();
                        }
                        currentOption = COMMAND;
                    }
                }
                if (currentOption == UPLOAD) {
                    // need create file on server
                    // wait from client file name, file length, file bytes
                    String fileName = in.readUTF();
                    long fileLength = in.readLong();
                    File file = new File(serverPath + fileName);
                    if (file.exists()) {
                        file.createNewFile();
                    } else {
                        throw new RuntimeException("file exist on server");
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    for (int i = 0; i < fileLength / 1024; i++) {
                        int read = in.read(buffer);
                        fos.write(buffer, 0, read);
                    }
                    currentOption = COMMAND;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
