package com.javamentor.qa.platform.models.entity.question;

public enum DateFilter {
    ALL,
    YEAR(365),
    MONTH(31),
    WEEK(7);


    DateFilter() {
    }
    public int getDay() {
        return i;
    }

    private  int i;

    DateFilter(int i) {
        this.i = i;
    }

}
