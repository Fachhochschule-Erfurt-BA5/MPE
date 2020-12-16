package com.pme.mpe.model.deprecated.format;

/**
 * The type Day.
 */
public class Day {

    /* /////////////////////Attributes///////////////////////// */

    private long id;

    private DayName dayName;
    private int weekday;

    /* /////////////////////Constructors/////////////////////////// */

    /**
     * Instantiates a new Day.
     *
     * @param dayName the day name
     * @param weekday the weekday
     */
    public Day(DayName dayName, int weekday){
        this.dayName = dayName;
        this.weekday = weekday;
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    /**
     * Gets day name.
     *
     * @return the day name
     */
    public DayName getDayName() {
        return dayName;
    }

    /**
     * Sets day name.
     *
     * @param dayName the day name
     */
    public void setDayName(DayName dayName) {
        this.dayName = dayName;
    }

    /**
     * Gets weekday.
     *
     * @return the weekday
     */
    public int getWeekday() {
        return weekday;
    }

    /**
     * Sets weekday.
     *
     * @param weekday the weekday
     */
    public void setWeekday(int weekday) {
        this.weekday = weekday;
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

    /* /////////////////////Enum///////////////////////// */

    /**
     * The enum Day name.
     */
    public enum DayName{
        /**
         * Monday day name.
         */
        MONDAY,
        /**
         * Tuesday day name.
         */
        TUESDAY,
        /**
         * Wednesday day name.
         */
        WEDNESDAY,
        /**
         * Thursday day name.
         */
        THURSDAY,
        /**
         * Friday day name.
         */
        FRIDAY,
        /**
         * Saturday day name.
         */
        SATURDAY,
        /**
         * Sunday day name.
         */
        SUNDAY
    }

}
