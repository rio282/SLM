package nl.rio282.slm.io;

import nl.rio282.slm.Tokenizer;

import java.io.File;

public class TokenizerWriter extends IO {

    private final Tokenizer tokenizer;
    private final File file;

    public TokenizerWriter(Tokenizer tokenizer, File file) {
        this.tokenizer = tokenizer;
        this.file = file;
    }

    public void write() {
        // TODO
    }

}
