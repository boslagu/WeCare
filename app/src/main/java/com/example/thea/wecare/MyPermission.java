package com.example.thea.wecare;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by Tottie on 1/27/2018.
 */

public class MyPermission {
    public static final int RECORD_AUDIO_REQUEST = 0;
    public static final int INTERNET_REQUEST = 0;
    Activity activity;
    Context context;
    public  MyPermission(Activity activity){
        this.activity = activity;
        this.context = activity;
    }
    public boolean checkPermissionAudioRecord(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED){
            return  true;
        } else {
            return false;
        }
    }
    public boolean checkPermissionInternet(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);
        if (result == PackageManager.PERMISSION_GRANTED){
            return  true;
        } else {
            return false;
        }
    }

    public void requestPermissionAudioRecord(int requestCode){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)){
            Toast.makeText(context.getApplicationContext(), "Permission needed", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST);
        }
    }
    public void requestPermissionInternet(int requestCode){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.INTERNET)){
            Toast.makeText(context.getApplicationContext(), "Permission needed", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET}, INTERNET_REQUEST);
        }
    }
}
