package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    static final int GOOGLE_SIGN_IN = 123;
    private static final String TAG = "GoogleActivity";
    TextInputLayout inputLayoutUsername, inputLayoutEmail, inputLayoutPassword, inputLayoutConfirmPassword;
    Button buttonSignUp, buttonSignIn;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth firebaseAuth;
    ImageView googleSignUp;
    GoogleSignInClient mGoogleSignInClient;
    ProgressBar progressBarLoading;

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
        //  Views
        radioGroup = findViewById(R.id.radio_group_user_type);
        buttonSignIn = findViewById(R.id.btn_sign_in);
        googleSignUp = findViewById(R.id.gmail_login);
        inputLayoutUsername = findViewById(R.id.inputLayout_username);
        inputLayoutEmail = findViewById(R.id.inputLayout_email);
        inputLayoutPassword = findViewById(R.id.inputLayout_password);
        inputLayoutConfirmPassword = findViewById(R.id.inputLayout_confirmPassword);
        buttonSignUp = findViewById(R.id.btn_sign_up);
        progressBarLoading = findViewById(R.id.progress_loading);


        //get instance from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //on Click the logo of G-mail
        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogle();
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Users");

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = inputLayoutUsername.getEditText().getText().toString();
                final String email = inputLayoutEmail.getEditText().getText().toString();
                final String password = inputLayoutPassword.getEditText().getText().toString();
                final String confirmPassword = inputLayoutConfirmPassword.getEditText().getText().toString();

                if (username.isEmpty()) {
                    inputLayoutUsername.setError("برجاء ادخال اسم المستخدم");
                    inputLayoutUsername.requestFocus();
                    return;
                }
                if (email.isEmpty()) {
                    inputLayoutEmail.setError("برجاء ادخال البريد الإلكتروني");
                    inputLayoutEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    inputLayoutEmail.setError("ادخل بريد إلكتروني صحيح");
                    inputLayoutEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    inputLayoutPassword.setError("برجاء ادخال كلمة السر");
                    inputLayoutPassword.requestFocus();
                    return;
                }
                if (!(confirmPassword.equals(password))) {
                    inputLayoutConfirmPassword.setError("كلمة السر غير متطابقة");
                    inputLayoutConfirmPassword.requestFocus();
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
                                        Toast.makeText(SignUp.this, "User created...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            Toast.makeText(SignUp.this, "User created...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            String string = email.substring(0, email.indexOf("@"));
                            intent.putExtra("UserName", string);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Registeration.class);
                startActivity(intent);
            }
        });
    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this, "نوع الحساب: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    void SignInGoogle() {
        progressBarLoading.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == GOOGLE_SIGN_IN) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(TAG, "Google sign in failed", e);
                }
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Firebase Success", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                    updateUI(user);
                } else {
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Firebase Failed", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            sendUserToWelcomeActivity(personName);
        }
    }

    private void sendUserToWelcomeActivity(String personName) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                .putExtra("UserName", personName));
        this.finish();
    }
}