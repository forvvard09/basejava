package main.java.app.util;

import main.java.app.model.Organization;

public class HtmlUtil {
    public static String formatDates(Organization.Position position) {
        return DateUtil.format(position.getStartData()) + " - " + DateUtil.format(position.getFinishData());
    }
}
