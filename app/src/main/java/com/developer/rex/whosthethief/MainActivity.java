package com.developer.rex.whosthethief;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton r1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        r1 = (RadioButton) findViewById(R.id.r1);
        r1.performClick();
    }

    public void start(View view) {
        int playerNo = radioGroup.getCheckedRadioButtonId();
        int no =0;
        if(playerNo == R.id.r1) {
            no=1;
        }
        else if(playerNo == R.id.r2) {
            no=2;
        }
        else if(playerNo == R.id.r3) {
            no=3;
        }
        else if(playerNo == R.id.r4) {
            no=4;
        }

        Intent i = new Intent(MainActivity.this, GameActivity.class);
        i.putExtra("PlayerNo", String.valueOf(no));
        startActivity(i);
    }
}
