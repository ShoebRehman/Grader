package shoeb.grader;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import shoeb.grader.R;

/**
 * A fragment representing a single Class detail screen.
 * This fragment is either contained in a {@link ClassListActivity}
 * in two-pane mode (on tablets) or a {@link ClassDetailActivity}
 * on handsets.
 */
public class ClassDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    //DBHelper helper;


    /**
     * The dummy content this fragment is presenting.
     */
    public classInfo mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClassDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //onCreate of the fragment, open a helper
        //helper = new DBHelper(getContext());
        //helper.open();

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the class content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = classContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.class_detail, container, false);

        // Show the class in the fragment
        if (mItem != null) {
            //the current item shown in the fragment gets it's information
            final int hwTotal = mItem.getHomeworkCount();
            final int projTotal = mItem.getProjectCount();
            final int testTotal = mItem.getTestCount();
            final int total = hwTotal+projTotal+testTotal;

            //gets the grade list of tests, homework, and projects
            grade[] test = mItem.getTest();
            grade[] hw = mItem.getHomework();
            grade[] proj = mItem.getProjects();

            int i = 0, k=0;

            TableLayout tbl = (TableLayout) rootView.findViewById(R.id.tbl);
            tbl.setGravity(Gravity.CENTER_HORIZONTAL);
            tbl.setStretchAllColumns(true);

            //the following is what sets up the details fragment page based on the information for the class showing
            while(i < total) {
                if (testTotal != 0) {

                    TableRow titleRow = new TableRow(getContext());
                    TextView title = new TextView(getContext());
                    title.setTextSize(30f);
                    title.setGravity(Gravity.LEFT);
                    title.setPadding(50, 0, 0, 50);
                    title.setText("Tests");
                    titleRow.addView(title);
                    tbl.addView(titleRow);


                    for (k = 1; k < testTotal + 1; k++, i++) {
                        TableRow r = new TableRow(getContext());
                        String num = "Test #";
                        num += k;

                        TextView tv = new TextView(getContext());
                        tv.setText(num);
                        tv.setTextSize(20f);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        r.addView(tv);

                        EditText et = new EditText(getContext());
                        et.setTextSize(20f);
                        et.setText("" + test[k - 1].getGrade());
                        et.setGravity(Gravity.CENTER_HORIZONTAL);
                        r.addView(et);
                        r.setGravity(Gravity.CENTER_HORIZONTAL);
                        tbl.addView(r);
                    }
                }

                if (hwTotal != 0) {

                    TableRow titleRow = new TableRow(getContext());
                    TextView title = new TextView(getContext());
                    title.setTextSize(30f);
                    title.setGravity(Gravity.LEFT);
                    title.setPadding(50, 0, 0, 50);
                    title.setText("Homeworks");
                    titleRow.addView(title);
                    tbl.addView(titleRow);


                    for (k = 1; k < hwTotal + 1; k++, i++) {
                        TableRow r = new TableRow(getContext());
                        String num = "Homework #";
                        num += k;

                        TextView tv = new TextView(getContext());
                        tv.setText(num);
                        tv.setTextSize(20f);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        r.addView(tv);

                        EditText et = new EditText(getContext());
                        et.setTextSize(20f);
                        et.setGravity(Gravity.CENTER_HORIZONTAL);
                        et.setText("" + hw[k - 1].getGrade());
                        r.addView(et);

                        r.setGravity(Gravity.CENTER_HORIZONTAL);
                        tbl.addView(r);
                    }
                }

                if (projTotal != 0) {

                    TableRow titleRow = new TableRow(getContext());
                    TextView title = new TextView(getContext());
                    title.setTextSize(30f);
                    title.setGravity(Gravity.LEFT);
                    title.setPadding(50, 0, 0, 50);
                    title.setText("Projects");
                    titleRow.addView(title);
                    tbl.addView(titleRow);


                    for (k = 1; k < projTotal + 1; k++, i++) {
                        TableRow r = new TableRow(getContext());
                        String num = "Project #";
                        num += k;

                        TextView tv = new TextView(getContext());
                        tv.setText(num);
                        tv.setTextSize(20f);
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        r.addView(tv);

                        EditText et = new EditText(getContext());
                        et.setTextSize(20f);
                        et.setGravity(Gravity.CENTER_HORIZONTAL);
                        et.setText("" + proj[k - 1].getGrade());
                        r.addView(et);

                        r.setGravity(Gravity.CENTER_HORIZONTAL);
                        tbl.addView(r);
                    }
                }
            }

            //sets up the projection portion of the details page
            TableRow titleRow = new TableRow(getContext());
            TextView title = new TextView(getContext());
            title.setTextSize(30f);
            title.setGravity(Gravity.LEFT);
            title.setPadding(50, 0, 0, 50);
            title.setText("Projections");
            titleRow.addView(title);
            tbl.addView(titleRow);

            TableRow r = new TableRow(getContext());
            String text = "Select Desired Grade:";

            TextView tv = new TextView(getContext());
            tv.setText(text);
            tv.setTextSize(20f);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            r.addView(tv);

            //creates the spinner
            final String[] grade = {"A+","A","A-","B+","B","B-","C+","C","D","F"};
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item, grade);

            final Spinner sp = new Spinner(getContext());
            sp.setAdapter(spinnerArrayAdapter);
            sp.setMinimumWidth(50);

            r.addView(sp);

            tbl.addView(r);

            final TextView textView = new TextView(getContext());
            textView.setText(getString(R.string.refreshButton));
            textView.setTextSize(15f);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setPadding(0, 20, 0, 20);

            tbl.addView(textView);

            Button refreshButton = new Button(getContext());
            refreshButton.setText("Update and Refresh");

            tbl.addView(refreshButton);

            final TableLayout finTbl = tbl;

            //based on the spinner selection, set desired grade
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = (String) sp.getSelectedItem();
                    int desGrade = 0;

                    switch(s){
                        case "A+":
                            desGrade = 98;
                            break;
                        case "A":
                            desGrade = 93;
                            break;
                        case "A-":
                            desGrade = 90;
                            break;
                        case "B+":
                            desGrade = 88;
                            break;
                        case "B":
                            desGrade = 83;
                            break;
                        case "B-":
                            desGrade = 80;
                            break;
                        case "C+":
                            desGrade = 78;
                            break;
                        case "C":
                            desGrade = 70;
                            break;
                        case "D":
                            desGrade = 60;
                            break;
                        case "F":
                            desGrade = 0;
                            break;
                    }

                    //math to calculate average grade for remaining tests is done here
                    int i = 3;
                    int k = 0;
                    double runningTotal = 0;
                    double percentageTotal = 0;
                    grade[] test = mItem.getTest();
                    grade[] hw = mItem.getHomework();
                    grade[] proj = mItem.getProjects();

                    while(i < finTbl.getChildCount()-4) {
                        if (testTotal != 0) {
                            for (k = 0; k < testTotal; k++, i++) {
                                TableRow r = (TableRow) finTbl.getChildAt(i);
                                EditText ed = (EditText) r.getChildAt(1);
                                try {
                                    String currString = ed.getText().toString();
                                    int currNum = 0;
                                    try {
                                        currNum = Integer.parseInt(currString);
                                    } catch (NumberFormatException e) {
                                        currNum = 0;
                                    }
                                    if (test[k].getGrade() != currNum) {
                                        test[k].setGrade(currNum);
                                    }
                                } catch (NullPointerException e) {
                                    ed.setText(0);
                                    test[k].setGrade(0);
                                }

                                if (test[k].getGrade() != 0) {
                                    double grade = 0;
                                    grade = test[k].getPercentage();
                                    grade = grade / 100;
                                    grade = test[k].getGrade() * grade;
                                    runningTotal += grade;
                                } else {
                                    percentageTotal += test[k].getPercentage();
                                }
                            }
                            i++;
                        }

                        if (hwTotal != 0) {
                            for (k = 0; k < hwTotal; k++, i++) {
                                TableRow r = (TableRow) finTbl.getChildAt(i);
                                EditText ed = (EditText) r.getChildAt(1);
                                try {
                                    String currString = ed.getText().toString();
                                    int currNum = 0;
                                    try {
                                        currNum = Integer.parseInt(currString);
                                    } catch (NumberFormatException e) {
                                        currNum = 0;
                                    }
                                    if (hw[k].getGrade() != currNum) {
                                        hw[k].setGrade(currNum);
                                    }
                                } catch (NullPointerException e) {
                                    ed.setText(0);
                                    hw[k].setGrade(0);
                                }

                                if (hw[k].getGrade() != 0) {
                                    double grade = 0;
                                    grade = hw[k].getPercentage();
                                    grade = grade / 100;
                                    grade = hw[k].getGrade() * grade;
                                    runningTotal += grade;
                                } else {
                                    percentageTotal += hw[k].getPercentage();
                                }
                            }
                            i++;
                        }

                        if (projTotal != 0) {
                            for (k = 0; k < projTotal; k++, i++) {
                                TableRow r = (TableRow) finTbl.getChildAt(i);
                                EditText ed = (EditText) r.getChildAt(1);
                                try {
                                    String currString = ed.getText().toString();
                                    int currNum = 0;
                                    try {
                                        currNum = Integer.parseInt(currString);
                                    } catch (NumberFormatException e) {
                                        currNum = 0;
                                    }
                                    if (proj[k].getGrade() != currNum) {
                                        proj[k].setGrade(currNum);
                                    }
                                } catch (NullPointerException e) {
                                    ed.setText(0);
                                    proj[k].setGrade(0);
                                }

                                if (proj[k].getGrade() != 0) {
                                    double grade = 0;
                                    grade = proj[k].getPercentage();
                                    grade = grade / 100;
                                    grade = proj[k].getGrade() * grade;
                                    runningTotal += grade;
                                } else {
                                    percentageTotal += proj[k].getPercentage();
                                }
                            }
                        }
                    }

                    double gradeLeft = desGrade - runningTotal;
                    double check = gradeLeft / percentageTotal;

                    mItem.setAll(test,hw,proj);
                    //helper.updateClass(mItem);

                    if(check > 1 ){
                        textView.setText("The desired grade you selected is not achievable. Please change the desired grade.");
                    }

                    else{
                        check *=100;
                        BigDecimal bd = new BigDecimal(check);
                        bd = bd.setScale(2, RoundingMode.HALF_UP);
                        textView.setText("For the remaining grades, you need to average of " + bd.doubleValue() + " for the remaining grades.");
                    }

                }
            });


        }

        return rootView;
    }
}
