package main.java.app.model;

public enum TypeSection {
    PERSONAL ("Личные качества"),
    OBJECTIVE ("Позиция"),
    ACHIEVEMENT ("Достижения"),
    QUALIFICATIONS ("Квалификация"),
    EXPERIENCE ("Опыт работы"),
    EDUCATION ("Образование");

    private String title;

    TypeSection(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
