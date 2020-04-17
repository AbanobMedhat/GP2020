package com.example.health;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.health.Utils.DummyData;


public class doctor_view extends AppCompatActivity implements MyPatientsProvider.HealthHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_view);
        RecyclerView view = (RecyclerView) findViewById(R.id.rv_numbers);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        view.setLayoutManager(linearLayoutManager);
        MyPatientsProvider myPatientsProvider = new MyPatientsProvider(DummyData.test(),this);
        view.setAdapter(myPatientsProvider);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(),
                linearLayoutManager.getOrientation());
        view.addItemDecoration(dividerItemDecoration);

    }


    @Override
    public void onclick() {
        Intent intent = new Intent(doctor_view.this,Details_view.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.form) {

            Intent intent = new Intent(doctor_view.this, ScrollingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}