package com.example.gmisproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class explicit_star_activity extends AppCompatActivity {
    private float rating_bin;
    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference reference = database.getReference("Rating");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit_star_activity);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Rating");
        RatingBar ratingbarexplicit = findViewById(R.id.explicit_Rating_bar_id);
        ratingbarexplicit.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
                String curretuserfirebase = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //String user = mAuth.getCurrentUser().getUid();
                rating_bin = rating;
                String Rate = String.valueOf(rating);
                //user.setRating(Rate);
                reference.child("rating value").setValue(Rate);
                reference.child("user name").setValue(curretuserfirebase);
                Toast.makeText(explicit_star_activity.this, "your rate is " + String.valueOf(rating_bin), Toast.LENGTH_LONG).show();
            }
        });
        final EditText edittextinput = findViewById(R.id.edittext_view_addnotes);
        final DatabaseReference secondreference = database.getReference("Reports");
        edittextinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addnotes = edittextinput.getText().toString();
                secondreference.child("add notes").setValue(addnotes);
                String curretuserfirebase = FirebaseAuth.getInstance().getCurrentUser().getUid();
                secondreference.child("user name").setValue(curretuserfirebase);
            }
        });
    }
}
