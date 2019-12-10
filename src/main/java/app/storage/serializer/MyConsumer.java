package main.java.app.storage.serializer;

import java.io.IOException;

@FunctionalInterface
public interface MyConsumer<T> {
    void accept(T t) throws IOException;
}
