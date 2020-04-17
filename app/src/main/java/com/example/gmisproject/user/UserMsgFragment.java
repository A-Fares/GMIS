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

import static com.example.gmisproject.user.UserMsgModel.COMPLAINT_MESSAGE;
import static com.example.gmisproject.user.UserMsgModel.INFORMATION_MESSAGE;
import static com.example.gmisproject.user.UserMsgModel.MAINTENANCE_MESSAGE;

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
        msgModelList.add(new UserMsgModel(INFORMATION_MESSAGE, "تم إضافة السلات المرغوب فيها برجاء التوجه لفرع الشركة لتأكيد عملية الدفع و تفعيل السلات", "012245555", " 50 جنيه"));
        msgModelList.add(new UserMsgModel(COMPLAINT_MESSAGE, "جاري العمل على شكواكم وسيتم ارسال عربة صيانة في موعد أقصاه يومين"));
        msgModelList.add(new UserMsgModel(MAINTENANCE_MESSAGE, "تم عمل الصيانة وحل جميع مشاكل السلة ", "سعداء بخدمتكم"));


        UserMsgAdapter adapter = new UserMsgAdapter(msgModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                    UserMsgModel userMsgModel = Snapshot.getValue(UserMsgModel.class);

                    Log.v("kkk", "Costs is: " + userMsgModel.getCosts());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}