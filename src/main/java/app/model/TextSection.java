package main.java.app.model;

import java.util.Objects;

public class TextSection extends AbstractSection {

    private String description;

    public TextSection(String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextSection)) return false;

        TextSection that = (TextSection) o;

        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }

    @Override
    public String toString() {
        return description;
    }
}
