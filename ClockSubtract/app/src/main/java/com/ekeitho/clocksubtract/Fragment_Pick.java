package com.ekeitho.clocksubtract;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.neopixl.pixlui.components.edittext.EditText;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

/**
 * Created by m652315 on 8/14/14.
 */
public class Fragment_Pick extends Fragment {

    private Fragment frag = this;
    private Double dub;
    private ActivityCommunicator activityCommunicator;

    public Fragment_Pick() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pick, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.frag_pick);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    Log.v("Done", "THE USER IS DONE");

                    FragmentActivity act = getActivity();

                    InputMethodManager imm = (InputMethodManager)
                            act.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

                    dub = Double.parseDouble(editText.getText().toString());
                    activityCommunicator.passDoubleToActivity(dub);
                    activityCommunicator.calculate();

                    act.getSupportFragmentManager().beginTransaction()
                            .remove(frag)
                            .commit();

                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        activityCommunicator = (ActivityCommunicator) getActivity();
    }

}
