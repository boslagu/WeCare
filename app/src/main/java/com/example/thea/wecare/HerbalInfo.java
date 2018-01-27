package com.example.thea.wecare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.speech.tts.TextToSpeech;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class HerbalInfo extends AppCompatActivity implements TextToSpeech.OnInitListener{

    String[] herbalChecker = {
            "alusiman",
            "atis",
            "atsuete",
            "balimbing",
            "bayabas",
            "buyo",
            "gugo",
            "kakawate",
            "kalatsutsi",
            //kalatusitsi sap
            "kamoteng",
            "kanya",
            "kataka-taka",
            "kilaw",
            "kulitis",
            "lagundi",
            "makabuhay",
            "pandakaki-puti",
            "ripe",
            "romero",
            "sabila",
            "takip-kuhol",
    };
    int[] images = {
            R.drawable.alusimanleaves1,
            R.drawable.alusimanleaves2,
            R.drawable.alusimanleaves3,
            R.drawable.atis1,
            R.drawable.atis2,
            R.drawable.atis3,
            R.drawable.atsueteleaves1,
            R.drawable.atsueteleaves2,
            R.drawable.atsueteleaves3,
            R.drawable.balimbingleaves1,
            R.drawable.balimbingleaves2,
            R.drawable.balimbingleaves3,
            R.drawable.bayabasleaves1,
            R.drawable.bayabasleaves2,
            R.drawable.bayabasleaves3,
            R.drawable.kalamansi1,
            R.drawable.kalamansi2,
            R.drawable.kalamansi3,
            R.drawable.gugo1,
            R.drawable.gugo2,
            R.drawable.gugo3,
            R.drawable.kakawate1,
            R.drawable.kakawate2,
            R.drawable.kakawate3,
            R.drawable.kalatsutsileaves1,
            R.drawable.kalatsutsileaves1,
            R.drawable.kalatsutsileaves1,
            R.drawable.kamotengkahoy1,
            R.drawable.kamotengkahoy2,
            R.drawable.kamotengkahoy3,
            R.drawable.kanyapistula1,
            R.drawable.kanyapistula2,
            R.drawable.kanyapistula3,
            R.drawable.katakataka1,
            R.drawable.katakataka2,
            R.drawable.katakataka3,
            R.drawable.kilawleaves1,
            R.drawable.kilawleaves2,
            R.drawable.kilawleaves3,
            R.drawable.kulitis1,
            R.drawable.kulitis2,
            R.drawable.kulitis3,
            R.drawable.lagundileaves1,
            R.drawable.lagundileaves2,
            R.drawable.lagundileaves3,
            R.drawable.makabuhay1,
            R.drawable.makabuhay2,
            R.drawable.makabuhay3,
            R.drawable.pandakaki1,
            R.drawable.pandakaki2,
            R.drawable.pandakaki3,
            R.drawable.papaya1,
            R.drawable.papaya2,
            R.drawable.papaya3,
            R.drawable.romeroleaves1,
            R.drawable.romeroleaves2,
            R.drawable.romeroleaves3,
            R.drawable.sabilaleaves1,
            R.drawable.sabilaleaves2,
            R.drawable.sabilaleaves3,
            R.drawable.takipkuhol1,
            R.drawable.takipkuhol2,
            R.drawable.takipkuhol3,
    };


    TextView txtTitle, txtDescription, txtChecker, txtBenefits;
    String itemTitle;
    private TextToSpeech tts;
    BookmarkDatabaseHelper bookmarkDatabaseHelper;
    HerbalDbHelper herbalDbHelper;
    ImageView imgViewHerbalInfo1, imgViewHerbalInfo2, imgViewHerbalInfo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_herbal_info);


        //****************************************************************************Initialization
        txtChecker = (TextView) findViewById(R.id.txtChecker);
        txtBenefits = (TextView) findViewById(R.id.txtBenefits);
        herbalDbHelper = new HerbalDbHelper(this);
        bookmarkDatabaseHelper = new BookmarkDatabaseHelper(this);
        tts = new TextToSpeech(this, this);

        //******************************************************************************get the data
        imgViewHerbalInfo1 = (ImageView) findViewById(R.id.imgViewHerbalInfo1);
        imgViewHerbalInfo2 = (ImageView) findViewById(R.id.imgViewHerbalInfo2);
        imgViewHerbalInfo3 = (ImageView) findViewById(R.id.imgViewHerbalInfo3);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemTitle = bundle.getString("title");

        //******************************************************************************display data
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText(itemTitle);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        fetchData();
        imgViewHerbalInfo1.setScaleType(ImageView.ScaleType.FIT_XY);
        imgViewHerbalInfo2.setScaleType(ImageView.ScaleType.FIT_XY);
        imgViewHerbalInfo3.setScaleType(ImageView.ScaleType.FIT_XY);
        String splitWords[] = txtTitle.getText().toString().toLowerCase().split(" ", 2);
        String firstWord = splitWords[0];


        int num1 = 0;
        for (int i = 0; i < herbalChecker.length; i++) {
            num1 = num1 + 3;
            if (i == 0) {
                if (firstWord.equals(herbalChecker[i])) {
                    imgViewHerbalInfo1.setImageResource(images[i]);
                    imgViewHerbalInfo2.setImageResource(images[i + 1]);
                    imgViewHerbalInfo3.setImageResource(images[i + 2]);
                }
            } else {
                if (firstWord.equals(herbalChecker[i])) {
                    imgViewHerbalInfo1.setImageResource(images[num1-3]);
                    imgViewHerbalInfo2.setImageResource(images[(num1-3) + 1]);
                    imgViewHerbalInfo3.setImageResource(images[(num1-3) + 2]);
                }
            }
        }

