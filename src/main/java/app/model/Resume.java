package main.java.app.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Class Resume initialisation resume class.
 *
 * @author Sergei Poddubniak (forvvard09@gmail.com)
 * @version 2.0
 * @since 18.02.2019
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    public static final Resume EMPTY = new Resume();

    static {
        EMPTY.setSection(SectionType.OBJECTIVE, TextSection.EMPTY);
        EMPTY.setSection(SectionType.PERSONAL, TextSection.EMPTY);
        EMPTY.setSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
        EMPTY.setSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
        EMPTY.setSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
        EMPTY.setSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
    }

    private String uuid;
    private String fullName;

    private Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
//        contacts = new EnumMap<>(ContactType.class);
//        sections = new EnumMap<>(SectionType.class);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return Collections.unmodifiableMap(contacts);
    }

    public void setContact(ContactType typeContact, String contact) {
        /*if (contacts == null) {
            contacts = new EnumMap<>(ContactType.class);
        }*/
        contacts.put(typeContact, contact);
    }

    public void removeContact(ContactType typeContact) {
        contacts.remove(typeContact);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return Collections.unmodifiableMap(sections);
    }

    public void setSection(SectionType typeSection, AbstractSection section) {
        /*if (sections == null) {
            sections = new EnumMap<>(SectionType.class);
        }*/
        sections.put(typeSection, section);
    }

    public void removeSection(SectionType typeSection) {
        sections.remove(typeSection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;

        Resume resume = (Resume) o;

        if (uuid != null ? !uuid.equals(resume.uuid) : resume.uuid != null) return false;
        if (fullName != null ? !fullName.equals(resume.fullName) : resume.fullName != null) return false;
        if (sections != null ? !sections.equals(resume.sections) : resume.sections != null) return false;
        return contacts != null ? contacts.equals(resume.contacts) : resume.contacts == null;

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (sections != null ? sections.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
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