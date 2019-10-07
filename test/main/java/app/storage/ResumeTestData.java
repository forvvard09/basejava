package main.java.app.storage;

import main.java.app.model.*;

import java.time.YearMonth;
import java.util.Arrays;

public class ResumeTestData {


    public static void main(String[] args) {
        Resume templateResume = new Resume("Egorov Nikalay");

        templateResume.setContact(TypeContact.valueOf("PHONE"), "+7(921) 855-0482");
        templateResume.setContact(TypeContact.valueOf("SKYPE"), "userSkype");
        templateResume.setContact(TypeContact.valueOf("EMEIL"), "contact1@mail.ru");

        AbstractSection personal = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        templateResume.setSection(TypeSection.valueOf("PERSONAL"), personal);

        AbstractSection achievement = new ListSection(Arrays.asList("С 2013 года: разработка проектов 'Разработка Web приложения','Java Enterprise', 'Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)'. Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));
        templateResume.setSection(TypeSection.valueOf("ACHIEVEMENT"), achievement);

        AbstractSection qualifications = new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Родной русский, английский \"upper intermediate",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js"));
        templateResume.setSection(TypeSection.valueOf("QUALIFICATIONS"), qualifications);

        OrganizationPeriod educationCoursera = new OrganizationPeriod("Coursera", "",
                YearMonth.of(2013, 3),
                YearMonth.of(2013, 5),
                "\"Functional Programming Principles in Scala> by Martin Odersky\""
                );
        OrganizationPeriod educationLuxoft = new OrganizationPeriod("Luxoft", "",
                YearMonth.of(2011, 3),
                YearMonth.of(2011, 4),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
        );

        AbstractSection education = new PeriodSection(Arrays.asList(educationCoursera, educationLuxoft));
        templateResume.setSection(TypeSection.valueOf("EDUCATION"), education);

        OrganizationPeriod experienceJavaOnline = new OrganizationPeriod("Java Online Projects", "",
                YearMonth.of(2013, 10),
                "Автор проекта",
                new TextSection("Создание, организация и проведение Java онлайн проектов и стажировок.")
        );
        OrganizationPeriod experienceWrike = new OrganizationPeriod("Wrike", "",
                YearMonth.of(2014, 10),
                YearMonth.of(2016, 1),
                "Старший разработчик (backend)",
                new TextSection("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
        );

        AbstractSection experience = new PeriodSection(Arrays.asList(experienceJavaOnline, experienceWrike));
        templateResume.setSection(TypeSection.valueOf("EXPERIENCE"), experience);

        System.out.println("Resume info:");
        System.out.println(templateResume);

        System.out.println("<Contacts>:");
        templateResume.getContacts().forEach((key, value) -> System.out.println(key + " " + value));

        System.out.println("<Sections>:");
        templateResume.getSections().forEach((key, value) -> System.out.println(key.getTitle() + ": " + value));
    }
}