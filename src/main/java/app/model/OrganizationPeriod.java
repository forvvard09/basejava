package main.java.app.model;

import java.time.YearMonth;
import java.util.Objects;

public class OrganizationPeriod {

    private final String nameOrganization;
    private final YearMonth startData;
    private final YearMonth finishData;
    private final String title;
    private final TextSection description;

    public OrganizationPeriod(String nameOrganization, YearMonth startData, YearMonth finishData, String title, TextSection description) {
        Objects.requireNonNull(nameOrganization, "nameOrganization must not be null");
        Objects.requireNonNull(startData, "startData must not be null");
        Objects.requireNonNull(title, "title must not be null");

        this.nameOrganization = nameOrganization;
        this.startData = startData;
        this.finishData = finishData;
        this.title = title;
        this.description = description;
    }

    public OrganizationPeriod(String nameOrganization, YearMonth startData, YearMonth finishData, String title) {
        this(nameOrganization, startData, finishData, title, null);
    }

    public OrganizationPeriod(String nameOrganization, YearMonth startData, String title, TextSection description) {
        this(nameOrganization, startData, null, title, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationPeriod)) return false;

        OrganizationPeriod that = (OrganizationPeriod) o;

        if (nameOrganization != null ? !nameOrganization.equals(that.nameOrganization) : that.nameOrganization != null)
            return false;
        if (startData != null ? !startData.equals(that.startData) : that.startData != null) return false;
        if (finishData != null ? !finishData.equals(that.finishData) : that.finishData != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = nameOrganization != null ? nameOrganization.hashCode() : 0;
        result = 31 * result + (startData != null ? startData.hashCode() : 0);
        result = 31 * result + (finishData != null ? finishData.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
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