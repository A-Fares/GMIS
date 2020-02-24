package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.gmisproject.user.UserFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static boolean backPressedTime;
    TextView textViewUsername;
    String string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set id for Textview

        textViewUsername = findViewById(R.id.text_username);

        string = getIntent().getExtras().getString("UserName");
        textViewUsername.setText(string);


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        UserFragmentAdapter adapter = new UserFragmentAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        //   viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(R.drawable.trash);
        tabLayout.getTabAt(1).setIcon(R.drawable.req1);
        tabLayout.getTabAt(2).setIcon(R.drawable.msg);
        onBackPressed();

    }

    @Override
    public void onBackPressed() {

        if (!backPressedTime) {
            Toast.makeText(this, "press again", Toast.LENGTH_SHORT).show();
            backPressedTime = true;
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {

                backPressedTime = false;
            }
        }.start();
    }
}