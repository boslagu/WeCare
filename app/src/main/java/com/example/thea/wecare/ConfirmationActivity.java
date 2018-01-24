package com.example.thea.wecare;

import java.util.ArrayList;
import java.util.Locale;
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


public class ConfirmationActivity extends AppCompatActivity  implements RecognitionListener, TextToSpeech.OnInitListener{
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
    String answer, keyWord;
    int questionNumber;
    private TextView questionLoaderTxt;
    //


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //
        tts = new TextToSpeech(this, this);
        questionLoaderTxt = (TextView) findViewById(R.id.questionLoadertxt);
        questionNumber = 0;
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

    }


    //

    public void toastThis(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    //
    public void questionLoader(){
        if (questionNumber == 0){
            questionLoaderTxt.setText("Are you sure that this is what you feel?");
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
                speakOutNow(questionLoaderTxt.getText().toString());
//                back to consult
            }
        }else if (questionNumber == 2){
            if (keyWord.equals("next")) {
                questionLoaderTxt.setText("I thought you are finished with that, haven't you?");
//                SAVE DISEASE TO DATABASE
            }
        }else if (questionNumber == 3){
            micOpenner();
            questionLoaderTxt.setText("Okay, goodbye");
        }
    }

    public void answerChecker(){
        boolean done = false, foundKeyWord = false;
        String[] arrAnswer = answer.split(" ");
        for (String arry : arrAnswer){
            if ((arry.equals("f***"))){
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
                        foundKeyWord = true;
                    } else {
                        if (arrAnswer[arrPostition].equals("yes") || arrAnswer[arrPostition].equals("yeah")) {
                            keyWord = "yes";
                            foundKeyWord = true;
                        }
                    }
                }
                arrPostition++;
            } while (done == false);

        }else if(questionNumber == 1) {

            if (keyWord.equals("yes")) {
                do {
                    if (arrPostition == arrAnswer.length) {
                        done = true;
                    } else {
                        if (arrAnswer[arrPostition].equals("7") || arrAnswer[arrPostition].equals("weeks")
                                || arrAnswer[arrPostition].equals("week")) {
                            done = true;
                            foundKeyWord = true;
                            keyWord = "none";
                        } else{
                            if (arrAnswer[arrPostition].equals("less")) {
                                done = true;
                                foundKeyWord = true;
                                keyWord = "next";
                            }
                        }
                    }
                    arrPostition++;
                }
                while (done == false);
            }
        }

        if (foundKeyWord == true){
            //do nothing
            toastThis(keyWord);
        }else{
            toastThis("Please answer the question");
            questionNumber = questionNumber - 1;
        }

        questionNumber++;
    }

    public void micOpenner(){
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        speech.startListening(recognizerIntent);
    }
    //


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

}
