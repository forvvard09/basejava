package main.java.app.model;

import java.util.List;
import java.util.Objects;

public class OrganizationPeriod {
    private final Link homePage;
    private final List<PositionHeld> listPositionHeld;

    public OrganizationPeriod(Link homePage, List<PositionHeld> listPositionHeld) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(listPositionHeld, "listPositionHeld must not be null");
        this.homePage = homePage;
        this.listPositionHeld = listPositionHeld;
    }

    public OrganizationPeriod(String name, String url, List<PositionHeld> listPositionHeld) {
        this.homePage = new Link(name, url);
        this.listPositionHeld = listPositionHeld;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrganizationPeriod)) return false;

        OrganizationPeriod that = (OrganizationPeriod) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return listPositionHeld != null ? listPositionHeld.equals(that.listPositionHeld) : that.listPositionHeld == null;

    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (listPositionHeld != null ? listPositionHeld.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return homePage.toString() + listPositionHeld;
    }
}