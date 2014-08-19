package com.ekeitho.clocksubtract;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.neopixl.pixlui.components.textview.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends FragmentActivity implements ActivityCommunicator{

    private TextView view;
    private SimpleDateFormat formatter;
    private Animation animation;
    private Double dub;

    @Override
    public void passDoubleToActivity(double hours_worked) {
        dub = hours_worked;
        Log.v("Hours Worked", "This is what you entered " + hours_worked);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /* set up */
        formatter = new SimpleDateFormat("h:mm a");
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
    public void passDatesToActivity(String someValue, Date date) {
        view.setText(someValue + formatter.format(date));
        view.startAnimation(animation);
    }
}
