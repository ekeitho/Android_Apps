package com.ekeitho.habits;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/*
 * NewMealActivity contains two fragments that handle
 * data entry and capturing a photo of a given meal.
 * The Activity manages the overall meal data.
 */
public class NewHabitActivity extends Activity {

    private Habit habit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        habit = new Habit();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_habit);
        FragmentManager manager = getFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragmentContainer);

        if(fragment == null) {
            fragment = new NewHabitFragment();
            manager.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }

    }

    public Habit getCurrentMeal() {
        return habit;
    }
}