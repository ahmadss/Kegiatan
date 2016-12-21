package com.catatankegiatan.kegiatan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ahmad on 28/11/2016.
 */
public class KegiatanDbAdapter  {
    private static final String DATABASE_NAME = "kegiatan.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_KEGIATAN = "kegiatan";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PESAN = "pesan";
    public static final String COLUMN_KATEGORI = "kategori";
    public static final String COLUMN_DATE = "date";

    private String[] semuaColumns = {COLUMN_ID, COLUMN_TITLE, COLUMN_PESAN, COLUMN_KATEGORI, COLUMN_DATE};

    public static final String CREATE_TABLE_KEGIATAN = "create table "+ TABLE_KEGIATAN + " ( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_PESAN + " text not null, "
            + COLUMN_KATEGORI + " text not null, "
            + COLUMN_DATE + ");";

    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    private KegiatanDbHelper kegiatanDbHelper;

    public KegiatanDbAdapter(Context ctx){
        context = ctx;
    }

    public KegiatanDbAdapter open() throws android.database.SQLException {
        kegiatanDbHelper = new KegiatanDbHelper(context);
        sqLiteDatabase = kegiatanDbHelper.getWritableDatabase();
        return this;
    }

    public Catatan createKegiatan(String judul, String pesan, Catatan.Kategori kategori){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, judul);
        contentValues.put(COLUMN_PESAN, pesan);
        contentValues.put(COLUMN_KATEGORI, kategori.name());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentValues.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis());
        }

        long insertId = sqLiteDatabase.insert(TABLE_KEGIATAN, null, contentValues);
        Cursor cursor = sqLiteDatabase.query(TABLE_KEGIATAN, semuaColumns, COLUMN_ID+ " = "+ insertId, null, null, null, null);
        cursor.moveToFirst();
        Catatan newCatatan = cursorKeCatatan(cursor);
        cursor.close();
        return newCatatan;
//        ContentValues contentValues= new ContentValues();
    }

    public long updateKegiatan(long idToUpdate, String newJudul, String newCatatan, Catatan.Kategori kategory){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, newJudul);
        contentValues.put(COLUMN_PESAN, newCatatan);
        contentValues.put(COLUMN_KATEGORI, kategory.name());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentValues.put(COLUMN_DATE, Calendar.getInstance().getTimeInMillis());
        }

        return sqLiteDatabase.update(TABLE_KEGIATAN, contentValues, COLUMN_ID+" = "+idToUpdate, null);
    }

    public long deleteKegiatan(long idKegiatan){
        return sqLiteDatabase.delete(TABLE_KEGIATAN, COLUMN_ID+ " = "+idKegiatan, null);
    }

    public void close(){
        kegiatanDbHelper.close();
    }

    public ArrayList<Catatan> getAllKegiatan(){
        ArrayList<Catatan> cataten = new ArrayList<Catatan>();
        Cursor cursor = sqLiteDatabase.query(TABLE_KEGIATAN, semuaColumns, null, null, null, null, null);

        for (cursor.moveToLast();!cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Catatan catatan = cursorKeCatatan(cursor);
            cataten.add(catatan);
        }

        cursor.close();
        return cataten;
    }

    private Catatan cursorKeCatatan(Cursor cursor){
        Catatan newCatatan = new Catatan(cursor.getString(1), cursor.getString(2), Catatan.Kategori.valueOf(cursor.getString(3)), cursor.getLong(0),cursor.getLong(4));

        return newCatatan;
    }

    private static class KegiatanDbHelper extends SQLiteOpenHelper {

        KegiatanDbHelper(Context ctx) {
            super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        }

         @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_TABLE_KEGIATAN);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.w(KegiatanDbHelper.class.getName(), "Upgrading database from version "+ oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_KEGIATAN);
            onCreate(db);
        }


    }
}
