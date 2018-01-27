package com.example.thea.wecare;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookmarkItem extends AppCompatActivity {


    //Declarations
//    Button btnDeleteItem;
    LinearLayout layoutHerbalItem, layoutDiseaseItem;
    TextView txtTitleBookmark, txtDescriptionBookmark, txtCheck, txtHereditaryB, txtcauseB, txtlooklikeB, txtDiagnosedB,
            txtCureB, txtSymptomsB, txtTreatmentB, txtAvoidB, txtDescriptionHerbalB;
    String itemTitle, type;
    BookmarkDatabaseHelper bookmarkDatabaseHelper;
    HerbalDbHelper herbalDbHelper;
    LearningDbHelper learningDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bookmark_item);

        txtHereditaryB = (TextView) findViewById(R.id.txtHereditaryB);
        txtcauseB = (TextView) findViewById(R.id.txtcauseB);
        txtlooklikeB = (TextView) findViewById(R.id.txtlooklikeB);
        txtDiagnosedB = (TextView) findViewById(R.id.txtDiagnosedB);
        txtcauseB = (TextView) findViewById(R.id.txtcauseB);
        txtCureB = (TextView) findViewById(R.id.txtCureB);
        txtSymptomsB = (TextView) findViewById(R.id.txtSymptomsB);
        txtTreatmentB = (TextView) findViewById(R.id.txtTreatmentB);
        txtAvoidB = (TextView) findViewById(R.id.txtAvoidB);
        txtDescriptionHerbalB = (TextView) findViewById(R.id.txtDescriptionHerbalB);

        txtCheck = (TextView) findViewById(R.id.txtCheck);
        layoutHerbalItem = (LinearLayout) findViewById(R.id.layoutHerbalItem);
        layoutDiseaseItem = (LinearLayout) findViewById(R.id.layoutDiseaseItem);
        herbalDbHelper = new HerbalDbHelper(this);
        learningDbHelper = new LearningDbHelper(this);
        bookmarkDatabaseHelper = new BookmarkDatabaseHelper(this);
        txtTitleBookmark = (TextView) findViewById(R.id.txtTitleDisease);
        txtDescriptionBookmark = (TextView) findViewById(R.id.txtDescriptionDisease);

        //******************************************************************************get the data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemTitle = bundle.getString("title");
        type = bundle.getString("type");

        //******************************************************************************display data
        txtTitleBookmark.setText(itemTitle);
//        txtDescriptionBookmark.setText(itemDescription);
        fetchData();
    }




    public void fetchData() {

        if (type.toString().toLowerCase().equals("herbal")) {

            layoutHerbalItem.setVisibility(View.VISIBLE);
            layoutDiseaseItem.setVisibility(View.GONE);

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
                txtCheck.setText(cursor.getString(cursor.getColumnIndex("herbalName")));
                if (txtCheck.getText().toString().toUpperCase().equals(txtTitleBookmark.getText().toString().toUpperCase())) {
                    txtDescriptionHerbalB.setText(cursor.getString(cursor.getColumnIndex("herbalDescription")));
                }
            }
        } else {

            layoutHerbalItem.setVisibility(View.GONE);
            layoutDiseaseItem.setVisibility(View.VISIBLE);

            learningDbHelper = new LearningDbHelper(this);
            try {

                learningDbHelper.createDataBase();
                learningDbHelper.openDataBase();

            } catch (Exception e) {
                e.printStackTrace();
            }
            SQLiteDatabase myLearn = learningDbHelper.getReadableDatabase();
            Cursor cursorLearn = myLearn.query("tbl_Learning" ,null, null, null, null, null, null);


            while (cursorLearn.moveToNext()) {
                txtCheck.setText(cursorLearn.getString(cursorLearn.getColumnIndex("diseaseName")));
                if (txtCheck.getText().toString().toUpperCase().equals(txtTitleBookmark.getText().toString().toUpperCase())) {
                    txtDescriptionBookmark.setText(cursorLearn.getString(cursorLearn.getColumnIndex("description")));


                    txtHereditaryB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("hereditary")));
                    txtcauseB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("cuase")));
                    txtlooklikeB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("looklike")));
                    txtDiagnosedB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("diagnosed")));
                    txtCureB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("cured")));
                    txtSymptomsB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("symptoms")));
                    txtTreatmentB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("treatments")));
                    txtAvoidB.setText(cursorLearn.getString(cursorLearn.getColumnIndex("Avoid")));
                }
            }
        }
    }

    //  ##########################################################***************************************View delete
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }
    //  ##########################################################***************************************Delete Item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item_delete) {


            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            String name = txtTitleBookmark.getText().toString();
                            int result = bookmarkDatabaseHelper.deleteData(name);
                            returnIntent();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?").setPositiveButton("yes", dialogClickListener).setNegativeButton("no", dialogClickListener).show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void returnIntent(){
        Intent intent = new Intent(this, BookMark.class);
        startActivity(intent);
    }
//    ###########################################################*****************************************End
}
