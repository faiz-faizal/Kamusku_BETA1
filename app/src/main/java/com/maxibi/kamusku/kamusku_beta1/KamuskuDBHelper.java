package com.maxibi.kamusku.kamusku_beta1;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by User on 8/8/2017.
 */

public class KamuskuDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "kamusku_database.db";
    private static String DB_LOCATION = "";
    private static final int DB_VERSION = 1;
    private static final String TB_NAME ="Word";
    private static final String CLM_MS = "Word_Ms";
    private static final String CLM_EN = "Word_En";
    private static final String CLM_FV = "Word_Favourite";


    private SQLiteDatabase kamuskuDatabase;
    private Context myContext;


    public KamuskuDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        //bagi location untuk database kamusku
        DB_LOCATION = myContext.getDatabasePath(DB_NAME).toString();
    }

    public void createDatabase() throws IOException{
        boolean dbExist = checkKamuskuDatabase();

        if(dbExist){

        }
        else
        {
            this.getWritableDatabase();
            try
            {
                copyDatabase();
            }catch (IOException e){
                throw new Error("Error copying databse");
            }
        }
    }

    private boolean checkKamuskuDatabase(){
        SQLiteDatabase checkDB = null;

        try{
            String location = DB_LOCATION;
            checkDB = SQLiteDatabase.openDatabase(location,null,SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e){

        }


        //dua kali kerja
        if( checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDatabase() throws IOException{
        //Buka db sebagai input stream
        InputStream inStream = myContext.getAssets().open(DB_NAME);

        //Location untuk mencipta db yang kosong
        String outFileName = DB_LOCATION;

        //Buka db yang kosong sebagai output streem
        OutputStream outStream = new FileOutputStream(outFileName);

        //Men transfer bytes dari inputfile ke outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ( ( length = inStream.read(buffer)) > 0){
            outStream.write(buffer, 0 , length);
        }

        // tutup stream
        outStream.flush(); //used to sure that all data from buffer is written
        outStream.close();
        inStream.close();
    }

    public void openDatabase() throws SQLException{
        //bukak database
        String location = DB_LOCATION;
        kamuskuDatabase = SQLiteDatabase.openDatabase(location, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close()
    {
        if( kamuskuDatabase != null)
            kamuskuDatabase.close();

        super.close();
    }
    //untuk create dan upgrade database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
