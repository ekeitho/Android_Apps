package com.ekeitho.habits;

import java.util.Arrays;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

/*
 * The FavoriteMealAdapter is an extension of ParseQueryAdapter
 * that has a custom layout for priority habits, including a
 * bigger preview image, the habits actions, and a "favorite"
 * star. 
 */

public class PriorityHabits extends ParseQueryAdapter<Habit> {

	public PriorityHabits(Context context) {
		super(context, new ParseQueryAdapter.QueryFactory<Habit>() {
			public ParseQuery<Habit> create() {
				// Here we can configure a ParseQuery to display
				// only top-rated meals.
				ParseQuery query = new ParseQuery("Habits");
                query.whereEqualTo("habit", "cookie problem");
				query.orderByDescending("habit");
				return query;
			}
		});
	}

	@Override
	public View getItemView(Habit habit, View v, ViewGroup parent) {

		if (v == null) {
			v = View.inflate(getContext(), R.layout.habitview, null);
		}
		super.getItemView(habit, v, parent);

		ParseImageView habitImage = (ParseImageView) v.findViewById(R.id.icon);
		ParseFile photoFile = habit.getParseFile("photo");
		if (photoFile != null) {
			habitImage.setParseFile(photoFile);
			habitImage.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
		}

		TextView titleTextView = (TextView) v.findViewById(R.id.text1);
		titleTextView.setText(habit.getHabit());
		TextView ratingTextView = (TextView) v
				.findViewById(R.id.favorite_meal_rating);
		ratingTextView.setText(habit.getEmotion());
		return v;
	}

}
