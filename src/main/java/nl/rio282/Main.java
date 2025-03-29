package nl.rio282;

import nl.rio282.slm.Predictor;
import nl.rio282.slm.Tokenizer;

import java.io.File;
import java.io.IOException;
import java.security.DrbgParameters;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final File FILE_THE_SONNETS = new File("the-sonnets--shakespeare.txt");
    private static final File FILE_OUTPUT_TOKENS = new File("tokens.out");

    private static final int TOKEN_LIMIT = 10;

    public static void main(String[] args) throws IOException {

        Tokenizer tokenizer = new Tokenizer(FILE_THE_SONNETS, FILE_OUTPUT_TOKENS);
        boolean wasTokenizationSuccessful = tokenizer.tokenize();
        if (!wasTokenizationSuccessful) {
            System.err.println("Failed to tokenize. Exiting...");
            return;
        }

        System.out.println("Tokenization successful!\n");

        // ask for input
        System.out.print("Write a word: ");
        Scanner scanner = new Scanner(System.in);
        String startingWord = scanner.nextLine();

        // init predictor
        Predictor predictor = new Predictor(tokenizer);
        predictor.performAnalysis();

        // get next tokens until we hit the token limit or a "."
        String next = predictor.predictNextToken(startingWord.toLowerCase(Locale.ROOT));
        for (int tokens = 0; tokens < TOKEN_LIMIT; ++tokens) {
            System.out.printf("%s ", next);
            next = predictor.predictNextToken(next);
            if (next.equals(".")) {
                System.out.println(next);
                break;
            }
        }
    }

}
