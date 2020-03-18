package com.example.gmisproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class tips extends AppCompatActivity {
    private ViewPager mainviewpager;
    private LinearLayout mDotslinearLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private Button previousbtn ;
    private Button Nextbtn ;
    private int currentPage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        mainviewpager = (ViewPager)findViewById(R.id.view_pager_id);
        mDotslinearLayout=(LinearLayout)findViewById(R.id.linear_layout_id);
        previousbtn =(Button)findViewById(R.id.button_previous);
        Nextbtn =(Button)findViewById(R.id.button_next);
        sliderAdapter = new SliderAdapter(this);
        mainviewpager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mainviewpager.addOnPageChangeListener(viewlistner);
        Nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainviewpager.setCurrentItem(currentPage +1);
            }
        });
        previousbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainviewpager.setCurrentItem(currentPage -1);
            }
        });
    }
    public void addDotsIndicator(int position){
        mDots = new TextView[3];
        mDotslinearLayout.removeAllViews();
        for(int i =0 ; i < mDots.length ; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colortransportwhite));
            mDotslinearLayout.addView(mDots[i]);
        }
        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }

    }
    ViewPager.OnPageChangeListener viewlistner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            currentPage= i;
            if(i==0){
                Nextbtn.setEnabled(true);
                previousbtn.setEnabled(false);
                previousbtn.setVisibility(View.INVISIBLE);
                Nextbtn.setText("Next");
                previousbtn.setText("");
            }
            else if (i == mDots.length -1){
                Nextbtn.setEnabled(true);
                previousbtn.setEnabled(true);
                previousbtn.setVisibility(View.VISIBLE);
                Nextbtn.setText("finish");
                Nextbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(tips.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                previousbtn.setText("previous");
            } else {
                Nextbtn.setEnabled(true);
                previousbtn.setEnabled(true);
                previousbtn.setVisibility(View.VISIBLE);
                Nextbtn.setText("Next");
                previousbtn.setText("previous");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}




