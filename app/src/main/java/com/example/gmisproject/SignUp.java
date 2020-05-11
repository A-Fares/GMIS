package com.example.gmisproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.Arrays;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class SignUp extends AppCompatActivity {
    static final int GOOGLE_SIGN_IN = 123;
    private static final String TAG = "GoogleActivity";
    private static final String TAG2 = "FacebookActivity";
    CallbackManager mCallbackManager;
    FirebaseUser user;
    TextInputLayout inputLayoutUsername, inputLayoutEmail, inputLayoutPassword, inputLayoutConfirmPassword;
    Button  buttonSignIn;
    FirebaseAuth firebaseAuth;
    ImageView googleSignUp, facebookSignUp;
    GoogleSignInClient mGoogleSignInClient;
    String userName, userEmail;
    ImageView imageViewUserPhotoProfile;
    CircularProgressButton buttonSignUp ;
    ProgressBar progressBaranimationLoading ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //  Views
        buttonSignIn = findViewById(R.id.btn_sign_in);
        googleSignUp = findViewById(R.id.gmail_login);
        facebookSignUp = findViewById(R.id.facebook_login);
        inputLayoutUsername = findViewById(R.id.inputLayout_username);
        inputLayoutEmail = findViewById(R.id.inputLayout_email);
        inputLayoutPassword = findViewById(R.id.inputLayout_password);
        inputLayoutConfirmPassword = findViewById(R.id.inputLayout_confirmPassword);
        buttonSignUp = findViewById(R.id.btn_sign_up);
        progressBaranimationLoading = findViewById(R.id.spin_kit);
        imageViewUserPhotoProfile = findViewById(R.id.user_logo);


        //get instance from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        configGoogleSignIn();
        //on Click the logo of G-mail
        googleSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogle();
            }
        });
        //on Click the Sign Up Button
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignUP();
            }
        });
        //on Click the logo of Facebook
        mCallbackManager = CallbackManager.Factory.create();
        facebookSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSignUp(mCallbackManager);
            }
        });
        //Go to SignIn Activity
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Registration.class);
                startActivity(intent);
            }
        });
    }

    private void facebookSignUp(CallbackManager mCallbackManager) {
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

    private void configGoogleSignIn() {
        // Configure Google Sign In
        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void startSignUP() {
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
        progressBaranimationLoading.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBaranimationLoading.setVisibility(View.GONE);
                    user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    progressBaranimationLoading.setVisibility(View.GONE);
                    updateUI(null);
                }
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //implement button signup
                AsyncTask<String, String, String> demoLogin = new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return "done";
                    }


                };

                buttonSignUp.startAnimation();
                demoLogin.execute();
            }

        });

    }

    void SignInGoogle() {
        //set progressbar visible
        progressBaranimationLoading.setVisibility(View.VISIBLE);
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
                    //set progressbar gone
                    progressBaranimationLoading.setVisibility(View.GONE);
                    user = firebaseAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    //set progressbar gone
                    progressBaranimationLoading.setVisibility(View.GONE);
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            userName = user.getDisplayName();
            if (user.getDisplayName() == null) {
                userName = inputLayoutUsername.getEditText().getText().toString();
            }
            userEmail = user.getEmail();
            userInformationToMainActivity(userName, userEmail);
        }
    }

    // send account information to check them and saving to database
    private void userInformationToMainActivity(String personName, String personEmail) {
        startActivity(new Intent(getApplicationContext(), User_or_Worker.class)
                .putExtra("username", personName).putExtra("email", personEmail));
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
                            progressBaranimationLoading.setVisibility(View.GONE);
                            user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG2, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressBaranimationLoading.setVisibility(View.GONE);
                            updateUI(null);
                        }
                    }
                });
    }
}
