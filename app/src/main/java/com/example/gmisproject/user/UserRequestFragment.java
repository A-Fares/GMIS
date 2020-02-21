package com.example.gmisproject.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.gmisproject.Employee;
import com.example.gmisproject.R;
import com.example.gmisproject.UsersRequests;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserRequestFragment extends Fragment {


    public UserRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_user_reqest, container, false);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Requests");

       // myRef.setValue("Hello, World!");

        final TextInputLayout fullName = rootView.findViewById(R.id.edit_full_name);
        final TextInputLayout userMail = rootView.findViewById(R.id.edit_user_mail);
        final TextInputLayout  Address = rootView.findViewById(R.id.edit_address);
        final TextInputLayout homeNumber = rootView.findViewById(R.id.edit_home_number);
        final TextInputLayout phoneNumber = rootView.findViewById(R.id.edit_phone_number);


        final UsersRequests usersRequests = new UsersRequests();

        Button btn = rootView.findViewById(R.id.button_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Employee.class);
                startActivity(intent);

                String fullname = fullName.getEditText().getText().toString();
                String usermail = userMail.getEditText().getText().toString();
                String address = Address.getEditText().getText().toString();
                String homenumber = homeNumber.getEditText().getText().toString();
                String phonenumber = phoneNumber.getEditText().getText().toString();

                usersRequests.setFull_name(fullname);
                usersRequests.setEmail(usermail);
                usersRequests.setAddress(address);
                usersRequests.setHome_number(homenumber);
                usersRequests.setPhone_number(phonenumber);

                myRef.child(usersRequests.getFull_name()).setValue(usersRequests);

               /* myRef.child("Full Name").setValue(fullname);
                myRef.child(("User Mail")).setValue(usermail);
                myRef.child(("Address")).setValue(address);
                myRef.child(("Home Number")).setValue(homenumber);
                myRef.child("Phone Number").setValue(phonenumber);
                */
            }
        });

        return rootView;
    }

}
