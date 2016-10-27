package in.doris.sharq.db.datasources;

import in.doris.sharq.db.beans.NamelistBean;
import in.doris.sharq.db.contracts.DbContract.Namelist;
import in.doris.sharq.db.dbHelper.SharqDbHelper;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NamelistDataSource {

	// Database fields
    private SQLiteDatabase database;
    private SharqDbHelper dbHelper;
    final String TAG = "NAMELIST";
    private String[] allColumns = { 
    		Namelist.COLUMN_ID,
    		Namelist.COLUMN_NAME,
    		Namelist.COLUMN_PHONE,
    		Namelist.COLUMN_PLACE,
    		Namelist.COLUMN_CAT,
    		Namelist.COLUMN_STS,
    		Namelist.COLUMN_REMARK,
    		Namelist.COLUMN_STAR
    };

    public NamelistDataSource(Context context) {
        dbHelper = SharqDbHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public NamelistBean createNamelist(NamelistBean namelistBeanObj) {
        ContentValues values = new ContentValues();
        open();
        values.put(Namelist.COLUMN_NAME, namelistBeanObj.getName());
        values.put(Namelist.COLUMN_PHONE, namelistBeanObj.getPhone());
        values.put(Namelist.COLUMN_PLACE, namelistBeanObj.getPlace());
        values.put(Namelist.COLUMN_CAT, namelistBeanObj.getCat());
        values.put(Namelist.COLUMN_STS, namelistBeanObj.getSts());
        values.put(Namelist.COLUMN_REMARK, namelistBeanObj.getRemark());
        values.put(Namelist.COLUMN_STAR, namelistBeanObj.getStar());
        long insertId = database.insert(Namelist.TABLE_NAME, null,
                        values);
        Cursor cursor = database.query(Namelist.TABLE_NAME,
                        allColumns, Namelist.COLUMN_ID + " = " + insertId, null,
                        null, null, null);
        cursor.moveToFirst();
        NamelistBean newNamelist = cursorToNamelist(cursor);
        Log.d(TAG, "namelist successfully created");
        cursor.close();
        return newNamelist;
    }

    public void deleteNamelist(NamelistBean namelistBeanObj) {
        long id = namelistBeanObj.getId();
        System.out.println("Namelist deletion initiated with id: " + id);
        database.delete(Namelist.TABLE_NAME, Namelist.COLUMN_ID
                + " = " + id, null);
        System.out.println("Namelist deleted with id: " + id);
    }

    public List<NamelistBean> getAllNamelists() {
        open();
        List<NamelistBean> namelists = new ArrayList<NamelistBean>();

        Cursor cursor = database.query(Namelist.TABLE_NAME,
                        allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	NamelistBean namelist = cursorToNamelist(cursor);
        	namelists.add(namelist);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
//        for(int i = 0; i < namelists.size(); i++) {
//            System.out.println(namelists.get(i).getId());
//        }
        return namelists;
    }
    
    public NamelistBean updateNamelist(NamelistBean namelistBeanObj) {
        ContentValues values = new ContentValues();
        values.put(Namelist.COLUMN_NAME, namelistBeanObj.getName());
        values.put(Namelist.COLUMN_PHONE, namelistBeanObj.getPhone());
        values.put(Namelist.COLUMN_PLACE, namelistBeanObj.getPlace());
        values.put(Namelist.COLUMN_CAT, namelistBeanObj.getCat());
        values.put(Namelist.COLUMN_STS, namelistBeanObj.getSts());
        values.put(Namelist.COLUMN_REMARK, namelistBeanObj.getRemark());
        values.put(Namelist.COLUMN_STAR, namelistBeanObj.getStar());
        String[] whereArgs = {namelistBeanObj.getId().toString()} ;
        long updateId = database.update(Namelist.TABLE_NAME, values, Namelist.COLUMN_ID + " = ?", whereArgs);
        Cursor cursor = database.query(Namelist.TABLE_NAME,
                        allColumns, Namelist.COLUMN_ID + " = " + updateId, null,
                        null, null, null);
        cursor.moveToFirst();
        namelistBeanObj = cursorToNamelist(cursor);
        cursor.close();
        return namelistBeanObj;
    }
    public NamelistBean updateNamelistAlternate(NamelistBean  namelistBeanObj) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Namelist.COLUMN_STS, namelistBeanObj.getSts());
        String[] whereArgs = {namelistBeanObj.getId().toString()} ;
        long updateId = database.update(Namelist.TABLE_NAME, values, Namelist.COLUMN_ID + " = ?", whereArgs);
        Cursor cursor = database.query(Namelist.TABLE_NAME,
                allColumns, Namelist.COLUMN_ID + " = " + updateId, null,
                null, null, null);
        cursor.moveToFirst();
        namelistBeanObj = cursorToNamelist(cursor);
        cursor.close();
        return namelistBeanObj;
//        database.execSQL("UPDATE " + Namelist.TABLE_NAME + " SET " + Namelist.COLUMN_STAR + " = '" + namelistBeanObj.getStar() + "', " + "' WHERE " + Namelist.COLUMN_ID + " = " + namelistBeanObj.getId() + "'");
//        database.close();
    }

    private NamelistBean cursorToNamelist(Cursor cursor) {
    	NamelistBean namelist = new NamelistBean();
    	namelist.setId(cursor.getLong(0));
    	namelist.setName(cursor.getString(1));
    	namelist.setPhone(cursor.getLong(2));
    	namelist.setPlace(cursor.getString(3));
    	namelist.setCat(cursor.getString(4));
    	namelist.setSts(cursor.getString(5));
    	namelist.setRemark(cursor.getString(6));
    	namelist.setStar(cursor.getString(7));
        return namelist;
    }
}
