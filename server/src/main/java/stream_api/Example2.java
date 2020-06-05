package stream_api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Example2 {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\mlev1219\\IdeaProjects\\geekbrains-cloud-storage\\server\\src\\main\\java\\stream_api\\input.txt");
        BufferedReader in = new BufferedReader(new FileReader(file));
        Optional<Integer> o = in.lines().flatMap(x -> Stream.of(x.split(" ")))
                .map(Integer::parseInt)
                .reduce((a, b) -> a + b);
        System.out.println(o.get());
        //Optional<String> opt = new Optional<>();
        //opt.orElse("null");
//        Optional<String> opt = in.lines()
//                .flatMap(arg -> Stream.of(arg.split(" ")))
//                .filter(str -> str.length() > 2)
//                .distinct() // O(NlogN)
//                .map(String::toUpperCase)
//                .map(arg -> arg.replaceAll("A", "O"))
//                .sorted()
//                .reduce(String::concat);
//        System.out.println(opt.get());
                //.map(arg -> arg.replaceAll(" ", ""))
                //.collect(Collectors.toList());
        //System.out.println(list);
    }
}
