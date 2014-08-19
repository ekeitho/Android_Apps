package com.ekeitho.clocksubtract;

import java.util.Date;

public interface ActivityCommunicator {

    public void passDatesToActivity(String someValue, Date date);
    public void passDoubleToActivity(double hours_worked);
}

