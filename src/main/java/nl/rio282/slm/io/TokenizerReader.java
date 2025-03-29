package nl.rio282.slm.io;

import nl.rio282.slm.Tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

public class TokenizerReader extends IO {

    private final Tokenizer tokenizer;
    private final File file;

    private final String CLEANING_REGEX = "[^a-zA-Z,\\s.!?']";
    private final String SPLITTER = "(?<=[.])|\\s+";

    public TokenizerReader(Tokenizer tokenizer, File file) {
        this.tokenizer = tokenizer;
        this.file = file;
    }

    public void read() throws IOException, URISyntaxException {
        try (BufferedReader br = Files.newBufferedReader(getPath(file), StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) processLine(line);
        } catch (Exception e) {
            System.err.println("Reading error.");
            throw e;
        }
    }

    private void processLine(String line) {
        String[] tokens = line.replaceAll(CLEANING_REGEX, "").toLowerCase(Locale.ROOT).split(SPLITTER);
        for (String token : tokens) if (!token.isEmpty()) tokenizer.addToken(token);
    }

}
