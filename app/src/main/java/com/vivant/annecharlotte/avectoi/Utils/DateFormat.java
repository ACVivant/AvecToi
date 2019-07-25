package com.vivant.annecharlotte.avectoi.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Anne-Charlotte Vivant on 25/07/2019.
 */
public class DateFormat {

    public String getRegisteredDate(Date myDate) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
        return  f.format(myDate);
    }
}
