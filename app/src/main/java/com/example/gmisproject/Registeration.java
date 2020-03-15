package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registeration extends AppCompatActivity {
    static final int GOOGLE_SIGN_IN = 123;
    private static final String TAG = "GoogleActivity";
    Button buttonSignUp, buttonSignIn;
    TextInputLayout editTextEmail, editTextPassword;
    FirebaseAuth firebaseAuth;
    ImageView facebookLogin, gmailLogin;
    BottomSheetDialog bottomSheetDialog;
    GoogleSignInClient mGoogleSignInClient;
    String type;
    DatabaseReference ref;
    SharedPreferencesConfig preferencesConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        // Reading Shared Preference User && Worker Login Status

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        if (preferencesConfig.readUserLoginStatus()) {
            Intent intent = new Intent(Registeration.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (preferencesConfig.readWorkerLoginStatus()) {
            Intent intent = new Intent(Registeration.this, Employee.class);
            startActivity(intent);
            finish();
        }


        buttonSignIn = findViewById(R.id.btn_sign_in_bottom);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog = new BottomSheetDialog(Registeration.this);
                bottomSheetDialog.setContentView(R.layout.bottomsheet_login);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                bottomSheetDialog.show();


                editTextEmail = bottomSheetDialog.findViewById(R.id.inputLayout_email);
                editTextPassword = bottomSheetDialog.findViewById(R.id.inputLayout_password);
                buttonSignIn = bottomSheetDialog.findViewById(R.id.btn_sign_in);
                buttonSignUp = bottomSheetDialog.findViewById(R.id.btn_signup);
                facebookLogin = bottomSheetDialog.findViewById(R.id.facebook_login);
                gmailLogin = bottomSheetDialog.findViewById(R.id.gmail_login);

                facebookLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Registeration.this, SignUp.class);
                        startActivity(intent);
                    }
                });

                buttonSignUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Registeration.this, SignUp.class);
                        startActivity(intent);
                    }
                });

                buttonSignIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String email = editTextEmail.getEditText().getText().toString().trim();
                        String password = editTextPassword.getEditText().getText().toString().trim();


                        if (email.isEmpty()) {
                            editTextEmail.setError("Enter your email");
                            editTextEmail.requestFocus();
                            return;
                        }
                        if (password.isEmpty()) {
                            editTextPassword.setError("Enter your password");
                            editTextPassword.requestFocus();
                            return;
                        }

                        //get instance from firebase

                        firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    check();
                                } else {
                                    Toast.makeText(getApplicationContext(), "UnSuccessful", Toast.LENGTH_SHORT).show();
                                    editTextEmail.getEditText().setText("");
                                    editTextPassword.getEditText().setText("");
                                }
                            }
                        });
                    }
                });

            }
        });
    }

    public void check() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                type = dataSnapshot.getValue().toString().trim();
                String email = editTextEmail.getEditText().getText().toString();
                Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
                if (type.equals("عميل")) {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registeration.this, MainActivity.class);
                    String string = email.substring(0, email.indexOf("@"));
                    intent.putExtra("UserName", string);
                    startActivity(intent);
                    // Writing Shared Preference User Login Status
                    preferencesConfig.writeUserLoginStatus(true);
                    finish();
                } else if (type.equals("عامل")) {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registeration.this, Employee.class);
                    startActivity(intent);
                    // Writing Shared Preference Worker Login Status
                    preferencesConfig.writeWorkerLoginStatus(true);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registeration.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
