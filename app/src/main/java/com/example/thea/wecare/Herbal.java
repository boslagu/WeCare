package com.example.thea.wecare;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Herbal extends AppCompatActivity {




    ListView listview;
    LinkedHashMap<String,String> namelist;
    ArrayList<String> titles;
    public static ArrayList<Item> data;
    CustomAdapter adapter;
    HerbalDbHelper db;
    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_herbal);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataBaseHelper = new DataBaseHelper(this);
        listview = (ListView) findViewById(R.id.lstHerbal);
        db = new HerbalDbHelper(this);
        titles = new ArrayList<>();
        data= new ArrayList<Item>();
        fetchData();

        //For listview click
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    }
    public void onBackPressed(){
        this.finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    public void fetchData() {
        db =new HerbalDbHelper(this);
        try {

            db.createDataBase();
            db.openDataBase();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        namelist=new LinkedHashMap<>();
        SQLiteDatabase sd = db.getReadableDatabase();
        Cursor cursor = sd.query("tbl_Herbal" ,null, null, null, null, null, null);
        titles=new ArrayList<String>();

        while (cursor.moveToNext()) {
                titles.add(cursor.getString(cursor.getColumnIndex("herbalName")));
        }
        Collections.sort(titles, String.CASE_INSENSITIVE_ORDER);
//-----------------------------------------------------------------------------------------------
        for (int i = 0; i < titles.size(); i++) {
            data.add(new Item(titles.get(i)));
        }
        adapter =  new CustomAdapter(this, R.layout.item, data);
        listview.setAdapter(adapter);
//-----------------------------------------------------------------------------------------------


    }

    //show search menu
    public boolean onCreateOptionsMenu(Menu menu) {
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

                //search items
                for (Item item : data) {
                    if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                        tempList.add(item);
                    }
                }

//                display the result
                adapter = new CustomAdapter(Herbal.this, R.layout.item, tempList);
                listview.setAdapter(adapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    //###################################################*********************************************************************************
//   Floating ActionButton
    public void Consult(View v) {
        Cursor cursor = dataBaseHelper.getAllData();
        if (cursor != null && cursor.getCount() > 0) {
            Intent intent = new Intent(this, Consult.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }
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