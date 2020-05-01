package com.example.gmisproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Employee extends AppCompatActivity {
    DatabaseReference referenceBins, referenceBinData;
    RecyclerView recyclerView;
    ArrayList<Integer> BinsData;
    UsersModel usersModel;
    BinsModel binsModel;
    String userName ,final_location;
    ImageView signOut;
    TextView textViewUsername;
    View binAlertLayout;
    //SharedPreferencesConfig preferencesConfig;
    ArrayList<BinsModel> binsModels;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String profilePicture;
    CircleImageView imageViewProfilePicture;
    private long backPressedTime;
    private Toast backToast;
    private BinsAdapter mAdapter;
    private SharedPreferences preferencesConfig;
    private FirebaseAuth firebaseAuth;
    private RecyclerView.LayoutManager mLayoutManager;
    private Bundle savedInstanceState;

    public Employee() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        textViewUsername = findViewById(R.id.textView_userName);
        imageViewProfilePicture = findViewById(R.id.user_logo);
        user = FirebaseAuth.getInstance().getCurrentUser();
        //set image profile
        if (user.getPhotoUrl() != null) {
            profilePicture = user.getPhotoUrl().toString();
            profilePicture += "?type=large";
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            Uri profilePicture = account.getPhotoUrl();
            Log.v("gggg", "url is " + profilePicture);
            Picasso.get().load(profilePicture).fit().placeholder(R.drawable.user_logo).into(imageViewProfilePicture);
            //    Glide.with(this).load(String.valueOf(profilePicture)).placeholder(R.drawable.user_logo).into(imageViewProfilePicture);
        }
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child(userId);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue().toString();
                textViewUsername.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        signOut = findViewById(R.id.sign_out);
        textViewUsername = findViewById(R.id.textView_userName);

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        final GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Employee.this);
                dialog.setContentView(R.layout.alertdialogsignoutemp);
                dialog.setCancelable(false);
                dialog.show();
                TextView textviewsignoutyes = dialog.findViewById(R.id.text_view_yesfor_signout_emp);
                TextView textviewsignoutno = dialog.findViewById(R.id.text_view_no_for_signout_emp);
                textviewsignoutyes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        firebaseAuth.getInstance().signOut();

                        preferencesConfig = getSharedPreferences(getResources().getString(R.string.login_preferences_user), MODE_PRIVATE);
                        preferencesConfig.edit().clear().commit();
                        Intent intent = new Intent(Employee.this, Registeration.class);
                        startActivity(intent);
                        mGoogleSignInClient.signOut();

                    }
                });
                textviewsignoutno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        binAlertLayout = findViewById(R.id.alert_bin_layout);
        binsModels = new ArrayList<BinsModel>();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);

        getUserBins();
        getBinsData();

        /*mAdapter.setOnItemClickListener(new EmpBinAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Employee.this, MapActivity.class);
                startActivity(intent);
            }
        });*/

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                userName = null;
            } else {
                userName = extras.getString("UserName");
            }
        } else {
            userName = (String) savedInstanceState.getSerializable("UserName");
        }
        textViewUsername.setText(userName);

        Log.v("Name", "Name == " + userName);
    }

    private void getUserBins() {
        //reading the Bin id that user have
        referenceBins = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceBins.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersModel = dataSnapshot.getValue(UsersModel.class);
                assert usersModel != null;
                BinsData = usersModel.getBins();
                if (BinsData == null) {
                    recyclerView.setVisibility(View.GONE);
                    binAlertLayout.setVisibility(View.VISIBLE);
                } else {
                    getBinsData();
                }
                Log.v("Binns", "bins == " + BinsData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Employee.this, "something went wrong ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getBinsData() {
        // searching for the data of bin that user have
        referenceBinData = FirebaseDatabase.getInstance().getReference().child("Bins");
        referenceBinData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binsModels.clear();
                for (DataSnapshot binSnapshot : dataSnapshot.getChildren()) {
                    binsModel = binSnapshot.getValue(BinsModel.class);
                    if (usersModel != null && usersModel.getBins() != null) {
                        BinsData = usersModel.getBins();
                        for (int i = 0; i < BinsData.size(); i++) {
                            assert binsModel != null;
                            int binID = binsModel.getBinId();
                            int userBinID = usersModel.getBins().get(i);
                            if (binID == userBinID) {
                                binsModels.add(binsModel);
                            }
                        }
                    }
                }
                mAdapter = new BinsAdapter(binsModels);
                recyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new BinsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        String position1 = String.valueOf(position+1);
                        DatabaseReference  reference_location = FirebaseDatabase.getInstance().getReference("Bins");
                        DatabaseReference reference_location1 = reference_location.child(position1);
                        DatabaseReference final_ref_location = reference_location1.child("location");

                        final_ref_location.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final_location = String.valueOf(dataSnapshot.getValue());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Intent intent = new Intent();

                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("google.navigation:q="+final_location));
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Please Press Again", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
