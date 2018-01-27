package com.example.thea.wecare;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TextToSpeech.OnInitListener{


    //***********************************************************************************Declaration
    TextView txtResult;
    DataBaseHelper myDb;
    LinearLayout layoutMale, layoutFemale;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //****************************************************************************Initialization
        txtResult = (TextView) findViewById(R.id.txtResult);
        myDb = new DataBaseHelper(this);
        layoutMale = (LinearLayout) findViewById(R.id.layoutMale);
        layoutFemale = (LinearLayout) findViewById(R.id.layoutFemale);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.getDefault());
                    tts.speak("Hello!. I am, Teya. . . how can i help you?. . ", TextToSpeech.QUEUE_ADD, null, null);
                }
            }
        });
        tts = new TextToSpeech(this, this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    //**********************************************************************************
    @Override
    public void onInit(int text) {

        if (text == TextToSpeech.SUCCESS){

            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {

//                    speakOutNow("Hello!. I am, Teya. . . how can i help you?. . ");
                            //"click consult to consult your your problem. learning if you want to learn something about herbals and diseases. herbal for the information of herbals that you need. click monitoring to reconsult.");

            }
            else{

            }

        }
        else{

        }
    }

    //**********************************************************************************
    private void speakOutNow (String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }



//
    @Override
    public void onBackPressed() {
        finish();
    }


//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }
//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookmark) {
            finish();
            Intent intent = new Intent(this, BookMark.class);
            startActivity(intent);
        } else if (id == R.id.nav_termofuse) {
            finish();
            Intent intent = new Intent(this, TermofUse.class);
            startActivity(intent);
        } else if (id == R.id.nav_aboutus) {
            finish();
            Intent intent = new Intent(this, AboutUs.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //**************************read database if empty, proceed to SignUp, if not proceed to Consult
    private void dataCheck() {
        String name;
        Cursor res = myDb.getAllData();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBufferName = new StringBuffer();
        if (res != null && res.getCount() > 0) {
            while (res.moveToNext()) {
                stringBufferName.append(res.getString(1).toLowerCase());
                stringBuffer.append(res.getString(9).toLowerCase());
            }
            name = stringBufferName.toString();
            txtResult.setText(stringBuffer.toString());

//            if (stringBuffer.toString().equals("male")){
//                speakOutNow("you are now about to consult, sir " + name + ". . please click on the part of the body, where you have your skin problem.");
                Intent intent = new Intent(this, Consult.class);
////                intent.putExtra("gender", stringBuffer.toString());
                startActivity(intent);
//            } else if (stringBuffer.toString().equals("female")){
//                speakOutNow("clicked.");
//                speakOutNow("you are now about to consult, ma'am " + name + ". . please click on the part of the body, where you have your skin problem.");
//                Intent intent = new Intent(this, Consult.class);
////                intent.putExtra("gender", stringBuffer.toString());
//                startActivity(intent);
//            }
        } else {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
    }
    //end of reading database


    public void consult (View view) {
        dataCheck();
    }
    public void herbal (View view) {
        speakOutNow("");
        finish();
        Intent intent = new Intent(this, Herbal.class);
        startActivity(intent);
    }
    public void learning (View view) {
        speakOutNow("");
        finish();
        Intent intent = new Intent(this, Learning.class);
        startActivity(intent);
    }
    public void monitoring (View view) {
        speakOutNow("");
        finish();
        Intent intent = new Intent(this, Monitoring.class);
        startActivity(intent);
    }
    public void a (View view){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=9-3OCc5g5oE"));
                startActivity(intent);
    }
}
