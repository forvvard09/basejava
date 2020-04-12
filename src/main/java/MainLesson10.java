package main.java;

import main.java.app.model.*;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

public class MainLesson10 {
    public static void main(String[] args) {
        String sectionTypeName = SectionType.ACHIEVEMENT.name();
        System.out.println(sectionTypeName);

        Resume resume = new Resume("testResume");
        resume.setContact(ContactType.SKYPE, "userSkype");
        resume.setContact(ContactType.EMAIL, "userEmail");

        System.out.println(resume);
        System.out.println(resume.getContacts());
        resume.setSection(SectionType.valueOf("PERSONAL"), new TextSection("Цель, Действия, Мысли"));
        System.out.println(resume.getSections());

        Organization.Position firstPostion = new Organization.Position(YearMonth.of(2013, 3),
                YearMonth.of(2013, 5),
                "\"Functional Programming Principles in Scala> by Martin Odersky\"");

        Organization.Position secondPostion = new Organization.Position(YearMonth.of(2014, 1),
                YearMonth.of(2014, 5),
                "\"Functional Programming Principles in Go> by Bobak\"");
        List<Organization.Position> listPositionCoursera = Arrays.asList(firstPostion, secondPostion);

        Organization educationCoursera = new Organization(new Link("Coursera", "coursera.ru"), listPositionCoursera);

        Link orgLuxoft = new Link("Luxoft", "");
        List<Organization.Position> listPositionLuxoft = Arrays.asList(new Organization.Position(YearMonth.of(2011, 3), YearMonth.of(2011, 4),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));

        Organization educationLuxoft = new Organization(orgLuxoft, listPositionLuxoft);
        AbstractSection education = new OrganizationSection(Arrays.asList(educationCoursera, educationLuxoft));

        resume.setSection(SectionType.valueOf("QUALIFICATIONS"), new OrganizationSection());

        System.out.println(resume);
    }
}
