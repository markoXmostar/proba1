package com.example.marko.proba1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public  static String myDATABASE="VIPS.db";

    private static int LOGIN_DIALOG = 1000;

    private boolean _isLogged = false;
    public boolean GetLogged() {
        return _isLogged;
    }
    public void SetLogged(boolean logiran) {
        _isLogged = logiran;
        invalidateOptionsMenu();
        if (_isLogged) {
            //kod za logiranog usera

        } else {
            //kod za nelogiranog ili odjavu

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "MainActivity started: onCreate", Toast.LENGTH_LONG).show();
        SetLogged(false);

        if (!GetLogged()) {
            LogirajSe();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.glavni_meni, menu);

        MenuItem item1 = menu.findItem(R.id.app_bar_artikli);
        MenuItem item2 = menu.findItem(R.id.app_bar_komitenti);
        item1.setVisible(GetLogged());
        item2.setVisible(GetLogged());
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.app_bar_login:
                LogirajSe();
                return true;
            case R.id.app_bar_artikli:
                Toast.makeText(this, "DRUGA", Toast.LENGTH_LONG).show();
                ;
                return true;
            case R.id.app_bar_komitenti:

                Intent intent = new Intent(getApplicationContext(), KomitentiActivity.class);
                startActivity(intent);

                return true;
            case R.id.app_bar_sinkronizacija:
                new JsonTask(this).execute("http://northwind.servicestack.net/customers.json");

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void LogirajSe() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        startActivityForResult(intent, LOGIN_DIALOG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LOGIN_DIALOG) {
            if (resultCode == RESULT_OK) {
                Log.i("MARKO_LOG","Login successfull. User name = " + data.getStringExtra("user"));
                SetLogged(true);
            }else{
                Log.i("MARKO_LOG","Login UNsuccessfull. User name = NULL" );
                SetLogged(false);
            }

        }
    }

    //----
    private class JsonTask extends AsyncTask<String, String, String> {


        private ProgressDialog pd;
        private MainActivity myMainActivity;
        public JsonTask(MainActivity activity) {
            myMainActivity=activity;
            pd = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }


        protected String doInBackground(String... params) {


        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            String myUrl=params[0];
            URL url =new URL(myUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");

            }

            return buffer.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


/*
            Log.d("JSON: ", "> " + result);
            JSON_Komitenti=result;
*/
        try {
            JSONObject jObject=new JSONObject(result);
            String komitenti=jObject.getString("Customers");
            JSONArray arr=new JSONArray(komitenti);

            ArrayList<Komitent> ListaKomitenata = new ArrayList<Komitent> ();

            for (int i=0;i<arr.length();i++){
                JSONObject komitent=arr.getJSONObject(i);
                Komitent kom=new Komitent(komitent.optString("Id"),komitent.optString("CompanyName"),komitent.optString("ContactTitle"),
                        komitent.optString("Address"),komitent.optString("City"),komitent.optString("PostalCode",""),komitent.optString("Country",""),
                        komitent.optString("Phone",""),komitent.optString("Fax"));
                ListaKomitenata.add(kom);

            }
            UpisiKomitenteUBazu(ListaKomitenata);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (pd.isShowing()){
            pd.dismiss();
        }

        //txtJson.setText(result);
    }

    private  void UpisiKomitenteUBazu(ArrayList<Komitent> Lista){

        //prvo otvori ili kreiraj bazu komitenata
        Log.i("SQL: ", "Otvaram bazu");

        SQLiteDatabase myDB = myMainActivity.openOrCreateDatabase(myDATABASE, MODE_PRIVATE,null);
        Log.i("SQL: ", "Kreiram tabelu");
        myDB.execSQL("CREATE TABLE IF NOT EXISTS komitenti (Id VARCHAR, CompanyName VARCHAR, " +
                "ContactTitle VARCHAR,Address VARCHAR, City VARCHAR, PostalCode VARCHAR, Country VARCHAR, Phone VARCHAR, Fax VARCHAR);");
        Log.i("SQL: ", "Brišem sve iz tabele");
        myDB.execSQL("DELETE FROM komitenti");
        for (int i=0;i<Lista.size();i++){
            Komitent myKom=Lista.get(i);


            myDB.execSQL("INSERT INTO komitenti (Id, CompanyName , " +
                    "ContactTitle ,Address , City , PostalCode , Country , Phone , Fax ) VALUES ('" + myKom.Id.replaceAll("'", "_") + "','" + myKom.CompanyName.replaceAll("'", "_") + "','" + myKom.ContactTitle.replaceAll("'", "_") +
                    "','" + myKom.Address.replaceAll("'", "_") + "','" + myKom.City.replaceAll("'", "_") +"','" + myKom.PostalCode.replaceAll("'", "_") + "','" + myKom.Country.replaceAll("'", "_") + "','" + myKom.Phone.replaceAll("'", "_") + "','" + myKom.Fax.replaceAll("'", "_") +"');");

        }
        Log.i("SQL: ", "Gotovo");
    }
}


}
