package client.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class DateUtils {
    public static final SimpleDateFormat HL7_FORMAT =
            new SimpleDateFormat("yyyyMMddHHmmss");

    private DateUtils() {
    }

    public static String toString(Date date) {
        if (date == null) return "";

        return HL7_FORMAT.format(date);
    }

    public static Date toDate(String text, SimpleDateFormat format) {
        try {
            return HL7_FORMAT.parse(text);
        } catch (Exception ex) {
            return null;
        }
    }

    public static Date nextDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }

    public static String generateDate(int fromYear, int toYear) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int year = DateUtils.randBetween(fromYear, toYear);// Here you can set Range of years you need
        int month = DateUtils.randBetween(0, 11);

        int hour = DateUtils.randBetween(9, 22); //Hours will be displayed in between 9 to 22
        int min = DateUtils.randBetween(0, 59);
        int sec = DateUtils.randBetween(0, 59);

        GregorianCalendar gc = new GregorianCalendar();

        gc.set(gc.YEAR, year);

        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(year, month, dayOfYear, hour, min,sec);
        fmt.setCalendar(gc);
        String dateFormatted = fmt.format(gc.getTime());

        return dateFormatted;
    }

    private static int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
