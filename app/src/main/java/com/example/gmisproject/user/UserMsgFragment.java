package com.example.gmisproject.user;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmisproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.gmisproject.user.UserMsgModel.COMPLAINING_RESPONSE;
import static com.example.gmisproject.user.UserMsgModel.REQUEST_RESPONSE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserMsgFragment extends Fragment {
    //DatabaseReference reference;
    private String complaintMsg;
    UserMsgModel userMsgModel ;
    public UserMsgFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user_msg, container, false);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userMsgModel = new UserMsgModel();
        DatabaseReference myRef = database.getReference("Responses");
        final RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewMessages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        final List<UserMsgModel> msgModelList = new ArrayList<UserMsgModel>();
        msgModelList.add(new UserMsgModel(REQUEST_RESPONSE, getResources().getString(R.string.msg_response), "012245555", " 50 جنيه"));
       // msgModelList.add(new UserMsgModel(COMPLAINING_RESPONSE, getResources().getString(R.string.complaint_response)));
        msgModelList.add(new UserMsgModel(COMPLAINING_RESPONSE, getResources().getString(R.string.message_maintenance_success)));
 myRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
            UserMsgModel userMsgModel = postSnapshot.getValue(UserMsgModel.class);
            msgModelList.add(userMsgModel);

            //Toast.makeText(getActivity(), msgModelList.get(0).getAddress() , Toast.LENGTH_LONG).show();
        }
        UserMsgAdapter adapter = new UserMsgAdapter(msgModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Toast.makeText(getActivity(),String.valueOf(msgModelList.get(3).getViewType()), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

        //UserMsgAdapter adapter = new UserMsgAdapter(msgModelList);
        //recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        return rootView;
    }

}