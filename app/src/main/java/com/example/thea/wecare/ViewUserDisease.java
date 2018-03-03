package com.example.thea.wecare;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.Locale;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.support.v7.app.AppCompatActivity;

public class ViewUserDisease extends AppCompatActivity implements RecognitionListener, TextToSpeech.OnInitListener{

    LinearLayout linearLayout;

    ArrayList<String> title=new ArrayList<String>();
    ArrayList<String> title1=new ArrayList<String>();

    // FOR TTS
    private TextToSpeech tts;
    private TextView returnedText;
    private ToggleButton toggleButton;
    //    Voice Recognition
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";

    //
    String answer, keyWord, myQuestion;
    int questionNumber;
    private TextView questionLoaderTxt;
    //



    String itemTitle;
    UserDiseaseDbHelper userDiseaseDbHelper;
    TextView txtDiseaseTitle, txtDiseaseInfo, txtDays, txtNameChecker;

    ListView listView;
    CustomAdapterMonitoringMessage adapter;
    public static ArrayList<MonitoringMessage> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_view_user_disease);

        questionLoaderTxt = (TextView) findViewById(R.id.questionLoadertxt);
        questionNumber = 0;
        //

        linearLayout = (LinearLayout) findViewById(R.id.mLayout);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);

        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.INVISIBLE);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.getDefault());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000);


        userDiseaseDbHelper = new UserDiseaseDbHelper(this);
        tts = new TextToSpeech(this, this);
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR){
                    tts.setLanguage(Locale.getDefault());
                    fetchData();
                }
            }
        });

        //******************************************************************************get the data
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        itemTitle = bundle.getString("title");

        //******************************************************************************display data
        txtDiseaseTitle = (TextView) findViewById(R.id.txtDiseaseTitle);
        txtDiseaseTitle.setText(itemTitle);
        txtDiseaseInfo = (TextView) findViewById(R.id.txtDiseaseInfo);
        txtNameChecker = (TextView) findViewById(R.id.txtNameChecker);
        txtDays = (TextView) findViewById(R.id.txtDays);
//        fetchData();

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
                }
            }
        });
        listView = (ListView)findViewById(R.id.listView);
        data= new ArrayList<MonitoringMessage>();
//        load();

        linearLayout.setVisibility(View.VISIBLE);
    }
//    ------------------------------------------------------------------------------------------Message
//public void load(){
//    ArrayList<String> title=new ArrayList<String>();
//    ArrayList<String> title1=new ArrayList<String>();
//    title.add("item1");
//    title.add("item2");
//    title.add("item3");
//    title1.add("item11");
//    title1.add("item12");
//    title1.add("item13");
//
//}

    //    ##########################################################################################################################
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
            questionLoaderTxt.setText("welcome back, you have come so early, are you feeling alright?");
            myQuestion = "welcome back, you have come so early, are you feeling alright?";
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
            if(keyWord.equals("not")) {
                questionLoaderTxt.setText("did you follow the instructions?");
                myQuestion = "did you follow the instructions?";
                speakOutNow(questionLoaderTxt.getText().toString());
                boolean listeningToTTS = false;
                do {
                    if (tts.isSpeaking()) {
                    } else {
                        listeningToTTS = true;
                        micOpenner();
                    }
                } while (listeningToTTS == false);
            }
        }
    }


    public void answerChecker() {
        boolean done = false, foundKeyWord = false, saveDisease = false;
        String[] arrAnswer = answer.split(" ");

        for (String arry : arrAnswer) {
            if ((arry.equals("f***"))) {
                toastThis("That is not a good word to say!");
                myQuestion = "That is not a good word to say!";
            }
        }
        int arrPostition = 0;

        if (questionNumber == 0) {
            do {
                if (arrPostition == arrAnswer.length) {
                    done = true;
                } else {
                    if (arrAnswer[arrPostition].equals("yes") || arrAnswer[arrPostition].equals("yeah")) {
                        done = true;
                        keyWord = "yes";
                        speakOutNow("that's good, just continue the medication until the given time");
                        myQuestion = "that's good, just continue the medication until the given time";

//                        insert
                        userDiseaseDbHelper = new UserDiseaseDbHelper(this);
                        SQLiteDatabase sd = userDiseaseDbHelper.getReadableDatabase();
                        Cursor cursor = sd.query("UserDisease_table" ,null, null, null, null, null, null);
                        while (cursor.moveToNext()) {
                            txtNameChecker.setText(cursor.getString(cursor.getColumnIndex("DISEASENAME")));
                            if (txtNameChecker.getText().toString().toUpperCase().equals(txtDiseaseTitle.getText().toString().toUpperCase())) {
                                myQuestion = cursor.getString(cursor.getColumnIndex("HERBALPROCEDURE"));
                            }
                        }

                        title.add(myQuestion);
                        title1.add("");
                        data.clear();

                        for (int i = 0; i < title.size(); i++) {
                            data.add(new MonitoringMessage(title.get(i), title1.get(i)));
                        }
                        adapter =  new CustomAdapterMonitoringMessage(this, R.layout.activity_monitoring_message, data);
                        listView.setAdapter(adapter);
                        foundKeyWord = true;
                    } else {
                        if (arrAnswer[arrPostition].equals("not") || arrAnswer[arrPostition].equals("no")) {
                            keyWord = "not";
                            foundKeyWord = true;
                            saveDisease = true;
                        }
                    }
                }
                arrPostition++;
            } while (done == false);

        } else if (questionNumber == 1) {

            if (arrAnswer[arrPostition].equals("not") || arrAnswer[arrPostition].equals("no")) {

                foundKeyWord = true;
                speakOutNow("I suggest you to do the right procedure until the given time and " +
                        "get back to me after your medication.");
                myQuestion = "I suggest you to do the right procedure until the given time and " +
                        "get back to me after your medication.";

                title.add(myQuestion);
                title1.add("");
                data.clear();

                for (int i = 0; i < title.size(); i++) {
                    data.add(new MonitoringMessage(title.get(i), title1.get(i)));
                }
                adapter =  new CustomAdapterMonitoringMessage(this, R.layout.activity_monitoring_message, data);
                listView.setAdapter(adapter);
            } else if (arrAnswer[arrPostition].equals("yeah") || arrAnswer[arrPostition].equals("yes")) {

                foundKeyWord = true;
                speakOutNow("you need to stop the medication because this herbal can't handle your" +
                        " problem, I advice you to consult for expert.");
                myQuestion = "you need to stop the medication because this herbal can't handle your" +
                        " problem, I advice you to consult for expert.";
//                delete disease to database
                String diseaseName = txtDiseaseTitle.getText().toString();
                userDiseaseDbHelper.deleteData(diseaseName);
            }
        }

        if (foundKeyWord == true) {
            //do nothing
            toastThis(keyWord);
        } else {
            speakOutNow("Please answer the question right. click the button to continue, or press back to go to" +
                    "consult");
            myQuestion = "Please answer the question right. click the button to continue, or press back to go to" +
                    "consult";
            questionNumber = questionNumber - 1;
        }

//        IF MUST PROCEED
        if (saveDisease == true) {
            toggleButtonEnabler();
        }
        questionNumber++;
    }

    public void micOpenner(){
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        speech.startListening(recognizerIntent);
    }

