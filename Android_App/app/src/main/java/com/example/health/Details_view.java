package com.example.health;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class Details_view extends AppCompatActivity {
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        // Create the adapter that will return a fragment for each of the three
// primary sections of the activity.
        List<String> pageTitles = new ArrayList<String>() {{
            add("Form");
            add("Data");
        }};

// this information can come from a database or web service
        List<Class> fragmentTypes = new ArrayList<Class>() {{
            add(FormResult.class);
            add(DataSensors.class);
        }};

        android.support.v4.app.FragmentPagerAdapter adapter =
                new DynamicPagerAdapter(getSupportFragmentManager(), pageTitles, fragmentTypes);

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }
}