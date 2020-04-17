package com.example.health.Utils;

import android.util.Log;

import java.util.ArrayList;

public class DummyData {
public static ArrayList<String> test() {
     ArrayList<String> mine = new ArrayList<String>();
     for(int i=0;i<30;i++) {
         mine.add("Patient name "+i);
         Log.d("HEBA", "test: " + mine.get(i));
     }
     return mine;
}

}
