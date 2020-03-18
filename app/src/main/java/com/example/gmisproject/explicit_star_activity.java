package com.example.gmisproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class explicit_star_activity extends AppCompatActivity {
    private float rating_bin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit_star_activity);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ImageView imageviewbackfromratingpage = findViewById(R.id.image_view_backfrom_ratingpage);
        imageviewbackfromratingpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(explicit_star_activity.this,MainActivity.class);
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
                final EditText edittextinput = findViewById(R.id.edittext_view_addnotes);
                String report = edittextinput.getText().toString();
                UsersModel ratingusers = new UsersModel(username,email,rate,report);
                Toast.makeText(explicit_star_activity.this, "your rate is " + String.valueOf(rate), Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(ratingusers);
                //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                //String curretuserfirebase = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //String user = mAuth.getCurrentUser().getUid();
                //rating_bin = rating;
                //String Rate = String.valueOf(rating);
                //user.setRating(Rate);
                //Toast.makeText(explicit_star_activity.this, "your rate is " + String.valueOf(rating_bin), Toast.LENGTH_LONG).show();
            }
        });
        final EditText edittextinput = findViewById(R.id.edittext_view_addnotes);
        buttonsendcompliants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ratingresult = (int) Math.round(ratingbarexplicit.getRating());
                String rate = String.valueOf(ratingresult);
                // if (firebaseAuth.getCurrentUser() != null) {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String username = email.substring(0, email.indexOf("@"));
                final EditText edittextinput = findViewById(R.id.edittext_view_addnotes);
                String report = edittextinput.getText().toString();
                UsersModel ratingusers = new UsersModel(username,email,rate,report);
                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(ratingusers);
            }
        });
    }
}


