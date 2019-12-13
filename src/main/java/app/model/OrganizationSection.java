package main.java.app.model;


import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class OrganizationSection extends AbstractSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Organization> listOrganization;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organization) {
        this(Arrays.asList(organization));
    }

    public OrganizationSection(List<Organization> listOrganization) {
        Objects.requireNonNull(listOrganization, "listOrganization must not be null");
        this.listOrganization = listOrganization;
    }

    public List<Organization> getListOrganizations() {
        return this.listOrganization;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationSection)) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(getListOrganizations(), that.getListOrganizations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListOrganizations());
    }

    @Override
    public String toString() {
        return listOrganization.toString();
    }
}
