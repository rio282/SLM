package nl.rio282.slm;

import nl.rio282.slm.io.TokenizerReader;
import nl.rio282.slm.io.TokenizerWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tokenizer {

    private List<String> tokens;

    private final TokenizerReader reader;
    private final TokenizerWriter writer;

    public Tokenizer(File input, File output) {
        tokens = new ArrayList<>();
        reader = new TokenizerReader(this, input);
        writer = new TokenizerWriter(this, output);
    }

    public boolean tokenize() {
        try {
            reader.read();

            Compressor compressor = new Compressor(tokens);
            tokens = compressor.useBytePairEncoding();

            writer.write();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("\nStacktrace:");
            for (StackTraceElement ste : Thread.currentThread().getStackTrace()) System.err.println(ste + "\n");
            return false;
        }

        return true;
    }

    public void addToken(String token) {
        tokens.add(token);
    }

    public List<String> getTokens() {
        return tokens;
    }
}
