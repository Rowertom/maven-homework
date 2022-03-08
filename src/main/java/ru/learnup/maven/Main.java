package ru.learnup.maven;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Main {

    public static void main(String[] args) throws Exception {
        int[] randomArray = new int[1000];
        for (int i = 0; i < randomArray.length; i++) {
            randomArray[i] = 100 + new Random().nextInt(100);
        }

        List<Integer> numbers = List.of(3, 5, 7, 9, 11);

        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<Future<Integer>> futures = new ArrayList<>();
        for (int x : numbers) {
            Future<Integer> future = service.submit(() -> {
                int numberTask = 0;
                for (int number : randomArray) {
                    if (number % x == 0) {
                        numberTask += number;
                    }
                }
                return numberTask;


            });
            futures.add(future);
        }

        int futuresNumber = 0;
        int max = Integer.MIN_VALUE;
        for (Future<Integer> futur : futures) {
            int y = futur.get();
            if (y > max) {
                max = y;
                futuresNumber = numbers.get(futures.indexOf(futur));
            }
        }
        service.shutdown();

        System.out.println("Сумма чисел делящихся на " + futuresNumber + " максимальная и равна " + max);


    }
}

