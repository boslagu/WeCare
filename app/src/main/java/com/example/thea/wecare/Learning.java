package com.example.thea.wecare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Learning extends AppCompatActivity {

    ListView lstLearningHerbal, lstLearningDisease;
    LearningDbHelper learningDbHelper;
    HerbalDbHelper herbalDbHelper;
    CustomAdapter adapter;
    ArrayList<String> herbalTitles;
    ArrayList<String> herbalDescriptions;
    public static ArrayList<Item> herbalData;

    DataBaseHelper dataBaseHelper;

    Button btnHerbal, btnLearning;
    boolean disease = false, herbal = false, searchHerbal = true;

    ArrayList<String> diseaseTitles;
    ArrayList<String> diseaseDescriptions;
    public static ArrayList<Item> diseaseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_learning);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBaseHelper = new DataBaseHelper(this);
        lstLearningHerbal = (ListView) findViewById(R.id.lstLearningHerbal);
        lstLearningDisease = (ListView) findViewById(R.id.lstLearningDisease);
        learningDbHelper = new LearningDbHelper(this);
        herbalDbHelper = new HerbalDbHelper(this);

        herbalTitles = new ArrayList<>();
        herbalDescriptions = new ArrayList<>();
        herbalData= new ArrayList<Item>();

        diseaseTitles = new ArrayList<>();
        diseaseDescriptions = new ArrayList<>();
        diseaseData= new ArrayList<Item>();

        btnHerbal = (Button) findViewById(R.id.btnHerbal);
        btnLearning = (Button) findViewById(R.id.btnLearning);

        fetchData();


        lstLearningHerbal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get information from the clicked item
                String itemTitle = ((TextView) view.findViewById(R.id.title)).getText().toString();

                //open the new activity and pass the data
                Intent intent = new Intent(getApplicationContext(), HerbalInfo.class);
                intent.putExtra("title", itemTitle);
                startActivity(intent);
            }
        });


        lstLearningDisease.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get information from the clicked item
                String itemTitle = ((TextView) view.findViewById(R.id.title)).getText().toString();

                toastThis(itemTitle);
                //open the new activity and pass the data
                Intent intent = new Intent(getApplicationContext(), DiseaseInfo.class);
                intent.putExtra("title", itemTitle);
                startActivity(intent);
            }
        });

    }

    public void toastThis(String txt){
        Toast.makeText(this, txt, Toast.LENGTH_SHORT).show();
    }


    public void btnShowDisease(View view) {
        searchHerbal = false;
        lstLearningDisease.setVisibility(View.VISIBLE);
        lstLearningHerbal.setVisibility(View.GONE);
        btnLearning.setBackgroundColor(Color.parseColor("#eff5f8"));
        btnLearning.setTextColor(Color.parseColor("#2762be"));
        btnHerbal.setBackgroundColor(Color.parseColor("#088dd5"));
        btnHerbal.setTextColor(Color.parseColor("#ffffff"));
//        if (disease == true) {
//
//        } else if (disease == false) {
//            diseasesDbHelper = new DiseasesDbHelper(this);
//            try {
//
//                diseasesDbHelper.createDataBase();
//                diseasesDbHelper.openDataBase();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            SQLiteDatabase myDiseaseDbHelper = diseasesDbHelper.getReadableDatabase();
//            Cursor cursor = myDiseaseDbHelper.query("tbl_Diseases", null, null, null, null, null, null);
//
//            while (cursor.moveToNext()) {
//                diseaseTitles.add(cursor.getString(cursor.getColumnIndex("diseaseName")));
////                diseaseDescriptions.add(cursor.getString(cursor.getColumnIndex("diseaseDescription")));
//            }
//
//
//            Collections.sort(diseaseTitles, String.CASE_INSENSITIVE_ORDER);
//
//
//            for (int i = 0; i < diseaseTitles.size(); i++) {
//                diseaseData.add(new Item(diseaseTitles.get(i)/*, diseaseDescriptions.get(i)""*/));
//            }
//            //display items
//            adapter = new CustomAdapter(this, R.layout.item, diseaseData);
//
//            lstLearningDisease.setAdapter(adapter);
//            disease = true;
//        }
    }

    public void btnShowHerbal(View view) {
        searchHerbal = true;
        lstLearningDisease.setVisibility(View.GONE);
        lstLearningHerbal.setVisibility(View.VISIBLE);
        btnHerbal.setBackgroundColor(Color.parseColor("#eff5f8"));
        btnLearning.setBackgroundColor(Color.parseColor("#088dd5"));
        btnHerbal.setTextColor(Color.parseColor("#2762be"));
        btnLearning.setTextColor(Color.parseColor("#ffffff"));
    }


    public void fetchData(){
        herbalDbHelper = new HerbalDbHelper(this);
        try {

            herbalDbHelper.createDataBase();
            herbalDbHelper.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }



        try {

            learningDbHelper.createDataBase();
            learningDbHelper.openDataBase();

            } catch (Exception e) {
                e.printStackTrace();
            }


            SQLiteDatabase myDiseaseDbHelper = learningDbHelper.getReadableDatabase();
            Cursor cursor1 = myDiseaseDbHelper.query("tbl_Learning", null, null, null, null, null, null);

            while (cursor1.moveToNext()) {
                diseaseTitles.add(cursor1.getString(cursor1.getColumnIndex("diseaseName")));
            }




        SQLiteDatabase myHerbalDbHelper = herbalDbHelper.getReadableDatabase();
        Cursor cursor = myHerbalDbHelper.query("tbl_Herbal", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            herbalTitles.add(cursor.getString(cursor.getColumnIndex("herbalName")));
//            diseaseTitles.add(cursor.getString(cursor.getColumnIndex("diseaseName")));
//            herbalDescriptions.add(cursor.getString(cursor.getColumnIndex("herbalDescription")));
        }

        Collections.sort(herbalTitles, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < herbalTitles.size(); i++) {
            herbalData.add(new Item(herbalTitles.get(i)));
        }
        //display items
        adapter = new CustomAdapter(this, R.layout.item, herbalData);
        lstLearningHerbal.setAdapter(adapter);


        Collections.sort(diseaseTitles, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < diseaseTitles.size(); i++) {
            diseaseData.add(new Item(diseaseTitles.get(i)));
        }
        //display items
        adapter = new CustomAdapter(this, R.layout.item, diseaseData);
        lstLearningDisease.setAdapter(adapter);
    }


    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    //show search menu
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Item> tempList = new ArrayList<>();

                if(searchHerbal == true) {
                    //search items
                    for (Item item : herbalData) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            tempList.add(item);
                        }
                    }
