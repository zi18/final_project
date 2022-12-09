package com.ironhackproject.bank;

import java.time.LocalDate;
import java.time.Period;

public class Utils {
    public static int calculateYears(LocalDate startDate, LocalDate currentDate) {
        if ((startDate != null) && (currentDate != null)) {
            return Period.between(startDate, currentDate).getYears();
        } else {
            return 0;
        }
    }

    public static int calculateMonths(LocalDate startDate, LocalDate currentDate) {
        if ((startDate != null) && (currentDate != null)) {
            return Period.between(startDate, currentDate).getMonths();
        } else {
            return 0;
        }
    }
}
