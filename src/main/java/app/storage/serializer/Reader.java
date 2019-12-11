package main.java.app.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface Reader {

    void apply() throws IOException;
}
