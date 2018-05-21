package com.example.andrey.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Andrey on 14.03.2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SQLiteDatabase.db";

    public static final String TABLE_NAME = "CITY";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_COUNTRY_NAME = "COUNTRY_NAME";
    public static final String COLUMN_TEMPERATURE = "TEMPERATURE";


    private SQLiteDatabase database;

    public SQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FIRST_NAME + " VARCHAR, "
                + COLUMN_COUNTRY_NAME + " VARCHAR, " + COLUMN_TEMPERATURE + " VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, contact.getFirstName());
        contentValues.put(COLUMN_COUNTRY_NAME, contact.getCountryName());
        contentValues.put(COLUMN_TEMPERATURE, contact.getTemperature());
        database.insert(TABLE_NAME, null, contentValues);
        database.close();
    }

    public void insertRecordAlternate(ContactModel contact) {
        database = this.getReadableDatabase();
        database.execSQL("INSERT INTO " + TABLE_NAME + "(" + COLUMN_FIRST_NAME + "," + COLUMN_COUNTRY_NAME + ") VALUES('" + contact.getFirstName() + "','" + contact.getCountryName() + "')");
        database.close();
    }



    public ArrayList<ContactModel> getAllRecords() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
        ContactModel contactModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                contactModel = new ContactModel();
                contactModel.setID(cursor.getString(0));
                contactModel.setFirstName(cursor.getString(1));
                contactModel.setCountryName(cursor.getString(2));
                contactModel.setTemperature(cursor.getString(3));

                contacts.add(contactModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }


    public ArrayList<ContactModel> getAllRecordsAlternate() {
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<ContactModel> contacts = new ArrayList<ContactModel>();
        ContactModel contactModel;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();

                contactModel = new ContactModel();
                contactModel.setID(cursor.getString(0));
                contactModel.setFirstName(cursor.getString(1));
                contactModel.setCountryName(cursor.getString(2));

                contacts.add(contactModel);
            }
        }
        cursor.close();
        database.close();

        return contacts;
    }

    public void updateRecordTemperature(ContactModel contact){
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, contact.getFirstName());
        contentValues.put(COLUMN_COUNTRY_NAME, contact.getCountryName());
        contentValues.put(COLUMN_TEMPERATURE, contact.getTemperature());
        Log.i(TAG,contact.getFirstName() + contact.getCountryName());
        database.update(TABLE_NAME, contentValues, COLUMN_FIRST_NAME + " = ?" + " AND " + COLUMN_COUNTRY_NAME + " = ?",
               new String[]{contact.getFirstName(), contact.getCountryName()});
        database.close();
    }

    public void updateRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, contact.getFirstName());
        contentValues.put(COLUMN_COUNTRY_NAME, contact.getCountryName());
        contentValues.put(COLUMN_TEMPERATURE, contact.getTemperature());
        database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public void updateRecordAlternate(ContactModel contact) {
        database = this.getReadableDatabase();
        database.execSQL("update " + TABLE_NAME + " set " + COLUMN_FIRST_NAME + " = '" + contact.getFirstName() + "', " +
                COLUMN_COUNTRY_NAME + " = '" + contact.getCountryName() + "' where " + COLUMN_ID + " = '" + contact.getID() + "'");
        database.close();
    }

    public void deleteAllRecords() {
        database = this.getReadableDatabase();
        database.delete(TABLE_NAME, null, null);
        database.close();
    }

    public void deleteAllRecordsAlternate() {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + TABLE_NAME);
        database.close();
    }

    public void deleteRecord(ContactModel contact) {
        database = this.getReadableDatabase();
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{contact.getID()});
        database.close();
    }

    public void deleteRecordAlternate(ContactModel contact) {
        database = this.getReadableDatabase();
        database.execSQL("delete from " + TABLE_NAME + " where " + COLUMN_ID + " = '" + contact.getID() + "'");
        database.close();
    }

    public ArrayList<String> getAllTableName()
    {
        database = this.getReadableDatabase();
        ArrayList<String> allTableNames=new ArrayList<String>();
        Cursor cursor=database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'",null);
        if(cursor.getCount()>0)
        {
            for(int i=0;i<cursor.getCount();i++)
            {
                cursor.moveToNext();
                allTableNames.add(cursor.getString(cursor.getColumnIndex("name")));
            }
        }
        cursor.close();
        database.close();
        return allTableNames;
    }

}
