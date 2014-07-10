package com.ekeitho.habits;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

public class HabitListActivity extends ListActivity {

    private ParseQueryAdapter<Habit> mainAdapter;
    private PriorityHabits favoritesAdapter;
    private SwipeDismissAdapter adapter;
    private ArrayAdapter<Habit> adapt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainAdapter = new ParseQueryAdapter<Habit>(this, Habit.class);
        mainAdapter.setTextKey("habit");
        mainAdapter.setImageKey("photo");

        // Subclass of ParseQueryAdapter
        favoritesAdapter = new PriorityHabits(this);

        // adapter the builds upon the dismiss of activities
        adapter = new SwipeDismissAdapter(mainAdapter, new OnDismissCallback() {
            @Override
            public void onDismiss(AbsListView listView, int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    ParseObject obj = mainAdapter.getItem(position);
                    obj.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            updateHabitList();
                        }
                    });
                }
            }
        });

        adapter.setAbsListView(getListView());
        getListView().setAdapter(adapter);
    }

    //sweet jaayssus
    //working on code

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_habit_list, menu);
        return true;
    }

    /*
     * Posting meals and refreshing the list will be controlled from the Action
     * Bar.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh: {
                updateHabitList();
                break;
            }

            case R.id.action_favorites: {
                showFavorites();
                break;
            }

            case R.id.action_new: {
                newHabits();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateHabitList() {
        mainAdapter.loadObjects();
        setListAdapter(mainAdapter);
    }

    private void showFavorites() {
        favoritesAdapter.loadObjects();
        setListAdapter(favoritesAdapter);
    }

    private void newHabits() {
        Intent i = new Intent(this, NewHabitActivity.class);
        startActivityForResult(i, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            // If a new post has been added, update
            // the list of posts
            updateHabitList();
        }
    }

}
