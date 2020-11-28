package com.pme.mpe.model.Format;

public class Week {

    /* /////////////////////Attributes///////////////////////// */

    public int year;
    public int weekNumber;
    public int startDayNumberInMonth;

    public MonthName monthName;

    /* /////////////////////Constructors/////////////////////////// */

    public Week(int year, int weekNumber, int startDayNumberInMonth, MonthName monthName){
        this.year                  = year;
        this.weekNumber            = weekNumber;
        this.startDayNumberInMonth = startDayNumberInMonth;
        this.monthName             = monthName;
    }


    /* /////////////////////Getter/Setter///////////////////////// */

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getStartDayNumberInMonth() {
        return startDayNumberInMonth;
    }

    public void setStartDayNumberInMonth(int startDayNumberInMonth) {
        this.startDayNumberInMonth = startDayNumberInMonth;
    }

    public MonthName getMonthName() {
        return monthName;
    }

    public void setMonthName(MonthName monthname) {
        this.monthName = monthname;
    }

    /* /////////////////////Enums///////////////////////// */

    public enum MonthName{
        JANUARY,
        FEBRUARY,
        MARCH,
        APRIL,
        MAY,
        JUNE,
        JULY,
        AUGUST,
        SEPTEMBER,
        OCTOBER,
        NOVEMBER,
        DECEMBER
    }
}
