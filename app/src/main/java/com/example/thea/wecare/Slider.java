package com.example.thea.wecare;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Slider extends AppCompatActivity {
//
//    ViewPager pager;
//    LinearLayout sliderDots;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_slider);
//
////        ActionBar actionBar = getSupportActionBar();
////        actionBar.hide();
//
//        pager = (ViewPager) findViewById(R.id.slider);
//
//        sliderDots = (LinearLayout) findViewById(R.id.sliderDots);
//
//        Custom_Adapter_Layout viewPagerAdapter = new Custom_Adapter_Layout(this);
//
//        pager.setAdapter(viewPagerAdapter);
//
//    }
//
//
//
//
//
//
//
//
//
//    public void onBackPressed(){
//        finish();
//        Intent intent = new Intent(this, Home.class);
//        startActivity(intent);
//    }
//}
private ViewPager mSliderViewPager;
    private LinearLayout mDotLayout;

    private TextView[] mDots;

    Button next, back;

    private int mCurrentPager;

    private Custom_Adapter_Layout sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );

        mSliderViewPager = (ViewPager) findViewById(R.id.SlideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.DotLayout);

        next = (Button) findViewById(R.id.btnNext);
        back = (Button) findViewById(R.id.btnBack);

        sliderAdapter = new Custom_Adapter_Layout( this);

        mSliderViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSliderViewPager.addOnPageChangeListener(viewListener);
    }

    public void addDotsIndicator(int position){

        mDots = new TextView[7];
        mDotLayout.removeAllViews();

        for(int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.textColorPrimary));

            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.color));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);

            mCurrentPager = i;

            if (i == 0) {
                back.setEnabled(false);
                next.setEnabled(true);
                back.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);

                back.setText("");
                next.setText("Next");
            } else if (i == mDots.length - 1) {
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

                next.setText("Finish");
                back.setText("Back");
            } else {
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

                next.setText("Next");
                back.setText("Back");
            }
        }
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
    public void BACK (View view){
        mSliderViewPager.setCurrentItem(mCurrentPager - 1);
    }
    public void NEXT (View view){
        if (next.getText() == "Finish"){
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
        else {
            mSliderViewPager.setCurrentItem(mCurrentPager + 1);
        }
    }
    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
