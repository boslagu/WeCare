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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

public class DiseaseInfo extends AppCompatActivity implements TextToSpeech.OnInitListener{


    TextView txtDiseaseLearning, txtDescriptionLearning, txtCheckerLearning, txtHereditaryL, txtcauseL, txtlooklikeL, txtDiagnosedL,
    txtCureL, txtSymptomsL, txtTreatmentL, txtAvoidL;
    String itemTitle;
    private TextToSpeech tts;
    LearningDbHelper learningDbHelper;
    BookmarkDatabaseHelper bookmarkDatabaseHelper;
    ImageView imgViewDiseaseInfo1, imgViewDiseaseInfo2, imgViewDiseaseInfo3;

//    ZoomControls zoomControls;
//    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_disease_info);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Disease");
        //****************************************************************************Initialization
        txtCheckerLearning = (TextView) findViewById(R.id.txtCheckerLearning);
        txtHereditaryL = (TextView) findViewById(R.id.txtHereditaryL);
        txtcauseL = (TextView) findViewById(R.id.txtcauseL);
        txtlooklikeL = (TextView) findViewById(R.id.txtlooklikeL);
        txtDiagnosedL = (TextView) findViewById(R.id.txtDiagnosedL);
        txtCureL = (TextView) findViewById(R.id.txtCureL);
        txtSymptomsL = (TextView) findViewById(R.id.txtSymptomsL);
        txtTreatmentL = (TextView) findViewById(R.id.txtTreatmentL);
        txtAvoidL = (TextView) findViewById(R.id.txtAvoidL);
        learningDbHelper = new LearningDbHelper(this);
        bookmarkDatabaseHelper = new BookmarkDatabaseHelper(this);
        tts = new TextToSpeech(this, this);

        //----------------------------------------------------------------------------------------Zoom
//        zoomControls = (ZoomControls) findViewById(R.id.zoom);
//        txtDiseaseLearning = (TextView) findViewById(R.id.txtDiseaseLearning);
//        txtDescriptionLearning = (TextView) findViewById(R.id.txtDescriptionLearning);
//
//        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float x = txtDiseaseLearning.getScaleX();
//                float y = txtDiseaseLearning.getScaleY();
//
//                txtDiseaseLearning.setScaleX((int)(x+1));
//                txtDiseaseLearning.setScaleY((int)(y+1));
//                txtDescriptionLearning.setScaleX((int)(x+1));
//                txtDescriptionLearning.setScaleY((int)(y+1));
//            }
//        });
//        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                float x = txtDiseaseLearning.getScaleX();
//                float y = txtDiseaseLearning.getScaleY();
//
//                txt.setScaleX((int)(x-1));
//                txt.setScaleY((int)(y-1));
//            }
//        });

        //******************************************************************************get the data
        imgViewDiseaseInfo1 = (ImageView) findViewById(R.id.imgViewDiseaseInfo1);
        imgViewDiseaseInfo2 = (ImageView) findViewById(R.id.imgViewDiseaseInfo2);
        imgViewDiseaseInfo3 = (ImageView) findViewById(R.id.imgViewDiseaseInfo3);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemTitle = bundle.getString("title");

