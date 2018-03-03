package com.example.thea.wecare;

//

import java.util.ArrayList;
import java.util.Locale;

import android.media.Image;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v7.app.AppCompatActivity;
//

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Procedure extends AppCompatActivity implements RecognitionListener, TextToSpeech.OnInitListener{

    LinearLayout linearLayout;
    ListView listView;
    CustomAdapterMessage adapter;
    public static ArrayList<ConsultMessage> data;
//
// FOR TTS
private TextToSpeech tts;
    private TextView returnedText;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    String answer, keyWord, myQuestion;
    int questionNumber, toggleButtonClick;
    private TextView questionLoaderTxt;
    //
//

    ImageView diseasePicture;

    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> title1=new ArrayList<String>();

    HerbalDbHelper herbalDbHelper;
    LearningDbHelper learningDbHelper;
    TextView diseasesNameProcedure, txtDiseaseName, txtDiseaseDescription, txtHerbalName, txtHerbalSciName, txtHerbalDescription,
            txtHerbalProcedure, txtHerbalDays, txtDaysProcedure, txtHereditary, txtCause, txtLooklike, txtDiagnosed, txtSymptoms;
    String titleName;
    UserDiseaseDbHelper userDiseaseDbHelper;
    final static int RQS_1 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_procedure);

        diseasePicture = (ImageView) findViewById(R.id.baymaxBodyProcedure);
        diseasePicture.setScaleType(ImageView.ScaleType.FIT_XY);

        linearLayout = (LinearLayout) findViewById(R.id.mLayout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Consult");
        //
        tts = new TextToSpeech(this, this);
        questionLoaderTxt = (TextView) findViewById(R.id.questionLoadertxt);
        questionNumber = 0;
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
//                    progressBar.setVisibility(View.VISIBLE);
//                    progressBar.setIndeterminate(true);
//                    speech.startListening(recognizerIntent);
                    questionLoader();
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                    questionLoaderTxt.setText("");
//                    toggleButtonEnabler();
                }
            }
        });
