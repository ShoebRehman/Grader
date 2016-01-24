package shoeb.grader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import shoeb.grader.R;

/**
 * Created by Shoeb on 1/15/2016.
 */
public class addNumber extends AppCompatActivity {
    static final int DATA_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        final LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View v = layoutInflater.inflate(R.layout.addactivitytests, null);
        setContentView(v);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerTests);

        final Button but = (Button) findViewById(R.id.testsNext);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //based on the dropdown items selected, stores the information into a class
                Intent cl = getIntent();
                Bundle b = cl.getExtras();
                classInfo myClass = (classInfo) b.getSerializable("class");


                Spinner sp = (Spinner) findViewById(R.id.spinnerTests);
                int tCount = Integer.parseInt(sp.getSelectedItem().toString());

                sp = (Spinner) findViewById(R.id.spinnerHomeworks);
                int hCount = Integer.parseInt(sp.getSelectedItem().toString());

                sp = (Spinner) findViewById(R.id.spinnerProjects);
                int pCount = Integer.parseInt(sp.getSelectedItem().toString());

                myClass.setTestCount(tCount);
                myClass.setHomeworkCount(hCount);
                myClass.setProjectCount(pCount);

                if(tCount+hCount+pCount < 2) {
                    Toast.makeText(getApplicationContext(),"Please select at least a total of 2 assignments.",Toast.LENGTH_SHORT).show();
                }
                else {
                    //class with count information is passed into the next activity
                    Intent intent = new Intent(getApplicationContext(), setGradeAndPercentages.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("class", myClass);
                    intent.putExtras(bundle);

                    startActivityForResult(intent, DATA_REQUEST);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        setResult(RESULT_OK,data);
        finish();
    }
}