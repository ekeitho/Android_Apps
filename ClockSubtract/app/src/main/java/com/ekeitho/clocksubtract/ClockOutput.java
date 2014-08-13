package com.ekeitho.clocksubtract;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;


public class ClockOutput extends Fragment implements TimePickerDialogFragment.TimePickerDialogHandler {

    private FragmentActivity myContext;

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public ClockOutput() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initialize(rootView);
        return rootView;
    }

    public void initialize(final View view) {
        Button clock1 = (Button) view.findViewById(R.id.clock1);
        Button clock2 = (Button) view.findViewById(R.id.clock2);
        Button clock3 = (Button) view.findViewById(R.id.clock3);

        clock1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder builder = new TimePickerBuilder()
                        .setFragmentManager(myContext.getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                builder.show();
            }
        });

        clock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder builder = new TimePickerBuilder()
                        .setFragmentManager(myContext.getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                builder.show();
            }
        });

        clock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder builder = new TimePickerBuilder()
                        .setFragmentManager(myContext.getSupportFragmentManager())
                        .setStyleResId(R.style.BetterPickersDialogFragment_Light);
                builder.show();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onDialogTimeSet(int i, int i2, int i3) {

    }
}