        //******************************************************************************display data
        txtDiseaseLearning = (TextView) findViewById(R.id.txtDiseaseLearning);
        txtDiseaseLearning.setText(itemTitle);
        txtDescriptionLearning = (TextView) findViewById(R.id.txtDescriptionLearning);
        fetchData();


//        pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.acne1);
//        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//        imgViewDiseaseInfo1.setImageDrawable(roundedBitmapDrawable);
//        roundedBitmapDrawable.setCircular(true);
        imgViewDiseaseInfo1.setScaleType(ImageView.ScaleType.FIT_XY);
        imgViewDiseaseInfo2.setScaleType(ImageView.ScaleType.FIT_XY);
        imgViewDiseaseInfo3.setScaleType(ImageView.ScaleType.FIT_XY);
        //        ********#################################################################################img herbal
        if (txtDiseaseLearning.getText().toString().toLowerCase().equals("acne")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.acne1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.acne2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.acne3);
        }
       else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("sunburn or erythema")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.sunburn1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.sunburn2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.sunburn3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("underarm or body odor ")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.underarm1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.underarm2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.underarm3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("allergy")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.allergy1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.allergy2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.allergy3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("eczema ")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.eczema1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.eczema2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.eczema3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("dandruff")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.dandruff1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.dandruff2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.dandruff3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("infected mosquito bites ")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.infected1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.infected2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.infected3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("measles")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.measles1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.measles2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.measles3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("chicken pox")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.chickenpox1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.chickenpox2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.chickenpox3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("burn")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.burn1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.burn2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.burn3);
        }

        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("scabies (“galis aso”)")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.scabies1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.scabies2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.scabies3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("abscess")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.abscess1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.abscess2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.abscess3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("alopecia areata")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.alopecia1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.alopecia2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.alopecia3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("basal cell carcinoma")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.basal1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.basal2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.basal3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("bowen's disease")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.bowen1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.bowen2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.bowen3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("darier's disease")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.darier1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.darier2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.darier3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("disseminated superficial actinic porokeratosis")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.disseminated1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.disseminated2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.disseminated3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("dystrophic epidermolysis bullosa (deb)")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.dystrophic1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.dystrophic2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.dystrophic3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("fungal infection of the nails")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.fungal1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.fungal2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.fungal3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("hives")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.hives1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.hives2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.hives3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("hyperhidrosis")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.hyperdrosis1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.hyperdrosis2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.hyperdrosis3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("ichthyosis")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.ichthyosis1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.ichthyosis2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.ichthyosis3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("impetigo")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.impetigo1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.impetigo2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.impetigo3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("lichen sclerosus")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.lichen1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.lichen2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.lichen3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("pemphigus vulgaris")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.pemphigus1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.pemphigus2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.pemphigus3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("psoriasis")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.psoriasis1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.psoriasis2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.psoriasis3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("rosacea")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.rosacea1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.rosacea2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.rosacea3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("sweet's syndrome")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.sweet1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.sweet2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.sweet3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("underarm or body odor")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.underarm1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.underarm2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.underarm3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("vitiligo")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.vitiligo1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.vitiligo2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.vitiligo3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("warts")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.warts1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.warts2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.warts3);
        }
        else if (txtDiseaseLearning.getText().toString().toLowerCase().equals("xeroderma pigmentosum (xp).")){
            imgViewDiseaseInfo1.setImageResource(R.drawable.xeroderma1);
            imgViewDiseaseInfo2.setImageResource(R.drawable.xeroderma2);
            imgViewDiseaseInfo3.setImageResource(R.drawable.xeroderma3);
        }
    }

    public void fetchData() {
        learningDbHelper = new LearningDbHelper(this);
        try {

            learningDbHelper.createDataBase();
            learningDbHelper.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
        }

        SQLiteDatabase sd = learningDbHelper.getReadableDatabase();
        Cursor cursor = sd.query("tbl_Learning" ,null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            txtCheckerLearning.setText(cursor.getString(cursor.getColumnIndex("diseaseName")));
            if (txtCheckerLearning.getText().toString().toUpperCase().equals(txtDiseaseLearning.getText().toString().toUpperCase())) {
                txtDescriptionLearning.setText(cursor.getString(cursor.getColumnIndex("description")));

                txtHereditaryL.setText(cursor.getString(cursor.getColumnIndex("hereditary")));
                txtcauseL.setText(cursor.getString(cursor.getColumnIndex("cuase")));
                txtlooklikeL.setText(cursor.getString(cursor.getColumnIndex("looklike")));
                txtDiagnosedL.setText(cursor.getString(cursor.getColumnIndex("diagnosed")));
                txtCureL.setText(cursor.getString(cursor.getColumnIndex("cured")));
                txtSymptomsL.setText(cursor.getString(cursor.getColumnIndex("symptoms")));
                txtTreatmentL.setText(cursor.getString(cursor.getColumnIndex("treatments")));
                txtAvoidL.setText(cursor.getString(cursor.getColumnIndex("Avoid")));
            }
        }
    }

    boolean viewed = false;
    public void viewDiseasePic1 (View view) {
        Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        if (viewed == false){
            viewed = true;
            imgViewDiseaseInfo1.startAnimation(animationIn);
        } else {
            viewed = false;
            imgViewDiseaseInfo1.startAnimation(animationOut);
        }
    }


    public void viewDiseasePic2 (View view) {
        Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        if (viewed == false){
            viewed = true;
            imgViewDiseaseInfo2.startAnimation(animationIn);
        } else {
            viewed = false;
            imgViewDiseaseInfo2.startAnimation(animationOut);
        }
    }


    public void viewDiseasePic3 (View view) {
        Animation animationIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        Animation animationOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        if (viewed == false){
            viewed = true;
            imgViewDiseaseInfo3.startAnimation(animationIn);
        } else {
            viewed = false;
            imgViewDiseaseInfo3.startAnimation(animationOut);
        }
    }

    @Override
    public void onInit(int status) {
        //Put Codes
    }

    //*******************************************************Function for inserting data to bookmark
    public void btnSaveBookmarkLearning(View v){

    }

    public void AddData(String newEntry, String type) {

        boolean insertData = bookmarkDatabaseHelper.insertData(newEntry, type);

        if(insertData==true){
            speakOutNow(txtDiseaseLearning.getText().toString()  + " successfully added to bookmark.");
        }else{
            speakOutNow("Something went wrong while adding the data.");
        }
    }


    //********************************************************************************Text to speech
    private void speakOutNow (String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);

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
            String type = "disease";
            Cursor data = bookmarkDatabaseHelper.getAllData();
            String newEntryHerbal = txtDiseaseLearning.getText().toString();
            boolean found = false;
            //******************************************************************************************
            if (data != null && data.getCount() > 0) {
                data.moveToFirst();
                while (!data.isAfterLast()) {
                    txtCheckerLearning.setText(data.getString(1).toString().toUpperCase());
                    if (txtCheckerLearning.getText().toString().equals(txtDiseaseLearning.getText().toString().toUpperCase())){
                        found = true;
                    }
                    data.moveToNext();
                }
                //******************************************************************************************
            }
            if (found == true){
                speakOutNow("This Disease is already in Bookmark.");
            }else {
                AddData(newEntryHerbal, type);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
//    ###########################################################*****************************************End

}
