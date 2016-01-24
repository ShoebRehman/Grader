package shoeb.grader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shoeb on 1/16/2016.
 */
public class setGradeAndPercentages extends AppCompatActivity {

    static final int DATA_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init for finish button
        Button button = new Button(this);
        button.setText("Finish");
        button.setWidth(200);


        //inflates layout from this activity
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        ScrollView sv = new ScrollView(this);
        TableLayout tbl = new TableLayout(this);

        //removes views just in case
        tbl.removeAllViews();
        sv.removeAllViews();

        Intent cl = getIntent();
        Bundle b = cl.getExtras();
        classInfo myClass = (classInfo) b.getSerializable("class");

        Integer[] percent = {0,5,10,15,20,25,30,40,50,60,70};
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item, percent);

        final int hwTotal = myClass.getHomeworkCount();
        final int projTotal = myClass.getProjectCount();
        final int testTotal = myClass.getTestCount();
        final int totalCount = hwTotal + projTotal + testTotal;

        TableRow r = new TableRow(this);
        TextView textView = new TextView(this);
        textView.setText("Assignment");
        textView.setTextSize(20f);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setGravity(Gravity.LEFT);
        r.addView(textView);

        TextView textView1 = new TextView(this);
        textView1.setText("Grade");
        textView1.setTextSize(20f);
        textView1.setTextColor(Color.parseColor("#ffffff"));
        textView1.setPadding(50, 0, 50, 0);
        textView1.setGravity(Gravity.CENTER);
        r.addView(textView1);

        TextView textView2 = new TextView(this);
        textView2.setText("% of Grade");
        textView2.setTextSize(20f);
        textView2.setTextColor(Color.parseColor("#ffffff"));
        textView2.setPadding(0, 0, 0, 0);
        textView2.setGravity(Gravity.CENTER);
        r.addView(textView2);

        r.setGravity(Gravity.CENTER);
        tbl.addView(r);


        for(int k = 1; k < testTotal+1; k++){
            String num = "Test #";
            num += k;

            Spinner sp = new Spinner(getApplicationContext());
            sp.setAdapter(spinnerArrayAdapter);
            sp.setMinimumWidth(50);

            TableRow row = new TableRow(getApplicationContext());

            TextView tv = new TextView(getApplicationContext());
            EditText et = new EditText(getApplicationContext());

            tv.setGravity(Gravity.LEFT);
            tv.setText(num);
            tv.setTextSize(20f);
            tv.setTextColor(Color.parseColor("#ffffff"));

            et.setGravity(Gravity.RIGHT);
            et.setTextColor(Color.parseColor("#ffffff"));
            et.setWidth(100);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);


            row.addView(tv);
            row.addView(et);
            row.addView(sp);

            row.setGravity(Gravity.CENTER);
            row.setPadding(0,10,0,10);

            tbl.addView(row);
        }

        for(int k = 1; k < hwTotal+1; k++){
            String num = "Homework #";
            num += k;

            Spinner sp = new Spinner(getApplicationContext());
            sp.setAdapter(spinnerArrayAdapter);
            sp.setMinimumWidth(50);

            TableRow row = new TableRow(getApplicationContext());

            TextView tv = new TextView(getApplicationContext());
            EditText et = new EditText(getApplicationContext());

            tv.setGravity(Gravity.LEFT);
            tv.setText(num);
            tv.setTextSize(20f);
            tv.setTextColor(Color.parseColor("#ffffff"));

            et.setGravity(Gravity.RIGHT);
            et.setTextColor(Color.parseColor("#ffffff"));
            et.setWidth(100);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);

            row.addView(tv);
            row.addView(et);
            row.addView(sp);

            row.setGravity(Gravity.CENTER);
            row.setPadding(0,10,0,10);

            tbl.addView(row);
        }

        for(int k = 1; k < projTotal+1; k++){
            String num = "Project #";
            num += k;

            Spinner sp = new Spinner(getApplicationContext());
            sp.setAdapter(spinnerArrayAdapter);
            sp.setMinimumWidth(50);

            TableRow row = new TableRow(getApplicationContext());

            TextView tv = new TextView(getApplicationContext());
            EditText et = new EditText(getApplicationContext());

            tv.setGravity(Gravity.LEFT);
            tv.setText(num);
            tv.setTextSize(20f);
            tv.setTextColor(Color.parseColor("#ffffff"));

            et.setGravity(Gravity.RIGHT);
            et.setTextColor(Color.parseColor("#ffffff"));
            et.setWidth(100);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);

            row.addView(tv);
            row.addView(et);
            row.addView(sp);

            row.setGravity(Gravity.CENTER);
            row.setPadding(0,10,0,10);

            tbl.addView(row);
        }

        tbl.setStretchAllColumns(true);

        tbl.addView(button);
        sv.addView(tbl);
        setContentView(sv);

        final TableLayout finTbl = tbl;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checks for percentages added and their total. Must equal 100
                int percentageTotal = 0;
                for(int i = 1; i < finTbl.getChildCount()-1;i++) {
                    TableRow row = (TableRow) finTbl.getChildAt(i);
                    Spinner s = (Spinner) row.getChildAt(2);
                    if((Integer) s.getSelectedItem() == 0){
                        Toast.makeText(getApplicationContext(), "One or more percentages are 0. Please update them.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    percentageTotal += (Integer) s.getSelectedItem();
                }

                if(percentageTotal != 100){
                    Toast.makeText(getApplicationContext(), "The percentages currently add up to "+percentageTotal+".Please make sure all percentages add up to 100", Toast.LENGTH_SHORT).show();
                    return;
                }

                int k;
                int i = 1;
                grade[] testArr = new grade[testTotal];
                grade[] hwArr = new grade[hwTotal];
                grade[] projArr = new grade[projTotal];

                Intent cl = getIntent();
                Bundle b = cl.getExtras();
                classInfo myClass = (classInfo) b.getSerializable("class");

                while(i < finTbl.getChildCount()-1){

                    for(k = 0; k < testTotal; k++,i++){
                        TableRow r = (TableRow) finTbl.getChildAt(i);
                        EditText ed = (EditText) r.getChildAt(1);

                        String val = ed.getText().toString();
                        int n = 0;
                        try {
                            n = Integer.parseInt(val);
                        }catch (NumberFormatException e){
                            n = 0;
                        }
                        Spinner s = (Spinner) r.getChildAt(2);
                        int p = (Integer) s.getSelectedItem();

                        grade g = new grade(n,p);
                        testArr[k] = g;
                    }

                    for(k = 0; k < hwTotal; k++,i++){
                        TableRow r = (TableRow) finTbl.getChildAt(i);
                        EditText ed = (EditText) r.getChildAt(1);

                        String val = ed.getText().toString();
                        int n = 0;
                        try {
                            n = Integer.parseInt(val);
                        }catch (NumberFormatException e){
                            n = 0;
                        }
                        Spinner s = (Spinner) r.getChildAt(2);
                        int p = (Integer) s.getSelectedItem();

                        grade g = new grade(n, p);
                        hwArr[k] = g;
                    }

                    for(k = 0; k < projTotal; k++,i++){
                        TableRow r = (TableRow) finTbl.getChildAt(i);
                        EditText ed = (EditText) r.getChildAt(1);

                        String val = ed.getText().toString();
                        int n = 0;
                        try {
                            n = Integer.parseInt(val);
                        }catch (NumberFormatException e){
                            n = 0;
                        }
                        Spinner s = (Spinner) r.getChildAt(2);
                        int p = (Integer) s.getSelectedItem();

                        grade g = new grade(n, p);
                        projArr[k] = g;
                    }

                }
                myClass.setAll(testArr,hwArr,projArr);

                Intent intent = new Intent(getApplicationContext(), ClassListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("class", myClass);
                intent.putExtras(bundle);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
