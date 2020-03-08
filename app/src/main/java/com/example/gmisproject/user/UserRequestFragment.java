package com.example.gmisproject.user;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.gmisproject.Employee;
import com.example.gmisproject.R;
import com.example.gmisproject.UsersRequests;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserRequestFragment extends Fragment {

    RadioGroup radioGroup;
    RadioButton radioButton;
    View rootView;
    public UserRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_user_reqest, container, false);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Requests");

       // myRef.setValue("Hello, World!");

        final TextInputLayout fullName = rootView.findViewById(R.id.textinput_use_fullName);
        final TextInputLayout userMail = rootView.findViewById(R.id.textinput_user_email);
        final TextInputLayout  Address = rootView.findViewById(R.id.textinput_user_address);
        final TextInputLayout binNumber = rootView.findViewById(R.id.textinput_user_binNumber);
        final TextInputLayout phoneNumber = rootView.findViewById(R.id.textinput_user_phone);


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
                String binNumbers = binNumber.getEditText().getText().toString();
                String phonenumber = phoneNumber.getEditText().getText().toString();

                usersRequests.setFull_name(fullname);
                usersRequests.setEmail(usermail);
                usersRequests.setAddress(address);
                usersRequests.setBin_number(binNumbers);
                usersRequests.setPhone_number(phonenumber);

             //   myRef.child(usersRequests.getFull_name()).setValue(usersRequests);

                FirebaseDatabase.getInstance().getReference("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(usersRequests);














            }
        });

        final RadioGroup radioGroup =rootView.findViewById(R.id.radio_group_cost_type);
        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = rootView.findViewById(radioId);
              //  Toast.makeText(UserRequestFragment.this.getActivity().getApplicationContext(), "نوع الحساب: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

}
