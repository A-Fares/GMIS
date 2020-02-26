package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_up extends AppCompatActivity {

    TextInputLayout editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword;
    Button buttonSignUp;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        radioGroup = findViewById(R.id.radio_group_user_type);

        TextView signIn = findViewById(R.id.text_view_sign_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Sign_up.this, Registeration.class);
                startActivity(intent);

            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        editTextUsername = findViewById(R.id.edit_text_username_sign_up);
        editTextEmail = findViewById(R.id.edit_text_email_sign_up);
        editTextPassword = findViewById(R.id.edit_text_password_sign_up);
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password_sign_up);
        buttonSignUp = findViewById(R.id.btn_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = editTextUsername.getEditText().getText().toString();
                final String email = editTextEmail.getEditText().getText().toString();
                final String password = editTextPassword.getEditText().getText().toString();
                final String confirmPassword = editTextConfirmPassword.getEditText().getText().toString();


                if (username.isEmpty()) {
                    editTextUsername.setError("name required");
                    editTextUsername.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    editTextEmail.setError("email required");
                    editTextEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    editTextPassword.setError("password required");
                    editTextPassword.requestFocus();
                    return;
                }
                if (!(confirmPassword.equals(password))){
                    editTextConfirmPassword.setError("password not match");
                    editTextConfirmPassword.requestFocus();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Users users = new Users(username, email, password);

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Sign_up.this, "User created...", Toast.LENGTH_SHORT).show();
                                    } else {

                                    }
                                }
                            });

                            Toast.makeText(Sign_up.this, "User created...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Sign_up.this, MainActivity.class);
                            String string = email.substring(0,email.indexOf("@"));
                            intent.putExtra("UserName", string);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Sign_up.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "نوع الحساب: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}
