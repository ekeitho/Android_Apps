package com.ekeitho.clocksubtract;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import com.doomonafireball.betterpickers.radialtimepicker.RadialPickerLayout;
import com.doomonafireball.betterpickers.radialtimepicker.RadialTimePickerDialog;


public class ClockOutput extends Fragment {

    private Button clock1, clock2, clock3;
    private ActivityCommunicator activityCommunicator;
    private int gcyear, gcmonth, gcday, flag = 0;

    public ClockOutput() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance();
        gcyear = calendar.get(Calendar.YEAR);
        gcmonth = calendar.get(Calendar.MONTH);
        gcday = calendar.get(Calendar.DAY_OF_MONTH);
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

    private void listenerClocks(final String order, final int index) {

        //using for radial picker time clocks preset times
        DateTime now = DateTime.now();

        RadialTimePickerDialog timePickerDialog = RadialTimePickerDialog
                .newInstance(new RadialTimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(RadialPickerLayout radialPickerLayout,
                                          int hours, int minutes) {
                        Date date = new Date(gcyear, gcmonth, gcday, hours, minutes, 0);
                        flag++;
                        activityCommunicator.passDateStrings(order + " time set is\n", date);
                        //used this method to add to an arrayList
                        //for efficiency and to be able to use this method for different amounts
                        //of clocks without repetition usage
                        activityCommunicator.addDates(date, index);

                        if (flag == 3) {
                            Fragment frag = new Fragment_Pick();
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.beginTransaction()
                                    .replace(R.id.frag, frag)
                                    .commit();
                            //make sure reset flag after next iteration
                            flag = 0;
                        }
                    }
                }, now.getHourOfDay(), now.getMinuteOfHour(), false);
        timePickerDialog.show(getFragmentManager(), "ClockOutput");

    }


    private void setListenersOnButtons() {

        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerClocks("First", 0);
            }
        });

        clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag < 1) {
                    Toast.makeText(getActivity(),
                            "Must choose a time before this button.", Toast.LENGTH_SHORT).show();
                } else {
                    listenerClocks("Second", 1);
                }
            }
        });

        clock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag < 2) {
                    Toast.makeText(getActivity(), "Must choose a time before this button.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    listenerClocks("Third", 2);
                }
            }
        });
    }

}
