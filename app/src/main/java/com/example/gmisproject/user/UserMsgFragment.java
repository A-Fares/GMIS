package com.example.gmisproject.user;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    DatabaseReference reference;

    public UserMsgFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user_msg, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerViewMessages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<UserMsgModel> msgModelList = new ArrayList<UserMsgModel>();
        msgModelList.add(new UserMsgModel(REQUEST_RESPONSE, getResources().getString(R.string.msg_response), "012245555", " 50 جنيه"));
        msgModelList.add(new UserMsgModel(COMPLAINING_RESPONSE, getResources().getString(R.string.complaint_response)));
        msgModelList.add(new UserMsgModel(COMPLAINING_RESPONSE, getResources().getString(R.string.message_maintenance_success)));


        UserMsgAdapter adapter = new UserMsgAdapter(msgModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
    }

}