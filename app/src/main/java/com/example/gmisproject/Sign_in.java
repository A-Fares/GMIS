package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sign_in extends AppCompatActivity {

    TextView signUp;
    Button buttonSignIn;
    TextInputLayout editTextUsername, editTextPassword;
    String string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextUsername = findViewById(R.id.edit_text_username_sign_in);
        editTextPassword = findViewById(R.id.edit_text_password_sign_in);
        buttonSignIn = findViewById(R.id.btn_sign_in);
        signUp = findViewById(R.id.text_view_sign_up);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_in.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            String password;

            @Override
            public void onClick(View v) {
                String username = editTextUsername.getEditText().getText().toString();
                password = editTextPassword.getEditText().getText().toString();


                myRef.child(username).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Users users = dataSnapshot.getValue(Users.class);
                        if (password.equals(users.getPassword())) {
                            Toast.makeText(Sign_in.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Sign_in.this, MainActivity.class);
                            string=editTextUsername.getEditText().getText().toString();
                            intent.putExtra("UserName",string);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Sign_in.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }

        });
    }
}
