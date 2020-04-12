package main.java.app.model;

public enum ContactType {
    PHONE("Телефон: "),
    SKYPE("Skype: "),
    EMAIL("EMAIL: "),
    LINKEDIN("Профиль LinkedIn: "),
    GITHUB("Профиль GitHub: "),
    STACKOVERFLOW("Профиль StackOveflow: ");

    private String title;

    ContactType(String title) {
        this.title = title;
    }
}
