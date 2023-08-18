package zw.org.nmr.limsehr.service.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
}
