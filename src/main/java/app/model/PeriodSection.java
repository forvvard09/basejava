package main.java.app.model;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class PeriodSection extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<OrganizationPeriod> itemsPeriod;

    public PeriodSection() {
    }

    public PeriodSection(OrganizationPeriod... organizationPeriod) {
        this(Arrays.asList(organizationPeriod));
    }

    public PeriodSection(List<OrganizationPeriod> itemsPeriod) {
        Objects.requireNonNull(itemsPeriod, "itemsPeriod must not be null");
        this.itemsPeriod = itemsPeriod;
    }

    public List<OrganizationPeriod> getItemsPeriod() {
        return this.itemsPeriod;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodSection)) return false;
        PeriodSection that = (PeriodSection) o;
        return Objects.equals(getItemsPeriod(), that.getItemsPeriod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemsPeriod());
    }

    @Override
    public String toString() {
        return itemsPeriod.toString();
    }
}
