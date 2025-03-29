package nl.rio282.slm.io;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public abstract class IO {

    protected Path getPath(File file) throws URISyntaxException {
        return  Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(file.getName())).toURI());
    }

}
