package com.example.thea.wecare;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class BodyProcedure extends AppCompatActivity {

    TextView txtDiseaseName, txtDiseaseDescription, txtHerbalName, txtHerbalSciName, txtHerbalDescription, txtHerbalProcedure, txtHerbalDays;
    GridView grv;
    ImageView baymaxBodyProcedure;
    UserDiseaseDbHelper userDiseaseDbHelper;
    HerbalDbHelper herbalDbHelper;
    DiseasesDbHelper diseasesDbHelper;
    private TextToSpeech tts;
    ArrayList<String> diseasesNames;
    String part;
    public static ArrayList<Item> data;
    ListView lstDiseasesProcedure;
    CustomAdapter adapter;
    LinearLayout lyoutprocedure;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_body_procedure);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Consult");


//        lyoutprocedure = (LinearLayout) findViewById(R.id.lyoutprocedure);
//        ImageView imageView = (ImageView) findViewById(R.id.imageview);

        lstDiseasesProcedure = (ListView) findViewById(R.id.lstDiseasesProcedure);
        data= new ArrayList<Item>();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        part = bundle.getString("title");

//        baymaxBodyProcedure = (ImageView) findViewById(R.id.baymaxBodyProcedure);
//        txtDiseaseName = (TextView) findViewById(R.id.txtDiseaseName);
//        txtDiseaseDescription = (TextView) findViewById(R.id.txtDiseaseDescription);
//        txtHerbalName = (TextView) findViewById(R.id.txtHerbalName);
//        txtHerbalSciName = (TextView) findViewById(R.id.txtHerbalSciName);
//        txtHerbalDescription = (TextView) findViewById(R.id.txtHerbalDescription);
//        txtHerbalProcedure = (TextView) findViewById(R.id.txtHerbalProcedure);
//        txtHerbalDays = (TextView) findViewById(R.id.txtHerbalDays);

        userDiseaseDbHelper = new UserDiseaseDbHelper(this);
        herbalDbHelper = new HerbalDbHelper(this);
        diseasesDbHelper = new DiseasesDbHelper(this);
        diseasesNames = new ArrayList<>();
//        baymaxBodyProcedure.setScaleType(ImageView.ScaleType.FIT_XY);

        grv = (GridView) findViewById(R.id.grv);

        lstDiseasesProcedure.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lstDiseasesProcedure.getLayoutParams();
//                layoutParams.height = 450;
//                lstDiseasesProcedure.setLayoutParams(layoutParams);
//
                String itemClicked = ((TextView) view.findViewById(R.id.title)).getText().toString();
//                txtDiseaseName.setText(itemClicked);
//                txtHerbalName.setText("");
//                lyoutprocedure.setVisibility(View.VISIBLE);
////              baymaxBodyProcedure.setImageResource(R.drawable.doc);
//                SQLiteDatabase myHerbalDbHelper = herbalDbHelper.getReadableDatabase();
//                Cursor cursor = myHerbalDbHelper.query("tbl_Herbal" ,null, null, null, null, null, null);
//                toastThis("cliked "+itemClicked);
//                while (cursor.moveToNext()) {
//                    if (txtDiseaseName.getText().toString().toUpperCase().equals(cursor.getString(0).toString().toUpperCase())) {
//                        txtDiseaseDescription.setText("Description - " + cursor.getString(1).toString() + System.getProperty("line.separator"));
//                        txtHerbalName.setText("Herbal cure" + System.getProperty("line.separator") + cursor.getString(2).toString());
//                        txtHerbalDescription.setText("Description - " + cursor.getString(4).toString() + System.getProperty("line.separator"));
//                        txtHerbalProcedure.setText("-" + System.getProperty("line.separator") + cursor.getString(5).toString());
//                        txtHerbalDays.setText("Days: "+cursor.getString(6).toString());
//                    }
//                }
//                if (txtDiseaseName.getText().toString().toLowerCase().equals("acne")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.acne2);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("allergy")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.allergy1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("burn")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.burn1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("chicken pox")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.chickenpox1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("dandruff")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.dandruff1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("eczema ")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.eczema1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("infected mosquito bites ")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.infected1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("measles")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.measles1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("acne")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.acne1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("scabies (“galis aso”)")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.scabies1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("sunburn or erythema")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.sunburn1);
//                } else if (txtDiseaseName.getText().toString().toLowerCase().equals("underarm or body odor ")){
//                    baymaxBodyProcedure.setImageResource(R.drawable.underarm1);
//                }


                Intent intent = new Intent(getApplicationContext(), Procedure.class);
                intent.putExtra("title", itemClicked);
                startActivity(intent);

            }
        });

        fetchData();
    }

    public void toastThis(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void fetchData() {
        diseasesDbHelper = new DiseasesDbHelper(this);
        try {

            diseasesDbHelper.createDataBase();
            diseasesDbHelper.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase myDiseasesDbHelper = diseasesDbHelper.getReadableDatabase();
        Cursor cursor = myDiseasesDbHelper.query("tbl_Diseases" ,null, null, null, null, null, null);
        diseasesNames = new ArrayList<String>();
        while (cursor.moveToNext()) {
            if (cursor.getString(cursor.getColumnIndex(part)).equals("true")) {
                diseasesNames.add(cursor.getString(cursor.getColumnIndex("diseaseName")));
            }
        }



//        String[] mNames = new String[diseasesNames.size()];
//        mNames = diseasesNames.toArray(mNames);

        Collections.sort(diseasesNames, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < diseasesNames.size(); i++) {
            data.add(new Item(diseasesNames.get(i)));
        }
        adapter =  new CustomAdapter(this, R.layout.item, data);
        lstDiseasesProcedure.setAdapter(adapter);

    }




    private void speakOutNow(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }
}
