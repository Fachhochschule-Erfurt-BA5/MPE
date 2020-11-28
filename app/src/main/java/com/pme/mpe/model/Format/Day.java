package com.pme.mpe.model.Format;

public class Day {

    /* /////////////////////Attributes///////////////////////// */

    //protected String dayName;

    protected Dayname dayname;

    protected int weekday;

    /* /////////////////////Constructors/////////////////////////// */

    public Day(Dayname dayname, int weekday){
        this.dayname = dayname;
        this.weekday = weekday;
    }

    /* /////////////////////Getter/Setter///////////////////////// */

    public Dayname getDayname() {
        return dayname;
    }

    public void setDayname(Dayname dayname) {
        this.dayname = dayname;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    /* /////////////////////Enum///////////////////////// */

    public enum Dayname{
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

}
