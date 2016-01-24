package shoeb.grader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import shoeb.grader.R;

/**
 * Created by Shoeb on 1/15/2016.
 */
public class addActivityName extends AppCompatActivity {
    static final int DATA_REQUEST = 1;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View v = layoutInflater.inflate(R.layout.addactivity, null, false);
        setContentView(v);

        final Button button = (Button) findViewById(R.id.classNext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //the class stores the name and is passed to the next activity
                Intent cl = getIntent();
                Bundle b = cl.getExtras();
                classInfo myClass = (classInfo) b.getSerializable("class");
                EditText et = (EditText) findViewById(R.id.nameText);
                String i = et.getText().toString();
                if(i != null) {
                    myClass.setName(i);

                    Intent intent = new Intent(getApplicationContext(), addNumber.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("class", myClass);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,DATA_REQUEST);
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
