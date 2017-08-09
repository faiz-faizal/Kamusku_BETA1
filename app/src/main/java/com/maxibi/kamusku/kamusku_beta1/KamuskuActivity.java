package com.maxibi.kamusku.kamusku_beta1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class KamuskuActivity extends AppCompatActivity {

    public static RecyclerView.Adapter adapter;
    public RecyclerView.LayoutManager layoutManager;
    private  RecyclerView recyclerView;

    public static ArrayList<WordObject> wData;

    KamuskuDBHelper db;

    ArrayList<String> msCombineList;
    ArrayList<String> enCombineList;

    LinkedHashMap<String,String> nameList;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamusku);

        //Recycleview
        recyclerView = (RecyclerView)findViewById(R.id.rv_kamusku);
        recyclerView.setHasFixedSize(true);

        db = new KamuskuDBHelper(this);

        searchView = (SearchView) findViewById(R.id.sv_kamusku);
        searchView.setQueryHint("Search here");
        searchView.setQueryRefinementEnabled(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        wData = new ArrayList<>();
        fetchData();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();

                final ArrayList<WordObject> filterList = new ArrayList<>();
                for( int i = 0; i < msCombineList.size(); i++)
                {
                    final String text = msCombineList.get(i).toLowerCase();
                    if(text.contains(newText)){
                        filterList.add(new WordObject(msCombineList.get(i), enCombineList.get(i)));

                    }
                }
                adapter = new CusAdapter(filterList);
                recyclerView.setAdapter(adapter);
                return  true;
            }
        });


    }

    private void fetchData() {

        db = new KamuskuDBHelper(this);

        try{
            db.createDatabase();
            db.openDatabase();

        } catch (IOException e) {
            e.printStackTrace();
        }

        nameList = new LinkedHashMap<>();
        int i2;

        SQLiteDatabase sd = db.getReadableDatabase();
        Cursor cursor = sd.query("Word",null, null, null, null,null, null);
        i2 = cursor.getColumnIndex("Word_Ms");
        msCombineList=new ArrayList<String>();
        enCombineList=new ArrayList<String>();

        while( cursor.moveToNext()){
            nameList.put(cursor.getString(i2), cursor.getString(cursor.getColumnIndex("Word_En")));
        }

        Iterator entries = nameList.entrySet().iterator();
        while (entries.hasNext()){
            Map.Entry thisEntry = (Map.Entry) entries.next();
            msCombineList.add(String.valueOf(thisEntry.getKey()));
            enCombineList.add("- "+String.valueOf(thisEntry.getValue()));
        }

        for( int i = 0; i< msCombineList.size();i++){
            wData.add(new WordObject(msCombineList.get(i), enCombineList.get(i)));
        }

        adapter = new CusAdapter(wData);
        recyclerView.setAdapter(adapter);



    }
}
