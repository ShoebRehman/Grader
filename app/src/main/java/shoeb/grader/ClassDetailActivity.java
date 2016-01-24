package shoeb.grader;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import shoeb.grader.R;


/**
 * An activity representing a single Class detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ClassListActivity}.
 */
public class ClassDetailActivity extends AppCompatActivity {

    static final int DATA_REQUEST = 1;
    //DBHelper helper;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        //helper = new DBHelper(this);
        //helper.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder ab = new AlertDialog.Builder(ClassDetailActivity.this);
                ab.setTitle("Are you sure you want to delete this class?");
                ab.setPositiveButton("Yes Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //gets the current working class and removes it in the map, list, and database
                        String name = (String) getIntent().getStringExtra(ClassDetailFragment.ARG_ITEM_ID);
                        classInfo item = classContent.ITEM_MAP.get(name);
                        classContent.ITEM_MAP.remove(name);
                        int loc = classContent.ITEMS.indexOf(item);
                        classContent.ITEMS.remove(loc);
                        //helper.removeClassByName(name);

                        navigateUpTo(new Intent(getApplicationContext(), ClassListActivity.class));
                    }
                });

                ab.setNegativeButton("No, don't delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                ab.show();
            }

        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ClassDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ClassDetailFragment.ARG_ITEM_ID));
            ClassDetailFragment fragment = new ClassDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.class_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ClassListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
