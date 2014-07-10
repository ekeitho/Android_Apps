package mcom.update;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by m652315 on 6/25/14.
 */
public class Menu extends ListActivity {

    String classes[] = {"startingPoint", "TextPlay", "Playground", "Email", "Camera", "Data", "Choose" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(Menu.this, android.R.layout.simple_list_item_1, classes));

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String local = classes[position];

        try {
            Class ourClass = Class.forName("mcom.update." + local);
            Intent ourIntent = new Intent(Menu.this, ourClass);
            startActivity(ourIntent);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
