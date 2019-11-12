package main.java.app.model;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PeriodSection extends AbstractSection {

    private final List<OrganizationPeriod> itemsPeriod;

    public PeriodSection(OrganizationPeriod... organizationPeriod) {
        this(Arrays.asList(organizationPeriod));
    }

    public PeriodSection(List<OrganizationPeriod> itemsPeriod) {
        Objects.requireNonNull(itemsPeriod, "itemsPeriod must not be null");
        this.itemsPeriod = itemsPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodSection)) return false;

        PeriodSection that = (PeriodSection) o;

        return itemsPeriod.equals(that.itemsPeriod);
    }

    @Override
    public int hashCode() {
        return itemsPeriod.hashCode();
    }

    @Override
    public String toString() {
        return itemsPeriod.toString();
    }
}
