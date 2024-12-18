package org.eligibilityms.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getPreviousMonthDate() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -1);  // Move to the previous month

        // Set to the last day of the previous month
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }
}