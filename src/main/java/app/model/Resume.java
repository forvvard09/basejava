package main.java.app.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Class Resume initialisation resume class.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
public class Resume implements Comparable<Resume> {
    private final String uuid;

    private  final String fullName;

    private static String EMPTY_FEILD_FULLNAME = "unknown";


    public Resume() {
        this(UUID.randomUUID().toString(), EMPTY_FEILD_FULLNAME);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume(String uuid) {
        this.uuid = uuid;
        this.fullName = EMPTY_FEILD_FULLNAME;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return this.fullName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(getUuid(), resume.getUuid()) &&
                Objects.equals(getFullName(), resume.getFullName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid());
    }

    @Override
    public String toString() {
        return String.format("Resume: uuid=%s, fullNam=%s", uuid, fullName);
    }


    @Override
    public int compareTo(final Resume o) {
        int compare = fullName.compareTo(o.fullName);
        if (compare == 0) {
            compare = uuid.compareTo(o.getUuid());
        }
        return compare;
    }
}