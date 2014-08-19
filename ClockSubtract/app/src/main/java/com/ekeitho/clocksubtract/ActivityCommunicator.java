package com.ekeitho.clocksubtract;

import java.util.Date;

public interface ActivityCommunicator {

    //use the spot in case the user goes back and changes past clock before calculation
    public void addDates(Date date, int index);
    public void passDateStrings(String someValue, Date date);
    public void passIntToActivity(int hours_worked);
    public void calculate();
}

