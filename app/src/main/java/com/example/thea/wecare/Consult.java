package com.example.thea.wecare;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Consult extends AppCompatActivity {
    ImageView imgBackFemale, imgFrontFemale, imgBackMale, imgFrontMale;
    ImageView ibtnBackFemale, ibtnFrontFemale, ibtnBackMale, ibtnFrontMale;
    String name, gender;
    TextView txtGenderResult;
    LinearLayout layoutMale, layoutFemale, layoutMaleFront, layoutMaleBack, layoutFrontFemale, layoutBackFemale;
    DataBaseHelper dataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_consult);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //**************************************************************Initialization of imageviews
        imgBackFemale = (ImageView) findViewById(R.id.imgBackFemale);
        imgFrontFemale = (ImageView) findViewById(R.id.imgFrontFemale);
        imgBackMale = (ImageView) findViewById(R.id.imgBackMale);
        imgFrontMale = (ImageView) findViewById(R.id.imgFrontMale);

        dataBaseHelper = new DataBaseHelper(this);

        //###
        layoutMaleBack = (LinearLayout) findViewById(R.id.layoutMaleBack);
        layoutMaleFront = (LinearLayout) findViewById(R.id.layoutMaleFront);
        layoutFrontFemale = (LinearLayout) findViewById(R.id.layoutFrontFemale);
        layoutBackFemale = (LinearLayout) findViewById(R.id.layoutBackFemale);
        //*************************************************************Initialization of imagebutton
        ibtnBackFemale = (ImageButton) findViewById(R.id.ibtnBackFemale);
        ibtnFrontFemale = (ImageButton) findViewById(R.id.ibtnFrontFemale);
        ibtnBackMale = (ImageButton) findViewById(R.id.ibtnBackMale);
        ibtnFrontMale = (ImageButton) findViewById(R.id.ibtnFrontMale);

        txtGenderResult = (TextView) findViewById(R.id.txtGenderResult);
        layoutMale = (LinearLayout) findViewById(R.id.layoutMale);
        layoutFemale = (LinearLayout) findViewById(R.id.layoutFemale);

        //****************************************************************************get the gender
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        gender = bundle.getString("gender");
//        txtGenderResult.setText(gender);

        fetchData();
        //****************************************************************************
        if(txtGenderResult.getText().toString().toLowerCase().equals("male")){
            layoutFemale.setVisibility(View.GONE);

        }else if(txtGenderResult.getText().toString().toLowerCase().equals("female")){
            layoutMale.setVisibility(View.GONE);

        }
    }



    public void fetchData(){

        Cursor res = dataBaseHelper.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBufferName = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBufferName.append(res.getString(1).toLowerCase());
                stringBuffer.append(res.getString(9).toLowerCase());
            }
            name = stringBufferName.toString();
            gender = stringBuffer.toString();
            txtGenderResult.setText(gender);
            Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
            if (stringBuffer.toString().equals("male")){
                Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
            } else if (stringBuffer.toString().equals("female")){
                Toast.makeText(this, gender, Toast.LENGTH_SHORT).show();
            }
        }
    }




    //***********************************************************************Function for back press
    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    //hide the image front
    public void btnBackMale(View v) {
        imgBackMale.setVisibility(View.VISIBLE);
        layoutMaleBack.setVisibility(View.VISIBLE);
        ibtnFrontMale.setVisibility(View.VISIBLE);
        imgFrontMale.setVisibility(View.GONE);
        layoutMaleFront.setVisibility(View.GONE);
        ibtnBackMale.setVisibility(View.GONE);
    }
    //hide the image back
    public void btnFrontMale(View v){
        imgBackMale.setVisibility(View.GONE);
        layoutMaleBack.setVisibility(View.GONE);
        ibtnFrontMale.setVisibility(View.GONE);
        imgFrontMale.setVisibility(View.VISIBLE);
        layoutMaleFront.setVisibility(View.VISIBLE);
        ibtnBackMale.setVisibility(View.VISIBLE);
    }

    public void btnBackFemale(View v){
        imgBackFemale.setVisibility(View.VISIBLE);
        layoutBackFemale.setVisibility(View.VISIBLE);
        ibtnFrontFemale.setVisibility(View.VISIBLE);
        imgFrontFemale.setVisibility(View.GONE);
        layoutFrontFemale.setVisibility(View.GONE);
        ibtnBackFemale.setVisibility(View.GONE);
    }

    public void btnFrontFemale(View v){
        imgBackFemale.setVisibility(View.GONE);
        layoutBackFemale.setVisibility(View.GONE);
        ibtnFrontFemale.setVisibility(View.GONE);
        imgFrontFemale.setVisibility(View.VISIBLE);
        layoutFrontFemale.setVisibility(View.VISIBLE);
        ibtnBackFemale.setVisibility(View.VISIBLE);
    }
    public void body(View v) {
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "body");
        startActivity(intent);
    }
    public void BodyFemale(View v) {
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "body");
        startActivity(intent);
    }
    public void Body(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "body");
        startActivity(intent);
    }
    public void Head(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "head");
        startActivity(intent);
    }
    public void Arm(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "arm");
        startActivity(intent);
    }
    public void Leg(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "leg");
        startActivity(intent);
    }
    public void Shoulder(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "shoulder");
        startActivity(intent);
    }
    public void Foot(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "foot");
        startActivity(intent);
    }
    public void Neck(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "neck");
        startActivity(intent);
    }
    public void Hand(View v){
        Intent intent = new Intent(this, BodyProcedure.class);
        intent.putExtra("title", "hand");
        startActivity(intent);
    }
    //###################################################*********************************************************************************
//   Floating ActionButton
    public void Consult(View v) {
        Intent intent = new Intent(this, Consult.class);
        startActivity(intent);
    }

    public void Learning(View v) {
        Intent intent = new Intent(this, Learning.class);
        startActivity(intent);
    }

    public void Herbal(View v) {
        Intent intent = new Intent(this, Herbal.class);
        startActivity(intent);
    }

    public void Monitor(View v) {
        Intent intent = new Intent(this, Monitoring.class);
        startActivity(intent);
    }

    public void Bookmark(View v) {
        Intent intent = new Intent(this, BookMark.class);
        startActivity(intent);
    }
}
