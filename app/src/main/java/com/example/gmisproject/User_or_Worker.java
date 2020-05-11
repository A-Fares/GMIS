package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
    Task<Void> databaseReference;
    String email, username, type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_or__worker);

        buttonUser = findViewById(R.id.btn_user);
        buttonWorker = findViewById(R.id.btn_worker);

        username = getIntent().getExtras().getString("username");
        email = getIntent().getExtras().getString("email");

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tips for guide new users
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        Intent intenttips = new Intent(User_or_Worker.this, tips.class);
                        startActivity(intenttips);

                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(runnable, 0);
                Intent intentUser = new Intent(User_or_Worker.this, MainActivity.class);
                startActivity(intentUser);
                UsersModel user = new UsersModel(email, username, type = "عميل");
                if (FirebaseAuth.getInstance().getUid() != null) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(User_or_Worker.this, "User created...", Toast.LENGTH_SHORT).show();
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

                UsersModel user = new UsersModel(email, username, type = "عامل");
                if (FirebaseAuth.getInstance().getUid() != null) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(User_or_Worker.this, "Employee created...", Toast.LENGTH_SHORT).show();
                                        Intent intentWorker = new Intent(User_or_Worker.this, Employee.class);
                                        startActivity(intentWorker);
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

