package stream_api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example3 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\mlev1219\\IdeaProjects\\geekbrains-cloud-storage\\server\\src\\main\\java\\stream_api\\input.txt");
        BufferedReader in = new BufferedReader(new FileReader(file));
        in.lines()
                .flatMap(s -> Stream.of(s.split(" +")))
                .map(s -> {
                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == '’') {
                            return s.substring(0, i);
                        }
                    }
                    return s;
                })
                .map(s -> s.toLowerCase().replaceAll("[^a-zA-z0-9]", ""))
                .filter(s -> s.length() > 0)
                .collect(HashMap<String, Integer>::new,
                        (map, s) -> map.put(s, map.getOrDefault(s, 0) + 1),
                        HashMap::putAll)
                .entrySet().stream().sorted((entry1, entry2) -> - entry1.getValue() + entry2.getValue())
                .forEach((entry) -> System.out.println(entry.getKey() + " : " + entry.getValue()));
    }
}
