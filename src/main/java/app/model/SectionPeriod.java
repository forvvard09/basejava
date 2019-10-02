package main.java.app.model;


import java.util.List;
import java.util.Objects;

public class SectionPeriod extends AbstractSection {

    private final List itemsPeriod;

    public SectionPeriod(List itemsPeriod) {
        Objects.requireNonNull(itemsPeriod, "itemsPeriod must not be null");
        this.itemsPeriod = itemsPeriod;
    }

    @Override
    public String toString() {
        return itemsPeriod.toString();

    }
}
