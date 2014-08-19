package com.ekeitho.clocksubtract;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.neopixl.pixlui.components.textview.TextView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends FragmentActivity implements ActivityCommunicator{

    private TextView view;
    private SimpleDateFormat formatter;
    private Animation animation;
    private int hours;
    private Date date1, date2, date3;
    private ArrayList<Date> date_list = new ArrayList<Date>();

    @Override
    public void passIntToActivity(int hours_worked) {
        hours = hours_worked;
        Log.v("Hours Worked", "This is what you entered " + hours_worked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* set up */
        formatter = new SimpleDateFormat("MMM d, h:mm a");
        view = (TextView) findViewById(R.id.welcome_text);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        view.startAnimation(animation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ClockOutput())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void addDates(Date date, int index) {
        date_list.add(index, date);
    }

    @Override
    public void calculate() {
        // numbers based on the Date() times
        // 60 000 = one minutes
        // 3 600 000 = one hour

        Date date1 = date_list.get(0);
        Date date2 = date_list.get(1);
        Date date3 = date_list.get(2);


        int hour_date_time = 3600000;
        int minute_date_time = 60000;

        //find minute difference
        int diff_min = 60 + date2.getMinutes() - date1.getMinutes();
        //find hour difference
        int diff_hour = ((diff_min / 60) - 1) +
                date2.getHours() - date1.getHours();
        //this occurs when overlaps from a 12 hour period
        if (diff_min > 59) {
            diff_min -= 60;
        }

        //subtracts user input of hours needed to work to what they've already currently worked
        int sub = hours * hour_date_time -
                (diff_hour * hour_date_time + diff_min * minute_date_time);

        //finds from the subtraction of how many hours needed to work
        int hours_left = sub/hour_date_time;
        int minutes_left = (sub - (hours_left * hour_date_time))/minute_date_time;
        Log.v("Diff", "Hours added " + hours_left + " minutes added " + minutes_left);


        Date final_date = new Date(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                date3.getHours() + hours_left,
                date3.getMinutes() + minutes_left);


        view.setText("You need to clock out at\n " + formatter.format(final_date));
        view.startAnimation(animation);

        //empty list for next iteration if necessary
        date_list.clear();

    }

    @Override
    public void passDateStrings(String someValue, Date date) {
        view.setText(someValue + formatter.format(date));
        view.startAnimation(animation);
    }
}
