package my.hikandgo.xmlbuilderapp.constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.util.Calendar.getInstance;

public enum DateFormats {
    MAIN_DATE_FORMAT ("dd/MM/yyyy"),
    FILENAME_FORMAT ("yyyyMMdd"),
    DIRECTORY_NAME_FORMAT ("dd_MM_yyyy");

    private final String format;

    DateFormats(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public static String currentDateGenerator(DateFormats dateFormat, int differenceDay) {
        Calendar cal = getInstance();
        cal.add(Calendar.DATE, differenceDay);
        SimpleDateFormat dateForm = new SimpleDateFormat(dateFormat.getFormat());
        return dateForm.format(cal.getTime());
    }}
