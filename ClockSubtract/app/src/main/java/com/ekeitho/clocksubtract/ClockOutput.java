package com.ekeitho.clocksubtract;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

public class ClockOutput extends Fragment implements FragCommunicator{


    private int flag = 0;
    private Button clock1, clock2, clock3;
    private Date date1, date2, date3, differenceDate;
    private ActivityCommunicator activityCommunicator;
    private double work_hours;

    public ClockOutput() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityCommunicator = (ActivityCommunicator) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        clock1 = (Button) view.findViewById(R.id.clock1);
        clock2 = (Button) view.findViewById(R.id.clock2);
        clock3 = (Button) view.findViewById(R.id.clock3);

        setListenersOnButtons();

        return view;
    }

    private void calculate() {
        //find minute difference
        long diff_min = 60 + date2.getMinutes() - date1.getMinutes();
        //find hour difference
        long diff_hour = ((diff_min / 60) - 1) +
                date2.getHours() - date1.getHours();
        //this occurs when overlaps from a 12 hour period
        if (diff_min > 59) {
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
                        .setFragmentManager(getActivity().getSupportFragmentManager())
                        .addTimePickerDialogHandler(new TimePickerDialogFragment.TimePickerDialogHandler() {
                            @Override
                            public void onDialogTimeSet(int i, int hour, int minutes) {
                                date1 = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hour, minutes, 0);
                                flag = 1;
                                activityCommunicator.passDatesToActivity("First time set is\n", date1);

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
                    Toast.makeText(getActivity(),
                            "Must choose a time before this button.", Toast.LENGTH_SHORT).show();
                } else {

                    TimePickerBuilder builder = new TimePickerBuilder()
                            .setFragmentManager(getActivity().getSupportFragmentManager())
                            .addTimePickerDialogHandler(new TimePickerDialogFragment.TimePickerDialogHandler() {
                                @Override
                                public void onDialogTimeSet(int i, int hours, int minutes) {
                                    date2 = new Date(Calendar.YEAR, Calendar.MONTH,
                                            Calendar.DAY_OF_MONTH, hours, minutes, 0);

                                    flag++;
                                    activityCommunicator.passDatesToActivity("Second time set is\n", date2);
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
                    Toast.makeText(getActivity(), "Must choose a time before this button.", Toast.LENGTH_SHORT).show();
                } else {

                    TimePickerBuilder builder = new TimePickerBuilder()
                            .setFragmentManager(getActivity().getSupportFragmentManager())
                            .addTimePickerDialogHandler(new TimePickerDialogFragment.TimePickerDialogHandler() {
                                @Override
                                public void onDialogTimeSet(int i, int hours, int minutes) {
                                    date3 = new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hours, minutes);
                                    activityCommunicator.passDatesToActivity("Third time set is\n", date3);

                                    Fragment frag = new Fragment_Pick();
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    fm.beginTransaction()
                                        .replace(R.id.frag, frag)
                                        .commit();
                                }
                            })
                            .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                    builder.show();
                }
            }


        });



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void fragCommunicator(double hours) {
        work_hours = hours;
    }
}
