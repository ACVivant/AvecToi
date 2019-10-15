package com.vivant.annecharlotte.avectoi.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Format date
 */
public class DateFormat {

    public String getRegisteredDate(Date myDate) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        return  f.format(myDate);
    }
}
