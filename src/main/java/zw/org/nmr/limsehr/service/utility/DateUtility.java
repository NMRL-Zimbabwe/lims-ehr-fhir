package zw.org.nmr.limsehr.service.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class DateUtility {

    public LocalDate stringToLocalDate(String dob) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dob;

        // convert String to LocalDate
        LocalDate localDateDOB = LocalDate.parse(date, formatter);

        return localDateDOB;
    }

    public LocalDateTime strToLocalDateTime(String dt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
        return LocalDateTime.parse(dt, formatter);
    }

    public LocalDateTime dateToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public LocalDateTime stringDateTimeToLocalDateTime(String stringDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        // Parse the datetime string to an OffsetDateTime object
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(stringDateTime, formatter);

        // Convert the OffsetDateTime to a LocalDateTime object
        return offsetDateTime.toLocalDateTime();
    }
}
