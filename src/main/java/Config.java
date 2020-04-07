package main.java;

import main.java.app.storage.SqlStorage;
import main.java.app.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    //    private final static File PROPS = new File("./config/resumes.properties");
//    private final static File PROPS = new File("C:/Users/poddubnyak/Dropbox/07 Programming/topJavaProjects/basejava/config/resumes.properties");
    private final static File PROPS = new File("D:/Dropbox/07 Programming/topJavaProjects/basejava/config/resumes.properties");

    private static final Config INSTANCE = new Config();

    private final File storageDir;
    private final Properties props = new Properties();
   /* private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;*/

    private final Storage storage;


    private Config() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));

            /*dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");*/
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file." + PROPS.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    /*public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }*/

    public Storage getStorage() {
        return storage;
    }
}
