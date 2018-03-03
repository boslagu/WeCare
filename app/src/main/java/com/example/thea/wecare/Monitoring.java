package com.example.thea.wecare;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Monitoring extends AppCompatActivity implements TextToSpeech.OnInitListener{

    DataBaseHelper dataBaseHelper;

    ArrayList<String> diseaseTitles;
    UserDiseaseDbHelper userDiseaseDbHelper;
    private TextToSpeech tts;
    public static ArrayList<Item> data;
    ListView lstUserDisease;
    TextView time;
    CustomAdapter adapter;
    ArrayList<String> diseasesNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_monitoring);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataBaseHelper = new DataBaseHelper(this);

        lstUserDisease = (ListView) findViewById(R.id.lstUserDisease);
        //String[] diseaseTitles;
        userDiseaseDbHelper = new UserDiseaseDbHelper(this);
        diseasesNames = new ArrayList<>();
        data= new ArrayList<Item>();
        tts = new TextToSpeech(this, this);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.getDefault());
                    fetchData();
                }
            }
        });
//        fetchData();
//**************************************************************************************Get the date
//        time = (TextView) findViewById(R.id.textView6);
//        /*Date*/ Calendar currentTime = Calendar.getInstance()/*.getTime()*/;
//
//        SimpleDateFormat df = new SimpleDateFormat("dd");
//        String formattedDate = df.format(currentTime.getTime());
//
//        time.setText(formattedDate.toString());
//
//        //date to notif the user
//            double d = Double.valueOf(String.valueOf(time.getText()));
//            time.setText(String.valueOf( (int) d));
//**************************************************************************************************
        //For listview click
        lstUserDisease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get information from the clicked item
                String itemTitle = ((TextView) view.findViewById(R.id.title)).getText().toString();

                //open the new activity and pass the data
                Intent intent = new Intent(getApplicationContext(), ViewUserDisease.class);
                intent.putExtra("title", itemTitle);
                startActivity(intent);
            }
        });

    }

    public void returnIntentHome(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    public void returnIntentConsult(){
        Intent intent = new Intent(this, Consult.class);
        startActivity(intent);
    }


    public void returnIntentSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void fetchData(){
        diseasesNames = new ArrayList<>();
        Cursor cursor = userDiseaseDbHelper.getAllData();
        if(cursor.getCount() == 0){
            speakOutNow("you have nothing to monitor, please consult first.");
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            Cursor cursor = dataBaseHelper.getAllData();
                            if (cursor != null && cursor.getCount() > 0) {
                                returnIntentConsult();
                            }else{
                                returnIntentSignUp();
                            }
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            returnIntentHome();
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want/need to consult?").setPositiveButton("yes", dialogClickListener).setNegativeButton("no", dialogClickListener).show();

        }else{
            while(cursor.moveToNext()){
                diseasesNames.add(cursor.getString(1));
            }

            Collections.sort(diseasesNames, String.CASE_INSENSITIVE_ORDER);
            for (int i = 0; i < diseasesNames.size(); i++) {
                data.add(new Item(diseasesNames.get(i)));
                Toast.makeText(this, diseasesNames.get(i), Toast.LENGTH_SHORT).show();
            }
            //display items
            adapter =  new CustomAdapter(this, R.layout.item, data);

            lstUserDisease.setAdapter(adapter);
        }

    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public void onInit(int text) {

        if (text == TextToSpeech.SUCCESS){

            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {

//                fetchData();

            }
            else{

            }

        }
        else{

        }
//        if (text == TextToSpeech.SUCCESS){
//
//            int language = tts.setLanguage(Locale.ENGLISH);
//            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
//                speakOutNow("hello. how are you? do you feel better?");
//                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case DialogInterface.BUTTON_POSITIVE:
//                                dialogYes();
//                                break;
//                            case DialogInterface.BUTTON_NEGATIVE:
//                                dialogNo();
//                                break;
//                        }
//                    }
//                };
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage("").setPositiveButton("yes", dialogClickListener).setNegativeButton("no", dialogClickListener).show();
//
//
//            }
//            else{

//            }
//
//        }
//        else{
//
//        }
    }

    private void speakOutNow(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }

    public void dialogYes(){
        speakOutNow("well, that's good to hear. do you still have problem with disease?");
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        speakOutNow("then, you have to do the medication for more days.");
                        //edit the data and add more days
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        speakOutNow("that's good to hear, now take care of your skin. get back to me if you want to consult again.");
                        //transfer the disease data to cured database
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("").setPositiveButton("yes", dialogClickListener).setNegativeButton("no", dialogClickListener).show();

    }

    public void dialogNo(){
        speakOutNow("did you follow the instruction i have given?");
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        speakOutNow("sorry to here that. if you felt worst, you have consult for experts, such as dermatologist.");
                        //transfer the disease data to failled database
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        speakOutNow("i advice you to do the procedure for a couple of days, and get back to me.");
                        //edit the data and add more days
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("").setPositiveButton("yes", dialogClickListener).setNegativeButton("no", dialogClickListener).show();

    }
    //   Floating ActionButton
    public void Consult (View v){
        Cursor cursor = dataBaseHelper.getAllData();
        if (cursor != null && cursor.getCount() > 0) {
            Intent intent = new Intent(this, Consult.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
    }
    public void Learning (View v){
        Intent intent = new Intent(this,Learning.class);
        startActivity(intent);
    }
    public void Herbal (View v){
        Intent intent = new Intent(this,Herbal.class);
        startActivity(intent);
    }
    public void Monitor (View v){
        Intent intent = new Intent(this,Monitoring.class);
        startActivity(intent);
    }
    public void Bookmark (View v){
        Intent intent = new Intent(this,BookMark.class);
        startActivity(intent);
    }
}