//        ********#################################################################################img herbal
//        if (txtTitle.getText().toString().toLowerCase().equals("alusiman leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.alusimanleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.alusimanleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.alusimanleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("ripe papaya with kalamansi juice")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.papaya1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.papaya2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.papaya3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("sabila leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.sabilaleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.sabilaleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.sabilaleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("romero leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.romeroleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.romeroleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.romeroleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("alusiman leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.alusimanleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.alusimanleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.alusimanleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("bayabas leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.bayabasleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.bayabasleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.bayabasleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("buyo  kalamansi")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kalamansi1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kalamansi2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kalamansi3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kakawate leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kakawate1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kakawate2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kakawate3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kalatsutsi sap from leaves and trunk")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kalatsutsileaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kalatsutsileaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kalatsutsileaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kanya pistula ( golden showers )")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kanyapistula1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kanyapistula2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kanyapistula3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("pandakaki-puti hot fot bath")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.pandakaki1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.pandakaki2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.pandakaki3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kulitis  hot compress")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kulitis1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kulitis2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kulitis3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("takip-kuhol poultice (panapal)")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.takipkuhol1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.takipkuhol2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.takipkuhol3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kilaw  leaves (luyang dilaw)")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kilawleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kilawleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kilawleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("gugo bark shampoo")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.gugo1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.gugo2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.gugo3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("sabila leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.sabilaleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.sabilaleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.sabilaleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kataka-taka leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.katakataka1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.katakataka2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.katakataka3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("atis -unripe fruit and seeds")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.atis1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.atis2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.atis3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kamoteng kahoy flour for starch bath")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kamotengkahoy1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kamotengkahoy2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kamotengkahoy3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("balimbing leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.balimbingleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.balimbingleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.balimbingleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("lagundi leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.lagundileaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.lagundileaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.lagundileaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("sabila leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.sabilaleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.sabilaleaves3);
//            imgViewHerbalInfo3.setImageResource(R.drawable.sabilaleaves2);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("atsuete leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.atsueteleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.atsueteleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.atsueteleaves3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("makabuhay vine hot tub bath")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.makabuhay1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.makabuhay2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.makabuhay3);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kalatsutsi leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.kalatsutsi2);
//            imgViewHerbalInfo2.setImageResource(R.drawable.kalatsutsi3);
//            imgViewHerbalInfo3.setImageResource(R.drawable.kalatsutsileaves1);
//        }
//        else if (txtTitle.getText().toString().toLowerCase().equals("kakawate leaves")){
//            imgViewHerbalInfo1.setImageResource(R.drawable.alusimanleaves1);
//            imgViewHerbalInfo2.setImageResource(R.drawable.alusimanleaves2);
//            imgViewHerbalInfo3.setImageResource(R.drawable.alusimanleaves3);
//        }
    }


    public void toastThis(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void fetchData() {
        herbalDbHelper = new HerbalDbHelper(this);
        try {

            herbalDbHelper.createDataBase();
            herbalDbHelper.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase sd = herbalDbHelper.getReadableDatabase();
        Cursor cursor = sd.query("tbl_Herbal" ,null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            txtChecker.setText(cursor.getString(cursor.getColumnIndex("herbalName")));
            if (txtChecker.getText().toString().toUpperCase().equals(txtTitle.getText().toString().toUpperCase())) {
                txtDescription.setText(cursor.getString(cursor.getColumnIndex("herbalDescription")));
                txtBenefits.setText(cursor.getString(cursor.getColumnIndex("herbalBenefits")));
            }
        }
    }


    //*************************************************************************Text to speech OnInit
    @Override
    public void onInit(int text) {

        if (text == TextToSpeech.SUCCESS){

            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
                speakOutNow("");

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


    //*******************************************************Function for inserting data to bookmark
    public void btnSaveBookmark(View v){
        String type = "herbal";
        Cursor data = bookmarkDatabaseHelper.getAllData();
        String newEntryHerbal = txtTitle.getText().toString();
        boolean found = false;
        //******************************************************************************************
        if (data != null && data.getCount() > 0) {
            data.moveToFirst();
            while (!data.isAfterLast()) {
                txtChecker.setText(data.getString(1).toString().toUpperCase());
                if (txtChecker.getText().toString().equals(txtTitle.getText().toString().toUpperCase())){
                    found = true;
                }
                data.moveToNext();
            }
            //******************************************************************************************
        }
        if (found == true){
            speakOutNow("This Herbal is already in Bookmark.");
        }else {
            AddData(newEntryHerbal, type);
        }
    }

    public void AddData(String newEntry, String type) {

        boolean insertData = bookmarkDatabaseHelper.insertData(newEntry, type);

        if(insertData==true){
            speakOutNow(txtTitle.getText().toString() + " successfully added to bookmark.");
        }else{
            speakOutNow("Something went wrong while adding the data.");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return true;
    }
    //  ##########################################################***************************************Delete Item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_save) {
            String type = "herbal";
            Cursor data = bookmarkDatabaseHelper.getAllData();
            String newEntryHerbal = txtTitle.getText().toString();
            boolean found = false;
            //******************************************************************************************
            if (data != null && data.getCount() > 0) {
                data.moveToFirst();
                while (!data.isAfterLast()) {
                    txtChecker.setText(data.getString(1).toString().toUpperCase());
                    if (txtChecker.getText().toString().equals(txtTitle.getText().toString().toUpperCase())){
                        found = true;
                    }
                    data.moveToNext();
                }
                //******************************************************************************************
            }
            if (found == true){
                speakOutNow("This Herbal is already in Bookmark.");
            }else {
                AddData(newEntryHerbal, type);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    ###########################################################*****************************************End

}
