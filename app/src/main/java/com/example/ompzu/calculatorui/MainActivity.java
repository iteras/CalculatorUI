package com.example.ompzu.calculatorui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<String> input = new ArrayList<String>(); //consists of 2 numbers and operation( [1,P,99] is 1 + 99
    private TextView textViewResult; //used in showing text on display
    private String nr = ""; //used in building numbers, as for creating numbers like 22, 99,43435
    private static String showResult=""; //used at end to show result
    private static String showEquation=""; //used at end to show what were in equation
    private static String btnId=""; //used to work with inputs, as input ID's go in this variable
    private static final String TAG = "MainActivity";
    private static ArrayList<String> inputFromReceiver = new ArrayList<String>();
    private static boolean inputReceived = false;


    public static void saveInput (ArrayList<String> str){
        if(BuildConfig.DEBUG){
            Log.d(TAG, "Broadcast received intent saved as: " + str.toString());
        }
        input = str;
        inputReceived = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onCreate called");
        }
    }

    public void buttonClicked(View view){
        Button btn = (Button) view;
        btnId = btn.getResources().getResourceName(btn.getId());
        btnId = btnId.substring(btnId.length() - 1, btnId.length());
        if(BuildConfig.DEBUG){
            Log.d(TAG, "Button clicked:" + btnId);
        }
        display(btnId);
    }


    //broadcasting out
    public void broadcastIntent(ArrayList<String> str){
        Intent intent = new Intent("CustomBroadcast");
        intent.putExtra("result", str);
        intent.setAction("com.SEND_RESULT_FROM_BRAIN");
        if(BuildConfig.DEBUG){
            Log.d(TAG, "Broadcast intent sent with content: " + input.toString());
        }
        sendBroadcast(intent);

    }

       public void display(String in) {
           if(in.length() > 0){ //if received sentence has something then it will be our input
               btnId = in;
           }

        if (btnId.equals("E")) { //input is equals
            if (!nr.isEmpty()) {
                input.add(nr);  //operation inserted, finsih the nr, add to arraylist and clean the string
            } else {
                nr = "";//remove number from memory
            }
        }
        if (!btnId.equals("P") && !btnId.equals("M") && !btnId.equals("X") &&
                !btnId.equals("D") && !btnId.equals("C") && !btnId.equals("E") &&
                !btnId.equals("W") && !btnId.equals("N") && !btnId.equals("S")) { //input is number
            if (btnId.equals("K") && !nr.contains(".")) { //inserts Coma to string
                btnId = ".";
                nr = nr + btnId;
                showEquation = showEquation + btnId;
            } else if (!btnId.equals("K")) {
                if (btnId.equals("0") && !nr.equals("0")) {
                    nr = nr + btnId; //click 7 nr is 7, click 4 and nr is 74, click 1 nr is 741
                    showEquation = showEquation + btnId;
                } else {
                    nr = nr + btnId; //click 7 nr is 7, click 4 and nr is 74, click 1 nr is 741
                    showEquation = showEquation + btnId;
                }
            }
        }

        if (btnId.equals("P") || btnId.equals("M") || btnId.equals("X") || btnId.equals("D") ||
                btnId.equals("W") || btnId.equals("N") || btnId.equals("S")) { //input is operation
            if (!nr.isEmpty()) {
                input.add(nr); //operation pressed, finsih the nr, add to arraylist and clean the string
            }
            if (input.size() == 1) {
                String inputOP = "";
                nr = ""; //remove number from memory
                if(btnId.equals("p")) inputOP = " + ";
                if(btnId.equals("M")) inputOP = " - ";
                if(btnId.equals("X")) inputOP = " * ";
                if(btnId.equals("D")) inputOP = " / ";
                if(btnId.equals("W")) inputOP = " pow ";
                if(btnId.equals("N")) inputOP = " sin ";
                if(btnId.equals("S")) inputOP = " cos ";
                showEquation = showEquation + inputOP; //displays operation marks
                input.add(btnId);
            }
        }

        if (btnId.contains("C")) { //input is Clear function
            input.clear(); //clean arrayList
            inputFromReceiver.clear();
            nr = "";//remove number from memory
            showResult = ""; //clean display result
            showEquation = ""; //clean display equation
            if (BuildConfig.DEBUG) Log.d(TAG, "Array reset: " + input.toString());
        }
        int firstOsCheck = 0;
        int secondOsCheck = 0;
        /*if(input.size() > 0){
            firstOsCheck = CalcEngine.compare(input.get(0)); //returns 0 if string in arraylist slot equals to operation, else its number
        }

        if(input.size() == 3){
            secondOsCheck = CalcEngine.compare(input.get(2)); //returns 0 if string in arraylist slot equals to operation, else its number
        }
*/
        if (input.size() >= 3 && firstOsCheck != 0 && secondOsCheck != 0
                && (input.contains("P") || input.contains("M") || input.contains("X") ||
                input.contains("D") || input.contains("W"))) {
            broadcastIntent(input); //out goes 2 numbers and 1 operation
            if (inputReceived != false){ //if successfully received something then
                inputFromReceiver = input; //save it
            }
/*
            input = CalcEngine.operation(input); //the operation will be done and equation will be calculated
            */
            if(input.size() == 1){

            //TODO
                showResult = inputFromReceiver.get(0);
            }
            nr = "";

        } else if(input.size() == 2 && (btnId.contains("S") ||btnId.contains("N"))){
            broadcastIntent(input); //out goes 2 numbers and 1 operation
            if (inputReceived != false){ //if successfully received something then
                inputFromReceiver = input; //save it
            }
        //    input = CalcEngine.operation(input); //the operation will be done and equation will be calculated
            nr = "";
            if(inputFromReceiver.size() == 1){
                showResult = inputFromReceiver.get(0);

            }
        }


            textViewResult = (TextView) findViewById(R.id.textViewResult);
            if (btnId.equals("E") || (btnId.equals("P") || btnId.equals("M") || btnId.equals("X") || btnId.equals("D") ||
                    btnId.equals("W") || btnId.equals("N") || btnId.equals("S") && input.size() > 2)) { //lets continue calculate w/o pressing "=" but any other
                //operation button

                if (!btnId.equals("E")) {

                    //nr = ""; //seesms it is not used
                    //  input.add(btnId);
                }
                nr = ""; //neccesery for after Equals button pressing, cleans variable "nr"
                if (input.size() == 1) { //if only numeric is inserted then displa
                    showEquation = showEquation + "=" + showResult + " "; //string which is displayed
                }
                textViewResult.setText(showEquation); //displaying
            } else {
                textViewResult.setText(showEquation + " "); //displaying
            }
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Button clicked: " + btnId + " in array: " + input.toString() + " And nr: " + nr);
            }


            //3 If's below change font size according to how long equations are displayed
            if (showEquation.length() > 25) {
                ((TextView) findViewById(R.id.textViewResult)).setTextSize(25);
            }
            if (showEquation.length() > 55) {
                ((TextView) findViewById(R.id.textViewResult)).setTextSize(15);
            }

            if (showEquation.length() < 25) {
                ((TextView) findViewById(R.id.textViewResult)).setTextSize(45);
            }
        }

       /* @Override
        public boolean onCreateOptionsMenu(Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    //}
        @Override
        protected void onStart() {
            super.onStart();
            // The activity is about to become visible.


        }
        @Override
        protected void onResume() {
            super.onResume();
            // The activity has become visible (it is now "resumed").
          /*  textViewResult = (TextView) findViewById(R.id.textViewResult);
            textViewResult.setText(showEquation);*/
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onResume called");
            }
        }
        @Override
        public void onSaveInstanceState(Bundle savedInstanceState){
         /*   savedInstanceState.putStringArrayList("input", input);
            savedInstanceState.putString("nr", nr);*/
            super.onSaveInstanceState(savedInstanceState);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Saving state");
            }
        }
        @Override
        protected void onPause() {
            super.onPause();
            // Another activity is taking focus (this activity is about to be "paused").
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onPause called");
            }

        }

        @Override
        protected void onStop() {
            super.onStop();
            // The activity is no longer visible (it is now "stopped")
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onStop called");
            }

        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // The activity is about to be destroyed.
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "onDestroy called");
            }

        }
    }