package com.example.gmisproject;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Employee extends AppCompatActivity {

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);


        ArrayList<EmpBin> empBins = new ArrayList<EmpBin>();
        empBins.add((new EmpBin(EmpBin.PUBLIC_BIN, 80, "10", "تعمل")));
        empBins.add(new EmpBin(EmpBin.PUBLIC_BIN, 60, "15", "لا تعمل"));
        empBins.add((new EmpBin(EmpBin.PRIVATE_BIN, 90, "10", "لا تعمل", "10 ش النصر", "01061462749")));
        empBins.add((new EmpBin(EmpBin.PRIVATE_BIN, 90, "10", "لا تعمل", "10 ش النصر", "01061462749")));
        empBins.add((new EmpBin(EmpBin.PRIVATE_BIN, 90, "10", "لا تعمل", "10 ش النصر", "01061462749")));
        empBins.add((new EmpBin(EmpBin.PRIVATE_BIN, 90, "10", "لا تعمل", "10 ش النصر", "01061462749")));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);

        mAdapter = new EmpBinAdapter(empBins);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        openSheet();

    }

    protected void openSheet() {
        floatingActionButton = findViewById(R.id.fab_show_sheet);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSheet();
            }
        });
    }

    public void openDialogSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
        bottomSheetDialog.show(getSupportFragmentManager(), "BottomSheetDialog");
    }

}