//


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        titleName = bundle.getString("title");
        userDiseaseDbHelper = new UserDiseaseDbHelper(this);
        txtDiseaseDescription = (TextView) findViewById(R.id.txtDiseaseDescription);
        txtHerbalProcedure = (TextView) findViewById(R.id.txtHerbalProcedure);
        txtHerbalDays = (TextView) findViewById(R.id.txtHerbalDays);
        txtHerbalName = (TextView) findViewById(R.id.txtHerbalName);
        txtHerbalSciName = (TextView) findViewById(R.id.txtHerbalSciName);
        txtHerbalDescription = (TextView) findViewById(R.id.txtHerbalDescription);
        txtDaysProcedure = (TextView) findViewById(R.id.txtDaysProcedure);
        diseasesNameProcedure = (TextView) findViewById(R.id.diseasesNameProcedure);

        txtHereditary = (TextView) findViewById(R.id.txtHereditary);
        txtCause = (TextView) findViewById(R.id.txtcause);
        txtLooklike = (TextView) findViewById(R.id.txtlooklike);
        txtDiagnosed = (TextView) findViewById(R.id.txtDiagnosed);
        txtSymptoms = (TextView) findViewById(R.id.txtSymptoms);

        diseasesNameProcedure.setText(titleName);
        txtDiseaseName = (TextView) findViewById(R.id.txtDiseaseName);
        txtDiseaseName.setText(titleName);
        herbalDbHelper = new HerbalDbHelper(this);
        learningDbHelper = new LearningDbHelper(this);
        fetchData();

        if (txtDiseaseName.getText().toString().toLowerCase().equals("acne")){
            diseasePicture.setImageResource(R.drawable.acne1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("sunburn or erythema")){
            diseasePicture.setImageResource(R.drawable.sunburn1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("underarm or body odor")){
            diseasePicture.setImageResource(R.drawable.underarm1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("allergy")){
            diseasePicture.setImageResource(R.drawable.allergy1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("eczema ")){
            diseasePicture.setImageResource(R.drawable.eczema1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("dandruff")){
            diseasePicture.setImageResource(R.drawable.dandruff1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("infected mosquito bites ")){
            diseasePicture.setImageResource(R.drawable.infected1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("measles")){
            diseasePicture.setImageResource(R.drawable.measles1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("chicken pox")){
            diseasePicture.setImageResource(R.drawable.chickenpox1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("burn")){
            diseasePicture.setImageResource(R.drawable.burn1);
        }
        else if (txtDiseaseName.getText().toString().toLowerCase().equals("scabies (“galis aso”)")){
            diseasePicture.setImageResource(R.drawable.scabies1);
        }
//----------------------------------------------------------------------------------------------------Message
        listView = (ListView)findViewById(R.id.listView);
        data= new ArrayList<ConsultMessage>();
//        load();
    }
//    public void load(){
//        ArrayList<String> title=new ArrayList<String>();
//        ArrayList<String> title1=new ArrayList<String>();
//        title.add("item1");
//        title.add("item2");
//        title.add("item3");
//        title1.add("item11");
//        title1.add("item12");
//        title1.add("item13");
//
//        for (int i = 0; i < title.size(); i++) {
//            data.add(new ConsultMessage(title.get(i), title1.get(i)));
//        }
//        adapter =  new CustomAdapterMessage(this, R.layout.activity_consult_message, data);
//        listView.setAdapter(adapter);
//    }


    //
    public void toggleButtonEnabler(){

        new CountDownTimer(3000, 1000){
            public void onTick(long millisIntilFinished){
//                            DO NOTHING
            }
            public void onFinish(){
                toggleButton.setChecked(true);
            }
        }.start();
    }

    public void questionLoader(){
        if (questionNumber == 0){
            questionLoaderTxt.setText("Are you sure that this is what you feel?");
            myQuestion = "Are you sure that this is what you feel?";
            speakOutNow(questionLoaderTxt.getText().toString());
            boolean listeningToTTS = false;
            do {
                if (tts.isSpeaking()) {
                } else {
                    listeningToTTS = true;
                    micOpenner();
                }
            } while (listeningToTTS == false);
        }else if (questionNumber == 1){
            if(keyWord.equals("yes")) {
                questionLoaderTxt.setText("How long do you have this disease?");
                myQuestion = "How long do you have this disease?";
                speakOutNow(questionLoaderTxt.getText().toString());
                boolean listeningToTTS = false;
                do {
                    if (tts.isSpeaking()) {
                    } else {
                        listeningToTTS = true;
                        micOpenner();
                    }
                } while (listeningToTTS == false);
            }else if(keyWord.equals("not")) {
                questionLoaderTxt.setText("Please read the information again and confirm if this is what you are experiencing");
                myQuestion = "Please read the information again and confirm if this is what you are experiencing";
                speakOutNow(questionLoaderTxt.getText().toString());
//                back to consult
            }
        }else if (questionNumber == 2){
            if (keyWord.equals("next")) {
                questionLoaderTxt.setText("Read and do the procedure until the given time, and get back to me after your " +
                        "medication.");
                myQuestion = "Read and do the procedure until the given time, and get back to me after your " +
                        "medication.";
//                SAVE DISEASE TO DATABASE THEN GO TO MONITORING
                dialogYes();
            }
        }else if (questionNumber == 3){
            micOpenner();
            questionLoaderTxt.setText("Okay, goodbye");
        }
    }


    public void answerChecker() {
        boolean done = false, foundKeyWord = false, saveDisease = false;
        String[] arrAnswer = answer.split(" ");

        for (String arry : arrAnswer) {
            if ((arry.equals("f***"))) {
                toastThis("That is not a good word to say! -_-");
            }
        }
        int arrPostition = 0;

        if (questionNumber == 0) {
            do {
                if (arrPostition == arrAnswer.length) {
                    done = true;
                } else {
                    if (arrAnswer[arrPostition].equals("not") || arrAnswer[arrPostition].equals("no")) {
                        done = true;
                        keyWord = "not";
                        speakOutNow("Please read the information again and confirm if this is what you are experiencing");
                        foundKeyWord = true;
                    } else {
                        if (arrAnswer[arrPostition].equals("yes") || arrAnswer[arrPostition].equals("yeah")) {
                            keyWord = "yes";
                            foundKeyWord = true;
                            saveDisease = true;
                        }
                    }
                }
                arrPostition++;
            } while (done == false);

        } else if (questionNumber == 1) {

            if (keyWord.equals("yes")) {
                do {


                    if (arrPostition == arrAnswer.length) {
                        done = true;
                    } else {
                        if (arrAnswer[arrPostition].equals("7")) {
                            done = true;
                            foundKeyWord = true;
                            speakOutNow("sorry to say that we can't help you, because have your disease for too long. " +
                                    "you might need to consult for skin experts");
                        } else if (arrAnswer[arrPostition].equals("week")){

                            int newPosition = 0;
                            do {
                                if (arrAnswer[newPosition].equals("less")) {
                                    done = true;
                                    saveDisease = true;
                                    foundKeyWord = true;
                                    keyWord = "next";
                                }
                                newPosition++;
                            }while (newPosition != arrAnswer.length);

                        } else if (arrAnswer[arrPostition].equals("weeks") || arrAnswer[arrPostition].equals("month")
                                || arrAnswer[arrPostition].equals("months") || arrAnswer[arrPostition].equals("year")
                                || arrAnswer[arrPostition].equals("years")) {
                            done = true;
                            foundKeyWord = true;
                            speakOutNow("sorry to say that we can't help you, because have your disease for too long. " +
                                    "you might need to consult for skin experts");
                        } else if (arrAnswer[arrPostition].equals("day") || arrAnswer[arrPostition].equals("days")){

                            int newPosition = 0;
                            do {
                                if (arrAnswer[newPosition].equals("1") || arrAnswer[newPosition].equals("2")
                                        || arrAnswer[newPosition].equals("3") || arrAnswer[newPosition].equals("4")
                                        || arrAnswer[newPosition].equals("5") || arrAnswer[newPosition].equals("6")) {
                                    done = true;
                                    saveDisease = true;
                                    foundKeyWord = true;
                                    keyWord = "next";
                                } else if (arrAnswer[newPosition].equals("7") || arrAnswer[newPosition].equals("8")
                                        || arrAnswer[newPosition].equals("9") || arrAnswer[newPosition].equals("10")
                                        || arrAnswer[newPosition].equals("11") || arrAnswer[newPosition].equals("12")){
                                    done = true;
                                    foundKeyWord = true;
                                    speakOutNow("sorry to say that we can't help you, because have your disease for too long. " +
                                            "you might need to consult for skin experts");
                                }
                                newPosition++;
                            }while (newPosition != arrAnswer.length);

                        }
                    }
                    arrPostition++;
                }
                while (done == false);
            }
        }

        if (foundKeyWord == true) {
            //do nothing
            toastThis(keyWord);
        } else {
            speakOutNow("Please answer the question right. click the button to continue, or press back to get back to" +
                    "consult");
            myQuestion = "Please answer the question right. click the button to continue, or press back to get back to" +
                    "consult";
            questionNumber = questionNumber - 1;
        }

        if (keyWord == "next"){
//            SAVE  TO DATABASE
//            HERE
            dialogYes();
        }
//        IF MUST PROCEED TO SAVE THE DISEASE
        if (saveDisease == true) {
            toggleButtonEnabler();
        }

//        ArrayList<String> title=new ArrayList<String>();
//        ArrayList<String> title1=new ArrayList<String>();
//        title.add(myQuestion);
//        title1.add(answer);
//        for (int i = 0; i < title.size(); i++) {
//            data.add(new ConsultMessage(title.get(i), title1.get(i)));
//        }
//        adapter =  new CustomAdapterMessage(this, R.layout.activity_consult_message, data);
//        listView.setAdapter(adapter);

        questionNumber++;
    }

    public void micOpenner(){
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        speech.startListening(recognizerIntent);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(1);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        returnedText.setText(errorMessage);
        toggleButton.setChecked(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        for (String result : matches)
            text += result;
        returnedText.setText(text);
        answer = text;

        title.add(myQuestion);
        title1.add(answer);
        data.clear();
        for (int i = 0; i < title.size(); i++) {
            data.add(new ConsultMessage(title.get(i), title1.get(i)));
        }
        adapter =  new CustomAdapterMessage(this, R.layout.activity_consult_message, data);
        listView.setAdapter(adapter);

        answerChecker();
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }


    //    TTS PART
    @Override
    public void onInit(int text) {

        if (text == TextToSpeech.SUCCESS){

            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED){
//            speakOutNow("Hi!");
            }
            else{
            }
        }
        else{
        }
    }
    private void speakOutNow (String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }

    //






        public void fetchData() {
            learningDbHelper = new LearningDbHelper(this);
            try {

                learningDbHelper.createDataBase();
                learningDbHelper.openDataBase();

            } catch (Exception e) {
                e.printStackTrace();
            }

            SQLiteDatabase myLearningDbHelper = learningDbHelper.getReadableDatabase();
            Cursor cursor = myLearningDbHelper.query("tbl_Learning" ,null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("diseaseName")).toLowerCase().equals(titleName.toLowerCase())) {
                    txtDiseaseDescription.setText(cursor.getString(1).toString()+System.getProperty("line.separator"));
                    txtHereditary.setText(cursor.getString(2).toString());
                    txtCause.setText(cursor.getString(3).toString());
                    txtLooklike.setText(cursor.getString(4).toString());
                    txtDiagnosed.setText(cursor.getString(5).toString());
                    txtSymptoms.setText(cursor.getString(7).toString());
                    txtHerbalName.setText(cursor.getString(10).toString());
                    txtHerbalDescription.setText(cursor.getString(11).toString()+System.getProperty("line.separator"));
                    txtHerbalProcedure.setText(cursor.getString(12).toString()+System.getProperty("line.separator"));
                    txtHerbalDays.setText("Do this within "+cursor.getString(13).toString()+"-8 Days");
                    txtDaysProcedure.setText(cursor.getString(13).toString());
                }
            }
    }

    public void btnConfirmDisease(View v) {
//        CALL FUNCTION FOR MIC
    }

    public  void dialogYes(){
        boolean result = false;
        String diseaseToConfirm, diseaseName, diseaseDescription, herbalName, herbalDescription, herbalProcedure, herbalDays;
        SQLiteDatabase myUserDiseaseDbHelper = userDiseaseDbHelper.getReadableDatabase();
        Cursor cursorDisease = myUserDiseaseDbHelper.query("UserDisease_table", null, null, null, null, null, null);
        int num = 1;

        while (cursorDisease.moveToNext()) {
            num++;
            diseaseToConfirm = cursorDisease.getString(cursorDisease.getColumnIndex("DISEASENAME")).toString().toString().toLowerCase();
            if (txtDiseaseName.getText().toString().toLowerCase().equals(diseaseToConfirm)) {
                result = true;
//                speakOutNow("Disease is already in monitoring. check or go to monitoring.");
            }
        }
        if (num < 4) {
            if (result == false) {

                speakOutNow("follow the instruction until the given time, then get back to me after the medication and if" +
                        " there's something went wrong.");
                myQuestion = "follow the instruction until the given time, then get back to me after the medication and if" +
                        " there's something went wrong.";
                //===================================================================================
                String time;
                /*Date*/ Calendar currentTime = Calendar.getInstance()/*.getTime()*/;
                SimpleDateFormat df = new SimpleDateFormat("dd");
                String formattedDate = df.format(currentTime.getTime());
                time = formattedDate.toString();
                double d = Double.valueOf(String.valueOf(time));
                int dd = (int) d;
                time = String.valueOf( (int) d);
                int hday = 0;
                hday = Integer.parseInt(txtDaysProcedure.getText().toString());

                Calendar calNow = Calendar.getInstance();
                Calendar calSet = (Calendar) calNow.clone();
calSet.set(calNow.get(Calendar.YEAR), calNow.get(Calendar.MONTH), (calNow.get(Calendar.DATE)/*+1hday*/),
        calNow.get(Calendar.HOUR_OF_DAY), calNow.get(Calendar.MINUTE)+1, 0000);
                if(calSet.compareTo(calNow) <= 0){
                    //Today Set time passed, count to tomorrow
                    calSet.add(Calendar.DATE, 1);
                }
                setAlarm(calSet);

                diseaseName = txtDiseaseName.getText().toString();
                diseaseDescription = txtDiseaseDescription.getText().toString();
                herbalName = txtHerbalName.getText().toString();
                herbalDescription = txtHerbalDescription.getText().toString();
                herbalProcedure = txtHerbalProcedure.getText().toString();
//
                double days = Double.valueOf(String.valueOf(txtDaysProcedure.getText()));
                double daysTo = Double.valueOf(String.valueOf(time));
                double total = days + daysTo;
//
                herbalDays = String.valueOf( (int) total);
////                speakOutNow("do the procedure, for "+txtDaysProcedure.getText()+" days");
        userDiseaseDbHelper.insertData(diseaseName, diseaseDescription, herbalName, herbalDescription, herbalProcedure, herbalDays);
                Intent intent = new Intent(this, Monitoring.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, Monitoring.class);
                startActivity(intent);
            }
        }else{
            speakOutNow("sorry, we can't add your disease in monitoring. because we care only 3 diseases at the same time");
        }

    }
    public  void dialogNo(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }


    private void setAlarm(Calendar targetCal){
        Intent intent = new Intent(getBaseContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0 /*MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT*/);
        intent.putExtra("extra", "alarm on");
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }



    public void toastThis(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

public void openlist (View view){
        linearLayout.setVisibility(View.VISIBLE);
}public void close (View view){
        linearLayout.setVisibility(View.GONE);
    }
}
