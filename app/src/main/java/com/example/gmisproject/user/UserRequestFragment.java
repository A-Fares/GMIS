package com.example.gmisproject.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.gmisproject.Employee;
import com.example.gmisproject.R;


public class UserRequestFragment extends Fragment {


    public UserRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user_reqest, container, false);

        Button btn = rootView.findViewById(R.id.button_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Employee.class);
                startActivity(intent);

            }
        });

        return rootView;
    }

}
