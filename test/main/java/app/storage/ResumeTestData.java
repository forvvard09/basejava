package main.java.app.storage;

import main.java.app.model.*;

import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {

    public Resume fillResume(String uuid, String fullName) {
        Resume currentResume = new Resume(uuid, fullName);
        setSectionPersonal(currentResume);
        setSectionContacts(currentResume);
        setSectionPersonal(currentResume);
        setSectionQualifications(currentResume);
        setSectionAchievement(currentResume);
        setSectionEducation(currentResume);
        setSectionExperience(currentResume);
        return currentResume;
    }

    private void setContactPhone(Resume currentResume) {
        currentResume.setContact(ContactType.valueOf("PHONE"), "+7(921) 855-0482");
    }

    private void setContactSkype(Resume currentResume) {
        currentResume.setContact(ContactType.valueOf("SKYPE"), "userSkype");
    }

    private void setContactEmeil(Resume currentResume) {
        currentResume.setContact(ContactType.valueOf("EMEIL"), "contact1@mail.ru");
    }


    private void setSectionContacts(Resume currentResume) {
        currentResume.setContact(ContactType.valueOf("PHONE"), "+7(921) 855-0482");
        currentResume.setContact(ContactType.valueOf("SKYPE"), "userSkype");
        currentResume.setContact(ContactType.valueOf("EMEIL"), "contact1@mail.ru");
    }

    private void setSectionPersonal(Resume currentResume) {
        AbstractSection personal = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        currentResume.setSection(SectionType.valueOf("PERSONAL"), personal);
    }

    private void setSectionAchievement(Resume currentResume) {
        AbstractSection achievement = new ListSection(Arrays.asList("С 2013 года: разработка проектов 'Разработка Web приложения','Java Enterprise', 'Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)'. Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));
        currentResume.setSection(SectionType.valueOf("ACHIEVEMENT"), achievement);
    }

    private void setSectionQualifications(Resume currentResume) {
        AbstractSection qualifications = new ListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Родной русский, английский \"upper intermediate",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js"));
        currentResume.setSection(SectionType.valueOf("QUALIFICATIONS"), qualifications);
    }

    private void setSectionEducation(Resume currentResume) {
        PositionHeld firstPostion = new PositionHeld(YearMonth.of(2013, 3),
                YearMonth.of(2013, 5),
                "\"Functional Programming Principles in Scala> by Martin Odersky\"");

        PositionHeld secondPostion = new PositionHeld(YearMonth.of(2014, 1),
                YearMonth.of(2014, 5),
                "\"Functional Programming Principles in Go> by Bobak\"");
        List<PositionHeld> listPositionHeldCoursera = Arrays.asList(firstPostion, secondPostion);

        OrganizationPeriod educationCoursera = new OrganizationPeriod("Coursera", "coursera.ru", listPositionHeldCoursera);

        Link orgLuxoft = new Link("Luxoft", "");
        List<PositionHeld> listPositionHeldLuxoft = Arrays.asList(new PositionHeld(YearMonth.of(2011, 3), YearMonth.of(2011, 4),
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""));

        OrganizationPeriod educationLuxoft = new OrganizationPeriod(orgLuxoft, listPositionHeldLuxoft);
        AbstractSection education = new PeriodSection(Arrays.asList(educationCoursera, educationLuxoft));
        currentResume.setSection(SectionType.valueOf("EDUCATION"), education);
    }

    private void setSectionExperience(Resume currentResume) {
        OrganizationPeriod experienceJavaOnline = new OrganizationPeriod("Java Online Projects", "",
                Arrays.asList(new PositionHeld(YearMonth.of(2013, 10),
                        "Автор проекта",
                        "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        OrganizationPeriod experienceWrike = new OrganizationPeriod("Wrike", "",
                Arrays.asList(
                        new PositionHeld(YearMonth.of(2014, 10),
                                YearMonth.of(2016, 1),
                                "Старший разработчик (backend)",
                                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                        new PositionHeld(YearMonth.of(2016, 2),
                                YearMonth.of(2018, 11),
                                "Главный разработчик (fullstack)",
                                "Проектирование и разработка микросерверной инфраструктуры взаимодействия сервисов (JavaScript, Jquerry, Angular, Docker, Kubernetes)")
                )
        );
        AbstractSection experience = new PeriodSection(Arrays.asList(experienceJavaOnline, experienceWrike));
        currentResume.setSection(SectionType.valueOf("EXPERIENCE"), experience);
    }
}