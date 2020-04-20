package com.example.gmisproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class explicit_star_rating_activity extends AppCompatActivity {
    private float rating_bin;
    private FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starforrating_activity);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        ImageView imageviewbackfromratingpage = findViewById(R.id.image_view_backfrom_ratingpage);
        imageviewbackfromratingpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(explicit_star_rating_activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Button  buttonsendcompliants = findViewById(R.id.button_send_complaints);
        final RatingBar ratingbarexplicit = findViewById(R.id.explicit_Rating_bar_id);
        ratingbarexplicit.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                int ratingresult = (int) Math.round(rating);
                String rate = String.valueOf(ratingresult);
                // if (firebaseAuth.getCurrentUser() != null) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String username = email.substring(0, email.indexOf("@"));
                Toast.makeText(explicit_star_rating_activity.this, "your rate is " + String.valueOf(rate), Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(email );
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username").setValue(username );
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("rate").setValue(rate );

            }
        });
        final EditText edittextinputcompliants = findViewById(R.id.edittext_view_addnotes);
        buttonsendcompliants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if (firebaseAuth.getCurrentUser() != null) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String username = email.substring(0, email.indexOf("@"));
                final EditText edittextinputcompliants = findViewById(R.id.edittext_view_addnotes);
                String report = edittextinputcompliants.getText().toString();
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(email );
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("username").setValue(username );
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("report").setValue(report );

            }
        });
    }
}


