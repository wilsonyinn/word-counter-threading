package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class WordCounter {

    public static void main(String[] args) {
        String response1 = WordCounter.countWords(1, "WarAndPeace.txt");
        String response2 = WordCounter.countWords(2, "WarAndPeace.txt");
        System.out.println(response1);
        System.out.println(response2);
//        Scanner scanner = new Scanner(System.in);
//
//        // Input number of threads
//        System.out.print("Enter number of threads: ");
//        int numThreads = scanner.nextInt();
//
//        // Input file
//        System.out.println("Enter the file path: ");
//        String filePath = scanner.next();
//
//        // Read file
//        String text = readFile(filePath);
//
//        // Count words
//        Map<String, AtomicInteger> wordCounts = new HashMap<>();
//        int chunkSize = text.length() / numThreads;
//        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
//        long startTime = System.currentTimeMillis();
//
//        for (int i = 0; i < numThreads; i++) {
//            int start = i * chunkSize;
//            int end = (i == numThreads - 1) ? text.length() : (i + 1) * chunkSize;
//            executorService.submit(new WordCounterTask(text.substring(start, end), wordCounts));
//        }
//        executorService.shutdown();
//        while (!executorService.isTerminated()) {
//            // Wait for all threads to finish
//        }
//
//        long endTime = System.currentTimeMillis();
//        long timeElapsed = endTime - startTime;
//        double secondsElapsed = timeElapsed / 1000.0;
//
//        System.out.println("Time Elapsed for " + numThreads + " threads: " + secondsElapsed);
//        // Find top 10 most repeated words
//        System.out.println("Top 10 most repeated words:");
//        wordCounts.entrySet().stream()
//                .sorted((entry1, entry2) -> entry2.getValue().get() - entry1.getValue().get())
//                .limit(10)
//                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

    }

    static String countWords(int numThreads, String fileName) {
        // Read file
        String text = readFile(fileName);

        // Count words
        Map<String, AtomicInteger> wordCounts = new HashMap<>();
        int chunkSize = text.length() / numThreads;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? text.length() : (i + 1) * chunkSize;
            executorService.submit(new WordCounterTask(text.substring(start, end), wordCounts));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // Wait for all threads to finish
        }

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        double secondsElapsed = timeElapsed / 1000.0;
        StringBuilder builder = new StringBuilder();

        builder.append("Time Elapsed for ").append(numThreads)
                .append(" threads: ").append(secondsElapsed).append("\n");
        // Find top 10 most repeated words
        builder.append("Top 10 most repeated words: \n");
        wordCounts.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().get() - entry1.getValue().get())
                .limit(10)
                .forEach(entry -> builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n"));

        String resposne = builder.toString();
        return resposne;
    }


    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(1);
        }
        return content.toString();
    }
}

class WordCounterTask implements Runnable {
    private final String text;
    private final Map<String, AtomicInteger> wordCounts;

    WordCounterTask(String text, Map<String, AtomicInteger> wordCounts) {
        this.text = text;
        this.wordCounts = wordCounts;
    }

    @Override
    public void run() {
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (word.length() >= 6){
                synchronized (wordCounts) {
                    wordCounts.computeIfAbsent(word, k -> new AtomicInteger()).incrementAndGet();
                }
            }
        }
    }
}