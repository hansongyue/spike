package top.doperj.util.customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.springframework.util.StringUtils.isEmpty;

public class CustomerUtil {
    public static String formatDate(Date date) {
        SimpleDateFormat ft =
                new SimpleDateFormat ("yyyy/MM/dd");
        ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateString = null;
        if (date != null) {
            dateString = ft.format(date);
        }
        return dateString;
    }

    public static Date parseDate(String dateStr) {
        if (isEmpty(dateStr)) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return null;
    }
}
