package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registeration extends AppCompatActivity {
    static final int GOOGLE_SIGN_IN = 123;
    private static final String TAG = "GoogleActivity";
    Button buttonSignUp, buttonSignIn;
    TextInputLayout editTextEmail, editTextPassword;
    FirebaseAuth firebaseAuth;
    ImageView facebookLogin, googleLogin;
    BottomSheetDialog bottomSheetDialog;
    GoogleSignInClient mGoogleSignInClient;
    String type, userName;
    UsersModel usersModel;
    ProgressBar progressBarLoading;
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
                googleLogin = bottomSheetDialog.findViewById(R.id.gmail_login);
                progressBarLoading = findViewById(R.id.progress_loading);

                //get instance from firebase
                firebaseAuth = FirebaseAuth.getInstance();

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
                        progressBarLoading.setVisibility(View.VISIBLE);
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBarLoading.setVisibility(View.INVISIBLE);
                                    check();
                                } else {
                                    progressBarLoading.setVisibility(View.INVISIBLE);
                                    editTextEmail.getEditText().setText("");
                                    editTextPassword.getEditText().setText("");
                                }
                            }
                        });

                    }
                });

                // Configure Google Sign In
                final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(Registeration.this, gso);

                googleLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SignInGoogle();
                    }
                });
            }
        });
    }

    public void check() {
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userNameSnapshot : dataSnapshot.getChildren()) {
                            usersModel = userNameSnapshot.getValue(UsersModel.class);
                            if (usersModel != null) {
                                userName = usersModel.getUsername();
                                type = usersModel.getType();
                            }
                        }

                        if (type.equals("عميل")) {
                            Intent intent = new Intent(Registeration.this, MainActivity.class);
                            intent.putExtra("UserName", userName);
                            startActivity(intent);
                            // Writing Shared Preference User Login Status
                            preferencesConfig.writeUserLoginStatus(true);
                            finish();
                        } else if (type.equals("عامل")) {
                            Intent intent = new Intent(Registeration.this, Employee.class);
                            intent.putExtra("UserName", userName);
                            startActivity(intent);
                            // Writing Shared Preference Worker Login Status
                            preferencesConfig.writeWorkerLoginStatus(true);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    public void checkGoogleLogin() {
        FirebaseDatabase.getInstance().getReference().child("Users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot userNameSnapshot : dataSnapshot.getChildren()) {
                            usersModel = userNameSnapshot.getValue(UsersModel.class);
                            if (usersModel != null) {
                                userName = usersModel.getUsername();
                                type = usersModel.getType();
                            }
                        }
                        if (type.equals("عميل")) {
                            Intent intent = new Intent(Registeration.this, MainActivity.class);
                            startActivity(intent);
                            // Writing Shared Preference User Login Status
                            preferencesConfig.writeUserLoginStatus(true);
                            finish();
                        } else if (type.equals("عامل")) {
                            Intent intent = new Intent(Registeration.this, Employee.class);
                            startActivity(intent);
                            // Writing Shared Preference Worker Login Status
                            preferencesConfig.writeWorkerLoginStatus(true);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

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
                    checkGoogleLogin();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            userInformationToMainActivity(personName, personEmail);
        }
    }

    private void userInformationToMainActivity(String personName, String personEmail) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                .putExtra("username", personName).putExtra("email", personEmail));
        this.finish();
    }


}
