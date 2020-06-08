package com.example.future.healthapp;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.future.healthapp.Utils.DummyData;
import com.example.future.healthapp.Utils.FriendlyMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class doctor_view extends AppCompatActivity implements MyPatientsProvider.HealthHandler {
    //firebase database
    FirebaseDatabase database;
    private ChildEventListener mChildEventListener;
    DatabaseReference docRef ;
    HashMap<String, String> hmap = new HashMap<String, String>();
    String uid;
    RecyclerView view;
    ProgressBar progressBar;
    ImageView empty;
    MyPatientsProvider myPatientsProvider;
    static ArrayList<String> newList=new ArrayList<String>();
    private static final String TAG = "HEBA";
    String name;
    static ArrayList<String>Keylist=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_view);
         empty=(ImageView)findViewById(R.id.empty);
        empty.setVisibility(View.GONE);
        final TextView textView=(TextView)findViewById(R.id.textView3);
        textView.setVisibility(View.GONE);
        database=FirebaseDatabase.getInstance();
        //get my uid
        uid=getIntent().getBundleExtra("HEBA").getString("UID");
        name=getIntent().getBundleExtra("HEBA").getString("NAME");
        FriendlyMessage friendlyMessage=new FriendlyMessage(uid,name);
        database.getReference().child("Doctors").push().setValue(friendlyMessage);
        Log.d(TAG, "onCreate: "+uid);
        GetMyPatients();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(progressBar.getVisibility()== View.VISIBLE){progressBar.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
                textView.setVisibility(View.VISIBLE);
                }
            }
        }, 4000);
        //Recycle view part
         view = (RecyclerView) findViewById(R.id.rv_numbers);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(linearLayoutManager);
         myPatientsProvider = new MyPatientsProvider(this,newList,this);
        view.setAdapter(myPatientsProvider);
        //DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(view.getContext(),linearLayoutManager.getOrientation());
        //view.addItemDecoration(dividerItemDecoration);
    }


    @Override
    public void onclick(long mid) {
        Intent intent = new Intent(doctor_view.this, Details_view.class);
        intent.putExtra("HEBA",mid);
        Log.d(TAG, "onclicklll: "+mid);
        startActivity(intent);
    }
/////////////////////Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doctor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.form) {

            Intent intent = new Intent(doctor_view.this, ScrollingActivity.class);
            intent.putExtra("HEBA",uid);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //////////////
    public  static ArrayList<String> get(){
        return Keylist;
    }
    void GetMyPatients(){
        docRef = database.getReference().child("Relation").child(uid);
        Log.d(TAG, "GetMyPatients: "+uid);
        mChildEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                Log.d(TAG, "onChildAdd: "+friendlyMessage);
                hmap.put(friendlyMessage.getUid(),friendlyMessage.getName());
                Log.d(TAG, "onCreate2: "+friendlyMessage.getName());
                //m.add("Ahmed");
                newList = new ArrayList<>(hmap.values());
                Keylist.add(friendlyMessage.getUid());
                Log.d(TAG, "onChildAdded: "+friendlyMessage.getUid());
                myPatientsProvider.swap(newList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String friendlyMessage = dataSnapshot.getValue(String.class);
                Log.d(TAG, "onChildChanged: "+friendlyMessage);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String friendlyMessage = dataSnapshot.getValue(String.class);
                Log.d(TAG, "onChildRemoved: "+friendlyMessage);
                hmap.remove(dataSnapshot.getKey());
                ArrayList<String> newList = new ArrayList<>(hmap.values());
                myPatientsProvider.swap(newList);
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        docRef.addChildEventListener(mChildEventListener);
    }
}