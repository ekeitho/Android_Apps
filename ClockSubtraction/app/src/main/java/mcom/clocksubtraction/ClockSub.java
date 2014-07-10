package mcom.clocksubtraction;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseInstallation;
import com.parse.PushService;

public class ClockSub extends Activity implements View.OnClickListener, View.OnFocusChangeListener {

    Button bCalc;
    TimePicker time1, time2, time3;
    TextView format;
    EditText etWorkHours;
    int clock1Hour, clock2Hour, clock1Min, clock2Min, clock3Hour, clock3Min, workHours;
    Boolean badChoice = false;
    Date tempClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clocksub);
        initialize();

        Parse.initialize(this, "Il62bTwt2UPvyLkGGEIBvY9xSYNsPC8EljZbyz0R",
        "2Qp7n7BWLQgGFxf3J0PFPCWLzcAMnIlGDSp6h87A");

        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("Leith", "Push pull.");
        testObject.saveInBackground();

        PushService.setDefaultPushCallback(this, ClockSub.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }


    @Override
    public void onClick(View v) {
        //getting both clock time based on users pref//
        clock1Hour = time1.getCurrentHour();
        clock2Hour = time2.getCurrentHour();
        clock1Min = time1.getCurrentMinute();
        clock2Min = time2.getCurrentMinute();

        //Hour difference using data logic = 3,600,000
        //Minute difference = 60,000
        Date clock1 = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, clock1Hour, clock1Min);
        Date clock2 = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, clock2Hour, clock2Min);
        Date clockDiff = new Date(clock2.getTime() - clock1.getTime());

        //getting time for lunch from user input//
        clock3Hour = time3.getCurrentHour();
        clock3Min = time3.getCurrentMinute();
        Date lunchClock = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, clock3Hour, clock3Min);

        //getting users work hours from input//
        workHours = Integer.parseInt(etWorkHours.getText().toString());
        Date work = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, workHours, 0);


        if ( clockDiff.getTime() < 0 ) {
            badChoice = true;
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Invalid Clock Choice!");
            alert.setMessage("Second Clock must be greater than First Clock!");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                }
            });
                /*alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });*/
            alert.setIcon(android.R.drawable.ic_dialog_alert);
            alert.show(); //make sure i can do this
        }

        if (!badChoice)
        {
            //this will get the two clock difference + the lunchTime
            tempClock = new Date( work.getTime() - clockDiff.getTime());

            Date temp = new Date( lunchClock.getTime() + (tempClock.getHours() * 3600000)
                    + (tempClock.getMinutes() * 60000) );


            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            String folderName = formatter.format(temp);


            format.setText("You need to clock out at " + folderName);

        }
        //necessary to change boolean back for next run
        badChoice = false;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
            /* use hasFocus so that when i click on another
            text view, the one before doesn't disappear
            since on focus change checks when it gains & loses
            focus
             */
                /*THINK ABOUT NOT DELETING THE USER INPUT THE 2ND TIME*/
        if (hasFocus) {
            switch (v.getId()) {
                case R.id.etWorkH:
                    etWorkHours.setText("");
                    break;
            }
        }
    }

    private void initialize() {
        //output result//
        format = (TextView) findViewById(R.id.tvString);

        //the two clocks that are appeared//
        time1 = (TimePicker) findViewById(R.id.timePicker1);
        time1.setIs24HourView(false);
        time2 = (TimePicker) findViewById(R.id.timePicker2);
        time2.setIs24HourView(false);

        //lunch hour clock time picker//
        time3 = (TimePicker) findViewById(R.id.timePicker3);
        time3.setIs24HourView(false);

        //Calculate difference button//
        bCalc = (Button) findViewById(R.id.bCalc);
        bCalc.setOnClickListener(this);

        //users work hours//
        etWorkHours = (EditText) findViewById(R.id.etWorkH);
        etWorkHours.setText("0");

        //set listeners for the edit texts//
        //this just gets rid of having to make the user do a delete//
        etWorkHours.setOnFocusChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
