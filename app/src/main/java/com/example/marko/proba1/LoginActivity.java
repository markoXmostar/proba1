package com.example.marko.proba1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public void onPrijava(View view){
        EditText usernameTextBox;
        EditText passwordTextBox;
        usernameTextBox=(EditText)findViewById(R.id.usernameTextBox);
        passwordTextBox=(EditText)findViewById(R.id.passwordTextBox);
        String username=usernameTextBox.getText().toString();
        String password=passwordTextBox.getText().toString();

        Intent returnIntent = new Intent();

        if (username!="" && password!=""){
            //provjera passworda i usernama
            if (username.equals(password)){
                returnIntent.putExtra("user",username);
                setResult(Activity.RESULT_OK,returnIntent);
            }else{
                returnIntent.putExtra("user","");
                setResult(Activity.RESULT_CANCELED,returnIntent);
            }
        }
        Log.i("MARKO_LOG","Završavam login dialog!");
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Neregularan način izlaska!",Toast.LENGTH_LONG).show();
        //finish(); // finish activity ako nije se logirao!
        //System.exit(1000);
    }
}
