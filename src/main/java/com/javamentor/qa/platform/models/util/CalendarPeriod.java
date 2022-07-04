package com.javamentor.qa.platform.models.util;

public enum CalendarPeriod {
    day(1),
    week(7),
    month(30),
    year(365);
    private final Integer days;
    CalendarPeriod(Integer days) {
        this.days = days;
    }

    public Integer getDays() {
        return days;
    }
}
