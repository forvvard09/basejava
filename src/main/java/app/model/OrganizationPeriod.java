package main.java.app.model;

import java.time.YearMonth;
import java.util.Objects;

public class OrganizationPeriod {
    private final Link homePage;

    private final YearMonth startData;
    private final YearMonth finishData;
    private final String title;
    private final TextSection description;

    public OrganizationPeriod(String nameOrganization,String linkOrganization, YearMonth startData, YearMonth finishData, String title, TextSection description) {
        Objects.requireNonNull(startData, "startData must not be null");
        Objects.requireNonNull(title, "title must not be null");

        this.homePage = new Link(nameOrganization, linkOrganization);
        this.startData = startData;
        this.finishData = finishData;
        this.title = title;
        this.description = description;
    }

    public OrganizationPeriod(String nameOrganization,String linkOrganization, YearMonth startData, YearMonth finishData, String title) {
        this(nameOrganization, linkOrganization, startData, finishData, title, null);
    }

    public OrganizationPeriod(String nameOrganization,String linkOrganization, YearMonth startData, String title, TextSection description) {
        this(nameOrganization, linkOrganization, startData, null, title, description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationPeriod)) return false;

        OrganizationPeriod that = (OrganizationPeriod) o;

        if (!homePage.equals(that.homePage)) return false;
        if (!startData.equals(that.startData)) return false;
        if (finishData != null ? !finishData.equals(that.finishData) : that.finishData != null) return false;
        if (!title.equals(that.title)) return false;
        return description != null ? description.equals(that.description) : that.description == null;

    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + startData.hashCode();
        result = 31 * result + (finishData != null ? finishData.hashCode() : 0);
        result = 31 * result + title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String currentlyTime = "н.в.";
        String resultOrganizationPeriod;
        if (finishData == null) {
            resultOrganizationPeriod = String.format("%s%s%s - %s %s %s", homePage, System.lineSeparator(), startData.toString(), currentlyTime, title, description.toString());
        } else if (description == null) {
            resultOrganizationPeriod = String.format("%s%s%s - %s %s", homePage, System.lineSeparator(), startData.toString(), currentlyTime, title);
        }
          else {
            resultOrganizationPeriod = String.format("%s%s%s - %s %s %s", homePage, System.lineSeparator(), startData.toString(), finishData.toString(), title, description.toString());
        }
        return resultOrganizationPeriod + System.lineSeparator();
    }
}