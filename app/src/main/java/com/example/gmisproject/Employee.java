package com.example.gmisproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Employee extends AppCompatActivity {
    private static boolean backPressedTime;
    DatabaseReference referenceBins, referenceBinData;
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    ArrayList<Integer> BinsData;
    UsersModel usersModel;
    BinsModel binsModel;
    String userName;
    ImageView signOut;
    TextView textViewUsername;
    SharedPreferencesConfig preferencesConfig;
    private BinsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        signOut = findViewById(R.id.sign_out);
        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Employee.this);
                dialog.setContentView(R.layout.dialogalert);
                dialog.setCancelable(false);
                dialog.show();
                Button btn_yes = dialog.findViewById(R.id.btn_yes);
                Button btn_no = dialog.findViewById(R.id.btn_no);
                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Shared Preference Worker Logout

                        preferencesConfig.writeWorkerLoginStatus(false);
                        Intent intent = new Intent(Employee.this, Registeration.class);
                        startActivity(intent);
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

        final ArrayList<BinsModel> binsModels = new ArrayList<BinsModel>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        //reading the Bin id that user have
        referenceBins = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceBins.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersModel = dataSnapshot.getValue(UsersModel.class);
                BinsData = usersModel.getBins();
                Log.v("Binns", "bins == " + BinsData);
        /*        empBins.clear();
                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    EmpBin empBin = Snapshot.getValue(EmpBin.class);
                    empBins.add(empBin);
                }
                mAdapter = new EmpBinAdapter(empBins);
                recyclerView.setAdapter(mAdapter);

                Collections.reverse(empBins);
                mAdapter.setOnItemClickListener(new EmpBinAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(Employee.this, MapActivity.class);
                        startActivity(intent);
                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Employee.this, "something went wrong ..", Toast.LENGTH_SHORT).show();
            }
        });

        // searching for the data of bin that user have 
        referenceBinData = FirebaseDatabase.getInstance().getReference().child("Bins");
        referenceBinData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binsModels.clear();
                for (DataSnapshot binSnapshot : dataSnapshot.getChildren()) {
                    binsModel = binSnapshot.getValue(BinsModel.class);
                    if (usersModel.getBins() != null) {
                        for (int i = 0; i < usersModel.getBins().size(); i++) {
                            assert binsModel != null;
                            int binID = binsModel.getBinId();
                            int userBinID = usersModel.getBins().get(i);
                            if (binID == userBinID) {
                                binsModels.add(binsModel);
                            }
                        }
                    }
                }
                mAdapter = new BinsAdapter(binsModels);
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new BinsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(Employee.this, MapActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*mAdapter.setOnItemClickListener(new EmpBinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Employee.this, MapActivity.class);
                startActivity(intent);
            }
        });*/
        textViewUsername = findViewById(R.id.textView_userName);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                userName = null;
            } else {
                userName = extras.getString("UserName");
            }
        } else {
            userName = (String) savedInstanceState.getSerializable("UserName");
        }
        textViewUsername.setText(userName);
    }


    @Override
    public void onBackPressed() {

        if (!backPressedTime) {
            Toast.makeText(this, "press again", Toast.LENGTH_SHORT).show();
            backPressedTime = true;
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                backPressedTime = false;
            }
        }.start();
    }

}
