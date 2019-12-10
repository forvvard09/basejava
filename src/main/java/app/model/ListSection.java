package main.java.app.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ListSection extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> items;

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListSection)) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(getItems(), that.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems());
    }

    @Override
    public String toString() {
        return System.lineSeparator().concat("* ") + String.join(System.lineSeparator().concat("* "), items);
    }
}
