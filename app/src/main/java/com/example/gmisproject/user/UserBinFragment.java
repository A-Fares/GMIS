package com.example.gmisproject.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gmisproject.MainActivity;
import com.example.gmisproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserBinFragment extends Fragment {

    public static float rating_bin ;

    public UserBinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user_bin, container, false);

        final RatingBar ratingBarUserBin = rootView.findViewById(R.id.rating_bar_user_bin);
        ratingBarUserBin.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rating_bin = rating ;
            }
        });

        return rootView;
    }

}
