package com.example.gmisproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    private EmpBinAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        ImageView signout = findViewById(R.id.sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog =new Dialog(Employee.this);
                dialog.setContentView(R.layout.dialogalert);
                dialog.setCancelable(false);
                dialog.show();
                Button btn_yes = dialog.findViewById(R.id.btn_yes);
                Button btn_no = dialog.findViewById(R.id.btn_no);
                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();

                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });


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

        mAdapter.setOnItemClickListener(new EmpBinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              //  Toast.makeText(Employee.this, "hello b", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Employee.this , MapActivity.class);
                startActivity(intent);
            }
        });
    }

}
