package com.example.ompzu.calculatorui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.Toast.makeText;

/**
 * Created by Ompzu on 4/11/2016.
 */
public class UIReceiver extends BroadcastReceiver {
    public String TAG = "Broadcast UI";

    public void onReceive(Context ctx, Intent i){
    Bundle extras = i.getExtras();
    ArrayList<String> debuggable = new ArrayList<String>();
    if (extras != null){
        debuggable.add(extras.toString());
        MainActivity.saveInput(debuggable);
    }
    Toast.makeText(ctx,"Intent received " + debuggable, Toast.LENGTH_SHORT ).show();
    if(BuildConfig.DEBUG){
        Log.d(TAG, "Broadcast UI onReceive " +  debuggable);
        if(debuggable.isEmpty()){
            Log.d(TAG, "Broadcast failed, instant didn't contain anything stringable" );
        }
    }
    }
}


