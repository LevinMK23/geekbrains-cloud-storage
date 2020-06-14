import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer implements Runnable{

    ServerSocketChannel srv;
    Selector selector;
    int clientCount = 0;
    ByteBuffer welcome = ByteBuffer.wrap("Hello".getBytes());
    ByteBuffer buffer = ByteBuffer.allocate(256);

    public NIOServer() throws IOException {
        srv = ServerSocketChannel.open();
        srv.socket().bind(new InetSocketAddress(8189));
        selector = Selector.open();
        srv.configureBlocking(false);
        srv.register(selector, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        System.out.println("Server started on port 8189");
        Iterator<SelectionKey> iterator;
        SelectionKey key;
        while (srv.isOpen()) {
            try {
                selector.select();
                iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    key = iterator.next();
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        handleRead(key);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleRead(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        StringBuilder msg = new StringBuilder();
        buffer.clear();
        int readed = 0;
        if (channel.isConnected()) {
            while ((readed = channel.read(buffer)) > 0) {
                buffer.flip();
                byte [] bytes = new byte[buffer.limit()];
                buffer.get(bytes);
                msg.append(new String(bytes));
                buffer.clear();
            }
            String message;
            if (readed < 0) {
                message = key.attachment() + " leave the chat";
                channel.close();
            } else {
                if (msg.toString().equals("quit")) {
                    message = key.attachment() + " leave the chat";
                    channel.close();
                } else {
                    message = key.attachment() + ": " + msg.toString();
                }
            }
            System.out.println(message);
            broadCastMessage(message);
        }
    }

    private void broadCastMessage(String message) throws IOException {
        ByteBuffer msg = ByteBuffer.wrap(message.getBytes());
        for (SelectionKey key : selector.keys()) {
            if (key.isValid() && key.channel() instanceof SocketChannel) {
                ((SocketChannel) key.channel()).write(msg);
                msg.rewind();
            }
        }
        selector.selectedKeys().clear();
    }

    private void handleAccept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
        clientCount++;
        String clientName = "Client # " + clientCount;
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, clientName);
        channel.write(welcome);
        welcome.rewind();
        System.out.println("New client was connected!");
    }
}
