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

public class ClockOutput extends Fragment {


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
                                activityCommunicator.passDateStrings("First time set is\n", date1);

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
                                    activityCommunicator.passDateStrings("Second time set is\n", date2);
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
                                    activityCommunicator.passDateStrings("Third time set is\n", date3);
                                    //maybe need to check null dates further on in development
                                    activityCommunicator.passDates(date1, date2, date3);

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

}
