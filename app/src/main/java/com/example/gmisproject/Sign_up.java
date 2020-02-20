package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up extends AppCompatActivity {

    TextInputLayout editTextUsername,editTextEmail,editTextPassword,editTextConfirmPassword;
    Button buttonSignUp;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        TextView signIn = findViewById(R.id.text_view_sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Sign_up.this, Sign_in.class);
                startActivity(intent);

            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        editTextUsername=findViewById(R.id.edit_text_username_sign_up);
        editTextEmail=findViewById(R.id.edit_text_email_sign_up);
        editTextPassword=findViewById(R.id.edit_text_password_sign_up);
        editTextConfirmPassword=findViewById(R.id.edit_text_confirm_password_sign_up);
        buttonSignUp=findViewById(R.id.btn_sign_up);

        users=new Users();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = editTextUsername.getEditText().getText().toString();
                String email=editTextEmail.getEditText().getText().toString();
                String password=editTextPassword.getEditText().getText().toString();

                buttonSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        users.setUsername(editTextUsername.getEditText().getText().toString());
                        users.setEmail(editTextEmail.getEditText().getText().toString());
                        users.setPassword(editTextPassword.getEditText().getText().toString());

                        myRef.child(users.getUsername()).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Sign_up.this, "User created...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Sign_up.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Sign_up.this, "Failed...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });


    }
}
