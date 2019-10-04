package main.java.app.model;

import com.sun.javafx.collections.UnmodifiableObservableMap;

import java.util.*;

/**
 * Class Resume initialisation resume class.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private final String fullName;

    private Map<String, String> contacts;

    private final Map<TypeSection, AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new HashMap<>();
        sections = new EnumMap<>(TypeSection.class);
    }

    public String getUuid() {
        return uuid;
    }

    public  Map<String, String> getContacts() {
        return Collections.unmodifiableMap(contacts);
    }

    public void setContact(String typeContact, String contact) {
        contacts.put(typeContact, contact);
    }

    public Map<TypeSection, AbstractSection> getSections() {
        return Collections.unmodifiableMap(sections);
    }

    public void setSection(TypeSection typeSection, AbstractSection section) {
        sections.put(typeSection, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!Objects.equals(uuid, resume.uuid)) return false;
        return Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("Resume: uuid=%s;%sfullName=%s", uuid, System.lineSeparator(), fullName);
    }

    @Override
    public int compareTo(final Resume o) {
        int compare = fullName.compareTo(o.fullName);
        return compare == 0 ? uuid.compareTo(o.getUuid()) : compare;
    }
}