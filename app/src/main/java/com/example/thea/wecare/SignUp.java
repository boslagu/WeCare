package com.example.thea.wecare;

import android.content.Intent;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class SignUp extends AppCompatActivity implements TextToSpeech.OnInitListener{

    //**********************************************************************************Declarations
    boolean male;
    String Gender;
    DataBaseHelper myDb;
    EditText etxtName, etxtLastName, etxtMidName, etxtMonth, etxtDay, etxtYear, etxtWeight, etxtHeight;
    Button btnSubmit;
    LinearLayout layoutMale, layoutFemale;
    private TextToSpeech tts;
    private boolean mGender = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //****************************************************************************Initialization
        myDb = new DataBaseHelper(this);
        etxtName = (EditText) findViewById(R.id.etxtName);
        etxtMidName = (EditText) findViewById(R.id.etxtMidName);
        etxtLastName = (EditText) findViewById(R.id.etxtLastName);
        etxtMonth = (EditText) findViewById(R.id.etxtMonth);
        etxtDay = (EditText) findViewById(R.id.etxtDay);
        etxtYear = (EditText) findViewById(R.id.etxtYear);
        etxtWeight = (EditText) findViewById(R.id.etxtWeight);
        etxtHeight = (EditText) findViewById(R.id.etxtHeight);
//        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        layoutMale = (LinearLayout) findViewById(R.id.layoutMale);
        layoutFemale = (LinearLayout) findViewById(R.id.layoutFemale);
        tts = new TextToSpeech(this, this);

        //*****************************************************************Function for radio button
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radioFemale:
                        male = false;
                        Gender = "Female";
                        mGender = true;
                        break;
                    case R.id.radioMale:
                        male = true;
                        Gender = "Male";
                        mGender = true;
                        break;
                }
            }


        });
    }


    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    //*************************************************************************Text to speech OnInit
    @Override
    public void onInit(int text) {

        if (text == TextToSpeech.SUCCESS){

            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
                speakOutNow("you have to sign up first, to be able to consult. fill up the form, for your identification.");

            }
            else{

            }

        }
        else{

        }
    }

    //********************************************************************************Text to speech
    private void speakOutNow (String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);

    }


    //***********************************************************************Insert data to database
    private void insertData() {
        String name = etxtName.getText().toString();
        String midname = etxtMidName.getText().toString();
        String surname = etxtLastName.getText().toString();
        String day = etxtDay.getText().toString();
        String month = etxtMonth.getText().toString();
        String year = etxtYear.getText().toString();
        String weight = etxtWeight.getText().toString();
        String hieght = etxtHeight.getText().toString();
        String gender = Gender.toString();
        Boolean result = myDb.insertData(name, surname, midname, day, month, year, weight, hieght, gender);


        //******************************************************************************************
        Cursor cursor = myDb.getAllData();
        if(cursor == null){
            //Toast.makeText(this, "Data Insertion Failed", Toast.LENGTH_SHORT).show();
        }else if (cursor != null){
            if (Gender == "Female"){
                speakOutNow("Welcome to consult, ma'am " + etxtName.getText());
            } else if (Gender == "Male"){
                speakOutNow("Welcome to consult, sir " + etxtName.getText());
            }
            Intent intent = new Intent(this, Consult.class);
            intent.putExtra("gender", Gender);
            startActivity(intent);
        }

    }
    //inserting end up here
    public void SecondPage(View view) {
        if(etxtName.getText().toString().equals("") || etxtDay.getText().toString().equals("") || etxtHeight.getText().toString().equals("") ||
                etxtLastName.getText().toString().equals("") || etxtMidName.getText().toString().equals("") || etxtMonth.getText().toString().equals("") ||
                etxtWeight.getText().toString().equals("") || etxtYear.getText().toString().equals("") || mGender == false || etxtYear.length()!= 4) {
            speakOutNow("please complete the information needed");
        }
        else{
            insertData();
        }
    }
    public void Cancel(View view){
Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
