package main.java.app.model;

public enum ContactType {
    PHONE("Телефон: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='phone:'>" + value + "</a>";
        }
    },
    SKYPE("Skype: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='skype:'>" + value + "</a>";
        }
    },
    EMAIL("EMAIL: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='linkedIn:'>" + value + "</a>";
        }
    },
    GITHUB("Профиль GitHub: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='github:'>" + value + "</a>";
        }
    },
    STACKOVERFLOW("Профиль StackOveflow: ") {
        @Override
        public String toHtml0(String value) {
            return "<a href='stackOverflow:'>" + value + "</a>";
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ":" + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }
}
