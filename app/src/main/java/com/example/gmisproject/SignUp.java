package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class SignUp extends AppCompatActivity {
    static final int GOOGLE_SIGN_IN = 123;
    private static final String TAG = "GoogleActivity";
    private static final String TAG2 = "FacebookActivity";
    CallbackManager mCallbackManager;
    FirebaseUser user;
    TextInputLayout inputLayoutUsername, inputLayoutEmail, inputLayoutPassword, inputLayoutConfirmPassword;
    Button buttonSignUp, buttonSignIn;
    RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseAuth firebaseAuth;
    ImageView googleSignUp, facebookSignUp;
    GoogleSignInClient mGoogleSignInClient;
    ProgressBar progressBarLoading;
    String type, userName;
    SharedPreferencesConfig preferencesConfig;

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
        facebookSignUp = findViewById(R.id.facebook_login);
        inputLayoutUsername = findViewById(R.id.inputLayout_username);
        inputLayoutEmail = findViewById(R.id.inputLayout_email);
        inputLayoutPassword = findViewById(R.id.inputLayout_password);
        inputLayoutConfirmPassword = findViewById(R.id.inputLayout_confirmPassword);
        buttonSignUp = findViewById(R.id.btn_sign_up);
        progressBarLoading = findViewById(R.id.progress_loading);

        // Reading Shared Preference User && Worker Login Status

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        if (preferencesConfig.readUserLoginStatus()) {
            Intent intent = new Intent(SignUp.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (preferencesConfig.readWorkerLoginStatus()) {
            Intent intent = new Intent(SignUp.this, Employee.class);
            startActivity(intent);
            finish();
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = radioGroup.findViewById(checkedId);
                switch (checkedId) {
                    case R.id.radio_btn_user:
                        type = radioButton.getText().toString();
                        break;
                    case R.id.radio_btn_worker:
                        type = radioButton.getText().toString();
                        break;
                }
            }
        });


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
                            UsersModel usersModel = new UsersModel(email, username, type);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(usersModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "User created...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            check();
                            if (type.equals("عميل")) {
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intenttips = new Intent(SignUp.this, tips.class);
                                        startActivity(intenttips);

                                    }
                                };
                                Handler handler = new Handler();
                                handler.postDelayed(runnable, 0);
                            }

                        } else {
                            Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        mCallbackManager = CallbackManager.Factory.create();
        facebookSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(SignUp.this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Log.d(TAG2, "facebook:onCancel");
                        // ...
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG2, "facebook:onError", error);
                        // ...
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

    public void check() {
        if (type.equals("عميل")) {
            Intent intent = new Intent(SignUp.this, MainActivity.class);
            userName = inputLayoutUsername.getEditText().getText().toString();
            intent.putExtra("UserName", userName);
            startActivity(intent);
            // Writing Shared Preference User Login Status
            preferencesConfig.writeUserLoginStatus(true);
            finish();
        } else {
            Intent intent = new Intent(SignUp.this, Employee.class);
            userName = inputLayoutUsername.getEditText().getText().toString();
            intent.putExtra("UserName", userName);
            startActivity(intent);
            // Writing Shared Preference Worker Login Status
            preferencesConfig.writeWorkerLoginStatus(true);
            finish();
        }
    }

    public void checkButton(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
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
            } else {
                mCallbackManager.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    //Google Authentication
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            String personName = user.getDisplayName();
            String personEmail = user.getEmail();
            userInformationToMainActivity(personName, personEmail);
        }
    }

    // send account information to check them and saving to database
    private void userInformationToMainActivity(String personName, String personEmail) {
        startActivity(new Intent(getApplicationContext(), User_or_Worker.class)
                .putExtra("username", personName).putExtra("email", personEmail));
        this.finish();
    }

    // Facebook Authentication
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG2, "signInWithCredential:success");
                            progressBarLoading.setVisibility(View.INVISIBLE);
                            user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG2, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBarLoading.setVisibility(View.INVISIBLE);
                            updateUI(null);
                        }
                    }
                });
    }
}
