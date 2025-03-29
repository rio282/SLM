package nl.rio282.slm.io;

import nl.rio282.slm.Tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class TokenizerReader extends IO {

    private final Tokenizer tokenizer;
    private final File file;

    /**
     * to lowercase, remove punctuation
     */
    private final String CLEANING_REGEX = "[^a-z\\s]";

    public TokenizerReader(Tokenizer tokenizer, File file) {
        this.tokenizer = tokenizer;
        this.file = file;
    }

    public void read() throws IOException, URISyntaxException {
        try (BufferedReader br = Files.newBufferedReader(getPath(file))) {
            while (br.readLine() != null) processLine(br.readLine());
        }
    }

    private void processLine(String line) {
        String[] tokens = line.replaceAll(CLEANING_REGEX, "").split("\\s+");
        for (String token : tokens) tokenizer.addToken(token);
    }

}
