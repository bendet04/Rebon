package id.bhinneka.rebon.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by bendet on 28/08/17.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Citros.db";
    public static final String TABLE_NAME = "pesanan";
    public static final String COLUMN_KODE = "kode";
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_TELEPON = "telepon";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table pesanan "+
                "(kode text primary key, nama text, telepon text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS pesanan");
        onCreate(sqLiteDatabase);
    }

    public boolean insertPesanan (String kode){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("kode", kode);
        db.insert("pesanan", null, contentValues);
        return true;
    }

    public Cursor getDataByKode(String kode){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM pesanan WHERE kode = "+kode+"", null);
        return res;
    }

    public Cursor getDataAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM pesanan", null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public Integer deleteContact (String kode) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("pesanan",
                "kode=?",
                new String[] { kode });
    }

    public ArrayList<String> getAllPesanan() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from pesanan", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_KODE)));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

}
