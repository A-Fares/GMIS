package com.example.gmisproject.user;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmisproject.BinsModel;
import com.example.gmisproject.R;
import com.example.gmisproject.UsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserBinFragment extends Fragment {
    private ArrayList<Integer> BinsData;
    private UsersModel usersModel;
    private BinsModel binsModel;
    private View binAlertLayout;
    private ArrayList<BinsModel> binsModels;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    public UserBinFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user_bin, container, false);

        binsModels = new ArrayList<BinsModel>();
        binAlertLayout=rootView.findViewById(R.id.alert_bin_layout);

        recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        checkUserBins();
        return rootView;
    }

    private void checkUserBins() {
        //reading the Bin id that user have
        DatabaseReference referenceBins = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        referenceBins.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersModel = dataSnapshot.getValue(UsersModel.class);
                assert usersModel != null;
                BinsData = usersModel.getBins();
                if (BinsData == null){
                    recyclerView.setVisibility(View.GONE);
                    binAlertLayout.setVisibility(View.VISIBLE);
                }else {
                    checkBinsData();
                }
                    Log.v("Binns", "bins == " + BinsData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //      Toast.makeText(getActivity().getApplicationContext(), "something went wrong ..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkBinsData() {
        // searching for the data of bin that user have
        DatabaseReference referenceBinData = FirebaseDatabase.getInstance().getReference().child("Bins");
        referenceBinData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binsModels.clear();
                for (DataSnapshot binSnapshot : dataSnapshot.getChildren()) {
                    binsModel = binSnapshot.getValue(BinsModel.class);
                    if (usersModel != null) {
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
                mAdapter = new UserBinAdapter(binsModels);
                recyclerView.setAdapter(mAdapter);

                Log.v("Binnss", "bins sssss");
                /*mAdapter.setOnItemClickListener(new BinsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(), MapActivity.class);
                        startActivity(intent);
                    }
                });*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
