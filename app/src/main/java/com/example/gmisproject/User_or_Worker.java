package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class User_or_Worker extends AppCompatActivity {

    Button buttonUser, buttonWorker;
    FirebaseAuth firebaseAuth;
    Task<Void> databaseReference;
    String email, username, type;
    //   SharedPreferencesConfig preferencesConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_or__worker);

/*
        // Reading Shared Preference User && Worker Login Status

        preferencesConfig=new SharedPreferencesConfig(getApplicationContext());

        if (preferencesConfig.readUserLoginStatus()){
            Intent intent=new Intent(User_or_Worker.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (preferencesConfig.readWorkerLoginStatus()){
            Intent intent=new Intent(User_or_Worker.this,Employee.class);
            startActivity(intent);
            finish();
        }

 */

        buttonUser = findViewById(R.id.btn_user);
        buttonWorker = findViewById(R.id.btn_worker);

        username = getIntent().getExtras().getString("username");
        email = getIntent().getExtras().getString("email");

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUser = new Intent(User_or_Worker.this, MainActivity.class);
                startActivity(intentUser);
                /*
                 // Writing Shared Preference User Login Status
                    preferencesConfig.writeUserLoginStatus(true);
                   finish();

                 */

                Users user = new Users(email, username, type = "عميل");
                if (FirebaseAuth.getInstance().getUid() != null) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(User_or_Worker.this, "User created...", Toast.LENGTH_SHORT).show();
                        /*            Intent intentUser=new Intent(User_or_Worker.this,MainActivity.class);
                                    startActivity(intentUser);*/
                                    } else {
                                        Toast.makeText(User_or_Worker.this, "error.....", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        buttonWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Users user = new Users(email, username, type = "عامل");
                if (FirebaseAuth.getInstance().getUid() != null) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(User_or_Worker.this, "Employee created...", Toast.LENGTH_SHORT).show();
                                        Intent intentWorker = new Intent(User_or_Worker.this, Employee.class);
                                        startActivity(intentWorker);
                                      /*
                                        // Writing Shared Preference Worker Login Status
                                        preferencesConfig.writeWorkerLoginStatus(true);
                                        finish();


                                       */

                                    } else {
                                        Toast.makeText(User_or_Worker.this, "error.....", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }
}

