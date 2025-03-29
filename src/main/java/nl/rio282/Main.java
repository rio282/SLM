package nl.rio282;

import nl.rio282.slm.Predictor;
import nl.rio282.slm.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final File FILE_THE_SONNETS = new File("the-sonnets--shakespeare.txt");
    private static final File FILE_DR_SEUSS = new File("drseuss.txt");
    private static final File FILE_OUTPUT_TOKENS = new File("tokens.out");

    private static final int TOKEN_LIMIT = 50;

    public static void main(String[] args) throws IOException {

        Tokenizer tokenizer = new Tokenizer(FILE_DR_SEUSS, FILE_OUTPUT_TOKENS);
        boolean wasTokenizationSuccessful = tokenizer.tokenize();
        if (!wasTokenizationSuccessful) {
            System.err.println("Failed to tokenize. Exiting...");
            return;
        }

        System.out.println("Tokenization successful!\n");

        // ask for input
        System.out.print("Write a word: ");
        Scanner scanner = new Scanner(System.in);
        String startingWord = scanner.nextLine().trim();
        if (startingWord.contains(" ")) {
            System.err.println("Starting word cannot contain a space.");
            return;
        }

        // init predictor
        Predictor predictor = new Predictor(tokenizer);
        predictor.performAnalysis();

        // add tokens to the list
        LinkedList<String> tokens = new LinkedList<>(Collections.singleton(startingWord.toLowerCase(Locale.ROOT)));
        for (int tokenCount = 0; tokenCount < TOKEN_LIMIT; ++tokenCount) {
            tokens.add(predictor.predictNextToken(tokens.getLast()));
            if (tokens.getLast().equals(".")) break;
        }

        // show output
        try {
            spit(tokens);
        } catch (InterruptedException ignored) {
            System.out.println("\nFailed to print out tokens.");
        }
    }

    private static void spit(List<String> tokens) throws InterruptedException {
        if (tokens.size() == 2) {
            System.err.println("Couldn't find word. Try another.");
            return;
        }

        Random random = new Random();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (token.endsWith(".")) token += "\n";

            // print token + suffix
            System.out.print(token);
            if (i == tokens.size() - 1) {
                System.out.println(".");
            } else if (!token.endsWith(".")) {
                System.out.print(" ");
            }

            Thread.sleep(5 + random.nextInt(70));
        }
    }

}
