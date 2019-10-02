package main.java.app.model;

import java.time.YearMonth;
import java.util.Objects;

public class organizationPeriod {

    private final String nameOrganization;
    private final YearMonth startData;
    private final YearMonth finishData;
    private final String title;
    private final SectionText description;

    public organizationPeriod(String nameOrganization, YearMonth startData, YearMonth finishData, String title, SectionText description) {
        Objects.requireNonNull(nameOrganization, "nameOrganization must not be null");
        Objects.requireNonNull(startData, "startData must not be null");
        Objects.requireNonNull(title, "title must not be null");

        this.nameOrganization = nameOrganization;
        this.startData = startData;
        this.finishData = finishData;
        this.title = title;
        this.description = description;
    }

    public organizationPeriod(String nameOrganization, YearMonth startData, YearMonth finishData, String title) {
        this(nameOrganization, startData, finishData, title, null);
    }

    public organizationPeriod(String nameOrganization, YearMonth startData, String title, SectionText description) {
        this(nameOrganization, startData, null, title, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof organizationPeriod)) return false;

        organizationPeriod that = (organizationPeriod) o;

        if (nameOrganization != null ? !nameOrganization.equals(that.nameOrganization) : that.nameOrganization != null)
            return false;
        if (startData != null ? !startData.equals(that.startData) : that.startData != null) return false;
        if (!finishData.equals(that.finishData)) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return description.equals(that.description);

    }

    @Override
    public int hashCode() {
        int result = nameOrganization != null ? nameOrganization.hashCode() : 0;
        result = 31 * result + (startData != null ? startData.hashCode() : 0);
        result = 31 * result + finishData.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String currentlyTime = "н.в.";
        String resultOrganizationPeriod;
        if (finishData == null) {
            resultOrganizationPeriod = String.format("%s%s%s - %s %s %s", nameOrganization, System.lineSeparator(), startData.toString(), currentlyTime, title, description.toString());
        } else if (description == null) {
            resultOrganizationPeriod = String.format("%s%s%s - %s %s", nameOrganization, System.lineSeparator(), startData.toString(), currentlyTime, title);
        }
          else {
            resultOrganizationPeriod = String.format("%s%s%s - %s %s %s", nameOrganization, System.lineSeparator(), startData.toString(), finishData.toString(), title, description.toString());
        }
        return resultOrganizationPeriod + System.lineSeparator();
    }
}
