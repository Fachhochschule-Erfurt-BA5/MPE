package com.pme.mpe.model.Format;

public class Month {

    /* /////////////////////Attributes///////////////////////// */

    protected int year;

    protected String daysInMonth;   //Why String Datatype?

    protected Day startingDay;
    protected Day endingDay;

    protected MonthName monthName;

    /* /////////////////////Constructors/////////////////////////// */

    public Month(int year, String daysInMonth, Day startingDay, Day endingDay, MonthName monthName){
        this.year        = year;
        this.daysInMonth = daysInMonth;
        this.startingDay = startingDay;
        this.endingDay   = endingDay;
        this.monthName   = monthName;
    }


    /* /////////////////////Getter/Setter///////////////////////// */

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(String daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public Day getStartingDay() {
        return startingDay;
    }

    public void setStartingDay(Day startingDay) {
        this.startingDay = startingDay;
    }

    public Day getEndingDay() {
        return endingDay;
    }

    public void setEndingDay(Day endingDay) {
        this.endingDay = endingDay;
    }

    public MonthName getMonthName() {
        return monthName;
    }

    public void setMonthName(MonthName monthName) {
        this.monthName = monthName;
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
