package nl.rio282.slm.io;

import nl.rio282.slm.Tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Locale;

public class TokenizerReader extends IO {

    private final Tokenizer tokenizer;
    private final File file;

    private final String CLEANING_REGEX = "[^a-zA-Z,\\s.]";

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
        String[] tokens = line.replaceAll(CLEANING_REGEX, "").toLowerCase(Locale.ROOT).split("\\s+");
        for (String token : tokens) if (!token.isEmpty()) tokenizer.addToken(token);
    }

}
