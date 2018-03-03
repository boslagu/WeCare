package com.example.thea.wecare;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TermofUse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.activity_termof_use);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
    public void philstar (View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.philstar.com/health-and-family/2015/04/07/1440983/your-herbal-supplement-safe-and-effective"));
        startActivity(intent);
    }
}
