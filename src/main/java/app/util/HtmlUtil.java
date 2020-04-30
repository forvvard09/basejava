package main.java.app.util;

import main.java.app.model.Organization;

public class HtmlUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Organization.Position position) {
        return DateUtil.format(position.getStartData()) + " - " + DateUtil.format(position.getFinishData());
    }
}
