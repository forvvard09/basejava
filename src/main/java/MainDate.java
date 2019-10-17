package main.java;

import org.apache.xml.dtm.DTMFilter;

import java.security.cert.LDAPCertStoreParameters;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainDate {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime());
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis() - date.getTime());
        System.out.println(System.currentTimeMillis() - start);

        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        System.out.println(cal.getTime());
        System.out.println("------");

        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ltd = LocalDateTime.of(ld, lt);
        System.out.println(ltd);

        SimpleDateFormat sdf = new SimpleDateFormat("YY/MM/dd");
        System.out.println(sdf.format(date));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY:MM:dd");
        System.out.println(dtf.format(ltd));



    }
}
