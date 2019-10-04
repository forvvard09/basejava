package main.java.app.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;

        ListSection that = (ListSection) o;

        return items != null ? items.equals(that.items) : that.items == null;

    }

    @Override
    public int hashCode() {
        return items != null ? items.hashCode() : 0;
    }

    @Override
    public String toString() {
        return System.lineSeparator().concat("* ") + String.join(System.lineSeparator().concat("* "), items);
    }
}