//    ##########################################################################################################################

    public void toastThis(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


    public void fetchData() {
        userDiseaseDbHelper = new UserDiseaseDbHelper(this);
        SQLiteDatabase sd = userDiseaseDbHelper.getReadableDatabase();
        Cursor cursor = sd.query("UserDisease_table" ,null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            txtNameChecker.setText(cursor.getString(cursor.getColumnIndex("DISEASENAME")));
            if (txtNameChecker.getText().toString().toUpperCase().equals(txtDiseaseTitle.getText().toString().toUpperCase())) {
                txtDiseaseInfo.setText(cursor.getString(cursor.getColumnIndex("DISEASEDESCRIPTION")));
                txtDays.setText(cursor.getString(cursor.getColumnIndex("HERBALDAYS")));

                Calendar currentTime = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd");
                String formattedDate = df.format(currentTime.getTime());
                if (formattedDate.equals(txtDays.getText())){
//                    #################################################################################################################

                    if (tts.isSpeaking()) {
                        speakOutNow("welcome back. do you feel better now?");
                    }
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    speakOutNow("very well. just keep your skin and body clean.");
                                    String name = txtDiseaseTitle.getText().toString();
                                    int result = userDiseaseDbHelper.deleteData(name);
                                    //remove to database
                                    String diseaseName = txtDiseaseTitle.getText().toString();
                                    userDiseaseDbHelper.deleteData(diseaseName);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    speakOutNow("did you follow the instructions that i have given?");
                                    dialogYes();
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Click Yes/No for your answer").setPositiveButton("yes", dialogClickListener).setNegativeButton("no", dialogClickListener).show();


//                    #################################################################################################################
                } else {
//                    #################################################################################################################
                    questionLoader();
//                    #################################################################################################################
                }
            }
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){

            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
//                fetchData();
            }
            else{

            }

        }
        else{

        }
    }

    private void speakOutNow(String text) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH,null);
    }



    public  void dialogYes(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    speakOutNow("you need to stop the medication and consult to experts, herbals can't handle your skin problem.");
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    speakOutNow("i suggest you to do the right procedure until the given time, please get back to me after your medication.");

                    break;
            }
        }
    };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Click Yes/No for your answer").setPositiveButton("yes", dialogClickListener).setNegativeButton("no", dialogClickListener).show();

    }

    public  void dialogNo(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }



//    ###############################################################################################################################

//    ##########################################################################################################################


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
            data.add(new MonitoringMessage(title.get(i), title1.get(i)));
        }
        adapter =  new CustomAdapterMonitoringMessage(this, R.layout.activity_monitoring_message, data);
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
    public void Openlist (View view){
        linearLayout.setVisibility(View.VISIBLE);
    }public void Close (View view){
        linearLayout.setVisibility(View.GONE);
    }
}
