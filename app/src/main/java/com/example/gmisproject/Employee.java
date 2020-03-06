package com.example.gmisproject;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Employee extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);


        ArrayList<EmpBin> empBins = new ArrayList<EmpBin>();
        empBins.add(new EmpBin("110", 80, "كومباوند التجمع الخامس", "لا تعمل"));
        empBins.add(new EmpBin("111", 10, "كومباوند التجمع الخامس", "لا تعمل"));
        empBins.add(new EmpBin("112", 30, "كومباوند التجمع الخامس", "لا تعمل"));
        empBins.add(new EmpBin("113", 40, "كومباوند التجمع الخامس", "لا تعمل"));
        empBins.add(new EmpBin("114", 60, "كومباوند التجمع الخامس", "لا تعمل"));
        empBins.add(new EmpBin("115", 20, "كومباوند التجمع الخامس", "لا تعمل"));


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);

        mAdapter = new EmpBinAdapter(empBins);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

}
