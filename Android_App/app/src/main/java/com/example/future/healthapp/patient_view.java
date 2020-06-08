package com.example.future.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.future.healthapp.Utils.FriendlyMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class patient_view extends AppCompatActivity {
    //TODO
    Intent mintent=getIntent();
    FirebaseDatabase database;
    private ChildEventListener mChildEventListener;
    DatabaseReference docRef ;
    private static final String TAG = "HEBA";
    String[]output;
    String uid;
    String name;
    String result="";
    String mDoctor;
    int id=0;
    String friendlyMessage;
    FloatingActionButton send;
    LinearLayout form;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_view);
        database = FirebaseDatabase.getInstance();
        form = (LinearLayout)findViewById(R.id.mform2);
        send=(FloatingActionButton)findViewById(R.id.send);
        uid=getIntent().getBundleExtra("HEBA").getString("UID");
        name=getIntent().getBundleExtra("HEBA").getString("NAME");
        mDoctor=getIntent().getBundleExtra("HEBA").getString("MDR");
        FriendlyMessage friendlyMessage = new FriendlyMessage(uid,name);
        docRef = database.getReference().child("Relation").child(mDoctor);
        docRef.push().setValue(friendlyMessage);
        String doc_uid = "85296";
        Log.d(TAG, "onCreate: "+uid);
        docRef = database.getReference().child("Relation");
        //docRef.push().setValue("heba");
        read_data();

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for(int i=0;i<output.length;i++){
                 RadioGroup radioGroup=(RadioGroup)findViewById(i+4);
                 int m=radioGroup.getCheckedRadioButtonId();
                 if(m!=-1){
                 result=result.concat(String.valueOf(m)+'/');}
                 else{
                     Toast.makeText(patient_view.this,"Please Answer the All Questions First",Toast.LENGTH_SHORT).show();
                     result="";
                     break;
                 }
                }
                docRef = database.getReference().child("FormsResult").child(uid);
                docRef.push().setValue(result);
                Toast.makeText(patient_view.this,"Your answers've been uploaded Successfully",Toast.LENGTH_SHORT).show();
                result="";
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic(mDoctor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg ="SUCCESSS";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(patient_view.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }
    void read_data(){
        //TODO we know my doctor
        //uid of the doctor
        docRef=database.getReference();

        Query lastQuery = docRef.child("Forms").child(mDoctor).orderByKey().limitToLast(1);
        lastQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                friendlyMessage = dataSnapshot.getValue(String.class);
                Log.d(TAG, "onChildAdd: "+friendlyMessage);
                deploy();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                friendlyMessage = dataSnapshot.getValue(String.class);
                Log.d(TAG, "onChildAdd: "+friendlyMessage);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

                //deploy();



    }
    void deploy(){
        output=friendlyMessage.split("/");
        for(String str :output){
            TextView et = new TextView(patient_view.this);
            et.setPadding(6,5,6,5);
            et.setMinLines(1);
            et.setMaxLines(3);
            et.setTextSize(20);
            et.setText(str);
            form.addView(et);
            /////////////////////////////////
            final RadioButton[] rb = new RadioButton[3];
            RadioGroup rg = new RadioGroup(patient_view.this); //create the RadioGroup
            rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
            for(int i=0; i<3; i++){
                rb[i]  = new RadioButton(patient_view.this);
                rb[i].setText(" option " + i);
                rb[i].setId(i);
                rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
            }
            rg.setPadding(6,0,0,5);
            rg.setId(id+4);
            form.addView(rg);//you add the whole RadioGroup to the layout
            id++;

        }

    }
}
