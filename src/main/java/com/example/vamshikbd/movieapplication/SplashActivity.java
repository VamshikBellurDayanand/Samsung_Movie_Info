package com.example.vamshikbd.movieapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);

        actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        } else {
            Log.i(TAG, "Action Bar is not loaded");
        }

        if(!isOnline(SplashActivity.this)) {
            Toast.makeText(this, "No internet Connection.", Toast.LENGTH_SHORT).show();
            createAlertDialog("No internet Connection.", "Please Check your intenet connection");
        } else {
            Thread splash_screen_thread = new Thread(){
                @Override
                public void run() {
                    try {
                        sleep(2000);
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        Log.e(TAG, "Exception in splash screen loading");
                        e.printStackTrace();
                    }
                }
            };
            splash_screen_thread.start();
        }

    }

    /*
        This method checks whether internet connection is there or not.
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /*
        This method creates a dialog to show the erro message
     */
    public void createAlertDialog(String title, String message) {
        android.support.v7.app.AlertDialog.Builder alert= new android.support.v7.app.AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        alert.create().show();
    }
}
