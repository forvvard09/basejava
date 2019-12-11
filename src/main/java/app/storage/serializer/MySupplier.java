package main.java.app.storage.serializer;

import java.io.IOException;

public interface MySupplier<T> {

    T get() throws IOException;
}
