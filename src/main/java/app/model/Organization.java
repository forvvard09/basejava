package main.java.app.model;

import main.java.app.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static main.java.app.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<Position> listPosition;

    public Organization() {
    }

    public Organization(Link homePage, List<Position> listPosition) {
        Objects.requireNonNull(homePage, "homePage must not be null");
        Objects.requireNonNull(listPosition, "listPosition must not be null");
        this.homePage = homePage;
        this.listPosition = listPosition;
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getListPosition() {
        return listPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (homePage != null ? !homePage.equals(that.homePage) : that.homePage != null) return false;
        return listPosition != null ? listPosition.equals(that.listPosition) : that.listPosition == null;

    }

    @Override
    public int hashCode() {
        int result = homePage != null ? homePage.hashCode() : 0;
        result = 31 * result + (listPosition != null ? listPosition.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return homePage.toString() + listPosition;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth startData;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth finishData;
        private String title;
        private String description;

        public Position() {
        }

        public Position(YearMonth startData, YearMonth finishData, String title, String description) {
            Objects.requireNonNull(startData, "startData must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startData = startData;
            this.title = title;
            this.finishData = finishData;
            this.description = description == null ? "" : description;
        }

        public Position(YearMonth startData, String title) {
            this(startData, NOW, title, null);
        }

        public Position(YearMonth startData, String title, String description) {
            this(startData, NOW, title, description);
        }

        public Position(YearMonth startData, YearMonth finishData, String title) {
            this(startData, finishData, title, null);
        }

        public YearMonth getStartData() {
            return startData;
        }

        public YearMonth getFinishData() {
            return finishData;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;

            Position that = (Position) o;

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