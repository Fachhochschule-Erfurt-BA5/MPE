package com.pme.mpe.model.format;

/**
 * The type Week.
 */
public class Week {

    /* /////////////////////Attributes///////////////////////// */

    private long id;

    private int year;
    private int weekNumber;
    private int startDayNumberInMonth;

    private Month.MonthName monthName;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Week.
     *
     * @param year                  the year
     * @param weekNumber            the week number
     * @param startDayNumberInMonth the start day number in month
     * @param monthName             the month name
     */
    public Week(int year, int weekNumber, int startDayNumberInMonth, Month.MonthName monthName){
        this.year                  = year;
        this.weekNumber            = weekNumber;
        this.startDayNumberInMonth = startDayNumberInMonth;
        this.monthName             = monthName;
    }


    /* /////////////////////Getter/Setter///////////////////////// */

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets week number.
     *
     * @return the week number
     */
    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * Sets week number.
     *
     * @param weekNumber the week number
     */
    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    /**
     * Gets start day number in month.
     *
     * @return the start day number in month
     */
    public int getStartDayNumberInMonth() {
        return startDayNumberInMonth;
    }

    /**
     * Sets start day number in month.
     *
     * @param startDayNumberInMonth the start day number in month
     */
    public void setStartDayNumberInMonth(int startDayNumberInMonth) {
        this.startDayNumberInMonth = startDayNumberInMonth;
    }

    /**
     * Gets month name.
     *
     * @return the month name
     */
    public Month.MonthName getMonthName() {
        return monthName;
    }

    /**
     * Sets month name.
     *
     * @param monthName the Month Name
     */
    public void setMonthName(Month.MonthName monthName) {
        this.monthName = monthName;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

}
