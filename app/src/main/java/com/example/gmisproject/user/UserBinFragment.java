package com.example.gmisproject.user;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmisproject.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserBinFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // public static float rating_bin;

    public UserBinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user_bin, container, false);

        ArrayList<UserBin> userBins = new ArrayList<UserBin>();

        userBins.add(new UserBin("10", "كومباوند التجمع الخامس", "تعمل", 70, R.drawable.trash));
        userBins.add(new UserBin("11", "كومباوند التجمع الخامس", "تعمل", 40, R.drawable.trash));
        userBins.add(new UserBin("18", "كومباوند التجمع الخامس", "تعمل", 20, R.drawable.trash));
        userBins.add(new UserBin("25", "كومباوند التجمع الخامس", "تعمل", 80, R.drawable.trash));
        userBins.add(new UserBin("7", "كومباوند التجمع الخامس", "تعمل", 25, R.drawable.trash));

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        UserBinAdapter adapter = new UserBinAdapter(userBins);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

       /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference reference = database.getReference("Rating");

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
        });*/
        return rootView;
    }


}
