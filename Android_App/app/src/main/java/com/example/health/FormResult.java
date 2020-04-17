package com.example.health;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FormResult extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.form_main, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.datatest);
        return rootView;
    }
}
