package main.java.app.model;

import java.util.List;
import java.util.Objects;

public class SectionList extends AbstractSection {

    private List<String> items;

    public SectionList(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    @Override
    public String toString() {
        return System.lineSeparator().concat("* ") + String.join(System.lineSeparator().concat("* "), items);
    }
}
