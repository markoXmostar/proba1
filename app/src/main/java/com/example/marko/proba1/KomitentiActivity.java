package com.example.marko.proba1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Map;

public class KomitentiActivity extends AppCompatActivity {

    private ListView komitentiListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komitenti);
        komitentiListView=(ListView)findViewById(R.id.komitentiListView);
        komitentiListView.setItemsCanFocus(false);

        //komitentiListView.setLongClickable(true);


        /*
        komitentiListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub


                Komitent myKom=(Komitent) arg0.getItemAtPosition(pos);

                Toast.makeText(arg1.getContext(), myKom.getCompanyName(), Toast.LENGTH_LONG).show();

                return true;
            }
        });
*/
        komitentiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
        komitentiListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), "Long Click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        UcitajListuIzBaze();

    }

    private void UcitajListuIzBaze(){

        ListKomitentAdapter listKomitentAdapter=new ListKomitentAdapter(this,R.layout.row_komitent);
        komitentiListView.setAdapter(listKomitentAdapter);
        Log.i("Marko LOG"," ucitavam bazu!");

        SQLiteDatabase myDB=this.openOrCreateDatabase(MainActivity.myDATABASE,this.MODE_PRIVATE,null);
        Cursor c=myDB.rawQuery("SELECT * FROM komitenti",null);

        Log.i("Marko LOG"," Baza učitana!");

        int IdIndex=c.getColumnIndex("Id");
        int CompanyNameIndex=c.getColumnIndex("CompanyName");
        int ContactTitleIndex=c.getColumnIndex("ContactTitle");
        int AddressIndex=c.getColumnIndex("Address");
        int CityIndex=c.getColumnIndex("City");
        int PostalCodeIndex=c.getColumnIndex("PostalCode");
        int CountryIndex=c.getColumnIndex("Country");
        int PhoneIndex=c.getColumnIndex("Phone");
        int FaxIndex=c.getColumnIndex("Fax");

        c.moveToFirst();
        int brojac=0;
        for (int j=0;j<c.getCount();j++){
            String Id;
            String CompanyName;
            String ContactTitle;
            String Address;
            String City;
            String PostalCode;
            String Country;
            String Phone;
            String Fax;

            Id=c.getString(IdIndex);
            CompanyName=c.getString(CompanyNameIndex);
            ContactTitle=c.getString(ContactTitleIndex);
            Address=c.getString(AddressIndex);
            City=c.getString(CityIndex);
            PostalCode=c.getString(PostalCodeIndex);
            Country=c.getString(CountryIndex);
            Phone=c.getString(PhoneIndex);
            Fax=c.getString(FaxIndex);

            Log.i("Marko LOG"," Red " + Integer.toString(brojac));
            Komitent komitentProvider=new Komitent(Id,CompanyName,ContactTitle,Address,City,PostalCode,Country,Phone,Fax);

            listKomitentAdapter.add(komitentProvider);
            brojac++;
            if (j!=c.getCount()){
                c.moveToNext();
            }
        }
        c.close();
    }
}
