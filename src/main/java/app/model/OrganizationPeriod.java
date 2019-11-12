package main.java.app.model;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static main.java.app.util.DateUtil.NOW;

public class OrganizationPeriod {
    private final Link homePage;
    private final List<PositionHeld> listPositionHeld;

    public OrganizationPeriod(Link homePage, List<PositionHeld> listPositionHeld) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(listPositionHeld, "listPositionHeld must not be null");
        this.homePage = homePage;
        this.listPositionHeld = listPositionHeld;
    }

    public OrganizationPeriod(String name, String url, PositionHeld... positionHelds) {
        this(new Link(name, url), Arrays.asList(positionHelds));
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

    public static class PositionHeld {
        private final YearMonth startData;
        private final YearMonth finishData;
        private final String title;
        private final String description;


        public PositionHeld(YearMonth startData, YearMonth finishData, String title, String description) {
            Objects.requireNonNull(startData, "startData must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startData = startData;
            this.title = title;
            this.finishData = finishData;
            this.description = description;
        }

        public PositionHeld(YearMonth startData, String title) {
            this(startData, NOW, title, null);
        }

        public PositionHeld(YearMonth startData, String title, String description) {
            this(startData, NOW, title, description);
        }

        public PositionHeld(YearMonth startData, YearMonth finishData, String title) {
            this(startData, finishData, title, null);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PositionHeld)) return false;

            PositionHeld that = (PositionHeld) o;

            if (startData != null ? !startData.equals(that.startData) : that.startData != null) return false;
            if (finishData != null ? !finishData.equals(that.finishData) : that.finishData != null) return false;
            if (title != null ? !title.equals(that.title) : that.title != null) return false;
            return description != null ? description.equals(that.description) : that.description == null;

        }

        @Override
        public int hashCode() {
            int result = startData != null ? startData.hashCode() : 0;
            result = 31 * result + (finishData != null ? finishData.hashCode() : 0);
            result = 31 * result + (title != null ? title.hashCode() : 0);
            result = 31 * result + (description != null ? description.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            String currentlyTime = "н.в.";
            String resultOrganizationPeriod;
            if (finishData.isAfter(YearMonth.now())) {
                resultOrganizationPeriod = String.format("%s%s - %s %s %s", System.lineSeparator(), startData.toString(), currentlyTime, title, description.toString());
            } else if (description == null) {
                resultOrganizationPeriod = String.format("%s%s - %s %s", System.lineSeparator(), startData.toString(), currentlyTime, title);
            }
              else {
                resultOrganizationPeriod = String.format("%s - %s %s %s", System.lineSeparator(), startData.toString(), finishData.toString(), title, description.toString());
            }
            return resultOrganizationPeriod + System.lineSeparator();
        }

    }
}