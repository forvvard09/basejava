package main.java.app.model;

public enum TypeContact {
    PHONE ("Мобильный телефон: "),
    SKYPE ("Skype: "),
    EMEIL ("EMEIL: "),
    LINKEDIN ("Профиль LinkedIn: "),
    GITHUB ("Профиль GitHub: "),
    STACKOVERFLOW ("Профиль StackOveflow: ");

    private String title;

    TypeContact(String title) {
        this.title = title;
    }
}