//                display the result
                    adapter = new CustomAdapter(Learning.this, R.layout.item, tempList);
                    lstLearningHerbal.setAdapter(adapter);
                } else if(searchHerbal == false) {
                    for (Item item : diseaseData) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            tempList.add(item);
                        }
                    }

//                display the result
                    adapter = new CustomAdapter(Learning.this, R.layout.item, tempList);
                    lstLearningDisease.setAdapter(adapter);
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    //###################################################*********************************************************************************
//   Floating ActionButton
    public void Consult (View v){
        Cursor cursor = dataBaseHelper.getAllData();
        if (cursor != null && cursor.getCount() > 0) {
            finish();
            Intent intent = new Intent(this, Consult.class);
            startActivity(intent);
        } else {
            finish();
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
    }
    public void Learning (View v){
        finish();
        Intent intent = new Intent(this,Learning.class);
        startActivity(intent);
    }
    public void Herbal (View v){
        finish();
        Intent intent = new Intent(this,Herbal.class);
        startActivity(intent);
    }
    public void Monitor (View v){
        finish();
        Intent intent = new Intent(this,Monitoring.class);
        startActivity(intent);
    }
    public void Bookmark (View v){
        finish();
        Intent intent = new Intent(this,BookMark.class);
        startActivity(intent);
    }
}
