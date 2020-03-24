package com.example.gmisproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.gmisproject.user.UserFragmentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    static String string;
    private static boolean backPressedTime;
    TextView textViewUsername;
    SharedPreferencesConfig preferencesConfig;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferencesConfig = new SharedPreferencesConfig(getApplicationContext());

        //Set id for Textview
        ImageView imageView = findViewById(R.id.image_view_star);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, explicit_star_activity.class);
                startActivity(intent);

            }
        });
        FloatingActionButton floatingbutton = findViewById(R.id.floating_button);
        floatingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialogalert);
                dialog.setCancelable(false);
                dialog.show();
                Button btn_yes = dialog.findViewById(R.id.btn_yes);
                Button btn_no = dialog.findViewById(R.id.btn_no);
                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        //Shared Preference User Logout

                        preferencesConfig.writeUserLoginStatus(false);
                        Intent intent = new Intent(MainActivity.this, Registeration.class);
                        startActivity(intent);
                        finish();

                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        textViewUsername = findViewById(R.id.textView_userName);

     /*   string = getIntent().getExtras().getString("UserName");
        textViewUsername.setText(string);
*/

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        UserFragmentAdapter adapter = new UserFragmentAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        // Find the tab layout that shows the tabs
        TabLayout tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        //   viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(R.drawable.bin);
        tabLayout.getTabAt(1).setIcon(R.drawable.request);
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