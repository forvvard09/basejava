package main.java.app.model;

import java.util.Objects;

public class SectionText extends AbstractSection {

    private String description;

    public SectionText(String description) {
        Objects.requireNonNull(description, "description must not be null");
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
