package com.pme.mpe.model.deprecated.format;

/**
 * The type Month.
 */
public class Month {

    /* /////////////////////Attributes///////////////////////// */

    private long id;

    private int year;
    private int daysInMonth;
    private int monthNumber;

    private Day startingDay;
    private Day endingDay;

    private MonthName monthName;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Month.
     *
     * @param year        the year
     * @param daysInMonth the days in month
     * @param startingDay the starting day
     * @param endingDay   the ending day
     * @param monthName   the month name
     * @param monthNumber the month number
     */
    public Month(int year, int daysInMonth, Day startingDay, Day endingDay, MonthName monthName, int monthNumber) {
        this.year = year;
        this.daysInMonth = daysInMonth;
        this.startingDay = startingDay;
        this.endingDay = endingDay;
        this.monthName = monthName;
        this.monthNumber = monthNumber;
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
     * Gets days in month.
     *
     * @return the days in month
     */
    public int getDaysInMonth() {
        return daysInMonth;
    }

    /**
     * Sets days in month.
     *
     * @param daysInMonth the days in month
     */
    public void setDaysInMonth(int daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    /**
     * Gets starting day.
     *
     * @return the starting day
     */
    public Day getStartingDay() {
        return startingDay;
    }

    /**
     * Sets starting day.
     *
     * @param startingDay the starting day
     */
    public void setStartingDay(Day startingDay) {
        this.startingDay = startingDay;
    }

    /**
     * Gets ending day.
     *
     * @return the ending day
     */
    public Day getEndingDay() {
        return endingDay;
    }

    /**
     * Sets ending day.
     *
     * @param endingDay the ending day
     */
    public void setEndingDay(Day endingDay) {
        this.endingDay = endingDay;
    }

    /**
     * Gets month name.
     *
     * @return the month name
     */
    public MonthName getMonthName() {
        return monthName;
    }

    /**
     * Sets month name.
     *
     * @param monthName the month name
     */
    public void setMonthName(MonthName monthName) {
        this.monthName = monthName;
    }

    /**
     * Gets month number.
     *
     * @return the month number
     */
    public int getMonthNumber() {
        return monthNumber;
    }

    /**
     * Sets month number.
     *
     * @param monthNumber the month number
     */
    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
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

    /* /////////////////////Enums///////////////////////// */

    /**
     * The enum Month name.
     */
    public enum MonthName{
        /**
         * January month name.
         */
        JANUARY,
        /**
         * February month name.
         */
        FEBRUARY,
        /**
         * March month name.
         */
        MARCH,
        /**
         * April month name.
         */
        APRIL,
        /**
         * May month name.
         */
        MAY,
        /**
         * June month name.
         */
        JUNE,
        /**
         * July month name.
         */
        JULY,
        /**
         * August month name.
         */
        AUGUST,
        /**
         * September month name.
         */
        SEPTEMBER,
        /**
         * October month name.
         */
        OCTOBER,
        /**
         * November month name.
         */
        NOVEMBER,
        /**
         * December month name.
         */
        DECEMBER
    }
}
