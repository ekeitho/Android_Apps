package com.ekeitho.clocksubtract;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.neopixl.pixlui.components.textview.TextView;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends FragmentActivity {

    private int flag = 0;
    private Button clock1, clock2, clock3;
    private TextView viewz;
    private Date date1, date2, date3, differenceDate;
    private Animation animation;
    private SimpleDateFormat formatter;


    private void calculate() {
        //find minute difference
        long diff_min = 60 + date2.getMinutes() - date1.getMinutes();
        //find hour difference
        long diff_hour = ((diff_min/60)-1) +
                date2.getHours() - date1.getHours();
        //this occurs when overlaps from a 12 hour period
        if( diff_min > 59) {
            diff_min -= 60;
        }

        //this output will tell you how much they have worked
        //now need to figure out the difference form the 8 hour
        Log.v("Diff", "Hours is: " + diff_hour);
        Log.v("Diff", "Minutes left is: " + diff_min);

        // 60 000 = one minutes
        // 3 600 000 = one hour
    }


    private void setListenersOnButtons() {
        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder builder = new TimePickerBuilder()
                        .setFragmentManager(getSupportFragmentManager())
                        .addTimePickerDialogHandler(new TimePickerDialogFragment.TimePickerDialogHandler() {
                            @Override
                            public void onDialogTimeSet(int i, int hour, int minutes) {
                                date1 = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hour, minutes, 0);
                                flag = 1;

                                viewz.setText("First time set is\n " + formatter.format(date1));
                                viewz.startAnimation(animation);
                            }
                        })
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                builder.show();
            }
        });

        clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag < 1) {
                    Toast.makeText(getApplicationContext(),
                            "Must choose a time before this button.", Toast.LENGTH_SHORT).show();
                } else {

                    TimePickerBuilder builder = new TimePickerBuilder()
                            .setFragmentManager(getSupportFragmentManager())
                            .addTimePickerDialogHandler(new TimePickerDialogFragment.TimePickerDialogHandler() {
                                @Override
                                public void onDialogTimeSet(int i, int hours, int minutes) {
                                    date2 = new Date(Calendar.YEAR, Calendar.MONTH,
                                            Calendar.DAY_OF_MONTH, hours, minutes,0);

                                    flag++;
                                    viewz.setText("Second time set is\n " + formatter.format(date2));
                                    viewz.startAnimation(animation);
                                }
                            })
                            .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                    builder.show();
                }
            }
        });

        clock3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (flag < 2) {
                    Toast.makeText(getApplicationContext(), "Must choose a time before this button.", Toast.LENGTH_SHORT).show();
                } else {

                    TimePickerBuilder builder = new TimePickerBuilder()
                            .setFragmentManager(getSupportFragmentManager())
                            .addTimePickerDialogHandler(new TimePickerDialogFragment.TimePickerDialogHandler() {
                                @Override
                                public void onDialogTimeSet(int i, int hours, int minutes) {
                                    date3 = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hours, minutes);
                                    viewz.setText("Third time set is\n " + formatter.format(date3));
                                    viewz.startAnimation(animation);

                                    Fragment frag = new Fragment_Pick();
                                    FragmentManager fm = getSupportFragmentManager();
                                    FragmentTransaction transaction = fm.beginTransaction();
                                    transaction.replace(R.id.frag, frag);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            })
                            .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                    builder.show();
                }
            }


        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        //this already contains the fragment layout
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_main);

        /* view finders */
            clock1 = (Button) findViewById(R.id.clock1);
            clock2 = (Button) findViewById(R.id.clock2);
            clock3 = (Button) findViewById(R.id.clock3);
            viewz = (TextView) findViewById(R.id.welcome_text);
            animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        /* set up */
            formatter = new SimpleDateFormat("h:mm a");
            viewz.startAnimation(animation);
            setListenersOnButtons();
        } else {

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

}
