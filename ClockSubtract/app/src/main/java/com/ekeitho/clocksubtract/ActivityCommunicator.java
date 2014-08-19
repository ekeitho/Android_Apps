package com.ekeitho.clocksubtract;

import java.util.Date;

public interface ActivityCommunicator {

    public void passDates(Date date1, Date date2, Date date3);
    public void passDateStrings(String someValue, Date date);
    public void passDoubleToActivity(double hours_worked);
    public void calculate();
}

