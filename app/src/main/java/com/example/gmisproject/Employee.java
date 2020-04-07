package com.example.gmisproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
    ArrayList<Integer> BinsData;
    UsersModel usersModel;
    BinsModel binsModel;
    String userName;
    ImageView signOut;
    TextView textViewUsername;
    View binAlertLayout;
    //SharedPreferencesConfig preferencesConfig;
    ArrayList<BinsModel> binsModels;
    private BinsAdapter mAdapter;
    private SharedPreferences preferencesConfig;
    private FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;


    private RecyclerView.LayoutManager mLayoutManager;
    private Bundle savedInstanceState;

    public Employee() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        signOut = findViewById(R.id.sign_out);
        textViewUsername = findViewById(R.id.textView_userName);
        //preferencesConfig = new SharedPreferencesConfig(getApplicationContext());
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Employee.this);
                dialog.setContentView(R.layout.alertdialogsignoutemp);
                dialog.setCancelable(false);
                dialog.show();
                TextView textviewsignoutyes = dialog.findViewById(R.id.text_view_yesfor_signout_emp);
                TextView textviewsignoutno = dialog.findViewById(R.id.text_view_no_for_signout_emp);
                textviewsignoutyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebaseAuth.getInstance().signOut();

                        preferencesConfig = getSharedPreferences(getResources().getString(R.string.login_preferences_user),MODE_PRIVATE);
                        preferencesConfig.edit().clear().commit();
                        Intent intent = new Intent(Employee.this, Registeration.class);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        //Shared Preference Worker Logout
                        //preferencesConfig.writeWorkerLoginStatus(false);
                        mGoogleSignInClient.signOut();

                    }
                });
                textviewsignoutno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        binAlertLayout = findViewById(R.id.alert_bin_layout);
        binsModels = new ArrayList<BinsModel>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        getUserBins();
        getBinsData();

        /*mAdapter.setOnItemClickListener(new EmpBinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Employee.this, MapActivity.class);
                startActivity(intent);
            }
        });*/

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

        Log.v("Name", "Name == " + userName);
    }

    private void getUserBins() {
        //reading the Bin id that user have
        referenceBins = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceBins.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersModel = dataSnapshot.getValue(UsersModel.class);
                assert usersModel != null;
                BinsData = usersModel.getBins();
                if (BinsData == null) {
                    recyclerView.setVisibility(View.GONE);
                    binAlertLayout.setVisibility(View.VISIBLE);
                } else {
                    getBinsData();
                }
                Log.v("Binns", "bins == " + BinsData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Employee.this, "something went wrong ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBinsData() {
        // searching for the data of bin that user have
        referenceBinData = FirebaseDatabase.getInstance().getReference().child("Bins");
        referenceBinData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binsModels.clear();
                for (DataSnapshot binSnapshot : dataSnapshot.getChildren()) {
                    binsModel = binSnapshot.getValue(BinsModel.class);
                    if (usersModel != null && usersModel.getBins() != null) {
                        BinsData = usersModel.getBins();
                        for (int i = 0; i < BinsData.size(); i++) {
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
