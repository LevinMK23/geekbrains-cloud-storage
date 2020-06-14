package stream_api;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Example1 {

    @FunctionalInterface
    static interface Func {
        int foo(int a, int b);
    }

    public static void main(String[] args) throws Exception {
        Runnable runnable = () -> {

        };
        Callable<Integer> call = () -> {
            int a = 4, b = 7;
            return a + b;
        };
        ExecutorService e = Executors.newFixedThreadPool(4);
        Future<Integer> f = e.submit(call);
        System.out.println(call.call());
        System.out.println(call.getClass());
        Func foo = Integer :: sum;
        System.out.println(runnable.getClass());
    }
}
