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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookmarkItem extends AppCompatActivity {

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
    ImageView imgDisease1, imgDisease2, imgDisease3, imgViewHerbalInfo1, imgViewHerbalInfo2, imgViewHerbalInfo3;

    //Declarations
//    Button btnDeleteItem;
    LinearLayout layoutHerbalItem, layoutDiseaseItem;
    TextView txtTitleBookmark, txtDescriptionBookmark, txtCheck, txtHereditaryB, txtcauseB, txtlooklikeB, txtDiagnosedB,
            txtCureB, txtSymptomsB, txtTreatmentB, txtAvoidB, txtDescriptionHerbalB, txtBenefitsB, txtTitleHerbalB;
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
        txtTitleHerbalB = (TextView) findViewById(R.id.txtTitleHerbalB);
        txtBenefitsB = (TextView) findViewById(R.id.txtBenefitsB);

        txtCheck = (TextView) findViewById(R.id.txtCheck);
        layoutHerbalItem = (LinearLayout) findViewById(R.id.layoutHerbalItem);
        layoutDiseaseItem = (LinearLayout) findViewById(R.id.layoutDiseaseItem);
        herbalDbHelper = new HerbalDbHelper(this);
        learningDbHelper = new LearningDbHelper(this);
        bookmarkDatabaseHelper = new BookmarkDatabaseHelper(this);
        txtTitleBookmark = (TextView) findViewById(R.id.txtTitleDisease);
        txtDescriptionBookmark = (TextView) findViewById(R.id.txtDescriptionDisease);


        imgViewHerbalInfo1 = (ImageView) findViewById(R.id.imgViewHerbalInfo1);
        imgViewHerbalInfo2 = (ImageView) findViewById(R.id.imgViewHerbalInfo2);
        imgViewHerbalInfo3 = (ImageView) findViewById(R.id.imgViewHerbalInfo3);
        imgDisease1 = (ImageView) findViewById(R.id.imgDisease1);
        imgDisease2 = (ImageView) findViewById(R.id.imgDisease2);
        imgDisease3 = (ImageView) findViewById(R.id.imgDisease3);

        //******************************************************************************get the data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemTitle = bundle.getString("title");
        type = bundle.getString("type");

        //******************************************************************************display data
        txtTitleBookmark.setText(itemTitle);
        txtTitleHerbalB.setText(itemTitle);
//        txtDescriptionBookmark.setText(itemDescription);
        fetchData();


        imgViewHerbalInfo1.setScaleType(ImageView.ScaleType.FIT_XY);
        imgViewHerbalInfo2.setScaleType(ImageView.ScaleType.FIT_XY);
        imgViewHerbalInfo3.setScaleType(ImageView.ScaleType.FIT_XY);
        imgDisease1.setScaleType(ImageView.ScaleType.FIT_XY);
        imgDisease2.setScaleType(ImageView.ScaleType.FIT_XY);
        imgDisease3.setScaleType(ImageView.ScaleType.FIT_XY);

        String splitWords[] = itemTitle.toLowerCase().split(" ", 2);
        String firstWord = splitWords[0];
        int num1 = 0;
        if (type.toLowerCase().equals("herbal")) {
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
                        imgViewHerbalInfo1.setImageResource(images[num1 - 3]);
                        imgViewHerbalInfo2.setImageResource(images[(num1 - 3) + 1]);
                        imgViewHerbalInfo3.setImageResource(images[(num1 - 3) + 2]);
                    }
                }
            }
        } else {
            if (itemTitle.toString().toLowerCase().equals("acne")){
                imgDisease1.setImageResource(R.drawable.acne1);
                imgDisease2.setImageResource(R.drawable.acne2);
                imgDisease3.setImageResource(R.drawable.acne3);
            } else if (itemTitle.toString().toLowerCase().equals("sunburn or erythema")){
                imgDisease1.setImageResource(R.drawable.sunburn1);
                imgDisease2.setImageResource(R.drawable.sunburn2);
                imgDisease3.setImageResource(R.drawable.sunburn3);
            } else if (itemTitle.toString().toLowerCase().equals("underarm or body odor ")){
                imgDisease1.setImageResource(R.drawable.underarm1);
                imgDisease2.setImageResource(R.drawable.underarm2);
                imgDisease3.setImageResource(R.drawable.underarm3);
            } else if (itemTitle.toString().toLowerCase().equals("allergy")){
                imgDisease1.setImageResource(R.drawable.allergy1);
                imgDisease2.setImageResource(R.drawable.allergy2);
                imgDisease3.setImageResource(R.drawable.allergy3);
            } else if (itemTitle.toString().toLowerCase().equals("eczema ")){
                imgDisease1.setImageResource(R.drawable.eczema1);
                imgDisease2.setImageResource(R.drawable.eczema2);
                imgDisease3.setImageResource(R.drawable.eczema3);
            } else if (itemTitle.toString().toLowerCase().equals("dandruff")){
                imgDisease1.setImageResource(R.drawable.dandruff1);
                imgDisease2.setImageResource(R.drawable.dandruff2);
                imgDisease3.setImageResource(R.drawable.dandruff3);
            } else if (itemTitle.toString().toLowerCase().equals("infected mosquito bites ")){
                imgDisease1.setImageResource(R.drawable.infected1);
                imgDisease2.setImageResource(R.drawable.infected2);
                imgDisease3.setImageResource(R.drawable.infected3);
            } else if (itemTitle.toString().toLowerCase().equals("measles")){
                imgDisease1.setImageResource(R.drawable.measles1);
                imgDisease2.setImageResource(R.drawable.measles2);
                imgDisease3.setImageResource(R.drawable.measles3);
            } else if (itemTitle.toString().toLowerCase().equals("chicken pox")){
                imgDisease1.setImageResource(R.drawable.chickenpox1);
                imgDisease2.setImageResource(R.drawable.chickenpox2);
                imgDisease3.setImageResource(R.drawable.chickenpox3);
            } else if (itemTitle.toString().toLowerCase().equals("burn")){
                imgDisease1.setImageResource(R.drawable.burn1);
                imgDisease2.setImageResource(R.drawable.burn2);
                imgDisease3.setImageResource(R.drawable.burn3);
            } else if (itemTitle.toString().toLowerCase().equals("scabies (“galis aso”)")){
                imgDisease1.setImageResource(R.drawable.scabies1);
                imgDisease2.setImageResource(R.drawable.scabies2);
                imgDisease3.setImageResource(R.drawable.scabies3);
            }
        }


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
                    txtBenefitsB.setText(cursor.getString(cursor.getColumnIndex("herbalBenefits")));
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
