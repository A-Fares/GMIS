package com.example.gmisproject.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gmisproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserBinFragment extends Fragment {

    public static float rating_bin;

    public UserBinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // write message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Rating");
        View rootView = inflater.inflate(R.layout.activity_user_bin, container, false);

        final RatingBar ratingBarUserBin = rootView.findViewById(R.id.rating_bar_user_bin);
        ratingBarUserBin.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_bin = rating;
                String Rate = String.valueOf(rating);
                //set values
                reference.child("user name").setValue("user name");
                reference.child("rating value").setValue(Rate);
                Toast.makeText(getActivity().getApplicationContext(), "Your Rate is " + String.valueOf(rating) + " !",Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
