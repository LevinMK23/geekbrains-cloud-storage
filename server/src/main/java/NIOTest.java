import java.io.IOException;
import java.nio.channels.Channel;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;

public class NIOTest {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("file.txt");
//        Files.write(path, new byte[]{65, 66, 67}, StandardOpenOption.CREATE);
//        Files.write(path, new byte[]{65, 66, 67}, StandardOpenOption.APPEND);
//        Files.write(path, new byte[]{65, 66, 67}, StandardOpenOption.APPEND);
        //Files.copy(path, Paths.get("file1.txt"), StandardCopyOption.REPLACE_EXISTING);
        //Files.createDirectory(Paths.get("./1"));
        Files.list(Paths.get("./1", "2")).forEach(System.out::println);

        //System.out.println(path.compareTo(Paths.get("file1.txt")));
    }
}
