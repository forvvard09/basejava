package main.java.app.storage;

import main.java.app.model.*;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ResumeTestData {

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String TEST_UID = UUID.randomUUID().toString();

    public static final Resume RESUME_TEST;
    public static final Resume RESUME_ONE;
    public static final Resume RESUME_TWO;
    public static final Resume RESUME_THREE;

    static {
        //resume 1
        RESUME_ONE = new Resume(UUID_1, "Name1");
        //resume 2
        RESUME_TWO = new Resume(UUID_2, "Name2");
        //resume 3
        RESUME_THREE = new Resume(UUID_3, "Name3");
        //resume 4
        RESUME_TEST = new Resume(TEST_UID, "NameTest");

        AbstractSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");


        AbstractSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");


        AbstractSection achievement = new ListSection(Arrays.asList("С 2013 года: разработка проектов 'Разработка Web приложения','Java Enterprise', 'Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)'. Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));


        AbstractSection qualifications = new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Родной русский, английский \"upper intermediate",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js"));


        Organization.Position firstPosition = new Organization.Position(YearMonth.of(2013, 3),
                YearMonth.of(2013, 5),
                "\"Functional Programming Principles in Scala> by Martin Odersky\"");

        Organization.Position secondPosition = new Organization.Position(YearMonth.of(2014, 1),
                YearMonth.of(2014, 5),
                "\"Functional Programming Principles in Go> by Bobak\"");
        List<Organization.Position> listPositionCoursera = Arrays.asList(firstPosition, secondPosition);

        Organization educationCoursera = new Organization(new Link("Coursera", "coursera.ru"), listPositionCoursera);

        Link orgLuxoft = new Link("Luxoft", "");
        List<Organization.Position> listPositionLuxoft = Arrays.asList(new Organization.Position(YearMonth.of(2011, 3), YearMonth.of(2011, 4),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));

        Organization educationLuxoft = new Organization(orgLuxoft, listPositionLuxoft);
        AbstractSection education = new OrganizationSection(Arrays.asList(educationCoursera, educationLuxoft));


        Organization experienceJavaOnline = new Organization("Java Online Projects", "",
                new Organization.Position(YearMonth.of(2013, 10),
                        "Автор проекта",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."));

        Organization experienceWrike = new Organization("Wrike", "",
                new Organization.Position(YearMonth.of(2014, 10),
                        YearMonth.of(2016, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                new Organization.Position(YearMonth.of(2016, 2),
                        YearMonth.of(2018, 11),
                        "Главный разработчик (fullstack)",
                        "Проектирование и разработка микросерверной инфраструктуры взаимодействия сервисов (JavaScript, Jquerry, Angular, Docker, Kubernetes)")
        );

        AbstractSection experience = new OrganizationSection(Arrays.asList(experienceJavaOnline, experienceWrike));

        RESUME_ONE.setContact(ContactType.valueOf("PHONE"), "+7(921) 855-0482");
        RESUME_ONE.setContact(ContactType.valueOf("SKYPE"), "userSkype");
        RESUME_ONE.setContact(ContactType.valueOf("EMAIL"), "contact1@mail.ru");
        RESUME_ONE.setSection(SectionType.valueOf("PERSONAL"), personal);
        RESUME_ONE.setSection(SectionType.valueOf("OBJECTIVE"), objective);
        RESUME_ONE.setSection(SectionType.valueOf("ACHIEVEMENT"), achievement);
        RESUME_ONE.setSection(SectionType.valueOf("QUALIFICATIONS"), qualifications);

        RESUME_ONE.setSection(SectionType.valueOf("EXPERIENCE"), experience);

        RESUME_TWO.setContact(ContactType.valueOf("PHONE"), "+7(921) 855-0482");
        RESUME_TWO.setContact(ContactType.valueOf("EMAIL"), "contact1@mail.ru");
        RESUME_TWO.setSection(SectionType.valueOf("OBJECTIVE"), objective);
        RESUME_TWO.setSection(SectionType.valueOf("PERSONAL"), personal);
        RESUME_TWO.setSection(SectionType.valueOf("ACHIEVEMENT"), achievement);
        RESUME_TWO.setSection(SectionType.valueOf("QUALIFICATIONS"), qualifications);
        RESUME_TWO.setSection(SectionType.valueOf("EXPERIENCE"), experience);

        RESUME_THREE.setSection(SectionType.valueOf("PERSONAL"), personal);
        RESUME_THREE.setSection(SectionType.valueOf("OBJECTIVE"), objective);
        RESUME_THREE.setSection(SectionType.valueOf("ACHIEVEMENT"), achievement);
        RESUME_THREE.setSection(SectionType.valueOf("EDUCATION"), education);
        RESUME_THREE.setSection(SectionType.valueOf("EXPERIENCE"), experience);
    }
}