package main.java.app.model;

import java.io.Serializable;
import java.util.Objects;

public class TextSection extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private String description;

    public TextSection() {
    }

    public TextSection(String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextSection)) return false;

        TextSection that = (TextSection) o;

        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public String toString() {
        return description;
    }
}
