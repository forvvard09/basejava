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
        resume.setContact(ContactType.EMEIL, "userEmeil");

        System.out.println(resume);
        System.out.println(resume.getContacts());
        resume.setSection(SectionType.valueOf("PERSONAL"), new TextSection("Цель, Действия, Мысли"));
        System.out.println(resume.getSections());

        OrganizationPeriod.PositionHeld firstPostion = new OrganizationPeriod.PositionHeld(YearMonth.of(2013, 3),
                YearMonth.of(2013, 5),
                "\"Functional Programming Principles in Scala> by Martin Odersky\"");

        OrganizationPeriod.PositionHeld secondPostion = new OrganizationPeriod.PositionHeld(YearMonth.of(2014, 1),
                YearMonth.of(2014, 5),
                "\"Functional Programming Principles in Go> by Bobak\"");
        List<OrganizationPeriod.PositionHeld> listPositionHeldCoursera = Arrays.asList(firstPostion, secondPostion);

        OrganizationPeriod educationCoursera = new OrganizationPeriod(new Link("Coursera", "coursera.ru"), listPositionHeldCoursera);

        Link orgLuxoft = new Link("Luxoft", "");
        List<OrganizationPeriod.PositionHeld> listPositionHeldLuxoft = Arrays.asList(new OrganizationPeriod.PositionHeld(YearMonth.of(2011, 3), YearMonth.of(2011, 4),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));

        OrganizationPeriod educationLuxoft = new OrganizationPeriod(orgLuxoft, listPositionHeldLuxoft);
        AbstractSection education = new PeriodSection(Arrays.asList(educationCoursera, educationLuxoft));

        resume.setSection(SectionType.valueOf("QUALIFICATIONS"), new PeriodSection());
    }
}
