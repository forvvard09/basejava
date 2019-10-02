package main.java.app.storage;

import main.java.app.model.*;

import java.time.YearMonth;
import java.util.Arrays;

public class ResumeTestData {


    public static void main(String[] args) {

        Resume templateResume = new Resume("Egorov Nikalay");

        templateResume.getContacts().put("Phone", "+7(921) 855-0482");
        templateResume.getContacts().put("Skype", "userSkype");
        templateResume.getContacts().put("Email", "contact1@mail.ru");


        AbstractSection personal = new SectionText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        templateResume.getSections().put(SectionType.valueOf("PERSONAL"), personal);

        AbstractSection achievement = new SectionList(Arrays.asList("С 2013 года: разработка проектов 'Разработка Web приложения','Java Enterprise', 'Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)'. Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));
        templateResume.getSections().put(SectionType.valueOf("ACHIEVEMENT"), achievement);

        AbstractSection qualifications = new SectionList(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Родной русский, английский \"upper intermediate",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js"));
        templateResume.getSections().put(SectionType.valueOf("QUALIFICATIONS"), qualifications);


        organizationPeriod educationCoursera = new organizationPeriod("Coursera",
                YearMonth.of(2013, 3),
                YearMonth.of(2013, 5),
                "\"Functional Programming Principles in Scala> by Martin Odersky\""
                );

        organizationPeriod educationLuxoft = new organizationPeriod("Luxoft",
                YearMonth.of(2011, 3),
                YearMonth.of(2011, 4),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""
        );

        AbstractSection education = new SectionPeriod(Arrays.asList(educationCoursera, educationLuxoft));
        templateResume.getSections().put(SectionType.valueOf("EDUCATION"), education);


        organizationPeriod experienceJavaOnline = new organizationPeriod("Java Online Projects",
                YearMonth.of(2013, 10),
                "Автор проекта",
                new SectionText("Создание, организация и проведение Java онлайн проектов и стажировок.")
        );

        organizationPeriod experienceWrike = new organizationPeriod("Wrike",
                YearMonth.of(2014, 10),
                YearMonth.of(2016, 1),
                "Старший разработчик (backend)",
                new SectionText("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.")
        );

        AbstractSection experience = new SectionPeriod(Arrays.asList(experienceJavaOnline, experienceWrike));
        templateResume.getSections().put(SectionType.valueOf("EXPERIENCE"), experience);


        System.out.println("Resume info:");
        System.out.println(templateResume);

        System.out.println("<Contacts>:");
        templateResume.getContacts().forEach((key, value) -> System.out.println(key + " " + value));


        System.out.println("<Sections>:");
        templateResume.getSections().forEach((key, value) -> System.out.println(key.getTitle() + ": " + value));
    }
}
