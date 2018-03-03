package com.example.thea.wecare;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.ActionProvider;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class BookMark extends AppCompatActivity {
    LinearLayout herbal, learning;
    ListView lstBookmarkHerbal, lstBookmarkLearning;
    BookmarkDatabaseHelper bookmarkDatabaseHelper;
    public static ArrayList<Item> myData;
    public static ArrayList<Item> myDataLearning;
    CustomAdapter adapter;
    Button btnHerbal, btnLearning;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_book_mark);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//********************************************************************************************************************************
        btnHerbal = (Button) findViewById(R.id.btnHerbal);
        btnLearning = (Button) findViewById(R.id.btnLearning);


        dataBaseHelper = new DataBaseHelper(this);
//********************************************************************************************************************************
        bookmarkDatabaseHelper = new BookmarkDatabaseHelper(this);
        lstBookmarkHerbal =(ListView) findViewById(R.id.lstBookHerbal);
        lstBookmarkLearning =(ListView) findViewById(R.id.lstBookLearning);
        myData= new ArrayList<Item>();
        myDataLearning= new ArrayList<Item>();
        readDB();
        herbal = (LinearLayout) findViewById(R.id.idHerbal);
        learning = (LinearLayout) findViewById(R.id.idLearning);


        //For listview click
        lstBookmarkHerbal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get information from the clicked item
                String itemTitle = ((TextView) view.findViewById(R.id.title)).getText().toString();

                //open the new activity and pass the data
                Intent intent = new Intent(getApplicationContext(), BookmarkItem.class);
                intent.putExtra("title", itemTitle);
                intent.putExtra("type", "herbal");
                startActivity(intent);
            }
        });

        //For listview click
        lstBookmarkLearning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //get information from the clicked item
                String itemTitle = ((TextView) view.findViewById(R.id.title)).getText().toString();

                //open the new activity and pass the data
                Intent intent = new Intent(getApplicationContext(), BookmarkItem.class);
                intent.putExtra("title", itemTitle);
                intent.putExtra("type", "disease");
                startActivity(intent);
            }
        });
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    private void readDB() {
        String typeChecker;
        ArrayList<String> herbalName = new ArrayList<>();
        ArrayList<String> diseaseName = new ArrayList<>();
        Cursor data = bookmarkDatabaseHelper.getAllData();
        if(data.getCount() == 0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()) {
                typeChecker = data.getString(2);
                if (typeChecker.toString().toLowerCase().equals("herbal")) {
                    herbalName.add(data.getString(1));
                } else {
                    diseaseName.add(data.getString(1));
                }
            }

            Collections.sort(herbalName, String.CASE_INSENSITIVE_ORDER);
            for (int i = 0; i < herbalName.size(); i++) {
                myData.add(new Item(herbalName.get(i)));
            }
            //display items
            adapter =  new CustomAdapter(this, R.layout.item, myData);
            lstBookmarkHerbal.setAdapter(adapter);




            Collections.sort(diseaseName, String.CASE_INSENSITIVE_ORDER);
            for (int i = 0; i < diseaseName.size(); i++) {
                myDataLearning.add(new Item(diseaseName.get(i)));
            }
            //display items
            adapter =  new CustomAdapter(this, R.layout.item, myDataLearning);
            lstBookmarkLearning.setAdapter(adapter);
        }
    }
//********************************************************************************************************************************
    public void showHerbal (View view){
        herbal.setVisibility(View.VISIBLE);
        learning.setVisibility(View.GONE);
        btnHerbal.setBackgroundColor(Color.parseColor("#eff5f8"));
        btnLearning.setBackgroundColor(Color.parseColor("#088dd5"));
        btnHerbal.setTextColor(Color.parseColor("#2762be"));
        btnLearning.setTextColor(Color.parseColor("#ffffff"));
    }
    public void showLearning (View view){
        herbal.setVisibility(View.GONE);
        learning.setVisibility(View.VISIBLE);
        btnLearning.setBackgroundColor(Color.parseColor("#eff5f8"));
        btnLearning.setTextColor(Color.parseColor("#2762be"));
        btnHerbal.setBackgroundColor(Color.parseColor("#088dd5"));
        btnHerbal.setTextColor(Color.parseColor("#ffffff"));
    }
//    ################################################################************************************************************************
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
