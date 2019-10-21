package com.vivant.annecharlotte.avectoi;

import com.vivant.annecharlotte.avectoi.Utils.DateFormat;

import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Test date formatting
 */
public class DateFormatTest {
    DateFormat test = new DateFormat();

    @Test
    public void  dateFormat() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        cal.set(Calendar.MONTH, 10); // Months are denombrated from 0 to 11, not from 1 to 12
        cal.set(Calendar.DAY_OF_MONTH, 12);
        Date date = cal.getTime();

        assertEquals("12/11/2018", test.getRegisteredDate(date));
    }
}
