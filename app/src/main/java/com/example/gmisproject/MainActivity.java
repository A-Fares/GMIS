package com.example.gmisproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.gmisproject.user.UserFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager =  findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        UserFragmentAdapter adapter = new UserFragmentAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout =  findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));
     //   viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(R.drawable.trash);
        tabLayout.getTabAt(1).setIcon(R.drawable.req1);
        tabLayout.getTabAt(2).setIcon(R.drawable.msg);
    }
}
