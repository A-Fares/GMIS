package com.example.gmisproject;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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
    private float ratingCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ratingCount = 2f;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);


        ArrayList<EmpBin> empBins = new ArrayList<EmpBin>();
        empBins.add((new EmpBin(EmpBin.PRIVATE_BIN, 90, "10", "لا تعمل", "10 ش النصر", "01061462749")));
        empBins.add((new EmpBin(EmpBin.PRIVATE_BIN, 90, "10", "لا تعمل", "10 ش النصر", "01061462749")));
        empBins.add((new EmpBin(EmpBin.PUBLIC_BIN, 80, "10", "تعمل")));
        empBins.add(new EmpBin(EmpBin.PUBLIC_BIN, 60, "15", "لا تعمل"));
        empBins.add((new EmpBin(EmpBin.PUBLIC_BIN, 80, "10", "تعمل")));
        empBins.add(new EmpBin(EmpBin.PUBLIC_BIN, 60, "15", "لا تعمل"));

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);

        mAdapter = new EmpBinAdapter(empBins);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        openSheet();

    }

    protected void openSheet() {
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog(R.layout.bottomsheet);
            }
        });
    }

    private void showAlertDialog(int layout) {
        dialogBuilder = new AlertDialog.Builder(Employee.this);
        View layoutView = getLayoutInflater().inflate(layout, null);
        ImageView dialogClose = layoutView.findViewById(R.id.close);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        // Get the rating bar object from the LinearLayout, and set its rating to the stored value.
        RatingBar ratingBar = layoutView.findViewById(R.id.rating_bar_result);
        ratingBar.setRating(ratingCount);
    }

 
}
