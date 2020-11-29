package com.lorenzana.hackafest;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.view.ViewGroup;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    private slideAdapter SlideAdapter;

    private Button mnextBtn;
    private Button mprevBtn;

    private int mCurrentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotslayout);

        mnextBtn = (Button) findViewById(R.id.nextBtn);
        mprevBtn = (Button) findViewById(R.id.prevBtn);

        SlideAdapter = new slideAdapter(this);

        mSlideViewPager.setAdapter(SlideAdapter);

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mnextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPosition +1);
            }
        });

        mprevBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mSlideViewPager.setCurrentItem(mCurrentPosition -1);
            }
        });
    }

    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.design_default_color_secondary_variant));

            mDotLayout.addView(mDots[i]);
        }

        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1){

        }

        public void onPageSelected(int i){

            addDotsIndicator(i);
            mCurrentPosition = i;
            if(i == 0){
                mnextBtn.setEnabled(true);
                mprevBtn.setEnabled(false);
                mprevBtn.setVisibility(View.INVISIBLE);
                //Onclicklistener

                mnextBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        mSlideViewPager.setCurrentItem(mCurrentPosition +1);
                    }
                });
                mnextBtn.setText("Next");
                mprevBtn.setText("");

            }
            else if(i == mDots.length-1){

                mnextBtn.setEnabled(true);
                mprevBtn.setEnabled(true);
                mprevBtn.setVisibility(View.VISIBLE);

                mnextBtn.setText("Get Started");
                mnextBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        Intent i = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                    }
                });
                mprevBtn.setText("Back");
            }
            else {

                mnextBtn.setEnabled(true);
                mprevBtn.setEnabled(true);
                mprevBtn.setVisibility(View.VISIBLE);
                //Onclicklistener
                mnextBtn.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        mSlideViewPager.setCurrentItem(mCurrentPosition +1);
                    }
                });
                mnextBtn.setText("Next");
                mprevBtn.setText("Back");

            }
        }

        public void onPageScrollStateChanged(int i){

        }
    };
}
