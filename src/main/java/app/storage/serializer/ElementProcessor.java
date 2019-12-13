package main.java.app.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface ElementProcessor {

    void process() throws IOException;
}
