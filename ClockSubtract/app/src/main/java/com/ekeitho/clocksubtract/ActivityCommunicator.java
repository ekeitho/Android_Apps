package com.ekeitho.clocksubtract;

public interface ActivityCommunicator {

    public void switchFragment(int index);
    public void listenerClocks(final String order, final int index);
    public void passIntToActivity(int hours_worked);
    public void calculate();
}

