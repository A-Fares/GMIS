package com.example.gmisproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        ImageView child = findViewById(R.id.image_view_back);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity(getParentActivityIntent());

            }
        });
    }
}
