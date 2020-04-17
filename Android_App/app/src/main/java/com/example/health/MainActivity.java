package com.example.health;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button doctor_button;
Button patient_button;
Button send;
TextView req;
Intent intent;
EditText email;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doctor_button=(Button)findViewById(R.id.doctor_button);
        patient_button=(Button)findViewById(R.id.patient_button);
        req=(TextView)findViewById(R.id.request);
        send=(Button)findViewById(R.id.button);
        email=(EditText)findViewById(R.id.editText);
        if(savedInstanceState!=null){
            sec_view();
        }

        doctor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(MainActivity.this,doctor_view.class);
                startActivity(intent);
            }
        });
        patient_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sec_view();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
               req.setVisibility(View.VISIBLE);
            }
        });


    }
    void sec_view(){
        Group group=findViewById(R.id.group);
        group.setVisibility(View.GONE);
        Group group2=findViewById(R.id.group2);
        group2.setVisibility(View.VISIBLE);
    }
}
