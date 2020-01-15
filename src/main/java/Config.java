package main.java;

import java.io.*;
import java.util.Properties;

public class Config {
    private final static File PROPS = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private File storageDir;
    Properties props = new Properties();

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file." + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
